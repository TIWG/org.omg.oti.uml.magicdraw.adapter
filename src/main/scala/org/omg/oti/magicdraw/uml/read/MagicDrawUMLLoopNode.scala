/*
 * Copyright 2014 California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * License Terms
 */

package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLLoopNode 
  extends MagicDrawUMLStructuredActivityNode
  with UMLLoopNode[MagicDrawUML] {

  override protected def e: Uml#LoopNode
  def getMagicDrawLoopNode: Uml#LoopNode = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  // 16.45
  override def bodyOutput
  : Seq[UMLOutputPin[Uml]]
  = e.getBodyOutput.to[Seq]

  // 16.45
  override def bodyPart
  : Set[UMLExecutableNode[Uml]]
  = e.getBodyPart.to[Set]

  // 16.45
  override def decider
  : Option[UMLOutputPin[Uml]]
  = for { result <- Option(e.getDecider) } yield result

  // 16.45
  override def isTestedFirst: Boolean = e.isTestedFirst

  // 16.45
  override def loopVariable
  : Seq[UMLOutputPin[Uml]]
  = e.getLoopVariable.to[Seq]

  // 16.45
  override def loopVariableInput
  : Seq[UMLInputPin[Uml]]
  = e.getLoopVariableInput.to[Seq]

  // 16.45
  override def result
  : Seq[UMLOutputPin[Uml]]
  = e.getResult.to[Seq]

  // 16.45
  override def setupPart
  : Set[UMLExecutableNode[Uml]]
  = e.getSetupPart.to[Set]

  // 16.45
  override def test
  : Set[UMLExecutableNode[Uml]]
  = e.getTest.to[Set]

}

case class MagicDrawUMLLoopNodeImpl
(e: MagicDrawUML#LoopNode, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLLoopNode
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLLoopNodeImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLLoopNode(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}