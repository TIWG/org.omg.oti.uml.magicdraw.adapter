package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLGeneralizationSet 
  extends UMLGeneralizationSet[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#GeneralizationSet
  import ops._

}
