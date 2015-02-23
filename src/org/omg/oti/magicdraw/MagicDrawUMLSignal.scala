package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLSignal 
  extends UMLSignal[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#Signal
  import ops._

}
