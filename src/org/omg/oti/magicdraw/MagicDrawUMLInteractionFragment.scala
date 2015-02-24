package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionFragment 
  extends UMLInteractionFragment[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#InteractionFragment
  import ops._

  override def covered: Iterable[UMLLifeline[Uml]] =
    e.getCovered.toIterable
    
  override def generalOrdering: Set[UMLGeneralOrdering[Uml]] =
    e.getGeneralOrdering.toSet[Uml#GeneralOrdering]
  
}
