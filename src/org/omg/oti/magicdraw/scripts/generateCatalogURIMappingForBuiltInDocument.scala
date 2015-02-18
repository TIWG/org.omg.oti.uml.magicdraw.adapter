package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent

import scala.Iterable
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile

import org.omg.oti.UMLClass
import org.omg.oti.UMLExtension
import org.omg.oti.UMLPrimitiveType
import org.omg.oti.UMLStereotype
import org.omg.oti.magicdraw.MagicDrawUMLUtil

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object generateCatalogURIMappingForBuiltInDocument {

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
    pkg: Model,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( project, ev, script, tree, node, pkg.asInstanceOf[Package], selection )

  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree,
    node: Node,
    top: Package,
    selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( p )

    import umlUtil._

    val pkg = umlPackage( top )

    val (pkgURI, otiURI) = pkg.URI match {
      case None => 
        return Failure( new IllegalArgumentException( "Package should have a URI!" ) )
      case Some( pURI ) =>
        pURI match {
          case "http://www.omg.org/spec/PrimitiveTypes/20100901" => 
            (pURI, "http://www.omg.org/spec/PrimitiveTypes/20131001")
          case "http://www.omg.org/spec/UML/20131001" => 
            (pURI, "http://www.omg.org/spec/UML/20131001")
          case "http://www.omg.org/spec/UML/20131001/StandardProfile" => 
            (pURI, "http://www.omg.org/spec/UML/20131001/StandardProfile")
          case x => 
              return Failure( new IllegalArgumentException( s"Unrecognized package with built-in URI: ${x}" ) )
        }
    }

    val rewrites =
      pkg.ownedTypes flatMap { t =>
      t match {
        case pt: UMLPrimitiveType[Uml] => 
          Iterable( s"""<uri uri="${otiURI}#${pt.name.get}" name="${pkgURI}#${pt.id}"/>""" )
        case s: UMLStereotype[Uml] => 
          Iterable( s"""<uri uri="${otiURI}#${s.name.get}" name="${pkgURI}#${s.id}"/>""" ) ++          
          (for { 
            baseProperty <- s.baseMetaPropertiesExceptRedefined
            baseID = s"${s.name.get}_${baseProperty.name.get}"
          } yield s"""<uri uri="${otiURI}#${baseID}" name="${pkgURI}#${baseProperty.id}"/>""")
          
        case ex: UMLExtension[Uml] => 
          val ee = ex.ownedEnds.head
          val end = ee._type.head.name.get
          val base = ex.metaclass.head.name.get
          val oti_extensionID = base+"_"+end
          val oti_endID = oti_extensionID+"-extension_"+end
          Iterable( 
              s"""<uri uri="${otiURI}#${oti_extensionID}" name="${pkgURI}#${ex.id}"/>""",
              s"""<uri uri="${otiURI}#${oti_endID}" name="${pkgURI}#${ee.id}"/>""" 
              )
          
        case c: UMLClass[Uml] => 
          Iterable( s"""<uri uri="${otiURI}#${c.name.get}" name="${pkgURI}#${c.id}"/>""" )
          
        case _ =>
          Iterable()
      }

    }

    val builtInRewrites = Iterable( s"""<uri uri="${otiURI}#_0" name="${pkgURI}#${pkg.id}"/>""" ) ++ rewrites
          
    builtInRewrites.foreach { rewrite => System.out.println(rewrite) }
    
    Success( None )
  }
}