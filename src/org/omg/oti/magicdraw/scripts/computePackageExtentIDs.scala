package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent

import scala.collection.JavaConversions.mapAsJavaMap
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile

import org.omg.oti.magicdraw.MagicDrawUMLUtil

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object computePackageExtentIDs {

  def doit(
    project: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree,
    node: Node,
    pkg: Profile,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( project, ev, script, tree, node, pkg.asInstanceOf[Package], selection )

  def doit(
    project: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree,
    node: Node,
    top: Package,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( project )
    umlUtil.computePackageExtentXMI_ID( top )

    val elementIDs = umlUtil.getElement2IDMap
    val errors = elementIDs filter ( _._2.isFailure )
    if ( errors.nonEmpty ) {
      System.out.println( s"${errors.size} errors when computing OTI XMI IDs for the package extent of ${top.getQualifiedName}" )
      guiLog.log( s"${errors.size} errors when computing OTI XMI IDs for the package extent of ${top.getQualifiedName}" )
      ( errors map ( _._1 ) toList ) sortBy ( umlUtil.getElementID( _ ) ) foreach { e =>
        val t = errors( e ).failed.get
        if ( errors.size > 100 )
          System.out.println( s" ${umlUtil.getElementID( e )} => ${t}" )
        else
          guiLog.addHyperlinkedText(
            s" <A>${e.getID}</A> => ${t}",
            Map( e.getID -> new SelectInContainmentTreeRunnable( e ) ) )
      }
    }
    else {
      System.out.println( s"No errors when computing OTI XMI IDs for the package extent of ${top.getQualifiedName}" )
      guiLog.log( s"No errors when computing XMI ID for the package extent of ${top.getQualifiedName}" )
    }

    val id2element = elementIDs flatMap {
      case ( e, Success( newID ) ) => Some( ( newID -> e ) )
      case ( _, _ )                => None
    } toMap;
    val sortedIDs = id2element.keys.toList.sorted filter { !_.contains( "appliedStereotypeInstance" ) }

    System.out.println( s" element2id map has ${sortedIDs.size} entries" )
    guiLog.log( s" element2id map has ${sortedIDs.size} entries" )
    val tooLong = sortedIDs.size > 2000
    for {
      n <- 0 until sortedIDs.size
      id = sortedIDs( n )
      e = id2element( id )
    } {
      if ( tooLong )
        System.out.println( s"${n}: ${id} => ${umlUtil.getElementID( e )}" )
      else
        guiLog.addHyperlinkedText(
          s" ${n}: <A>${id}</A> => ${e.getID}",
          Map( id -> new SelectInContainmentTreeRunnable( e ) ) )
    }

    Success( None )
  }

}