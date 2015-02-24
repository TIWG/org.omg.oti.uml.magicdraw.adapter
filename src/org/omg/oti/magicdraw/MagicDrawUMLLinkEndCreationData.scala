package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkEndCreationData 
  extends UMLLinkEndCreationData[MagicDrawUML]
  with MagicDrawUMLLinkEndData {

  override protected def e: Uml#LinkEndCreationData
  import ops._

  override def insertAt: Option[UMLInputPin[Uml]] =
    Option.apply( e.getInsertAt )
    
  override def isReplaceAll: Boolean =
    e.isReplaceAll
  
  override def endData_createLinkAction: Option[UMLCreateLinkAction[Uml]] =
    Option.apply( e.get_createLinkActionOfEndData )
  
}
