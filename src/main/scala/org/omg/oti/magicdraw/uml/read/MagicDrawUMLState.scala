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
import scala.collection.immutable._
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String

import scala.collection.JavaConversions._

trait MagicDrawUMLState 
  extends MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLVertex
  with UMLState[MagicDrawUML] {

  override protected def e: Uml#State
  def getMagicDrawState: Uml#State = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  // 14.1
  def doActivity
  : Option[UMLBehavior[Uml]]
  = for { result <- Option(e.getDoActivity) } yield result

  def entry
  : Option[UMLBehavior[Uml]]
  = for { result <- Option(e.getEntry) } yield result

  def exit
  : Option[UMLBehavior[Uml]]
  = for { result <- Option(e.getExit) } yield result
  
  // 14.1
  def submachine
  : Option[UMLStateMachine[Uml]]
  = for { result <- Option(e.getSubmachine) } yield result
  
  // 15.48
  def inState_objectNode
  : Set[UMLObjectNode[Uml]]
  = e.get_objectNodeOfInState.to[Set]
  
  override def stateInvariant
  : Option[UMLConstraint[Uml]]
  = for { result <- Option(e.getStateInvariant) } yield result

}

case class MagicDrawUMLStateImpl
(e: MagicDrawUML#State, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLState
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLStateImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLState(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}