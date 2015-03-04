package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateBinding 
  extends UMLTemplateBinding[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#TemplateBinding
  import ops._

}
