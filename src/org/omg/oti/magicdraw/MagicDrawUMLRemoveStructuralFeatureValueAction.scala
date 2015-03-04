package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLRemoveStructuralFeatureValueAction 
  extends UMLRemoveStructuralFeatureValueAction[MagicDrawUML]
  with MagicDrawUMLWriteStructuralFeatureAction {

  override protected def e: Uml#RemoveStructuralFeatureValueAction
  import ops._

  // 16.36
	override def isRemoveDuplicates: Boolean = ???
  
  // 16.36
	override def removeAt: Option[UMLInputPin[Uml]] = ???
}
