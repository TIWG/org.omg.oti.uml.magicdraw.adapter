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

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object changePackageExtentIDs {

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

    val mdInstallDir = new File( ApplicationEnvironment.getInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )
    val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
    require( otiDir.exists && otiDir.isDirectory )
    val migrationMM = Metamodel( otiDir )
    
    val dir = new File( project.getDirectory )
    require(dir.exists && dir.isDirectory())
    val migrationF = new File( dir, project.getName + ".migration.xmi" )
    require( migrationF.exists && migrationF.canRead )
    val migrationURI = URI.createFileURI( migrationF.getAbsolutePath )
    
    migrationMM.loadOld2NewIDMappingResource( migrationURI ) match {
      case Failure( t ) => Failure( t )
      case Success( old2newIDmigration ) =>
        val entries = old2newIDmigration.getEntries
        guiLog.log(s" Loaded ${entries.size} old2new ID migration entries" )
        
        val resetElements = entries map { entry => project.getElementByID( entry.getOldID.get ) }
        val resetMap = entries flatMap { entry =>
          val oldId = entry.getOldID.get
          val newId = entry.getNewID.get
          val e = project.getElementByID( oldId ).asInstanceOf[Element]
          val uuid = UUIDRegistry.getUUID( e )
          Seq( oldId -> newId, uuid -> uuid )
        } toMap;
        
        val runnable = new RunnableWithProgress() {
          def run( progressStatus: ProgressStatus ): Unit = {                      
            ChangeElementID.resetIDS( project, resetElements, resetMap, progressStatus )
          }
        }
        
        MagicDrawProgressStatusRunner.runWithProgressStatus( runnable, s"Change XMI:IDs", true, 0 )
                
        if (entries.size > 2000) {
          entries foreach { entry => System.out.println( s" new=${entry.getNewID.get} => old=${entry.getOldID.get}")}
        } else {
          entries foreach { entry => 
            val id = entry.getNewID.get
            val e = project.getElementByID( id )
            guiLog.addHyperlinkedText(
              s" new=<A>${id}</A> <= old=${entry.getOldID.get}",
              Map( id -> new SelectInContainmentTreeRunnable( e ) ) )
          }
        }
        
        Success( None )
    }
  }
}