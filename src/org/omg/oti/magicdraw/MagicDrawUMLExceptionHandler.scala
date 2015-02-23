package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExceptionHandler 
  extends UMLExceptionHandler[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#ExceptionHandler
  import ops._

}
