package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLActionExecutionSpecification 
  extends UMLActionExecutionSpecification[MagicDrawUML]
  with MagicDrawUMLExecutionSpecification {

  override protected def e: Uml#ActionExecutionSpecification
  import ops._

  override def action = 
    Option.apply( e.getAction )
}
