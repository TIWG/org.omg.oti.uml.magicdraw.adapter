package org.omg.oti.magicdraw.scripts

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.io.File

import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField

import scala.collection.JavaConversions.asJavaCollection
import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.collection.JavaConversions.mapAsJavaMap
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

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.XMLResource
import org.omg.oti._
import org.omg.oti.migration._
import org.omg.oti.canonicalXMI._
import org.omg.oti.magicdraw._
import org.omg.oti.magicdraw.canonicalXMI._

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object generateIDsForPackageExtents {

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Profile, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, ev, selection )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Package,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, ev, selection flatMap { case pv: PackageView => Some( pv.getPackage ) } )

  def doit(
    p: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: PackageView,
    triggerElement: Profile,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( p, ev, selection flatMap { case pv: PackageView => Some( pv.getPackage ) } )

  def doit(
    p: Project, ev: ActionEvent,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val mdInstallRoot = ApplicationEnvironment.getInstallRoot
    val mdInstallDir = new File( mdInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )

    val pp = p.getPrimaryProject
    if ( !ProjectUtilities.isStandardSystemProfile( pp ) )
      return Failure( new IllegalArgumentException( s"The project must be a standard/system profile project" ) )

    val uri = pp.getLocationURI
    require( uri.isFile )

    val modulePath = uri.deresolve( URI.createFileURI( mdInstallRoot ) )
    require( modulePath.isRelative )

    val panel = new JPanel()
    panel.setLayout( new JideBoxLayout( panel, BoxLayout.Y_AXIS ) )

    panel.add( new JLabel( "Enter MD root-relative path of the previous version of the Standard/System Profile module: " ), BorderLayout.BEFORE_LINE_BEGINS )

    val modulePathField = new JTextField
    modulePathField.setText( modulePath.path )
    modulePathField.setColumns( modulePath.path.length() + 10 )
    modulePathField.setEditable( true )
    modulePathField.setFocusable( true )
    panel.add( modulePathField )

    panel.updateUI()

    val status = JOptionPane.showConfirmDialog(
      Application.getInstance.getMainFrame,
      panel,
      "Specify the relative path of the previous module version",
      JOptionPane.OK_CANCEL_OPTION )

    val projectFilename = augmentString( modulePathField.getText )
    if ( status != JOptionPane.OK_OPTION || projectFilename.isEmpty ) {
      guiLog.log( "Cancelled" )
      return Success( None )
    }

    implicit val umlUtil = MagicDrawUMLUtil( p )
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
    val omgCatalog = if ( defaultOMGCatalogFile.exists() ) Seq( defaultOMGCatalogFile ) else chooseCatalogFile( "Select the OMG UML 2.5 *.catalog.xml file" ).toSeq

    val defaultMDCatalogFile = new File( new File( ApplicationEnvironment.getInstallRoot ).toURI.resolve( "dynamicScripts/org.omg.oti.magicdraw/md18Catalog/omg.magicdraw.catalog.xml" ) )
    val mdCatalog = if ( defaultMDCatalogFile.exists() ) Seq( defaultMDCatalogFile ) else chooseCatalogFile( "Select the MagicDraw UML 2.5 *.catalog.xml file" ).toSeq

    ( CatalogURIMapper.createCatalogURIMapper( omgCatalog ),
      CatalogURIMapper.createCatalogURIMapper( mdCatalog ) ) match {
        case ( Failure( t ), _ ) => Failure( t )
        case ( _, Failure( t ) ) => Failure( t )
        case ( Success( documentURIMapper ), Success( builtInURIMapper ) ) =>

          var result: Option[Try[Option[MagicDrawValidationDataResults]]] = None
          val runnable = new RunnableWithProgress() {

            def run( progressStatus: ProgressStatus ): Unit =
              result = Some(
                generateIDs(
                  progressStatus,
                  p, mdInstallDir, projectFilename,
                  selectedPackages,
                  documentURIMapper, builtInURIMapper,
                  ignoreCrossReferencedElementFilter ) )

          }

          MagicDrawProgressStatusRunner.runWithProgressStatus(
            runnable,
            s"Generating OTI Canonical XMI:IDs for ${selectedPackages.size} packages...",
            true, 0 )

          require( result.isDefined )
          result.get
      }
  }

  def generateIDs(
    progressStatus: ProgressStatus,
    p: Project, mdInstallDir: File, projectFilename: String,
    specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
    documentURIMapper: CatalogURIMapper,
    builtInURIMapper: CatalogURIMapper,
    ignoreCrossReferencedElementFilter: Function1[UMLElement[MagicDrawUML], Boolean] )( implicit umlUtil: MagicDrawUMLUtil ): Try[Option[MagicDrawValidationDataResults]] = {
    import umlUtil._

    val a = Application.getInstance()
    val guiLog = a.getGUILog()

    progressStatus.setCurrent( 0 )
    progressStatus.setMax( 0 )
    progressStatus.setMax( specificationRootPackages.size + 1 )
    progressStatus.setLocked( true )

    DocumentSet.constructDocumentSetCrossReferenceGraph(
      specificationRootPackages,
      documentURIMapper, builtInURIMapper,
      builtInDocuments = Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile ),
      ignoreCrossReferencedElementFilter ) match {
        case Failure( t ) => Failure( t )
        case Success( ( resolved, unresolved ) ) if ( unresolved.nonEmpty ) =>

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

        case Success( ( resolved, unresolved ) ) if ( unresolved.isEmpty ) =>
          val idGenerator = MagicDrawIDGenerator( resolved )
          for {
            pkg <- specificationRootPackages
            _ = progressStatus.increase()
            _ = progressStatus.setDescription( s"Generating OTI Canonical XMI:IDs for '${pkg.name.get}'..." )
            ok = idGenerator.computePackageExtentXMI_ID( pkg )
          } {
            ok match {
              case Failure( t ) => return Failure( t )
              case Success( _ ) => ()
            }
          }

          val elementIDs = idGenerator.getElement2IDMap
          val errors = elementIDs filter ( _._2.isFailure )
          if ( errors.nonEmpty ) {
            guiLog.log( s"${errors.size} errors when computing OTI XMI IDs for the package extent(s) of:" )
            for { pkg <- specificationRootPackages } {
              guiLog.log( s" => ${pkg.qualifiedName.get}" )
            }
            ( errors map ( _._1 ) toList ) sortBy ( _.id ) foreach { e =>
              val t = errors( e ).failed.get
              if ( errors.size > 100 ) System.out.println( s" ${e.id} => ${t}" )
              else guiLog.addHyperlinkedText(
                s" <A>${e.id}</A> => ${t}",
                Map( e.id -> e.selectInContainmentTreeRunnable ) )
            }
          }
          else {
            guiLog.log( s"No errors when computing OTI XMI IDs for the package extent(s) of:" )
            for { pkg <- specificationRootPackages } {
              guiLog.log( s" => ${pkg.qualifiedName.get}" )
            }
          }

          progressStatus.increase()
          progressStatus.setDescription( s"Constructing old/new migration map..." )
            
          val id2element = elementIDs filter
            { case ( e, _ ) => specificationRootPackages.exists( p => p.isAncestorOf( e ) ) } flatMap {
              case ( e, Success( newID ) ) => Some( ( newID -> e ) )
              case ( _, _ )                => None
            } toMap;
          val sortedIDs = id2element.keys.toList.sorted filter { !_.contains( "appliedStereotypeInstance" ) }

          val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
          require( otiDir.exists && otiDir.isDirectory )
          val migrationMM = Metamodel( otiDir )

          val old2newMapping = migrationMM.makeOld2NewIDMapping( projectFilename )

          System.out.println( s" element2id map has ${sortedIDs.size} entries" )
          guiLog.log( s" element2id map has ${sortedIDs.size} entries" )
          val tooLong = sortedIDs.size > 2000
          for {
            n <- 0 until sortedIDs.size
            id = sortedIDs( n )
            e = id2element( id )
          } {
            val oldID = e.id
            val old2newEntry = migrationMM.makeOld2NewIDEntry
            old2newEntry.setOldID( oldID )
            old2newEntry.setNewID( id )
            old2newMapping.addEntry( old2newEntry )

            if ( tooLong ) System.out.println( s"${n}: ${id} => ${oldID}" )
            else guiLog.addHyperlinkedText(
              s" ${n}: <A>${id}</A> => ${oldID}",
              Map( id -> e.asInstanceOf[MagicDrawUMLElement].selectInContainmentTreeRunnable ) )
          }

          val dir = new File( project.getDirectory )
          require( dir.exists() && dir.isDirectory() )
          val migrationF = new File( dir, project.getName + ".migration.xmi" )
          val migrationURI = URI.createFileURI( migrationF.getAbsolutePath )
          val r = migrationMM.rs.createResource( migrationURI )
          r.getContents.add( old2newMapping.eObject )
          val options = Map( XMLResource.OPTION_ENCODING -> "UTF-8" )
          r.save( options )

          guiLog.log( s" Saved migration model at: ${migrationF} " )
          Success( None )
      }
  }

}