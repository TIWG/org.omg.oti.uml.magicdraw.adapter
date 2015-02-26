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
import com.nomagic.magicdraw.core.Application

object NamespaceInspectorWidget {

  import ComputedDerivedWidgetHelper._
  
  def importedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =     
      elementOperationWidget[UMLNamespace[MagicDrawUML], UMLPackageableElement[MagicDrawUML]]( 
          derived, e, 
          (_.importedPackages), 
          MagicDrawUMLUtil( project ) )
    
  def allImportedPackagesTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =  
      elementOperationWidget[UMLNamespace[MagicDrawUML], UMLPackageableElement[MagicDrawUML]]( 
          derived, e, 
          (_.allImportedPackagesTransitively), 
          MagicDrawUMLUtil( project ) )
    
  def importedMembers(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      elementOperationWidget[UMLNamespace[MagicDrawUML], UMLPackageableElement[MagicDrawUML]]( 
          derived, e, 
          (_.importedMember), 
          MagicDrawUMLUtil( project ) )  

  def visibleMembers(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      elementOperationWidget[UMLNamespace[MagicDrawUML], UMLPackageableElement[MagicDrawUML]]( 
          derived, e, 
          (_.visibleMembers), 
          MagicDrawUMLUtil( project ) )  
      
  def allVisibleMembersTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      elementOperationWidget[UMLNamespace[MagicDrawUML], UMLPackageableElement[MagicDrawUML]]( 
          derived, e, 
          (_.allVisibleMembersTransitively), 
          MagicDrawUMLUtil( project ) )  

  def allVisibleMembersAccessibleTransitively(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =   
      elementOperationWidget[UMLNamespace[MagicDrawUML], UMLPackageableElement[MagicDrawUML]]( 
          derived, e, 
          (_.allVisibleMembersAccessibleTransitively), 
          MagicDrawUMLUtil( project ) )  
          
}