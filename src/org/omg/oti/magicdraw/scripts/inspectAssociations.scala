package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Success
import scala.util.Try
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import org.omg.oti.migration.Metamodel
import com.nomagic.magicdraw.core.ApplicationEnvironment
import java.io.File
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.XMLResource
import scala.util.Failure
import com.nomagic.magicdraw.uml.UUIDRegistry
import com.nomagic.magicdraw.core.utils.ChangeElementID
import com.nomagic.task.RunnableWithProgress
import com.nomagic.task.ProgressStatus
import com.nomagic.magicdraw.ui.MagicDrawProgressStatusRunner
import com.nomagic.magicdraw.core.ProjectUtilitiesInternal
import java.util.UUID
import com.nomagic.ci.persistence.local.spi.localproject.LocalPrimaryProject
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement
import com.nomagic.magicdraw.uml.symbols.paths.AssociationView
import com.nomagic.magicdraw.uml.symbols.PresentationElement
import org.omg.oti.UMLAssociation
import org.omg.oti.UMLProperty
import org.omg.oti.magicdraw.MagicDrawUML

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object inspectAssociations {

  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: AssociationView,
    triggerElement: Association,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {
    
    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    val selectedAssociations = selection.toIterator selectByKindOf ( { case a: AssociationView => umlAssociation( a.getAssociation ) } ) toList;
    
    selectedAssociations foreach { a =>      
      a.memberEnd.toList match {
        case end1 :: end2 :: Nil => inspectAssociation( umlUtil, a, end1, end2 )
        case _                   => ()
      }
    }

    guiLog.log("- Done")
    Success( None )
  }
      
      
  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree,
    node: Node,
    top: Association,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val app = Application.getInstance()
    val guiLog = app.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedAssociations = selection.toIterator selectByKindOf ( { case a: Uml#Association => umlAssociation( a ) } ) toList;
    selectedAssociations foreach { a =>
      a.ownedEnd.toList match {
        case end1 :: end2 :: Nil => inspectAssociation( umlUtil, a, end1, end2 )
        case _                   => ()
      }
    }

    Success( None )
  }

  def inspectAssociation( umlUtil: MagicDrawUMLUtil, a: UMLAssociation[MagicDrawUML], end1: UMLProperty[MagicDrawUML], end2: UMLProperty[MagicDrawUML] ): Unit = {
    import umlUtil._
    val app = Application.getInstance()
    val guiLog = app.getGUILog()

    guiLog.log( s" association: ${a.qualifiedName.get}")
    a.getDirectedAssociationEnd match {
      case None =>
        guiLog.log( "Not a directed association! " )
        guiLog.log( s" end1: ${end1.qualifiedName.get}")
        guiLog.log( s" end2: ${end2.qualifiedName.get}")

      case Some( ( sourceEnd, targetEnd ) ) =>
        guiLog.log( s" sourceEnd: ${sourceEnd.qualifiedName} ")
        guiLog.log( s" targetEnd: ${targetEnd.qualifiedName} ")
        
    }
  }
}