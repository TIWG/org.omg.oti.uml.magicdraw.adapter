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

object ProfileInspectorWidget {

  
  def allImportedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pf: UMLProfile[Uml] =>
        val apf = pf.allImportedProfiles.toSeq.sortBy { pf => pf.qualifiedName.get }
        Success( apf.map { pf => ReferenceNodeInfo( pf.name.get, pf.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
  
  
  def allNestedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pf: UMLProfile[Uml] =>
        val apf = pf.allNestedProfiles.toSeq.sortBy { pf => pf.qualifiedName.get }
        Success( apf.map { pf => ReferenceNodeInfo( pf.name.get, pf.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
  
  def allVisibleProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pf: UMLProfile[Uml] =>
        val apf = pf.allVisibleProfiles.toSeq.sortBy { pf => pf.qualifiedName.get }
        Success( apf.map { pf => ReferenceNodeInfo( pf.name.get, pf.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
}