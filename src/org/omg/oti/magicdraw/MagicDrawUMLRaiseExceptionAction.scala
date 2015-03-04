package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLRaiseExceptionAction 
  extends UMLRaiseExceptionAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#RaiseExceptionAction
  import ops._

  // 16.56
  override def exception: Option[UMLInputPin[Uml]] = ???
}
