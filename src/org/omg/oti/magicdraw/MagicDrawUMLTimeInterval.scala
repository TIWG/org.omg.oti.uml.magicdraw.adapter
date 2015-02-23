package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTimeInterval 
  extends UMLTimeInterval[MagicDrawUML]
  with MagicDrawUMLInterval {

  override protected def e: Uml#TimeInterval
  import ops._

}
