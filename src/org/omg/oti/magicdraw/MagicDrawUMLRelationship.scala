package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRelationship 
  extends UMLRelationship[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#Relationship
  import ops._
  
  override def relatedElement = e.getRelatedElement.toSet[Uml#Element]
  
  override def realization_abstraction = e.get_abstraction.toSet[Uml#InformationFlow]
  
}