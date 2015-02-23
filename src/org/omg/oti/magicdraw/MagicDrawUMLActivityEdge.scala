package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityEdge 
  extends UMLActivityEdge[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#ActivityEdge
  import ops._

  override def guard =
    Option.apply( e.getGuard )
    
  override def inGroup =
    e.getInGroup.toSet[Uml#ActivityGroup]
  
  override def inPartition =
    e.getInPartition.toSet[Uml#ActivityPartition]
  
  override def interrupts =
    Option.apply( e.getInterrupts )
  
  override def source =
    Option.apply( e.getSource )
    
  override def target =
    Option.apply( e.getTarget )
      
  override def weight =
    Option.apply( e.getWeight )

  override def realizingActivityEdge_informationFlow =
    e.get_informationFlowOfRealizingActivityEdge.toSet[Uml#InformationFlow]
  
}
