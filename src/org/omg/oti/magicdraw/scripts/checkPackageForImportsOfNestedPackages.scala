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
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement
import com.nomagic.magicdraw.uml.symbols.PresentationElement
import com.nomagic.magicdraw.uml.symbols.shapes.PackageView
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
import org.omg.oti.api._
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import org.omg.oti.magicdraw.actions.AddMissingImportFromParentPackage
import com.nomagic.actions.NMAction

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object checkPackageForImportsOfNestedPackages {

  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Profile,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedPackages = selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage ) } )
    checkPackageForImportsOfNestedPackages( p, selectedPackages )
  }

  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Package,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedPackages = selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage ) } )
    checkPackageForImportsOfNestedPackages( p, selectedPackages )
  }

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Package, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedPackages = selection.toSet selectByKindOf ( { case pkg: Package => umlPackage( pkg ) } )
    checkPackageForImportsOfNestedPackages( p, selectedPackages )
  }

  def checkPackageForImportsOfNestedPackages(
    p: Project,
    pkgs: Iterable[UMLPackage[MagicDrawUML]] )( implicit umlUtil: MagicDrawUMLUtil ): Try[Option[MagicDrawValidationDataResults]] = {

    import umlUtil._
    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()

    guiLog.log( s"Checking ${pkgs.size} package(s)..." )

    val elementMessages = for {
      pkg <- pkgs ++ pkgs.flatMap( _.allNestedPackages )
      missingImport <- pkg.nonImportedNestedPackages
      actions = List( AddMissingImportFromParentPackage() )
    } yield ( ( missingImport.getMagicDrawPackage -> ( s"Add import from parent package, ${pkg.name.get}", actions ) ) )

    if ( elementMessages.isEmpty ) {
      guiLog.log( s"OK" )
      Success( None )
    }
    else {
      guiLog.log( s"Found ${elementMessages.size} missing imports!" )
      makeMDIllegalArgumentExceptionValidation(
        p,
        s"*** ${elementMessages.size} missing imports of nested packages ***",
        elementMessages.toMap[Element, ( String, List[NMAction] )],
        "*::MagicDrawOTIValidation",
        "*::UnresolvedCrossReference" )
    }
  }
}