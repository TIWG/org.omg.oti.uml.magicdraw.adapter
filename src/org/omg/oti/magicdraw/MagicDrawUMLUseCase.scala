package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLUseCase 
  extends UMLUseCase[MagicDrawUML]
  with MagicDrawUMLBehavioredClassifier {

  override protected def e: Uml#UseCase
  import ops._

  override def subject: Set[UMLClassifier[Uml]] =
    e.getSubject.toSet[Uml#Classifier]
  
  override def extendedCase_extend: Set[UMLExtend[Uml]] =
    e.get_extendOfExtendedCase.toSet[Uml#Extend]
  
  override def addition_include: Set[UMLInclude[Uml]] =
    e.get_includeOfAddition.toSet[Uml#Include]
    
	override def ownedUseCase_classifier: Option[UMLClassifier[Uml]] = ???
  
}