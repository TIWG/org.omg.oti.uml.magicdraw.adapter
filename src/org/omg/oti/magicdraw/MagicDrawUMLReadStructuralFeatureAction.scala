package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadStructuralFeatureAction 
  extends UMLReadStructuralFeatureAction[MagicDrawUML]
  with MagicDrawUMLStructuralFeatureAction {

  override protected def e: Uml#ReadStructuralFeatureAction
  import ops._

}
