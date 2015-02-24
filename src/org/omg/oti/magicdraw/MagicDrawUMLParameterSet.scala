package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLParameterSet 
  extends UMLParameterSet[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#ParameterSet
  import ops._

  override def condition: Set[UMLConstraint[Uml]] =
    e.getCondition.toSet[Uml#Constraint]
  
  override def parameter: Set[UMLParameter[Uml]] =
    e.getParameter.toSet[Uml#Parameter]
  
  
}
