package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLEnumerationLiteral 
  extends UMLEnumerationLiteral[MagicDrawUML]
  with MagicDrawUMLInstanceSpecification {

  override protected def e: Uml#EnumerationLiteral
  import ops._
    
  override def classifier = 
    Set[Uml#Classifier](e.getEnumeration)

}