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