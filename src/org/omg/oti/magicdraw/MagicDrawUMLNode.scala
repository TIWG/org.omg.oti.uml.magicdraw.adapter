package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLNode 
  extends UMLNode[MagicDrawUML]
  with MagicDrawUMLClass
  with MagicDrawUMLDeploymentTarget {

  override protected def e: Uml#Node
  import ops._

}
