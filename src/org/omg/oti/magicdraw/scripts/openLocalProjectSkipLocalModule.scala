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

import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.actions.ActionsID
import com.nomagic.magicdraw.actions.ActionsProvider
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project

import org.eclipse.emf.common.util.URI
import org.omg.oti.changeMigration.Metamodel

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes.MainToolbarMenuAction
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import gov.nasa.jpl.magicdraw.enhanced.migration.LocalModuleMigrationInterceptor

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object openLocalProjectSkipLocalModule {

  def doit( project: Project, ev: ActionEvent, script: MainToolbarMenuAction ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val ap = ActionsProvider.getInstance
    val mainToolbarActionMgr = ap.getMainToolbarActions
    val openProjectAction = mainToolbarActionMgr.getActionFor( ActionsID.OPEN_PROJECT )

    val mdInstallDir = new File( ApplicationEnvironment.getInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )
    val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
    require( otiDir.exists && otiDir.isDirectory )
    val migrationMM = Metamodel( otiDir )

    val guiLog = a.getGUILog()
    guiLog.clearLog()

    MigrationHelper.chooseMigrationFile match {
      case Some( migrationFile ) =>
        require( migrationFile.exists && migrationFile.canRead )
        val migrationURI = URI.createFileURI( migrationFile.getAbsolutePath )

        migrationMM.loadOld2NewIDMappingResource( migrationURI ) match {
          case Failure( t ) => Failure( t )
          case Success( old2newIDmigration ) =>
            val entries = old2newIDmigration.getEntries
            guiLog.log( s" Loaded ${entries.size} old2new ID migration entries" )

            require( old2newIDmigration.getModelIdentifier.isDefined )
            val oldModuleFile = new File( mdInstallDir, old2newIDmigration.getModelIdentifier.get )
            require( oldModuleFile.exists && oldModuleFile.canRead )

            LocalModuleMigrationInterceptor.clearForceSkipLocalModules
            LocalModuleMigrationInterceptor.addForceSkipLocalModule( URI.createFileURI( oldModuleFile.getAbsolutePath ) )

            try {
              openProjectAction.actionPerformed( ev )
            }
            finally {
              LocalModuleMigrationInterceptor.clearForceSkipLocalModules
            }
        }
      case None =>
        System.out.println( s"openLocalProjectSkipLocalModule: cancelled" )
    }
    Success( None )
  }
}