/*
 *
 *  License Terms
 *
 *  Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 *  U.S. Government sponsorship acknowledged.
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *
 *   *   Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *   *   Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the
 *       distribution.
 *
 *   *   Neither the name of Caltech nor its operating division, the Jet
 *       Propulsion Laboratory, nor the names of its contributors may be
 *       used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import java.io.File

import scala.collection.JavaConversions.mapAsJavaMap
import scala.collection.JavaConversions.seqAsJavaList
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.core.utils.ChangeElementID
import com.nomagic.magicdraw.ui.MagicDrawProgressStatusRunner
import com.nomagic.magicdraw.uml.UUIDRegistry
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.task.ProgressStatus
import com.nomagic.task.RunnableWithProgress
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element

import org.eclipse.emf.common.util.URI
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import org.omg.oti.changeMigration.Metamodel

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object changePackageExtentIDs {

  def doit(
    project: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.MainToolbarMenuAction ): Try[Option[MagicDrawValidationDataResults]] =
    doit( project )

  def doit( project: Project ): Try[Option[MagicDrawValidationDataResults]] = {
    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( project )

    val mdInstallDir = new File( ApplicationEnvironment.getInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )
    val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
    require( otiDir.exists && otiDir.isDirectory )
    val migrationMM = Metamodel( otiDir )

    val dir = new File( project.getDirectory )
    require( dir.exists && dir.isDirectory() )
    val migrationF = new File( dir, project.getName+".migration.xmi" )
    require( migrationF.exists && migrationF.canRead )
    val migrationURI = URI.createFileURI( migrationF.getAbsolutePath )

    migrationMM.loadOld2NewIDMappingResource( migrationURI ) match {
      case Failure( t ) => Failure( t )
      case Success( old2newIDmigration ) =>
        val entries = old2newIDmigration.getEntries
        guiLog.log( s" Loaded ${entries.size} old2new ID migration entries" )

        val resetElements = entries map { entry => project.getElementByID( entry.getOldID.get ) }
        val resetMap = entries flatMap { entry =>
          val oldId = entry.getOldID.get
          val newId = entry.getNewID.get
          Option.apply( project.getElementByID( oldId ) ) match {
            case Some( e: Element ) =>
              val uuid = UUIDRegistry.getUUID( e )
              if ( oldId == newId )
                None
              else
                Seq( oldId -> newId, uuid -> uuid )
            case _ =>
              None
          }
        } toMap;

        val idMap = new java.util.HashMap[String, String]()
        for { ( key, value ) <- resetMap } { idMap.put(key, value ) }
        
        val runnable = new RunnableWithProgress() {
          def run( progressStatus: ProgressStatus ): Unit = {
            ChangeElementID.resetIDS( project, resetElements, idMap, progressStatus )
          }
        }

        MagicDrawProgressStatusRunner.runWithProgressStatus( runnable, s"Change XMI:IDs", true, 0 )

        if ( entries.size > 2000 ) {
          entries foreach { entry => System.out.println( s" new=${entry.getNewID.get} => old=${entry.getOldID.get}" ) }
        } else {
          entries foreach { entry =>
            val id = entry.getNewID.get
            val e = project.getElementByID( id )
            guiLog.addHyperlinkedText(
              s" new=<A>${id}</A> <= old=${entry.getOldID.get}",
              Map( id -> new SelectInContainmentTreeRunnable( e ) ) )
          }
        }

        guiLog.log( s"Done! (${entries.size} old2new ID migrations)" )
        Success( None )
    }
  }
}