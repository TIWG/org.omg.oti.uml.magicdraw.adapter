package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLAbstraction 
  extends UMLAbstraction[MagicDrawUML]
  with MagicDrawUMLDependency {

  import ops._
  override protected def e: Uml#Abstraction

  override def mapping = Option.apply(e.getMapping)
}
