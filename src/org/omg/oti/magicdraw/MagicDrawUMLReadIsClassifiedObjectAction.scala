package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadIsClassifiedObjectAction 
  extends UMLReadIsClassifiedObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadIsClassifiedObjectAction
  import ops._

  // 16.30
  override def classifier: Option[UMLClassifier[Uml]] = ??? 
  
  // 16.30
  override def isDirect: Boolean = ???
  
  // 16.30
  override def _object: Option[UMLInputPin[Uml]] = ???
  
  // 16.30
  override def result: Option[UMLOutputPin[Uml]] = ??? 
}
