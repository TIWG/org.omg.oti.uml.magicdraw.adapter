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

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object addPackageImportsForNestedPackages {

  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Profile,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    addPackageImportsForNestedPackages( p, umlUtil, selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage) } ) )
    Success( None )
  }
      
  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Package,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    addPackageImportsForNestedPackages( p, umlUtil, selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage) } ) )    
    Success( None )
  }
       
  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Profile, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {
        
    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    addPackageImportsForNestedPackages( p, umlUtil, selection.toSet selectByKindOf ( { case pkg: Package => umlPackage( pkg ) } ) )    
    Success( None )
  }
    
  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Package, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {
        
    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    addPackageImportsForNestedPackages( p, umlUtil, selection.toSet selectByKindOf ( { case pkg: Package => umlPackage( pkg ) } ) )    
    Success( None )
  }
    
  def addPackageImportsForNestedPackages(
      p: Project,
      umlUtil: MagicDrawUMLUtil, 
      pkgs: Iterable[UMLPackage[MagicDrawUML]] ): Unit = {
    
    import umlUtil._
    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()
    
    val f = p.getElementsFactory
    
    val allPkgs = pkgs ++ pkgs.flatMap (_.allNestedPackages)
    allPkgs foreach { pkg =>
      val mdPkg = pkg.getMagicDrawPackage
      val importedPackages = pkg.packageImport.flatMap(_.importedPackage)
      val nestedPackages2Import = pkg.nestedPackage -- importedPackages
      nestedPackages2Import foreach { npkg =>
          val i = f.createPackageImportInstance
          i.setImportingNamespace(mdPkg)
          i.setImportedPackage(npkg.getMagicDrawPackage)
        guiLog.log(s"Add import: ${mdPkg.getQualifiedName} => ${npkg.qualifiedName.get}")
      }
    }
    guiLog.log("Done")
  }
}