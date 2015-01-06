package org.omg.oti.magicdraw.scripts

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField
import scala.collection.JavaConversions.mapAsJavaMap
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import com.jidesoft.swing.JideBoxLayout
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

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object computePackageExtentIDs {

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

    val mdInstallRoot = ApplicationEnvironment.getInstallRoot
    val mdInstallDir = new File( mdInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )

    val ip = ProjectUtilities.getProject( top )
    if ( !ProjectUtilities.isStandardSystemProfile( ip ) )
      return Failure( new IllegalArgumentException( s"The selected package, '${top.getQualifiedName}', is in a project, '${ip.getLocationURI}', that should have the MD Standard/System Profile flag set" ) )

    val uri = ip.getLocationURI
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
   
    val previousModulePath = augmentString( modulePathField.getText )
    if ( status != JOptionPane.OK_OPTION || previousModulePath.isEmpty ) {
      guiLog.log("Cancelled")
      return Success( None )
    }
    
    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._
    
    umlUtil.computePackageExtentXMI_ID( top )

    val elementIDs = umlUtil.getElement2IDMap
    val errors = elementIDs filter ( _._2.isFailure )
    if ( errors.nonEmpty ) {
      System.out.println( s"${errors.size} errors when computing OTI XMI IDs for the package extent of ${top.getQualifiedName}" )
      guiLog.log( s"${errors.size} errors when computing OTI XMI IDs for the package extent of ${top.getQualifiedName}" )
      ( errors map ( _._1 ) toList ) sortBy ( _.id ) foreach { e =>
        val t = errors( e ).failed.get
        if ( errors.size > 100 ) System.out.println( s" ${e.id} => ${t}" )
        else guiLog.addHyperlinkedText(
          s" <A>${e.id}</A> => ${t}",
          Map( e.id -> e.selectInContainmentTreeRunnable ) )
      }
    }
    else {
      System.out.println( s"No errors when computing OTI XMI IDs for the package extent of ${top.getQualifiedName}" )
      guiLog.log( s"No errors when computing XMI ID for the package extent of ${top.getQualifiedName}" )
    }

    val id2element = elementIDs filter { case ( e, _ ) => top.isAncestorOf( e ) } flatMap {
      case ( e, Success( newID ) ) => Some( ( newID -> e ) )
      case ( _, _ )                => None
    } toMap;
    val sortedIDs = id2element.keys.toList.sorted filter { !_.contains( "appliedStereotypeInstance" ) }

    val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
    require( otiDir.exists && otiDir.isDirectory )
    val migrationMM = Metamodel( otiDir )

    val old2newMapping = migrationMM.makeOld2NewIDMapping( previousModulePath )

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