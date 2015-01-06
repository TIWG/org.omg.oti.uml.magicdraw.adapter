package org.omg.oti.magicdraw

import org.omg.oti.UMLType

import scala.collection.JavaConversions._

trait MagicDrawUMLType extends UMLType[MagicDrawUML] with MagicDrawUMLPackageableElement {
  override protected def e: Uml#Type
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def typedElementsOfType = e.get_typedElementOfType.toIterator
  
  def endTypeOfAssociation = e.get_associationOfEndType.toIterator
}