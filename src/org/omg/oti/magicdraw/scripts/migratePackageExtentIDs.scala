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

    val proxyManager = project.getProxyManager
    if ( !proxyManager.isElementProxy( pkg ) )
      return Failure( new IllegalArgumentException( s"Select a r/o, proxy shared package" ) )

    val proxyAttachedProject = ProjectUtilities.getAttachedProject( pkg )
    val uri = proxyAttachedProject.getLocationURI
    require( uri.isFile() )

    val migrationF = new File( new File( uri.path ).getParentFile.getParentFile, uri.trimFileExtension.lastSegment + ".migration.xmi" )
    guiLog.log( s"Migration file: ${migrationF}" )
    require( migrationF.exists && migrationF.canRead )
    
    MigrationHelper.migrate( project, migrationF )
  }
}