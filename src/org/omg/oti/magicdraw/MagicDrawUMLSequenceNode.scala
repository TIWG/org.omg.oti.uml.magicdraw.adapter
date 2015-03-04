package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLSequenceNode 
  extends UMLSequenceNode[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#SequenceNode
  import ops._

  // 16.45
	override def executableNode: Seq[UMLExecutableNode[Uml]] = ??? 
}
