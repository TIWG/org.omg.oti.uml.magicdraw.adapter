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
import com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.StructuredClassifier

object StructuredClassifierInspectorWidget {

  import ComputedDerivedWidgetHelper._
  
  def allRoles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedWidget,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] =     
      elementOperationWidget[UMLStructuredClassifier[MagicDrawUML], UMLConnectableElement[MagicDrawUML]]( 
          derived, e, 
          (_.allRoles), 
          MagicDrawUMLUtil( project ) )
          
  def compositeStructureTree(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedTree,
    ek: MagicDrawElementKindDesignation, e: StructuredClassifier ): Try[Seq[( AbstractTreeNodeInfo, Map[String, AbstractTreeNodeInfo] )]] = {
   
    val treeInfo = TreeNodeInfo(
      identifier = s"${e.getQualifiedName}",
      nested = Seq() )

    Success( Seq( ( treeInfo, Map[String, AbstractTreeNodeInfo]() ) ) )
  }
    
}