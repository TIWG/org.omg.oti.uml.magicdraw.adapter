package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLBehaviorExecutionSpecification 
  extends UMLBehaviorExecutionSpecification[MagicDrawUML]
  with MagicDrawUMLExecutionSpecification {

  override protected def e: Uml#BehaviorExecutionSpecification
  import ops._

  override def behavior: Option[UMLBehavior[Uml]] =
    Option.apply( e.getBehavior )
    
}
