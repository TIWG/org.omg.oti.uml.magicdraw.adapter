package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInputPin 
  extends UMLInputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#InputPin
  import ops._

}
