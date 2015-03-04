package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLMergeNode 
  extends UMLMergeNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#MergeNode
  import ops._

}
