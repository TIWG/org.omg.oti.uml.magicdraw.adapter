package org.omg.oti.magicdraw

import org.omg.oti.UMLRelationship

import scala.collection.JavaConversions._

trait MagicDrawUMLRelationship extends UMLRelationship[MagicDrawUML] with MagicDrawUMLElement {
  override protected def e: Uml#Relationship 

  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def relatedElements = e.getRelatedElement.toIterator
}