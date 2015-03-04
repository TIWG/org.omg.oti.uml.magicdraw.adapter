package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLUsage 
  extends UMLUsage[MagicDrawUML]
  with MagicDrawUMLDependency {

  override protected def e: Uml#Usage
  import ops._

}
