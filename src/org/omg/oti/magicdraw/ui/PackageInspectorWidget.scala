package org.omg.oti.magicdraw.ui

import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import javax.swing.JOptionPane
import scala.collection.JavaConversions._
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.dialogs.specifications.SpecificationDialogManager
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin
import gov.nasa.jpl.dynamicScripts.magicdraw.designations.MagicDrawElementKindDesignation
import gov.nasa.jpl.dynamicScripts.magicdraw.specificationDialog.SpecificationComputedComponent
import gov.nasa.jpl.dynamicScripts.magicdraw.ui.nodes._
import gov.nasa.jpl.dynamicScripts.magicdraw.utils._

import org.omg.oti._
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil

object PackageInspectorWidget {

  import ComputedDerivedWidgetHelper._
  
  def packageOperationWidget(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element,
    f: Function1[UMLPackage[MagicDrawUML], Iterable[UMLPackageableElement[MagicDrawUML]]]): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {

    implicit val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._

    umlElement( e ) match {
      case pkg: UMLPackage[Uml] =>         
        Success( createGroupTableUIPanelForPackageableElements( derived, f(pkg) ) )

      case x =>
        Failure( new IllegalArgumentException( s"Not a package; instead got a ${x.xmiType}" ) )
    }
  }
  
  def nonImportedNestedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.nonImportedNestedPackages) )
  
  def allNestedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allNestedPackages) )
  
  def allNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allNestingPackagesTransitively) )
  
  def allDirectlyImportedPackagesIncludingNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allDirectlyImportedPackagesIncludingNestingPackagesTransitively) )
  
  def allApplicableStereotypes(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allApplicableStereotypes) )
  
  def containingProfile(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.containingProfile) )
  
  def allDirectlyAppliedProfilesExceptNestingPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allDirectlyAppliedProfilesExceptNestingPackages ) )
  
  def allDirectlyAppliedProfilesIncludingNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allDirectlyAppliedProfilesIncludingNestingPackagesTransitively ) )
  
  def allDirectlyVisibleMembersTransitivelyAccessibleExceptNestingPackagesAndAppliedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allDirectlyVisibleMembersTransitivelyAccessibleExceptNestingPackagesAndAppliedProfiles ) )
  
  def allIndirectlyAppliedProfilesIncludingNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allIndirectlyAppliedProfilesIncludingNestingPackagesTransitively) )
  
  def allForwardReferencesToImportablePackageableElementsFromAllOwnedElementsTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allForwardReferencesToImportablePackageableElementsFromAllOwnedElementsTransitively) )

  def allIndirectlyVisibleMembersTransitivelyAccessibleFromNestingPackagesAndAppliedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      packageOperationWidget( project, ev, derived, ek, e, (_.allIndirectlyVisibleMembersTransitivelyAccessibleFromNestingPackagesAndAppliedProfiles) )
  
}