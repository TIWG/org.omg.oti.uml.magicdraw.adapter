package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDuration 
  extends UMLDuration[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#Duration
  import ops._

  override def expr =
    Option.apply( e.getExpr ) 
    
  override def observation =
    e.getObservation.toSet[Uml#Observation]
  
  override def max_durationInterval =
    e.get_durationIntervalOfMax.toSet[Uml#DurationInterval]
    
  override def min_durationInterval =
    e.get_durationIntervalOfMin.toSet[Uml#DurationInterval]
    
}
