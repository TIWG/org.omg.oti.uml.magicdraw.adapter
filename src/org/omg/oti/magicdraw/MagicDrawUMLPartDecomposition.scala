package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPartDecomposition 
  extends UMLPartDecomposition[MagicDrawUML]
  with MagicDrawUMLInteractionUse {

  override protected def e: Uml#PartDecomposition
  import ops._

}
