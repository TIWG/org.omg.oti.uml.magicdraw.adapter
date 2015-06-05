/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Success
import scala.util.Try
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
import org.omg.oti._
import org.omg.oti.api._
import org.omg.oti.operations._
import org.omg.oti.magicdraw._
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import org.omg.oti.changeMigration.Metamodel
import com.nomagic.magicdraw.core.ApplicationEnvironment
import java.io.File
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.XMLResource
import scala.util.Failure
import com.nomagic.magicdraw.uml.UUIDRegistry
import com.nomagic.magicdraw.core.utils.ChangeElementID
import com.nomagic.task.RunnableWithProgress
import com.nomagic.task.ProgressStatus
import com.nomagic.magicdraw.ui.MagicDrawProgressStatusRunner
import com.nomagic.magicdraw.core.ProjectUtilitiesInternal
import java.util.UUID
import com.nomagic.ci.persistence.local.spi.localproject.LocalPrimaryProject
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes.MainToolbarMenuAction
import com.nomagic.magicdraw.core.project.ProjectsManager
import javax.swing.JFileChooser
import com.nomagic.magicdraw.actions.ActionsID
import com.nomagic.magicdraw.actions.ActionsProvider
import gov.nasa.jpl.magicdraw.enhanced.migration.LocalModuleMigrationInterceptor
import javax.swing.filechooser.FileFilter
import org.omg.oti.api.UMLStereotype
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object nameTest {

  def doit( p: Project, ev: ActionEvent, script: MainToolbarMenuAction ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val ap = ActionsProvider.getInstance
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedElements = getMDBrowserSelectedElements map { e => umlElement( e ) }
    selectedElements foreach { e =>
      val mdE = e.getMagicDrawElement
      
      guiLog.log( s" ID=${e.id}" )

      val tv = e.tagValues
      guiLog.log( s"tv: ${tv.size}" )
      tv foreach {
        case ( tvp, tvv ) =>
          guiLog.log( s" => ${tvp.qualifiedName.get}: ${tvv}" )

      }
  
      val mdIS = Option.apply( mdE.getAppliedStereotypeInstance ) 
      guiLog.log( s" mdID=${mdE.getID}: mdIS=${mdIS.isDefined} =${mdIS}" )

      mdIS match {
        case None =>
          guiLog.log( s" no AppliedStereotypeInstance!" )

        case Some( is ) =>
          guiLog.log( s" AppliedStereotypeInstance: ${is.getSlot.size} slots" )

          for {
            s <- is.getSlot
            p = s.getDefiningFeature match { case p: Uml#Property => umlProperty( p ) }
            v = umlValueSpecification( s.getValue ).toSeq
          } {
            guiLog.log( s" => ${p.qualifiedName.get}: ${s.getValue}" )
            guiLog.log( s" => ${p.qualifiedName.get}: ${v}" )
          }
      }
      
      
      val eMetaclass = mdE.getClassType
      val appliedS = StereotypesHelper.getStereotypes( mdE ).toSet[Uml#Stereotype].toList.sortBy(_.qualifiedName.get)
      System.out.println(s"Applied stereotypes: ${appliedS.size}")
      appliedS.foreach{ s => 
        val metaProperties = StereotypesHelper.getExtensionMetaProperty( s, true ) filter { p =>
          val pMetaclass = StereotypesHelper.getClassOfMetaClass( p.getType.asInstanceOf[Uml#Class] )
          eMetaclass == pMetaclass || StereotypesHelper.isSubtypeOf( pMetaclass, eMetaclass )
        }
        val sGeneral = getAllGeneralStereotypes( s ).toList.sortBy(_.qualifiedName.get)   
        System.out.println(s"Applied: ${s.qualifiedName.get} with ${metaProperties.size} meta-properties, ${sGeneral.size} general stereotypes")
        metaProperties.foreach{p => System.out.println(s"meta-property: ${p.getQualifiedName}")}
             
        sGeneral.foreach{ sg =>
          val mdSG = sg.getMagicDrawElement.asInstanceOf[Stereotype]
          val gmetaProperties = StereotypesHelper.getExtensionMetaProperty( mdSG, true ) filter { p =>
            val pMetaclass = StereotypesHelper.getClassOfMetaClass( p.getType.asInstanceOf[Uml#Class] )
            eMetaclass == pMetaclass || StereotypesHelper.isSubtypeOf( pMetaclass, eMetaclass )
          }
          System.out.println(s"General: ${sg.qualifiedName.get} with ${gmetaProperties.size} meta-properties")
          gmetaProperties.foreach{p => System.out.println(s"general meta-property: ${p.getQualifiedName}")}
        }
          
      }
      
      e match { 
        case s: UMLStereotype[Uml] =>
          val mdS = s.getMagicDrawElement.asInstanceOf[Stereotype]
          val baseClasses1 = StereotypesHelper.getBaseClasses( mdS, false )          
          System.out.println(s" baseClasses1: ${baseClasses1.size}")
          baseClasses1.toList.sortBy(_.getQualifiedName).foreach{p => System.out.println(s"baseClass1: ${p.getQualifiedName}")}
          
          val baseClasses2 = StereotypesHelper.getBaseClasses( mdS, true ) 
          System.out.println(s" baseClasses2: ${baseClasses2.size}")
          baseClasses2.toList.sortBy(_.getQualifiedName).foreach{p => System.out.println(s"baseClass2: ${p.getQualifiedName}")}
          
          val metaProperties = StereotypesHelper.getExtensionMetaProperty( mdS, false ) 
          System.out.println(s" metaProperties: ${metaProperties.size}")
          metaProperties.toList.sortBy(_.getQualifiedName).foreach{p => System.out.println(s"meta property: ${p.getQualifiedName}")}
        case _ => ()
      }
    }

    Success( None )
  }

  def getMDBrowserSelectedElements(): Set[Element] = {
    val project = Application.getInstance().getProjectsManager().getActiveProject()
    if ( null == project )
      return Set()

    val tab = project.getBrowser().getActiveTree()
    val elementFilter: ( Node => Option[Element] ) = { n => if ( n.getUserObject().isInstanceOf[Element] ) Some( n.getUserObject().asInstanceOf[Element] ) else None }
    val elements = tab.getSelectedNodes().map( elementFilter( _ ) ) filter ( _.isDefined ) map ( _.get )
    elements.toSet
  }
}