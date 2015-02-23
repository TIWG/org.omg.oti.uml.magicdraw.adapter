package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCombinedFragment 
  extends UMLCombinedFragment[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#CombinedFragment
  import ops._

}
