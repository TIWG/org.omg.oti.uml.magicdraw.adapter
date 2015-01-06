package org.omg.oti.magicdraw

import org.omg.oti.UMLDataType

trait MagicDrawUMLDataType extends UMLDataType[MagicDrawUML] with MagicDrawUMLClassifier {
  override protected def e: Uml#DataType
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}