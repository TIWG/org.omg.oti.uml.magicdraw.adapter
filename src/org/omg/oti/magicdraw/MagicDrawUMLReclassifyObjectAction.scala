package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReclassifyObjectAction 
  extends UMLReclassifyObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReclassifyObjectAction
  import ops._

  // 16.30
	override def isReplaceAll: Boolean = ???
  
  // 16.30
	override def newClassifier: Set[UMLClassifier[Uml]] = ??? 
  
  // 16.30
	override def _object: Option[UMLInputPin[Uml]] = ???
  
  // 16.30  
	override def oldClassifier: Set[UMLClassifier[Uml]] = ???
  
}
