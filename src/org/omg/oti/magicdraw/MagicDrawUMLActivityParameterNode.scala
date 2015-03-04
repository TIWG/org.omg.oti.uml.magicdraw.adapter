package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLActivityParameterNode 
  extends UMLActivityParameterNode[MagicDrawUML]
  with MagicDrawUMLObjectNode {

  override protected def e: Uml#ActivityParameterNode
  import ops._

  override def parameter: Option[UMLParameter[Uml]] = 
    Option.apply( e.getParameter )

}
