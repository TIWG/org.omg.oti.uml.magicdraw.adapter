package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLSubstitution 
  extends UMLSubstitution[MagicDrawUML]
  with MagicDrawUMLRealization {

  override protected def e: Uml#Substitution
  import ops._

  override def contract: Option[UMLClassifier[Uml]] =
    Option.apply(e.getContract)
    
  override def substitutingClassifier: Option[UMLClassifier[Uml]] =
    Option.apply(e.getSubstitutingClassifier)
  
}
