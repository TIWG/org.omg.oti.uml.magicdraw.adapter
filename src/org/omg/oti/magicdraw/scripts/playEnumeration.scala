package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent

import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.actions.ActionsProvider
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project

import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes.MainToolbarMenuAction
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults


object playEnumeration {

  def doit( p: Project, ev: ActionEvent, script: MainToolbarMenuAction ): Try[Option[MagicDrawValidationDataResults]] = {

    val a = Application.getInstance()
    val ap = ActionsProvider.getInstance
    val guiLog = a.getGUILog()
    guiLog.clearLog()

    
    guiLog.log( s"Done project id=${p.getID}, primary id = ${p.getPrimaryProject.getProjectID}" )
    Success( None )
  }

}