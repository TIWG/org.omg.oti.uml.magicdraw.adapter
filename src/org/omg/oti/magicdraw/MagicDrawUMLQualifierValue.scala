package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLQualifierValue 
  extends UMLQualifierValue[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#QualifierValue
  import ops._

}
