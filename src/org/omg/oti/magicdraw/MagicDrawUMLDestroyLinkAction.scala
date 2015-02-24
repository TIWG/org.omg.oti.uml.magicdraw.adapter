package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDestroyLinkAction 
  extends UMLDestroyLinkAction[MagicDrawUML]
  with MagicDrawUMLWriteLinkAction {

  override protected def e: Uml#DestroyLinkAction
  import ops._

  override def endData: Iterable[UMLLinkEndDestructionData[Uml]] =
    umlLinkEndData( e.getEndData.toIterable ) selectByKindOf
    { case d: UMLLinkEndDestructionData[Uml] => d }
}
