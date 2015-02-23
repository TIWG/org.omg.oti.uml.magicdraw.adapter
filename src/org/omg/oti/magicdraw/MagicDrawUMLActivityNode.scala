package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityNode 
  extends UMLActivityNode[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#ActivityNode
  import ops._

}
