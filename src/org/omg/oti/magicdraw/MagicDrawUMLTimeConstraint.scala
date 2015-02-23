package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTimeConstraint 
  extends UMLTimeConstraint[MagicDrawUML]
  with MagicDrawUMLIntervalConstraint {

  override protected def e: Uml#TimeConstraint
  import ops._

}
