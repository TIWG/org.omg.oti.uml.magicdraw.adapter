package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCallBehaviorAction 
  extends UMLCallBehaviorAction[MagicDrawUML]
  with MagicDrawUMLCallAction {

  override protected def e: Uml#CallBehaviorAction
  import ops._

  override def behavior: Option[UMLBehavior[Uml]] =
    Option.apply( e.getBehavior )
    
}
