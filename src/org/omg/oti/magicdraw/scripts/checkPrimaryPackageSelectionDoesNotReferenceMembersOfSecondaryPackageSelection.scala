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

import org.omg.oti._
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults

/**
 * Select N packages in a diagram
 * Invoke this dynamic script from the context menu of 1 of the packages.
 *
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object checkPrimaryPackageSelectionDoesNotReferenceMembersOfSecondaryPackageSelection {

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

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    val secondaryPackages = (selection.toSet - triggerView).toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage) } )
    
    checkPrimaryPackageSelectionDoesNotReferenceMembersOfSecondaryPackageSelection( umlUtil, triggerElement, secondaryPackages )
    
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
    
    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    val secondaryPackages = (selection.toSet - triggerView).toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage) } )
    
    checkPrimaryPackageSelectionDoesNotReferenceMembersOfSecondaryPackageSelection( umlUtil, triggerElement, secondaryPackages )
    
    Success( None )
  }
      
      
  def checkPrimaryPackageSelectionDoesNotReferenceMembersOfSecondaryPackageSelection(
      umlUtil: MagicDrawUMLUtil, 
      primaryPkg: UMLPackage[MagicDrawUML], 
      secondaryPkgs: Iterable[UMLPackage[MagicDrawUML]] ): Unit = {
    
    import umlUtil._
    val app = Application.getInstance()
    val guiLog = app.getGUILog()

    val primaryAccessible = primaryPkg.allVisibleMembersTransitively
    val primaryAndAppliedProfiles = Set(primaryPkg) ++ primaryPkg.allIndirectlyAppliedProfilesIncludingNestingPackagesTransitively
    val primaryScope = primaryPkg.allDirectlyImportedPackagesIncludingNestingPackagesTransitively ++ primaryPkg.allImportedPackagesTransitively ++ primaryAndAppliedProfiles
    
    val secondaryExceptPrimaryOrImported = secondaryPkgs.toSet -- primaryScope
    guiLog.log(s"secondaryExceptPrimaryOrImported: ${secondaryExceptPrimaryOrImported.size}")
    secondaryExceptPrimaryOrImported.foreach { p => guiLog.log(s" - ${p.qualifiedName.get}") }
    
    val secondaryContents = secondaryExceptPrimaryOrImported.flatMap (_.allOwnedElements.selectByKindOf { case pe: UMLPackageableElement[Uml] => pe } toSet) toSet
    val secondaryVisible = secondaryExceptPrimaryOrImported.flatMap (_.allVisibleMembersTransitively) toSet
        
    val shouldBeImported = (secondaryContents & secondaryVisible) & primaryAccessible
    guiLog.log(s"OK?: ${shouldBeImported.isEmpty}")
    
    shouldBeImported.foreach { e =>
      val mdE = e.getMagicDrawElement
      val link=s"${mdE.getHumanType}: ${mdE.getHumanName}"
      guiLog.addHyperlinkedText(s" should be imported: <A>${link}</A>", Map(link-> new SelectInContainmentTreeRunnable( mdE ) ) )
    }

  }
}