package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDecisionNode 
  extends UMLDecisionNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#DecisionNode
  import ops._

}
