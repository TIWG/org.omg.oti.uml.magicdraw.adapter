package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLValuePin 
  extends UMLValuePin[MagicDrawUML]
  with MagicDrawUMLInputPin {

  override protected def e: Uml#ValuePin
  import ops._

}
