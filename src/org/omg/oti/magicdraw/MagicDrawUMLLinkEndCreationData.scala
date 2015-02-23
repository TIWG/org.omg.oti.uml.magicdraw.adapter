package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkEndCreationData 
  extends UMLLinkEndCreationData[MagicDrawUML]
  with MagicDrawUMLLinkEndData {

  override protected def e: Uml#LinkEndCreationData
  import ops._

}
