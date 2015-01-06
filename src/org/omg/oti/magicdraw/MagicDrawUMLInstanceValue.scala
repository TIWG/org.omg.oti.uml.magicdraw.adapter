package org.omg.oti.magicdraw

import org.omg.oti.UMLInstanceValue

trait MagicDrawUMLInstanceValue extends UMLInstanceValue[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#InstanceValue
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def instance = Option.apply( e.getInstance )
}