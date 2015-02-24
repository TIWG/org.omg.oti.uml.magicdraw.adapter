package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadExtentAction 
  extends UMLReadExtentAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadExtentAction
  import ops._

  // 16.30
  override def classifier: Option[UMLClassifier[Uml]] = ??? 
  
  // 16.30
  override def result: Option[UMLOutputPin[Uml]] = ??? 
}
