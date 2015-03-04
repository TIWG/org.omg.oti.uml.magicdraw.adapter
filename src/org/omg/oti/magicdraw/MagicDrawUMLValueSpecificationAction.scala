package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLValueSpecificationAction 
  extends UMLValueSpecificationAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ValueSpecificationAction
  import ops._

  override def result: Option[UMLOutputPin[Uml]] =
    Option.apply( e.getResult )
    
  override def value: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getValue )
    
}
