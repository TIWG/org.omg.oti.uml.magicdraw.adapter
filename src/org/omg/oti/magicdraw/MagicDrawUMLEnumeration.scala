package org.omg.oti.magicdraw

import org.omg.oti.UMLEnumeration

trait MagicDrawUMLEnumeration extends UMLEnumeration[MagicDrawUML] with MagicDrawUMLDataType {
  override protected def e: Uml#Enumeration
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}