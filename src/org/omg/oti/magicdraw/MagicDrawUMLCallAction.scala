package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCallAction 
  extends UMLCallAction[MagicDrawUML]
  with MagicDrawUMLInvocationAction {

  override protected def e: Uml#CallAction
  import ops._

  override def isSynchronous: Boolean =
    e.isSynchronous
   
  override def result: Seq[UMLOutputPin[Uml]] =
    e.getResult.toSeq
    
}
