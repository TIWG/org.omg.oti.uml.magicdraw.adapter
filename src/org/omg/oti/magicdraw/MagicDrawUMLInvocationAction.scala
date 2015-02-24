package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInvocationAction 
  extends UMLInvocationAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#InvocationAction
  import ops._

  override def argument: Iterable[UMLInputPin[Uml]] =
    e.getArgument.toIterable
    
  override def onPort: Option[UMLPort[Uml]] =
    Option.apply( e.getOnPort )
    
}
