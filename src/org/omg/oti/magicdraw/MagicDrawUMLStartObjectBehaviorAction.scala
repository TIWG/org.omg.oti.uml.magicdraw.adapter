package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLStartObjectBehaviorAction 
  extends UMLStartObjectBehaviorAction[MagicDrawUML]
  with MagicDrawUMLCallAction {

  override protected def e: Uml#StartObjectBehaviorAction
  import ops._

  // 16.13
  override def _object: Option[UMLInputPin[Uml]] = ???
  
}
