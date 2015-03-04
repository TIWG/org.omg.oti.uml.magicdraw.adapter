package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadLinkAction 
  extends UMLReadLinkAction[MagicDrawUML]
  with MagicDrawUMLLinkAction {

  override protected def e: Uml#ReadLinkAction
  import ops._

  // 16.34
  override def result: Option[UMLOutputPin[Uml]] = ??? 
}
