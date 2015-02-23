package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLEnumeration 
  extends UMLEnumeration[MagicDrawUML]
  with MagicDrawUMLDataType {

  override protected def e: Uml#Enumeration
  import ops._
  
  override def ownedLiteral = e.getOwnedLiteral.toSeq
  
}