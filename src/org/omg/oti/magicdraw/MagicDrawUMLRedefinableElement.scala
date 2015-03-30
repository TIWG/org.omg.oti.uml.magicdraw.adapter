package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLRedefinableElement 
  extends UMLRedefinableElement[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  import ops._
  override protected def e: Uml#RedefinableElement
    
  override def isLeaf = e.isLeaf
  
  override def redefinedElement: Set[UMLRedefinableElement[Uml]] = 
    e.getRedefinedElement.toSet[Uml#RedefinableElement]
  
  override def redefinitionContext: Iterable[UMLClassifier[Uml]] = 
    e.getRedefinitionContext.toIterable
  
  override def redefinedElement_redefinableElement: Set[UMLRedefinableElement[Uml]] =  
    e.get_redefinableElementOfRedefinedElement.toSet[Uml#RedefinableElement]
  
}