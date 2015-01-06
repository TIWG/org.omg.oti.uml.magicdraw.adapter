package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import java.io.File

import scala.collection.JavaConversions.mapAsJavaMap
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.core.ProjectUtilities
import com.nomagic.magicdraw.core.modules.ModulesServiceInternal
import com.nomagic.magicdraw.ui.MagicDrawProgressStatusRunner
import com.nomagic.magicdraw.ui.browser.Node
import com.nomagic.magicdraw.ui.browser.Tree
import com.nomagic.magicdraw.uml.ConvertElementInfo
import com.nomagic.magicdraw.uml.Refactoring
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.task.ProgressStatus
import com.nomagic.task.RunnableWithProgress
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile

import org.eclipse.emf.common.util.URI
import org.omg.oti.magicdraw.MagicDrawUMLUtil
import org.omg.oti.migration.Metamodel

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object migratePackageExtentIDs {

  def doit(
    project: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Profile, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] =
    doit( project, ev, script, tree, node, pkg.asInstanceOf[Package], selection )

  def doit(
    project: Project, ev: ActionEvent,
    script: DynamicScriptsTypes.BrowserContextMenuAction,
    tree: Tree, node: Node,
    pkg: Package, selection: java.util.Collection[Element] ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( project )

    val mdInstallDir = new File( ApplicationEnvironment.getInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )
    val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
    require( otiDir.exists && otiDir.isDirectory )
    val migrationMM = Metamodel( otiDir )

    val proxyManager = project.getProxyManager
    if ( !proxyManager.isElementProxy( pkg ) )
      return Failure( new IllegalArgumentException( s"Select a r/o, proxy shared package" ) )

    val proxyAttachedProject = ProjectUtilities.getAttachedProject( pkg )
    val uri = proxyAttachedProject.getLocationURI
    require( uri.isFile() )

    val migrationF = new File( new File( uri.path ).getParentFile.getParentFile, uri.trimFileExtension.lastSegment + ".migration.xmi" )
    guiLog.log( s"Migration file: ${migrationF}" )
    require( migrationF.exists && migrationF.canRead )
    val migrationURI = URI.createFileURI( migrationF.getAbsolutePath )

    migrationMM.loadOld2NewIDMappingResource( migrationURI ) match {
      case Failure( t ) => Failure( t )
      case Success( old2newIDmigration ) =>
        val entries = old2newIDmigration.getEntries
        guiLog.log( s" Loaded ${entries.size} old2new ID migration entries" )

        val iPrimaryProject = project.getPrimaryProject

        if ( ModulesServiceInternal.useLocalModuleWithWizard( iPrimaryProject ) ) {
          var error: Throwable = null

          val runnable = new RunnableWithProgress() {
            def run( progressStatus: ProgressStatus ): Unit = {

              val proxyManager = project.getProxyManager

              val migrationPairs = entries flatMap { entry =>
                project.getElementByID( entry.getOldID.get ) match {
                  case oe: Element if ( proxyManager.isElementProxy( oe ) ) =>
                    project.getElementByID( entry.getNewID.get ) match {
                      case ne: Element => Some( ( oe, ne ) )
                      case _           => None
                    }
                  case _ => None
                }
              } toList

              if ( migrationPairs.size > 2000 ) {
                migrationPairs foreach { case ( oe, ne ) => System.out.println( s" new=${ne.getID} => old=${oe.getID}" ) }
              }
              else {
                migrationPairs foreach {
                  case ( oe, ne ) =>
                    guiLog.addHyperlinkedText(
                      s" new=<A>${ne.getID}</A> <= old=${oe.getID}",
                      Map( ne.getID -> new SelectInContainmentTreeRunnable( ne ) ) )
                }
              }
              if ( migrationPairs.isEmpty ) {
                error = new IllegalArgumentException( s"Migration metadata does not match anything. No proxy migration rules created." )
                return
              }

              progressStatus.setCurrent( 0 )
              progressStatus.setMax( migrationPairs.size )
              migrationPairs.foreach {
                case ( oe, ne ) =>
                  val info = new ConvertElementInfo( oe.getClassType )
                  info.setConvertOnlyIncomingReferences( false )
                  Refactoring.Replacing.replace( oe, ne, info )
                  progressStatus.increase
              }
            }
          }

          MagicDrawProgressStatusRunner.runWithProgressStatus( runnable, s"Migrate XMI:IDs", true, 0 )

          if ( error != null )
            return Failure( error )
        }

        Success( None )
    }
  }
}