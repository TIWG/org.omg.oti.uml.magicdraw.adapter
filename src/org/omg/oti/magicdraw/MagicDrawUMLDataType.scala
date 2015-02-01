package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLDataType extends UMLDataType[MagicDrawUML] with MagicDrawUMLClassifier {
  override protected def e: Uml#DataType
  
  import ops._
  
  def ownedAttributes = umlProperty( e.getOwnedAttribute )
  def ownedOperations = umlOperation( e.getOwnedOperation )
  
}