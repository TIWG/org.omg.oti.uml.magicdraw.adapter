package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionFragment 
  extends UMLInteractionFragment[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#InteractionFragment
  import ops._

}
