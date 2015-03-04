package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDurationObservation 
  extends UMLDurationObservation[MagicDrawUML]
  with MagicDrawUMLObservation {

  override protected def e: Uml#DurationObservation
  import ops._

  override def event =
    e.getEvent.toSeq
    
  override def firstEvent =
    e.isFirstEvent() map ((b) => if (b) true else false) toSeq
  
}
