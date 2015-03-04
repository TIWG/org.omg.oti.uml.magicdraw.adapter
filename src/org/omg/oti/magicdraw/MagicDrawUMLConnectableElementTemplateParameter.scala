package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLConnectableElementTemplateParameter 
  extends UMLConnectableElementTemplateParameter[MagicDrawUML]
  with MagicDrawUMLTemplateParameter {

  override protected def e: Uml#ConnectableElementTemplateParameter
  import ops._

  override def parameteredElement: Option[UMLConnectableElement[Uml]] =
    Option.apply( e.getParameteredElement )
    
}
