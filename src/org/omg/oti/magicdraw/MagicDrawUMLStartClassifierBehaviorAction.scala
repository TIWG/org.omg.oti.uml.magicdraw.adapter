package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStartClassifierBehaviorAction 
  extends UMLStartClassifierBehaviorAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#StartClassifierBehaviorAction
  import ops._

}
