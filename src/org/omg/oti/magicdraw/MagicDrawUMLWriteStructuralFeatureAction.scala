package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLWriteStructuralFeatureAction 
  extends UMLWriteStructuralFeatureAction[MagicDrawUML]
  with MagicDrawUMLStructuralFeatureAction {

  override protected def e: Uml#WriteStructuralFeatureAction
  import ops._

}
