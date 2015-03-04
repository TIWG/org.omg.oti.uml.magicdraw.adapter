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
import org.omg.oti.api._
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import com.nomagic.magicdraw.core.Application

object ElementInspectorWidget {

  import ComputedDerivedWidgetHelper._
    
  def appliedStereotypes(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLStereotype[MagicDrawUML]]( 
          derived, e, 
          (_.getAppliedStereotypes.keySet), 
          MagicDrawUMLUtil( project ) )  
          
  def allForwardReferencesFromStereotypeTagProperties(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLElement[MagicDrawUML]]( 
          derived, e, 
          (_.allForwardReferencesFromStereotypeTagProperties), 
          MagicDrawUMLUtil( project ) )      

  def allForwardReferencesToElements(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLElement[MagicDrawUML]]( 
          derived, e, 
          (_.allForwardReferencesToElements), 
          MagicDrawUMLUtil( project ) )      

  def allForwardReferencesToImportablePackageableElements(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLElement[MagicDrawUML]]( 
          derived, e, 
          (_.allForwardReferencesToImportablePackageableElements), 
          MagicDrawUMLUtil( project ) )     
          
  def getPackageOwnerWithURI(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLElement[MagicDrawUML]]( 
          derived, e, 
          (_.getPackageOwnerWithURI), 
          MagicDrawUMLUtil( project ) )    
          
  def packageOrProfileOwner(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLPackage[MagicDrawUML]]( 
          derived, e, 
          (getPackageOrProfileOwner(_)), 
          MagicDrawUMLUtil( project ) )   
          
  def allOwnedElementsWithinPackageScope(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = 
      elementOperationWidget[UMLElement[MagicDrawUML], UMLElement[MagicDrawUML]]( 
          derived, e, 
          (_.allOwnedElementsWithinPackageScope), 
          MagicDrawUMLUtil( project ) )      
}