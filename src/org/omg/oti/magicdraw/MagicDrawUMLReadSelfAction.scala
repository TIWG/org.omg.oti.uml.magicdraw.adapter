package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadSelfAction 
  extends UMLReadSelfAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadSelfAction
  import ops._

  // 16.30
	override def result: Option[UMLOutputPin[Uml]] = ??? 
}
