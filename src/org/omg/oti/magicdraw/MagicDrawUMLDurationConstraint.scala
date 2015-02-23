package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDurationConstraint 
  extends UMLDurationConstraint[MagicDrawUML]
  with MagicDrawUMLIntervalConstraint {

  override protected def e: Uml#DurationConstraint
  import ops._

}
