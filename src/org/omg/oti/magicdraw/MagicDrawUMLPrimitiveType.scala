package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPrimitiveType 
  extends UMLPrimitiveType[MagicDrawUML]
  with MagicDrawUMLDataType {

  import ops._
  override protected def e: Uml#PrimitiveType
  
}