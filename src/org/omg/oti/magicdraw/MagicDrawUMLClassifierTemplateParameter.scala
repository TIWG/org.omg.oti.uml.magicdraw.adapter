package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLClassifierTemplateParameter 
  extends UMLClassifierTemplateParameter[MagicDrawUML]
  with MagicDrawUMLTemplateParameter {

  override protected def e: Uml#ClassifierTemplateParameter
  import ops._

}
