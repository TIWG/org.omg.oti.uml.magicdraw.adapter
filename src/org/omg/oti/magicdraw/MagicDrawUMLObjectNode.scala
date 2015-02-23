package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLObjectNode 
  extends UMLObjectNode[MagicDrawUML]
  with MagicDrawUMLActivityNode
  with MagicDrawUMLTypedElement {

  override protected def e: Uml#ObjectNode
  import ops._

}
