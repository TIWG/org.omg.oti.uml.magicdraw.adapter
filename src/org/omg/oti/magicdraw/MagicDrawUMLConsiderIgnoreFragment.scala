package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLConsiderIgnoreFragment 
  extends UMLConsiderIgnoreFragment[MagicDrawUML]
  with MagicDrawUMLCombinedFragment {

  override protected def e: Uml#ConsiderIgnoreFragment
  import ops._

  override def message: Set[UMLNamedElement[Uml]] = ??? 

}
