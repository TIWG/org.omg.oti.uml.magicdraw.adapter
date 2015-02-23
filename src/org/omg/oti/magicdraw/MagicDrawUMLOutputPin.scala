package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOutputPin 
  extends UMLOutputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#OutputPin
  import ops._

}
