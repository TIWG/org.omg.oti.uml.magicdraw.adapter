package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLConnectionPointReference 
  extends UMLConnectionPointReference[MagicDrawUML]
  with MagicDrawUMLVertex {

  override protected def e: Uml#ConnectionPointReference
  import ops._

  // 14.1
  override def entry: Set[UMLPseudostate[Uml]] = ???
  
  // 14.1
  override def exit: Set[UMLPseudostate[Uml]] = ???
  
  
}
