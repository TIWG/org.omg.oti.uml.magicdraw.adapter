package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent
import java.io.File

import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.actions.ActionsID
import com.nomagic.magicdraw.actions.ActionsProvider
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project

import org.eclipse.emf.common.util.URI
import org.omg.oti.migration.Metamodel

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes.MainToolbarMenuAction
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import gov.nasa.jpl.magicdraw.enhanced.migration.LocalModuleMigrationInterceptor

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

    MigrationHelper.chooseMigrationFile match {
      case Some( migrationFile ) =>
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
            LocalModuleMigrationInterceptor.addForceSkipLocalModule( URI.createFileURI( oldModuleFile.getAbsolutePath ) )

            try {
              openProjectAction.actionPerformed( ev )
            }
            finally {
              LocalModuleMigrationInterceptor.clearForceSkipLocalModules
            }
        }
      case None =>
        System.out.println( s"openLocalProjectSkipLocalModule: cancelled" )
    }
    Success( None )
  }
}