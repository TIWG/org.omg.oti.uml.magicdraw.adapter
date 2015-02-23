package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDevice 
  extends UMLDevice[MagicDrawUML]
  with MagicDrawUMLNode {

  override protected def e: Uml#Device
  import ops._

}
