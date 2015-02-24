package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkAction 
  extends UMLLinkAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#LinkAction
  import ops._

  override def endData: Iterable[UMLLinkEndData[Uml]] =
    e.getEndData.toIterable
      
  override def inputValue: Set[UMLInputPin[Uml]] =
    e.getInputValue.toSet[Uml#InputPin]
}
