package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExecutableNode 
  extends UMLExecutableNode[MagicDrawUML]
  with MagicDrawUMLActivityNode {

  override protected def e: Uml#ExecutableNode
  import ops._

}
