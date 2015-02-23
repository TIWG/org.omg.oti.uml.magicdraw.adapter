package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLObjectFlow 
  extends UMLObjectFlow[MagicDrawUML]
  with MagicDrawUMLActivityEdge {

  override protected def e: Uml#ObjectFlow
  import ops._

}
