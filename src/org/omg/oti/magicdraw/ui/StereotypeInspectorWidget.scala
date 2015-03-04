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
import org.omg.oti.api._
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import com.nomagic.magicdraw.core.Application

object StereotypeInspectorWidget {

  import ComputedDerivedWidgetHelper._

  def profile(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLProfile[MagicDrawUML]](
      derived, e,
      ( _.profile.toSet ),
      MagicDrawUMLUtil( project ) )

  def getSpecializedStereotypes(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
    implicit val umlUtil = MagicDrawUMLUtil( project )
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLStereotype[MagicDrawUML]](
      derived, e,
      ( org.omg.oti.getSpecializedStereotypes( _ ) ),
      umlUtil )
  }
  
  def getSpecializedStereotypesOutsideProfile(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
    implicit val umlUtil = MagicDrawUMLUtil( project )
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLStereotype[MagicDrawUML]](
      derived, e,
      ( org.omg.oti.getSpecializedStereotypesOutsideProfile( _ ) ),
      umlUtil )
  }
  
  def getSpecializedStereotypesWithinProfile(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
    implicit val umlUtil = MagicDrawUMLUtil( project )
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLStereotype[MagicDrawUML]](
      derived, e,
      ( org.omg.oti.getSpecializedStereotypesWithinProfile( _ ) ),
      umlUtil )
  }
  
  def getAllSpecializedStereotypes(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
    implicit val umlUtil = MagicDrawUMLUtil( project )
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLStereotype[MagicDrawUML]](
      derived, e,
      ( org.omg.oti.getAllSpecializedStereotypes( _ ) ),
      umlUtil )
  }
  
  def getAllSpecializedStereotypesWithinProfile(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
    implicit val umlUtil = MagicDrawUMLUtil( project )
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLStereotype[MagicDrawUML]](
      derived, e,
      ( org.omg.oti.getAllSpecializedStereotypesWithinProfile( _ ) ),
      umlUtil )
  }
  def getSpecializedStereotypesFromOtherProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
    implicit val umlUtil = MagicDrawUMLUtil( project )
    elementOperationWidget[UMLStereotype[MagicDrawUML], UMLStereotype[MagicDrawUML]](
      derived, e,
      ( org.omg.oti.getSpecializedStereotypesFromOtherProfiles( _ ) ),
      umlUtil )
  }
}