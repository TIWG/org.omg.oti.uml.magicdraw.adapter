package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOpaqueBehavior 
  extends UMLOpaqueBehavior[MagicDrawUML]
  with MagicDrawUMLBehavior {

  override protected def e: Uml#OpaqueBehavior
  import ops._

  override def body: Seq[String] =
    e.getBody.toSeq
   
  override def language: Seq[String] =
    e.getLanguage.toSeq
    
}
