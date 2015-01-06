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
object openLocalProjectSkipLocalModule {

  def doit( project: Project, ev: ActionEvent, script: MainToolbarMenuAction ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val ap = ActionsProvider.getInstance
    val mainToolbarActionMgr = ap.getMainToolbarActions
    val openProjectAction = mainToolbarActionMgr.getActionFor( ActionsID.OPEN_PROJECT )

    val mdInstallDir = new File( ApplicationEnvironment.getInstallRoot )
    require( mdInstallDir.exists && mdInstallDir.isDirectory )
    val otiDir = new File( mdInstallDir, "dynamicScripts/org.omg.oti" )
    require( otiDir.exists && otiDir.isDirectory )
    val migrationMM = Metamodel( otiDir )

    val guiLog = a.getGUILog()
    guiLog.clearLog()

    val dir = ProjectsManager.getRecentFilePath match {
      case _@ ( null | "" ) =>
        System.getProperty( "user.dir" ) match {
          case _@ ( null | "" ) => "."
          case d                => d
        }
      case d => d
    }
    
    val ff = new FileFilter() {
      
      def getDescription: String = "*.migration.xmi"
      
      def accept(f: File): Boolean = 
        f.isDirectory() ||
        ( f.isFile() && f.getName.endsWith(".migration.xmi") )
        
    }

    val fc = new JFileChooser( mdInstallDir ) {

      override def getFileSelectionMode: Int = JFileChooser.FILES_ONLY

      override def getDialogTitle = "Select a *.migration.xmi file"
    }

    fc.setFileFilter( ff )
    fc.setFileHidingEnabled(true)
    fc.setAcceptAllFileFilterUsed( false )
    
    fc.showOpenDialog( a.getMainFrame ) match {
      case JFileChooser.APPROVE_OPTION =>
        val migrationFile = fc.getSelectedFile
        require( migrationFile.exists && migrationFile.canRead )
        val migrationURI = URI.createFileURI( migrationFile.getAbsolutePath )

        migrationMM.loadOld2NewIDMappingResource( migrationURI ) match {
          case Failure( t ) => Failure( t )
          case Success( old2newIDmigration ) =>
            val entries = old2newIDmigration.getEntries
            guiLog.log( s" Loaded ${entries.size} old2new ID migration entries" )

            require( old2newIDmigration.getModelIdentifier.isDefined )
            val oldModuleFile = new File( mdInstallDir, old2newIDmigration.getModelIdentifier.get )
            require( oldModuleFile.exists && oldModuleFile.canRead )
            
            LocalModuleMigrationInterceptor.clearForceSkipLocalModules
            LocalModuleMigrationInterceptor.addForceSkipLocalModule( URI.createFileURI( oldModuleFile.getAbsolutePath ))
            
            try {
              openProjectAction.actionPerformed( ev )
            } finally {
              LocalModuleMigrationInterceptor.clearForceSkipLocalModules
            }
        }
      case _ =>
        System.out.println( s"openLocalProjectSkipLocalModule: cancelled" )
    }
    Success( None )
  }
}