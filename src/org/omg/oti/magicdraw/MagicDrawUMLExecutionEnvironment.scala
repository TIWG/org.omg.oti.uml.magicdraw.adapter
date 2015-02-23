package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExecutionEnvironment 
  extends UMLExecutionEnvironment[MagicDrawUML]
  with MagicDrawUMLNode {

  override protected def e: Uml#ExecutionEnvironment
  import ops._

}
