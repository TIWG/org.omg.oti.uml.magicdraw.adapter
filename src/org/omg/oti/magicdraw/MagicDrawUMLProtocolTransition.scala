package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLProtocolTransition 
  extends UMLProtocolTransition[MagicDrawUML]
  with MagicDrawUMLTransition {

  override protected def e: Uml#ProtocolTransition
  import ops._

  // 14.41
  override def postCondition: Option[UMLConstraint[Uml]] = ???
  
  // 14.41
  override def preCondition: Option[UMLConstraint[Uml]] = ???
  
  
}
