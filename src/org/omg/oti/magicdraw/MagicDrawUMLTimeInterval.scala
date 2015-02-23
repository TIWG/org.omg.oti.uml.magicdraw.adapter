package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTimeInterval 
  extends UMLTimeInterval[MagicDrawUML]
  with MagicDrawUMLInterval {

  override protected def e: Uml#TimeInterval
  import ops._

  override def timeInterval_max =
    Option.apply( e.getMax )
    
  override def timeInterval_min =
    Option.apply( e.getMin )

}
