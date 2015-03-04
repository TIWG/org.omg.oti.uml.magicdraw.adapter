package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInterval 
  extends UMLInterval[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#Interval
  import ops._
  
  override def max = 
    Option.apply( e.getMax )
    
  override def min =
    Option.apply( e.getMin )

}