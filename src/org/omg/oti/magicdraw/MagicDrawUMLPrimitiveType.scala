package org.omg.oti.magicdraw

import org.omg.oti.UMLPrimitiveType

trait MagicDrawUMLPrimitiveType extends UMLPrimitiveType[MagicDrawUML] with MagicDrawUMLDataType {
  override protected def e: Uml#PrimitiveType
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}