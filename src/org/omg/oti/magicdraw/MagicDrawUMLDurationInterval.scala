package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDurationInterval 
  extends UMLDurationInterval[MagicDrawUML]
  with MagicDrawUMLInterval {

  override protected def e: Uml#DurationInterval
  import ops._

  override def durationInterval_max =
    Option.apply( e.getMax )
    
  override def durationInterval_min =
    Option.apply( e.getMin )
    
}
