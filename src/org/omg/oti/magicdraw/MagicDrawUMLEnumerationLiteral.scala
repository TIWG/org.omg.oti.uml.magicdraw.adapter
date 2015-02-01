package org.omg.oti.magicdraw

import org.omg.oti._

trait MagicDrawUMLEnumerationLiteral extends UMLEnumerationLiteral[MagicDrawUML] with MagicDrawUMLInstanceSpecification {
  override protected def e: Uml#EnumerationLiteral
  
  import ops._
    
  override def classifiers: Iterable[UMLClassifier[Uml]] = super.classifiers
  
}