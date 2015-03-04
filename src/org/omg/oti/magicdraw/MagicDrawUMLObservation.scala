package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLObservation 
  extends UMLObservation[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Observation
  import ops._

  override def observation_timeExpression =
    Option.apply( e.get_timeExpressionOfObservation )
    
  override def observation_duration =
    Option.apply( e.get_durationOfObservation )
    
}
