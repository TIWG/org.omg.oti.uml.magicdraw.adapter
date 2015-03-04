package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTransition 
  extends UMLTransition[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#Transition
  import ops._

  override def kind: UMLTransitionKind.Value = ???

  override def source: Option[UMLVertex[Uml]] = ??? 

  override def target: Option[UMLVertex[Uml]] = ???
}
