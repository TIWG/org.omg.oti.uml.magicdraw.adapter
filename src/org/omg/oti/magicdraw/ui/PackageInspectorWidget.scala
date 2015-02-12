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

  def accessibleMembersIncludingAllAppliedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pkg: UMLPackage[Uml] =>
        val apkg = pkg.accessibleMembersIncludingAllAppliedProfiles.toSeq.sortBy { p => p.qualifiedName.get }
        Success( apkg.map { p => ReferenceNodeInfo( p.name.get, p.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
  
  def allApplicableStereotypes(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pkg: UMLPackage[Uml] =>
        val apf = pkg.allApplicableStereotypes.toSeq.sortBy { s => s.qualifiedName.get }
        Success( apf.map { s => ReferenceNodeInfo( s.name.get, s.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
  
  def allAppliedProfiles(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pkg: UMLPackage[Uml] =>
        val apf = pkg.allAppliedProfiles.toSeq.sortBy { pf => pf.qualifiedName.get }
        Success( apf.map { pf => ReferenceNodeInfo( pf.name.get, pf.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
  
  
  def allProfileApplications(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pkg: UMLPackage[Uml] =>
        val apf = pkg.allProfileApplications.toSeq.sortBy { ap => ap.appliedProfile.get.qualifiedName.get }
        Success( apf.map { ap => ReferenceNodeInfo( ap.appliedProfile.get.name.get, ap.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
  
  def allNestingPackages(
    project: Project, ev: ActionEvent, derived: DynamicScriptsTypes.ComputedDerivedProperty,
    ek: MagicDrawElementKindDesignation, e: Element ): Try[Seq[Any]] = {
    
    val umlUtil = MagicDrawUMLUtil( project )
    import umlUtil._
          
    umlElement( e ) match { 
      case pkg: UMLPackage[Uml] =>
        val anp = pkg.allNestingPackages.toSeq.sortBy { p => p.qualifiedName.get }
        Success( anp.map { np => ReferenceNodeInfo( np.qualifiedName.get, np.getMagicDrawElement ) } )
        
      case x =>
        Failure( new IllegalArgumentException(s"Not a package; instead got a ${x.xmiType}"))
    }
  }
}