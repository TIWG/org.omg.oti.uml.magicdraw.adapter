package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLIntervalConstraint 
  extends UMLIntervalConstraint[MagicDrawUML]
  with MagicDrawUMLConstraint {

  override protected def e: Uml#IntervalConstraint
  import ops._

  override def specification: Option[UMLInterval[Uml]] =
    Option.apply( e.getSpecification )
    
}
