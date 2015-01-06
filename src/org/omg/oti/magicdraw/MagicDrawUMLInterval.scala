package org.omg.oti.magicdraw

import org.omg.oti.UMLInterval

trait MagicDrawUMLInterval extends UMLInterval[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#Interval
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}