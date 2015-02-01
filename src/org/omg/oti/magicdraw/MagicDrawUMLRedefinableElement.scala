package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._

trait MagicDrawUMLRedefinableElement extends UMLRedefinableElement[MagicDrawUML] with MagicDrawUMLNamedElement {
  override protected def e: Uml#RedefinableElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
    
  def redefiningElements = umlRedefinableElement( e.get_redefinableElementOfRedefinedElement )
  
  def redefinedElements: Iterable[UMLRedefinableElement[Uml]] = umlRedefinableElement( e.getRedefinedElement )
  def redefinitionContexts: Iterable[UMLClassifier[Uml]] = umlClassifier( e.getRedefinitionContext )
  
}