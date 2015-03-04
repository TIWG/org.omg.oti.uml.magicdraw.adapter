package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLFinalNode 
  extends UMLFinalNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#FinalNode
  import ops._

}
