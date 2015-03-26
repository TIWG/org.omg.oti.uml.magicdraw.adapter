package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._
import scala.collection.JavaConversions._

trait MagicDrawUMLStateMachine 
  extends UMLStateMachine[MagicDrawUML]
  with MagicDrawUMLBehavior {

  override protected def e: Uml#StateMachine
  import ops._

  // 14.1
  def submachineState: Set[UMLState[Uml]] = ???
  
  override def extendedStateMachine: Set[UMLStateMachine[Uml]] =
    umlStateMachine( e.getExtendedStateMachine.toSet )
    
  override def extendedStateMachine_stateMachine: Set[UMLStateMachine[Uml]] =
    umlStateMachine( e.get_stateMachineOfExtendedStateMachine.toSet )    

}
