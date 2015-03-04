package org.omg.oti.magicdraw.scripts

import java.awt.event.ActionEvent

import scala.collection.JavaConversions.asJavaCollection
import scala.language.implicitConversions
import scala.language.postfixOps
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.openapi.uml.SessionManager
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement
import com.nomagic.magicdraw.uml.symbols.PresentationElement
import com.nomagic.magicdraw.uml.symbols.shapes.InstanceSpecificationView
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification

import org.omg.oti.api._
import org.omg.oti.magicdraw._

import gov.nasa.jpl.dynamicScripts._
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes._
import gov.nasa.jpl.dynamicScripts.magicdraw._
import gov.nasa.jpl.dynamicScripts.magicdraw.actions._

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object invokeMainToolbarMenuAction {

  def doit(
    p: Project,
    ev: ActionEvent,
    script: DynamicScriptsTypes.DiagramContextMenuAction,
    dpe: DiagramPresentationElement,
    triggerView: InstanceSpecificationView,
    triggerElement: InstanceSpecification,
    selection: java.util.Collection[PresentationElement] ): Try[Option[MagicDrawValidationDataResults]] = {

    implicit val umlUtil = MagicDrawUMLUtil( p )
    import umlUtil._

    val dsInstance = umlInstanceSpecification( triggerElement )
    for {
      classNames <- dsInstance.getValuesOfFeatureSlot( "className" )
      methodNames <- dsInstance.getValuesOfFeatureSlot( "methodName" )
    } ( classNames.toList, methodNames.toList ) match {
      case ( List( c: UMLLiteralString[Uml] ), List( m: UMLLiteralString[Uml] ) ) =>
        ( c.value, m.value ) match {
          case ( Some( className ), Some( methodName ) ) =>
            return invoke(
              p, ev, triggerElement,
              className, methodName )
          case ( _, _ ) =>
            ()
        }
      case ( _, _ ) =>
        ()
    }

    makeMDIllegalArgumentExceptionValidation(
      p,
      s"*** Ill-formed DiagramContextMenuActionForSelection instance specification ***",
      Map( triggerElement -> ( "Check the instance specification details", List() ) ),
      "*::MagicDrawOTIValidation",
      "*::UnresolvedCrossReference" )
  }

  def invoke(
    p: Project,
    ev: ActionEvent,
    invocation: InstanceSpecification,
    className: String, methodName: String )( implicit umlUtil: MagicDrawUMLUtil ): Try[Option[MagicDrawValidationDataResults]] = {

    import umlUtil._

    val dsPlugin = DynamicScriptsPlugin.getInstance
    val reg: DynamicScriptsRegistry = dsPlugin.getDynamicScriptsRegistry

    val scripts = for {
      ( _, menus ) <- reg.toolbarMenuPathActions
      menu <- menus
      script <- menu.scripts
      if ( script.className.jname == className && script.methodName.sname == methodName )
    } yield script

    if ( scripts.size != 1 )
      makeMDIllegalArgumentExceptionValidation(
        p,
        s"*** Ambiguous invocation; there are ${scripts.size} relevant dynamic script actions matching the class/method name criteria ***",
        Map( invocation -> ( "Check the instance specification details", List() ) ),
        "*::MagicDrawOTIValidation",
        "*::UnresolvedCrossReference" )

    else {
      val script = scripts.head
      val action = DynamicScriptsLaunchToolbarMenuAction( script, script.name.hname )

      val sm = SessionManager.getInstance
      if ( sm.isSessionCreated( p ) )
        sm.closeSession( p )

      action.actionPerformed( ev )
      Success( None )
    }
  }
}