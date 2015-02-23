package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInterval 
  extends UMLInterval[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#Interval
  import ops._
}