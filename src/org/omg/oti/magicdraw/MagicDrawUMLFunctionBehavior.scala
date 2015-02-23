package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLFunctionBehavior 
  extends UMLFunctionBehavior[MagicDrawUML]
  with MagicDrawUMLOpaqueBehavior {

  override protected def e: Uml#FunctionBehavior
  import ops._

}
