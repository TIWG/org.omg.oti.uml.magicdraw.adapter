package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStructuralFeatureAction 
  extends UMLStructuralFeatureAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#StructuralFeatureAction
  import ops._

  override def _object: Option[UMLInputPin[Uml]] =
    Option.apply( e.getObject )
    
  override def structuralFeature: Option[UMLStructuralFeature[Uml]] =
    Option.apply( e.getStructuralFeature )
    
}
