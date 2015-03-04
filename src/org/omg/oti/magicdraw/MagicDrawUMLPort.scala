package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLPort 
  extends UMLPort[MagicDrawUML]
  with MagicDrawUMLProperty {

  import ops._
  override protected def e: Uml#Port

  // 11.10
	override def isBehavior: Boolean = ???
  
  // 11.10
  override def isConjugated: Boolean = ???
  
  // 11.10
  override def isService: Boolean = ???
  
  // 11.10
  override def protocol: Option[UMLProtocolStateMachine[Uml]] = 
    Option.apply( e.getProtocol )
  
  // 11.10
  override def provided: Set[UMLInterface[Uml]] = ???
  
  // 11.10
  override def required: Set[UMLInterface[Uml]] = ???
  
  // 13.2
	override def port_trigger: Set[UMLTrigger[Uml]] = ???
  
  // 16.13
  override def onPort_invocationAction: Set[UMLInvocationAction[Uml]] = ???
}