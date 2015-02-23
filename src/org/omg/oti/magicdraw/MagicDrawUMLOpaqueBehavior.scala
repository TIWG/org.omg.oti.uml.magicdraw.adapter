package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOpaqueBehavior 
  extends UMLOpaqueBehavior[MagicDrawUML]
  with MagicDrawUMLBehavior {

  override protected def e: Uml#OpaqueBehavior
  import ops._

}
