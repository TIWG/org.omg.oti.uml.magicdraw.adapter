package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInformationItem 
  extends UMLInformationItem[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#InformationItem
  import ops._

}
