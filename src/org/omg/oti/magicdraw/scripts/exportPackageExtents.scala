package org.omg.oti.magicdraw.scripts

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField
import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import com.jidesoft.swing.JideBoxLayout
import com.nomagic.actions.NMAction
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.core.ProjectUtilities
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.XMLResource
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import org.omg.oti.migration.Metamodel
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import org.omg.oti.magicdraw.MagicDrawUMLElement
import org.omg.oti._
import org.omg.oti.canonicalXMI._
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object exportPackageExtents {

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Profile, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, ev, script, tree, node, pkg.asInstanceOf[Package], selection )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    top: Package, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedPackages = selection.toIterator selectByKindOf ( { case p: Package => umlPackage( p ) } ) toList;

    val otiSpecificationRoot_s = umlStereotype( OTI_SPECIFICATION_ROOT_S.get )
    val otiSpecificationRoot_packageURI = umlProperty( OTI_SPECIFICATION_ROOT_packageURI.get )

    val ( roots, nonRoots ) = selectedPackages partition ( _.hasStereotype( otiSpecificationRoot_s ) )

    def specificationRootURI( p: UMLPackage[Uml] ): Option[String] = {
      p.tagValues.get( otiSpecificationRoot_packageURI ) match {
        case Some( Seq( uri: UMLLiteralString[_] ) ) => uri.value
        case _                                       => None
      }
    }

    /**
     * Ignore OMG production-related elements pertaining to OMG SysML 1.4 spec.
     */
    def ignoreCrossReferencedElementFilter( e: UMLElement[Uml] ): Boolean = e match {

      case ne: UMLNamedElement[Uml] =>
        val neQName = ne.qualifiedName.get
        DynamicScriptsPlugin.wildCardMatch( neQName, "UML Standard Profile::MagicDraw Profile::*" ) ||
        DynamicScriptsPlugin.wildCardMatch( neQName, "*::OTI::*" ) ||
        DynamicScriptsPlugin.wildCardMatch( neQName, "*OMG*" ) ||
        DynamicScriptsPlugin.wildCardMatch( neQName, "Specifications::*" )

      case e =>
        false
    }

    val ( okRoots, anonymousRoots ) = roots partition ( specificationRootURI( _ ).isDefined )

    guiLog.log( s"- roots: ${roots.size}" )
    guiLog.log( s"- nonRoots: ${nonRoots.size}" )
    guiLog.log( s"- okRoots: ${okRoots.size}" )
    guiLog.log( s"- anonymousRoots: ${anonymousRoots.size}" )

    if ( nonRoots.nonEmpty ) {

      guiLog.log( s"*** ${nonRoots.size} packages not stereotyped OTI::SpecificationRoot! ***" )
      val elementMessages = nonRoots map { nr => ( nr.getMagicDrawElement -> ( "Not a SpecificationRoot", List() ) ) } toMap

      Success( Some(
        MagicDrawValidationDataResults.makeMDIllegalArgumentExceptionValidation( p, "Not OTI SpecificationRoot packages",
          elementMessages,
          "*::MagicDrawOTIValidation",
          "*::NotOTISpecificationRoot" ).validationDataResults ) )

    }
    else if ( anonymousRoots.nonEmpty ) {

      guiLog.log( s"*** ${anonymousRoots.size} anonymous OTI::SpecificationRoot-stereotyped packages! ***" )
      val elementMessages = anonymousRoots map { nr => ( nr.getMagicDrawElement -> ( "SpecificationRoot without URI", List() ) ) } toMap

      Success( Some(
        MagicDrawValidationDataResults.makeMDIllegalArgumentExceptionValidation( p, "OTI SpecificationRoot packages must have a SpecificationRoot::packageURI",
          elementMessages,
          "*::MagicDrawOTIValidation",
          "*::OTISpecificationRootMustHaveURI" ).validationDataResults ) )

    }
    else {

      guiLog.log( s"Make ${okRoots.size} documents..." )
      val selectedDocuments = for {
        p <- okRoots
        pURI <- specificationRootURI( p )
      } yield {
        val d = SerializableDocument( uri = new java.net.URI( pURI ), scope = p )
        guiLog.log( s" => make document for: uri=${pURI} (p=${p.getMagicDrawElement.getID}) = ${d}" )
        d
      }

      val ds = DocumentSet(
        serializableDocuments = selectedDocuments.toSet,
        builtInDocuments = Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile ) )

      val ( g, unresolved ) = ds.externalReferenceDocumentGraph( ignoreCrossReferencedElementFilter )
      guiLog.log( s"Graph: ${g}" )

      if ( unresolved.nonEmpty ) {

        guiLog.log( s"*** ${unresolved.size} unresolved cross-references ***" )
        val elementMessages = unresolved map { u =>
          val mdXRef = u.externalReference.getMagicDrawElement
          val a = new NMAction( s"Select${u.hashCode}", s"Select ${mdXRef.getHumanType}: ${mdXRef.getHumanName}", 0 ) {

            def actionPerformed( ev: ActionEvent ): Unit = {
              u.externalReference.selectInContainmentTreeRunnable.run
            }
          }
          ( u.documentElement.getMagicDrawElement ->
            ( s"cross-reference to: ${mdXRef.getHumanType}: ${mdXRef.getHumanName} (ID=${mdXRef.getID})",
              List( a ) ) )
        } toMap;

        Success( Some(
          MagicDrawValidationDataResults.makeMDIllegalArgumentExceptionValidation( p, s"*** ${unresolved.size} unresolved cross-references ***",
            elementMessages,
            "*::MagicDrawOTIValidation",
            "*::UnresolvedCrossReference" ).validationDataResults ) )

      }
      else {

        ds.serialize match {
          case Success( _ ) =>
            guiLog.log( s"Done..." )
            Success( None )

          case Failure( t ) =>
            Failure( t )
        }

      }
    }
  }
}