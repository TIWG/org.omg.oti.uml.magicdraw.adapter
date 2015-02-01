package org.omg.oti.magicdraw

import org.omg.oti._

import scala.collection.JavaConversions._

trait MagicDrawUMLClassifier extends UMLClassifier[MagicDrawUML] with MagicDrawUMLNamespace with MagicDrawUMLType with MagicDrawUMLRedefinableElement {
  override protected def e: Uml#Classifier
  
  import ops._
    
  override def isAbstract = e.isAbstract()
  
  def attributes = e.getAttribute.toSeq
  
  def instanceSpecifications: Set[UMLInstanceSpecification[Uml]] = umlInstanceSpecification( e.get_instanceSpecificationOfClassifier.toSet )
  
}