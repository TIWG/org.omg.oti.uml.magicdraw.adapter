package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLClassifierTemplateParameter 
  extends UMLClassifierTemplateParameter[MagicDrawUML]
  with MagicDrawUMLTemplateParameter {

  override protected def e: Uml#ClassifierTemplateParameter
  import ops._

  override def allowSubstitutable: Boolean =
    e.isAllowSubstitutable
    
  override def constrainingClassifier: Set[UMLClassifier[Uml]] =
    e.getConstrainingClassifier.toSet[Uml#Classifier]
 
  override def parameteredElement: Option[UMLClassifier[Uml]] =
    Option.apply( e.getParameteredElement )
    
}
