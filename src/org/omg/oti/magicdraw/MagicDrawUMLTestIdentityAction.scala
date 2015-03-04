package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTestIdentityAction 
  extends UMLTestIdentityAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#TestIdentityAction
  import ops._

  override def first: Option[UMLInputPin[Uml]] =
    Option.apply( e.getFirst )
      
	override def result: Option[UMLOutputPin[Uml]] =
    Option.apply( e.getResult )
    
  override def second: Option[UMLInputPin[Uml]] =
    Option.apply( e.getSecond )

}
