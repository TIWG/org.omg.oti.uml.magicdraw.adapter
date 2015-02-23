package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStartObjectBehaviorAction 
  extends UMLStartObjectBehaviorAction[MagicDrawUML]
  with MagicDrawUMLCallAction {

  override protected def e: Uml#StartObjectBehaviorAction
  import ops._

}
