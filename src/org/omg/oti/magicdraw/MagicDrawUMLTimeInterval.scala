package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTimeInterval 
  extends UMLTimeInterval[MagicDrawUML]
  with MagicDrawUMLInterval {

  override protected def e: Uml#TimeInterval
  import ops._

  override def max =
    Option.apply( e.getMax )
    
  override def min =
    Option.apply( e.getMin )

}
