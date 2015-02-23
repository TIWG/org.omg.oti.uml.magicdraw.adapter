package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLClass 
  extends UMLClass[MagicDrawUML]
  with MagicDrawUMLEncapsulatedClassifier
  with MagicDrawUMLBehavioredClassifier {

  override protected def e: Uml#Class
  import ops._
  
  override def extension = e.getExtension.toSet[Uml#Extension]
  
  override def isAbstract = e.isAbstract
  
  override def isActive = e.isActive
  
  override def nestedClassifier = e.getNestedClassifier.toSeq
  
  override def ownedAttribute = e.getOwnedAttribute.toSeq
  
  override def ownedOperation = e.getOwnedOperation.toSeq
  
}