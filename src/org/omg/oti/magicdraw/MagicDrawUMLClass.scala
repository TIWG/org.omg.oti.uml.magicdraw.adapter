package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLClass extends UMLClass[MagicDrawUML] with MagicDrawUMLEncapsulatedClassifier with MagicDrawUMLBehavioredClassifier {
  override protected def e: Uml#Class
  
  import ops._
  
  override def isAbstract = e.isAbstract()
  
  def nestedClassifiers = e.getNestedClassifier.toSeq
  def ownedAttributes = e.getOwnedAttribute.toSeq
  def ownedOperations = e.getOwnedOperation.toSeq
}