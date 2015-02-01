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
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes.MainToolbarMenuAction
import com.nomagic.magicdraw.core.project.ProjectsManager
import javax.swing.JFileChooser
import com.nomagic.magicdraw.actions.ActionsID
import com.nomagic.magicdraw.actions.ActionsProvider
import gov.nasa.jpl.magicdraw.enhanced.migration.LocalModuleMigrationInterceptor
import javax.swing.filechooser.FileFilter

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object nameTest {

  def doit( p: Project, ev: ActionEvent, script: MainToolbarMenuAction ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val ap = ActionsProvider.getInstance
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val selectedElements = getMDBrowserSelectedElements map { e => umlElement( e ) }
    selectedElements foreach { e =>

      guiLog.log( s" ID=${e.id}" )

      val tv = e.tagValues
      guiLog.log( s"tv: ${tv.size}" )
      tv foreach {
        case ( tvp, tvv ) =>
          guiLog.log( s" => ${tvp.qualifiedName.get}: ${tvv}" )

      }

      val mdE = e.getMagicDrawElement      
      val mdIS = Option.apply( mdE.getAppliedStereotypeInstance ) 
      guiLog.log( s" mdID=${mdE.getID}: mdIS=${mdIS.isDefined} =${mdIS}" )

      mdIS match {
        case None =>
          guiLog.log( s" no AppliedStereotypeInstance!" )

        case Some( is ) =>
          guiLog.log( s" AppliedStereotypeInstance: ${is.getSlot.size} slots" )

          for {
            s <- is.getSlot
            p = s.getDefiningFeature match { case p: Uml#Property => umlProperty( p ) }
            v = umlValueSpecification( s.getValue ).toSeq
          } {
            guiLog.log( s" => ${p.qualifiedName.get}: ${s.getValue}" )
            guiLog.log( s" => ${p.qualifiedName.get}: ${v}" )
          }
      }
    }

    Success( None )
  }

  def getMDBrowserSelectedElements(): Set[Element] = {
    val project = Application.getInstance().getProjectsManager().getActiveProject()
    if ( null == project )
      return Set()

    val tab = project.getBrowser().getActiveTree()
    val elementFilter: ( Node => Option[Element] ) = { n => if ( n.getUserObject().isInstanceOf[Element] ) Some( n.getUserObject().asInstanceOf[Element] ) else None }
    val elements = tab.getSelectedNodes().map( elementFilter( _ ) ) filter ( _.isDefined ) map ( _.get )
    elements.toSet
  }
}