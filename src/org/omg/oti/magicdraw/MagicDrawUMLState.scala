package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLState 
  extends UMLState[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLVertex {

  override protected def e: Uml#State
  import ops._

  // 14.1
  def doActivity: Option[UMLBehavior[Uml]] = ??? 
  def entry: Option[UMLBehavior[Uml]] = ??? 
  def exit: Option[UMLBehavior[Uml]] = ???
  
  // 14.1
  def submachine: Option[UMLStateMachine[Uml]] = ??? 
  
  // 15.48
  def inState_objectNode: Set[UMLObjectNode[Uml]] = ??? 
}
