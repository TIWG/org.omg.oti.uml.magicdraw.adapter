package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTimeConstraint 
  extends UMLTimeConstraint[MagicDrawUML]
  with MagicDrawUMLIntervalConstraint {

  override protected def e: Uml#TimeConstraint
  import ops._

  override def firstEvent =
    if (e.isFirstEvent) None
    else Some( false )
    
  override def specification: Option[UMLTimeInterval[Uml]] =
    Option.apply( e.getSpecification )
    
}
