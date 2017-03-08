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

trait MagicDrawUMLOutputPin
  extends MagicDrawUMLPin
  with UMLOutputPin[MagicDrawUML] {

  override protected def e: Uml#OutputPin
  def getMagicDrawOutputPin: Uml#OutputPin = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def bodyOutput_clause
  : Set[UMLClause[Uml]]
  = e.get_clauseOfBodyOutput.to[Set]
      
  override def decider_clause
  : Option[UMLClause[Uml]]
  = for { result <- Option( e.get_clauseOfDecider ) } yield result

  override def decider_loopNode
  : Option[UMLLoopNode[Uml]]
  = for { result <- Option( e.get_loopNodeOfDecider ) } yield result
  
  override def bodyOutput_loopNode
  : Set[UMLLoopNode[Uml]]
  = e.get_loopNodeOfBodyOutput.to[Set]

  override def loopVariable_loopNode
  : Option[UMLLoopNode[Uml]]
  = for { result <- Option(e.get_loopNodeOfLoopVariable()) } yield result
  
  override def output_action
  : Option[UMLAction[Uml]]
  = for { result <- Option(e.get_actionOfOutput()) } yield result

  override def result_acceptEventAction
  : Option[UMLAcceptEventAction[Uml]]
  = for { result <- Option(e.get_acceptEventActionOfResult()) } yield result

}

case class MagicDrawUMLOutputPinImpl
(e: MagicDrawUML#OutputPin, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLOutputPin
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLOutputPinImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLOutputPin(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}