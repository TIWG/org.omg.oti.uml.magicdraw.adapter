package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLJoinNode 
  extends UMLJoinNode[MagicDrawUML]
  with MagicDrawUMLControlNode {

  override protected def e: Uml#JoinNode
  import ops._

  override def isCombineDuplicate: Boolean =
    e.isCombineDuplicate
    
  override def joinSpec: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getJoinSpec )  
    
}
