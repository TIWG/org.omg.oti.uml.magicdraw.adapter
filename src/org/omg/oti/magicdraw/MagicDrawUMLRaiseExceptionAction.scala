package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRaiseExceptionAction 
  extends UMLRaiseExceptionAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#RaiseExceptionAction
  import ops._

}
