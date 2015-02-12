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

object NamespaceInspectorWidget {

  def importedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case ns: UMLNamespace[Uml] =>
        val apkg = ns.importedPackages.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a namespace; instead got a ${x.xmiType}"))
    }
  }
  
  def allImportedPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case ns: UMLNamespace[Uml] =>
        val apkg = ns.allImportedPackages.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a namespace; instead got a ${x.xmiType}"))
    }
  }
  
  def importedMembers(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case ns: UMLNamespace[Uml] =>
        val apkg = ns.importedMembers.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a namespace; instead got a ${x.xmiType}"))
    }
  }
  
  def visibleMembers(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case ns: UMLNamespace[Uml] =>
        val apkg = ns.visibleMembers.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a namespace; instead got a ${x.xmiType}"))
    }
  }
  
  def allVisibleMembers(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case ns: UMLNamespace[Uml] =>
        val apkg = ns.allVisibleMembers.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a namespace; instead got a ${x.xmiType}"))
    }
  }
    
  def accessibleMembers(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case ns: UMLNamespace[Uml] =>
        val apkg = ns.accessibleMembers.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a namespace; instead got a ${x.xmiType}"))
    }
  }
}