package org.omg.oti.magicdraw

import org.omg.oti._

import scala.collection.JavaConversions._

trait MagicDrawUMLClassifier extends UMLClassifier[MagicDrawUML] with MagicDrawUMLNamespace with MagicDrawUMLType with MagicDrawUMLRedefinableElement {
  override protected def e: Uml#Classifier
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def attribute: Seq[UMLProperty[Uml]] = e.getAttribute.toSeq
  
  def classifierOfInstanceSpecifications: Set[UMLInstanceSpecification[Uml]] = umlInstanceSpecification( e.get_instanceSpecificationOfClassifier.toSet )
  
}