package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCreateObjectAction 
  extends UMLCreateObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#CreateObjectAction
  import ops._

	override def classifier: Option[UMLClassifier[Uml]] = ???
  
  override def result: Option[UMLOutputPin[Uml]] = ??? 
  
}
