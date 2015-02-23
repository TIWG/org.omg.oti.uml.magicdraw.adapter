package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkEndData 
  extends UMLLinkEndData[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#LinkEndData
  import ops._

}
