package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStateInvariant 
  extends UMLStateInvariant[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#StateInvariant
  import ops._

}
