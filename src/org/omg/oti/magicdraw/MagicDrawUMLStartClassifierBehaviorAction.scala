package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLStartClassifierBehaviorAction 
  extends UMLStartClassifierBehaviorAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#StartClassifierBehaviorAction
  import ops._

  // 16.30
  override def _object: Option[UMLInputPin[Uml]] = ???
  
}
