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
import org.apache.xml.resolver.CatalogManager
import com.nomagic.magicdraw.core.project.ProjectsManager
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter

/**
 * Should be functionally equivalent to exportPackageExtents but simpler thanks to the methods in DocumentSet's companion object
 *
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object exportPackageExtents2OTICanonicalXMI {

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

    val selectedPackages: Set[UMLPackage[Uml]] = selection.toIterator selectByKindOf ( { case p: Package => umlPackage( p ) } ) toSet;

    /**
     * Ignore OMG production-related elements pertaining to OMG SysML 1.4 spec.
     */
    def ignoreCrossReferencedElementFilter( e: UMLElement[Uml] ): Boolean = e match {

      case ne: UMLNamedElement[Uml] =>
        val neQName = ne.qualifiedName.get
        DynamicScriptsPlugin.wildCardMatch( neQName, "UML Standard Profile::MagicDraw Profile::*" ) ||
          DynamicScriptsPlugin.wildCardMatch( neQName, "*::OTI::*" ) ||
          DynamicScriptsPlugin.wildCardMatch( neQName, "*OMG*" ) ||
          DynamicScriptsPlugin.wildCardMatch( neQName, "Specifications::SysML.profileAnnotations::*" )

      case e =>
        false
    }

    val defaultOMGCatalogFile = new File( new File( ApplicationEnvironment.getInstallRoot ).toURI.resolve( "dynamicScripts/org.omg.oti/omgCatalog/omg.local.catalog.xml" ) )
    val omgCatalog = if ( defaultOMGCatalogFile.exists() ) Seq( defaultOMGCatalogFile ) else chooseCatalogFile("Select the OMG UML 2.5 *.catalog.xml file").toSeq

    val defaultMDCatalogFile = new File( new File( ApplicationEnvironment.getInstallRoot ).toURI.resolve( "dynamicScripts/org.omg.oti.magicdraw/md18Catalog/omg.magicdraw.catalog.xml" ) )
    val mdCatalog = if ( defaultMDCatalogFile.exists() ) Seq( defaultMDCatalogFile ) else chooseCatalogFile("Select the MagicDraw UML 2.5 *.catalog.xml file").toSeq

    ( CatalogURIMapper.createCatalogURIMapper( omgCatalog ),
      CatalogURIMapper.createCatalogURIMapper( mdCatalog ) ) match {
      case ( Failure( t ), _ ) => Failure( t )
      case ( _, Failure( t ) ) => Failure( t )
      case ( Success( documentURIMapper ), Success( builtInURIMapper ) )  =>

        DocumentSet.constructDocumentSetCrossReferenceGraph(
          specificationRootPackages = selectedPackages,
          documentURIMapper, builtInURIMapper,
          builtInDocuments = Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile ),
          valueSpecificationTagConverter = DocumentSet.serializeValueSpecificationAsTagValue[Uml] _,
          ignoreCrossReferencedElementFilter ) match {
            case Failure( t ) => Failure( t )
            case Success( ( ds, g, element2document, unresolved ) ) =>

              if ( unresolved.nonEmpty ) {
                guiLog.log( s"*** ${unresolved.size} unresolved cross-references ***" )
                val elementMessages = unresolved map { u =>
                  val mdXRef = u.externalReference.getMagicDrawElement
                  val a = new NMAction( s"Select${u.hashCode}", s"Select ${mdXRef.getHumanType}: ${mdXRef.getHumanName}", 0 ) {
                    def actionPerformed( ev: ActionEvent ): Unit = u.externalReference.selectInContainmentTreeRunnable.run
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
              else
                ds.serialize( g, element2document ) match {
                  case Success( _ ) =>                    
                    guiLog.log( s"Graph: ${g}" )
                    guiLog.log( s"Done..." )
                    Success( None )

                  case Failure( t ) =>
                    Failure( t )
                }
          }
    }
  }
}