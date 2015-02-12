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
object setOTIUUIDs {

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
    
    val pkg = umlPackage(top)
    
    val mdCounter = p.getCounter
    val flag = mdCounter.canResetIDForObject
    mdCounter.setCanResetIDForObject(true)
    
    guiLog.log(s"Resetting xmi:uuid to 'org.omg.' + xmi:id (which should have been reset to OTI Canonical xmi:id)")
    
    try {
      UUIDRegistry.setUUID( pkg.getMagicDrawElement, s"org.omg.${pkg.id}" )
      for { 
        e <- pkg.allOwnedElements
      } {
        UUIDRegistry.setUUID( e.getMagicDrawElement, s"org.omg.${e.id}" )
      }
      guiLog.log("Done")
    } finally {
      mdCounter.setCanResetIDForObject(flag)
    }
    Success( None )
  }
}