package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCreateLinkAction 
  extends UMLCreateLinkAction[MagicDrawUML]
  with MagicDrawUMLWriteLinkAction {

  override protected def e: Uml#CreateLinkAction
  import ops._

  override def endData: Iterable[UMLLinkEndCreationData[Uml]] =
    umlLinkEndData( e.getEndData.toIterable ) selectByKindOf 
    { case d: UMLLinkEndCreationData[Uml] => d }
    
  override def inputValue: Set[UMLInputPin[Uml]] =
    e.getInputValue.toSet[Uml#InputPin]

}
