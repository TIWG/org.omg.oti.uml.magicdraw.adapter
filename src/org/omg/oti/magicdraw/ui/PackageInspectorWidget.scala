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
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin
import gov.nasa.jpl.dynamicScripts.magicdraw.designations.MagicDrawElementKindDesignation
import gov.nasa.jpl.dynamicScripts.magicdraw.specificationDialog.SpecificationComputedComponent
import gov.nasa.jpl.dynamicScripts.magicdraw.ui.nodes._
import gov.nasa.jpl.dynamicScripts.magicdraw.utils._

import org.omg.oti.api._
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil

object PackageInspectorWidget {

  import ComputedDerivedWidgetHelper._
  import RelationTripleWidgetHelper._

  def nonImportedNestedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.nonImportedNestedPackages ),
      MagicDrawUMLUtil( project ) )

  def allNestedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allNestedPackages ),
      MagicDrawUMLUtil( project ) )

  def allNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allNestingPackagesTransitively ),
      MagicDrawUMLUtil( project ) )

  def allDirectlyImportedPackagesIncludingNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allDirectlyImportedPackagesIncludingNestingPackagesTransitively ),
      MagicDrawUMLUtil( project ) )

  def allPackagesWithinScope(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackage[MagicDrawUML]](
      derived, e,
      ( _.allPackagesWithinScope ),
      MagicDrawUMLUtil( project ) )
      
  def allApplicableStereotypes(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allApplicableStereotypes ),
      MagicDrawUMLUtil( project ) )

  def containingProfile(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.containingProfile ),
      MagicDrawUMLUtil( project ) )

  def allDirectlyAppliedProfilesExceptNestingPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allDirectlyAppliedProfilesExceptNestingPackages ),
      MagicDrawUMLUtil( project ) )

  def allDirectlyAppliedProfilesIncludingNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allDirectlyAppliedProfilesIncludingNestingPackagesTransitively ),
      MagicDrawUMLUtil( project ) )

  def allDirectlyVisibleMembersTransitivelyAccessibleExceptNestingPackagesAndAppliedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allDirectlyVisibleMembersTransitivelyAccessibleExceptNestingPackagesAndAppliedProfiles ),
      MagicDrawUMLUtil( project ) )

  def allIndirectlyAppliedProfilesIncludingNestingPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allIndirectlyAppliedProfilesIncludingNestingPackagesTransitively ),
      MagicDrawUMLUtil( project ) )

  def allForwardReferencesToImportablePackageableElementsFromAllOwnedElementsTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allForwardReferencesToImportablePackageableElementsFromAllOwnedElementsTransitively ),
      MagicDrawUMLUtil( project ) )

  def allIndirectlyVisibleMembersTransitivelyAccessibleFromNestingPackagesAndAppliedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.allIndirectlyVisibleMembersTransitivelyAccessibleFromNestingPackagesAndAppliedProfiles ),
      MagicDrawUMLUtil( project ) )

  def forwardReferencesToPackagesOrProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLPackage[MagicDrawUML], UMLPackageableElement[MagicDrawUML]](
      derived, e,
      ( _.forwardReferencesToPackagesOrProfiles.get ),
      MagicDrawUMLUtil( project ) )

  def forwardReferencesBeyondPackageScope(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    e match {
      case p: Package =>
        relationTripleWidget(
          derived, p,
          ( _.forwardReferencesBeyondPackageScope ),
          MagicDrawUMLUtil( project ) )
      case _ =>
        Failure( new IllegalArgumentException( "Not a package!" ) )
    }
}