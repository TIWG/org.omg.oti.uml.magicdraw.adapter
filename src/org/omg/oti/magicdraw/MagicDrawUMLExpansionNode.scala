package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExpansionNode 
  extends UMLExpansionNode[MagicDrawUML]
  with MagicDrawUMLObjectNode {

  override protected def e: Uml#ExpansionNode
  import ops._

}
