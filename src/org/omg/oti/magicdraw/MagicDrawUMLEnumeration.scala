package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLEnumeration extends UMLEnumeration[MagicDrawUML] with MagicDrawUMLDataType {
  override protected def e: Uml#Enumeration
  
  import ops._
  
  def ownedLiterals = e.getOwnedLiteral.toSeq
  
}