package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLConnectableElementTemplateParameter 
  extends UMLConnectableElementTemplateParameter[MagicDrawUML]
  with MagicDrawUMLTemplateParameter {

  override protected def e: Uml#ConnectableElementTemplateParameter
  import ops._

}
