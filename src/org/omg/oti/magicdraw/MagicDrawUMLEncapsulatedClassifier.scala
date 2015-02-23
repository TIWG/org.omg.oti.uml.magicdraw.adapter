package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLEncapsulatedClassifier 
  extends UMLEncapsulatedClassifier[MagicDrawUML]
  with MagicDrawUMLStructuredClassifier {

  override protected def e: Uml#EncapsulatedClassifier
  import ops._

}
