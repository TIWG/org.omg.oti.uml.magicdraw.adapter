package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLAddStructuralFeatureValueAction 
  extends UMLAddStructuralFeatureValueAction[MagicDrawUML]
  with MagicDrawUMLWriteStructuralFeatureAction {

  override protected def e: Uml#AddStructuralFeatureValueAction
  import ops._

}
