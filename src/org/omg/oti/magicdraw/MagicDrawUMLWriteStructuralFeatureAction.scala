package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLWriteStructuralFeatureAction 
  extends UMLWriteStructuralFeatureAction[MagicDrawUML]
  with MagicDrawUMLStructuralFeatureAction {

  override protected def e: Uml#WriteStructuralFeatureAction
  import ops._

  override def result: Option[UMLOutputPin[Uml]] =
    Option.apply( e.getResult )
    
  override def value: Option[UMLInputPin[Uml]] =
    Option.apply( e.getValue )
    
}
