package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLoopNode 
  extends UMLLoopNode[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#LoopNode
  import ops._

  // 16.45
  override def bodyOutput: Seq[UMLOutputPin[Uml]] = ???

  // 16.45
  override def bodyPart: Set[UMLExecutableNode[Uml]] = ???

  // 16.45
  override def decider: Option[UMLOutputPin[Uml]] = ???

  // 16.45
  override def isTestedFirst: Boolean = ???

  // 16.45
  override def loopVariable: Seq[UMLOutputPin[Uml]] = ???

  // 16.45
  override def loopVariableInput: Iterable[UMLInputPin[Uml]] = ???

  // 16.45
  override def result: Iterable[UMLOutputPin[Uml]] = ???

  // 16.45
  override def setupPart: Set[UMLExecutableNode[Uml]] = ???

  // 16.45
  override def test: Set[UMLExecutableNode[Uml]] = ???
  
}
