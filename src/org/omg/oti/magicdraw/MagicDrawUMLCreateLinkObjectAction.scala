package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCreateLinkObjectAction 
  extends UMLCreateLinkObjectAction[MagicDrawUML]
  with MagicDrawUMLCreateLinkAction {

  override protected def e: Uml#CreateLinkObjectAction
  import ops._

  override def result: Option[UMLOutputPin[Uml]] = ???
  
}
