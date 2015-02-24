package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkEndDestructionData 
  extends UMLLinkEndDestructionData[MagicDrawUML]
  with MagicDrawUMLLinkEndData {

  override protected def e: Uml#LinkEndDestructionData
  import ops._

  override def destroyAt: Option[UMLInputPin[Uml]] =
    Option.apply( e.getDestroyAt )
    
  override def isDestroyDuplicates: Boolean =
    e.isDestroyDuplicates
    
  override def endData_destroyLinkAction: Option[UMLDestroyLinkAction[Uml]] =
    Option.apply( e.get_destroyLinkActionOfEndData )
   
}
