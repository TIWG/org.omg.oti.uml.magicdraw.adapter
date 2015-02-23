package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInterface 
  extends UMLInterface[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#Interface
  import ops._

}
