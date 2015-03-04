package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTimeObservation 
  extends UMLTimeObservation[MagicDrawUML]
  with MagicDrawUMLObservation {

  override protected def e: Uml#TimeObservation
  import ops._

  override def event = 
    Option.apply( e.getEvent )
    
  override def firstEvent =
    e.isFirstEvent
  
}
