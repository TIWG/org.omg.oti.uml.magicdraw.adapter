package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkEndData 
  extends UMLLinkEndData[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#LinkEndData
  import ops._

  override def end: Option[UMLProperty[Uml]] =
    Option.apply( e.getEnd )
    
  override def qualifier: Set[UMLQualifierValue[Uml]] =
    e.getQualifier.toSet[Uml#QualifierValue]
  
  override def value: Option[UMLInputPin[Uml]] =
    Option.apply( e.getValue )
    
  override def endData_linkAction: Option[UMLLinkAction[Uml]] =
    Option.apply( e.get_linkActionOfEndData )
}
