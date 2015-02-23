package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLComponent 
  extends UMLComponent[MagicDrawUML]
  with MagicDrawUMLClass {

  override protected def e: Uml#Component
  import ops._

  override def isIndirectlyInstantiated = e.isIndirectlyInstantiated
  
  override def provided = e.getProvided.toSet[Uml#Interface]
  
  override def realization = e.getRealization.toSet[Uml#ComponentRealization]
  
  override def required = e.getRequired.toSet[Uml#Interface]
  
}
