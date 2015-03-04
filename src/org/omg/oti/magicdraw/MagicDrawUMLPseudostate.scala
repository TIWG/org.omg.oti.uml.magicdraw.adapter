package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLPseudostate 
  extends UMLPseudostate[MagicDrawUML]
  with MagicDrawUMLVertex {

  override protected def e: Uml#Pseudostate
  import ops._

  // 14.1
  override def kind: UMLPseudostateKind.Value = ???
  
  // 14.1
  override def entry_connectionPointReference: Option[UMLConnectionPointReference[Uml]] = ???
  
  // 14.1
  override def exit_connectionPointReference: Option[UMLConnectionPointReference[Uml]] = ???
  
}
