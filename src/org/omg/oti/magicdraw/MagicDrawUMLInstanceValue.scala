package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInstanceValue 
  extends UMLInstanceValue[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#InstanceValue
  import ops._
  
  def instance = Option.apply( e.getInstance )
}