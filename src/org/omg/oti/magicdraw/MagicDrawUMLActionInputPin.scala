package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLActionInputPin 
  extends UMLActionInputPin[MagicDrawUML]
  with MagicDrawUMLInputPin {

  override protected def e: Uml#ActionInputPin
  import ops._

  override def fromAction = Option.apply( e.getFromAction )
}
