package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDataType 
  extends UMLDataType[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#DataType
  import ops._
  
  override def ownedAttribute = e.getOwnedAttribute.toSeq
  
  override def ownedOperation = e.getOwnedOperation.toSeq
  
}