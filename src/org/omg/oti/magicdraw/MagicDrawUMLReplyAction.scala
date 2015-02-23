package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReplyAction 
  extends UMLReplyAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReplyAction
  import ops._

}
