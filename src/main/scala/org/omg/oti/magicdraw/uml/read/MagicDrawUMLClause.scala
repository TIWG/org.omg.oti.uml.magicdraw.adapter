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

import org.omg.oti.uml.read.api._

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String

trait MagicDrawUMLClause 
  extends MagicDrawUMLElement
  with UMLClause[MagicDrawUML] {

  implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  override protected def e: Uml#Clause
  def getMagicDrawClause: Uml#Clause = e

	override def body
  : Set[UMLExecutableNode[Uml]]
  = e.getBody.to[Set]
  
  override def bodyOutput
  : Seq[UMLOutputPin[Uml]]
  = e.getBodyOutput.to[Seq]
  
  override def decider
  : Option[UMLOutputPin[Uml]]
  = for { result <- Option(e.getDecider) } yield result

  override def predecessorClause
  : Set[UMLClause[Uml]]
  = e.getPredecessorClause.to[Set]
  
  override def successorClause
  : Set[UMLClause[Uml]]
  = e.getSuccessorClause.to[Set]
  
	override def test
  : Set[UMLExecutableNode[Uml]]
  = e.getTest.to[Set]

}

case class MagicDrawUMLClauseImpl
(e: MagicDrawUML#Clause, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLClause
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLClauseImpl =>
      this.hashCode == that.hashCode &&
      this.e == that.e &&
      this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLClause(ID=${e.getID})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}