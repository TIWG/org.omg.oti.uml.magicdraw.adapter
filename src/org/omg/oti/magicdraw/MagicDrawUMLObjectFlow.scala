package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLObjectFlow 
  extends UMLObjectFlow[MagicDrawUML]
  with MagicDrawUMLActivityEdge {

  override protected def e: Uml#ObjectFlow
  import ops._

  // 15.1
  override def isMulticast: Boolean = ???
  
  // 15.1
  override def isMultireceive: Boolean = ???
  
  // 15.1
  override def selection: Option[UMLBehavior[Uml]] = ???
  
  // 15.1
  override def transformation: Option[UMLBehavior[Uml]] = ???
  
  // 15.1
  override def decisionInputFlow_decisionNode: Option[UMLDecisionNode[Uml]] = ???
  
}
