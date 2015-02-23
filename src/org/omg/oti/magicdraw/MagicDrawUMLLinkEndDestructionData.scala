package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkEndDestructionData 
  extends UMLLinkEndDestructionData[MagicDrawUML]
  with MagicDrawUMLLinkEndData {

  override protected def e: Uml#LinkEndDestructionData
  import ops._

}
