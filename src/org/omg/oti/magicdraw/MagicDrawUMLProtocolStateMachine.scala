package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLProtocolStateMachine 
  extends UMLProtocolStateMachine[MagicDrawUML]
  with MagicDrawUMLStateMachine {

  override protected def e: Uml#ProtocolStateMachine
  import ops._

  // 14.41
	override def conformance: Set[UMLProtocolConformance[Uml]] = ???
  
  // 14.41
  override def generalMachine_protocolConformance: Set[UMLProtocolConformance[Uml]] = ???
  
  // 11.10
	override def protocol_port: Set[UMLPort[Uml]] = ???
  
  // 10.7
  override def protocol_interface: Option[UMLInterface[Uml]] = ???
}
