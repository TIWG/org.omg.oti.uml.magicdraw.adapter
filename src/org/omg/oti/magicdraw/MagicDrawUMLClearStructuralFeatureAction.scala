package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLClearStructuralFeatureAction 
  extends UMLClearStructuralFeatureAction[MagicDrawUML]
  with MagicDrawUMLStructuralFeatureAction {

  override protected def e: Uml#ClearStructuralFeatureAction
  import ops._

  override def result: Option[UMLOutputPin[Uml]] =
    Option.apply( e.getResult )
}
