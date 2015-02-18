package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Success
import scala.util.Try
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project
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
import org.omg.oti.magicdraw.actions.SelectInContainmentTreeAction
import org.omg.oti.validation._

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object checkEachSelectedPackageReferencesOnlyAccessibleMembers {

  def doitExceptNestingPackagesAndAppliedProfiles(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Profile,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    checkEachSelectedPackageReferencesOnlyAccessibleMembersExceptNestingPackagesAndAppliedProfiles(
      p,
      selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage ) } ) )
  }
  
  def doitIncludingNestingPackagesAndAppliedProfiles(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Profile,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    checkEachSelectedPackageReferencesOnlyAccessibleMembersIncludingNestingPackagesAndAppliedProfiles(
      p,
      selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage ) } ) )
  }

  def doitExceptNestingPackagesAndAppliedProfiles(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Package,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    checkEachSelectedPackageReferencesOnlyAccessibleMembersExceptNestingPackagesAndAppliedProfiles(
      p,
      selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage ) } ) )
  }

  def doitIncludingNestingPackagesAndAppliedProfiles(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Package,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    checkEachSelectedPackageReferencesOnlyAccessibleMembersIncludingNestingPackagesAndAppliedProfiles(
      p,
      selection.toSet selectByKindOf ( { case pv: PackageView => umlPackage( pv.getPackage ) } ) )
  }

  def checkEachSelectedPackageReferencesOnlyAccessibleMembersExceptNestingPackagesAndAppliedProfiles(
    p: Project,
    pkgs: Iterable[UMLPackage[MagicDrawUML]] )( implicit _umlUtil: MagicDrawUMLUtil ): Try[Option[MagicDrawValidationDataResults]] = {

    import _umlUtil._
    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()
    
    val rules = new UMLPackageableElementRules[Uml, MagicDrawUMLUtil] {
      implicit val umlOps = _umlUtil
    }

    implicit val referencedButNotAccessibleValidationConstructor = rules.defaultReferencedButNotAccessibleConstructor _

    val elementMessages = ( for {
      pkg <- pkgs
      _ = guiLog.log( s"Analyzing ${pkg.qualifiedName.get}" )
      actions = List( SelectInContainmentTreeAction( pkg.getMagicDrawPackage ) )
      violation <- rules.findNonAccessibleButReferencedImportablePackabeableElementsExceptNestingPackagesAndAppliedProfiles( pkg )
    } yield (
      violation.referencedButNotAccessible.getMagicDrawElement ->
      ( s"unaccessible cross-reference from ${pkg.qualifiedName.get}", actions ) ) ) toMap

    if ( elementMessages.nonEmpty ) {
      guiLog.log( s"Error! -- found ${elementMessages.size} unaccessible cross-references!" )
      makeMDIllegalArgumentExceptionValidation(
        p,
        s"*** ${elementMessages.size} unaccessible cross-references ***",
        elementMessages,
        "*::MagicDrawOTIValidation",
        "*::UnresolvedCrossReference" )
    }
    else {
      guiLog.log( s"Success! -- All packages reference only accessible members" )
      Success( None )
    }
  }
  
  def checkEachSelectedPackageReferencesOnlyAccessibleMembersIncludingNestingPackagesAndAppliedProfiles(
    p: Project,
    pkgs: Iterable[UMLPackage[MagicDrawUML]] )( implicit _umlUtil: MagicDrawUMLUtil ): Try[Option[MagicDrawValidationDataResults]] = {

    import _umlUtil._
    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()
    
    val rules = new UMLPackageableElementRules[Uml, MagicDrawUMLUtil] {
      implicit val umlOps = _umlUtil
    }

    implicit val referencedButNotAccessibleValidationConstructor = rules.defaultReferencedButNotAccessibleConstructor _

    val elementMessages = ( for {
      pkg <- pkgs
      _ = guiLog.log( s"Analyzing ${pkg.qualifiedName.get}" )
      actions = List( SelectInContainmentTreeAction( pkg.getMagicDrawPackage ) )
      violation <- rules.findNonAccessibleButReferencedImportablePackabeableElementsIncludingNestingPackagesAndAppliedProfiles( pkg )
    } yield (
      violation.referencedButNotAccessible.getMagicDrawElement ->
      ( s"unaccessible cross-reference from ${pkg.qualifiedName.get}", actions ) ) ) toMap

    if ( elementMessages.nonEmpty ) {
      guiLog.log( s"Error! -- found ${elementMessages.size} unaccessible cross-references!" )
      makeMDIllegalArgumentExceptionValidation(
        p,
        s"*** ${elementMessages.size} unaccessible cross-references ***",
        elementMessages,
        "*::MagicDrawOTIValidation",
        "*::UnresolvedCrossReference" )
    }
    else {
      guiLog.log( s"Success! -- All packages reference only accessible members" )
      Success( None )
    }
  }
}