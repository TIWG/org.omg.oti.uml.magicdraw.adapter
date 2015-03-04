package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadStructuralFeatureAction 
  extends UMLReadStructuralFeatureAction[MagicDrawUML]
  with MagicDrawUMLStructuralFeatureAction {

  override protected def e: Uml#ReadStructuralFeatureAction
  import ops._

  // 16.36  
	override def result: Option[UMLOutputPin[Uml]] = ??? 
}
