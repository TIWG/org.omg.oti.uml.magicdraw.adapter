package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import java.io.File
import scala.collection.JavaConversions.asJavaCollection
import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import com.nomagic.actions.NMAction
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.MagicDrawProgressStatusRunner
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement
import com.nomagic.magicdraw.uml.symbols.PresentationElement
import com.nomagic.magicdraw.uml.symbols.shapes.PackageView
import com.nomagic.task.ProgressStatus
import com.nomagic.task.RunnableWithProgress
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile
import org.omg.oti.api._
import org.omg.oti.canonicalXMI.CatalogURIMapper
import org.omg.oti.canonicalXMI.DocumentSet
import org.omg.oti.magicdraw.MagicDrawUML
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model

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
    doit( p, selection )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    top: Package, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, selection )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    top: Model, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, selection )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Profile,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, selection flatMap { case pv: PackageView => Some( pv.getPackage ) } )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Package,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, selection flatMap { case pv: PackageView => Some( pv.getPackage ) } )

  def doit(
    p: Project,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedPackages: Set[UMLPackage[Uml]] =
      selection.toIterator selectByKindOf ( { case p: Package => umlPackage( p ) } ) toSet;

    /**
     * Ignore OMG production-related elements pertaining to OMG SysML 1.4 spec.
     */
    def ignoreCrossReferencedElementFilter( e: UMLElement[Uml] ): Boolean = e match {

      case ne: UMLNamedElement[Uml] =>
        val neQName = ne.qualifiedName.get
          DynamicScriptsPlugin.wildCardMatch( neQName, "UML Standard Profile::MagicDraw Profile::*" ) ||
          DynamicScriptsPlugin.wildCardMatch( neQName, "*OMG*" ) ||
          DynamicScriptsPlugin.wildCardMatch( neQName, "Specifications::SysML.profileAnnotations::*" )

      case e =>
        false
    }

    val defaultOMGCatalogFile =
      new File(
        new File(
          ApplicationEnvironment.getInstallRoot ).toURI.resolve( "dynamicScripts/org.omg.oti/omgCatalog/omg.local.catalog.xml" ) )
    val omgCatalog =
      if ( defaultOMGCatalogFile.exists() ) Seq( defaultOMGCatalogFile )
      else chooseCatalogFile( "Select the OMG UML 2.5 *.catalog.xml file" ).toSeq

    val defaultMDCatalogFile =
      new File(
        new File( ApplicationEnvironment.getInstallRoot ).toURI.resolve( "dynamicScripts/org.omg.oti.magicdraw/md18Catalog/omg.magicdraw.catalog.xml" ) )
    val mdCatalog =
      if ( defaultMDCatalogFile.exists() ) Seq( defaultMDCatalogFile )
      else chooseCatalogFile( "Select the MagicDraw UML 2.5 *.catalog.xml file" ).toSeq

    ( CatalogURIMapper.createCatalogURIMapper( omgCatalog ),
      CatalogURIMapper.createCatalogURIMapper( mdCatalog ) ) match {
        case ( Failure( t ), _ ) => Failure( t )
        case ( _, Failure( t ) ) => Failure( t )
        case ( Success( documentURIMapper ), Success( builtInURIMapper ) ) =>

          var result: Option[Try[Option[MagicDrawValidationDataResults]]] = None
          val runnable = new RunnableWithProgress() {

            def run( progressStatus: ProgressStatus ): Unit =
              result = Some(
                exportPackageExtents(
                  progressStatus,
                  p, selectedPackages,
                  documentURIMapper, builtInURIMapper,
                  ignoreCrossReferencedElementFilter ) )

          }

          MagicDrawProgressStatusRunner.runWithProgressStatus(
            runnable,
            s"Export ${selectedPackages.size} packages to OTI Canonical XMI...",
            true, 0 )

          require( result.isDefined )
          result.get
      }
  }

  def exportPackageExtents(
    progressStatus: ProgressStatus,
    p: Project,
    specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
    documentURIMapper: CatalogURIMapper,
    builtInURIMapper: CatalogURIMapper,
    ignoreCrossReferencedElementFilter: Function1[UMLElement[MagicDrawUML], Boolean] )( implicit umlUtil: MagicDrawUMLUtil ): Try[Option[MagicDrawValidationDataResults]] = {
    import umlUtil._

    val a = Application.getInstance()
    val guiLog = a.getGUILog()

    progressStatus.setCurrent( 0 )
    progressStatus.setMax( 0 )
    progressStatus.setMax( 1 )
    progressStatus.setLocked( true )

    DocumentSet.constructDocumentSetCrossReferenceGraph(
      specificationRootPackages,
      documentURIMapper, builtInURIMapper,
      builtInDocuments = Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile ),
      builtInDocumentEdges = Set( MDBuiltInUML2PrimitiveTypes, MDBuiltInStandardProfile2UML ),
      ignoreCrossReferencedElementFilter ) match {
        case Failure( t ) => Failure( t )
        case Success( ( resolved, unresolved ) ) =>

          val result = if ( unresolved.isEmpty ) Success( None )
          else {
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
              MagicDrawValidationDataResults.makeMDIllegalArgumentExceptionValidation(
                p, s"*** ${unresolved.size} unresolved cross-references ***",
                elementMessages,
                "*::MagicDrawOTIValidation",
                "*::UnresolvedCrossReference" ).validationDataResults ) )
          }

          resolved.serialize( valueSpecificationTagConverter = DocumentSet.serializeValueSpecificationAsTagValue[Uml] _ ) match {
            case Success( _ ) =>
              progressStatus.increase()
              guiLog.log( s"Graph: ${resolved.g}" )
              guiLog.log( s"Done..." )
              result

            case Failure( t ) =>
              Failure( t )
          }
      }
  }
}