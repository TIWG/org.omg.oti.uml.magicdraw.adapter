package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRemoveStructuralFeatureValueAction 
  extends UMLRemoveStructuralFeatureValueAction[MagicDrawUML]
  with MagicDrawUMLWriteStructuralFeatureAction {

  override protected def e: Uml#RemoveStructuralFeatureValueAction
  import ops._

}
