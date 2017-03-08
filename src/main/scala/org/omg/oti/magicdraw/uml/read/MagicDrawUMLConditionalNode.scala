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
import scala.{Any,Boolean,Int,StringContext}
import scala.Predef.String

trait MagicDrawUMLConditionalNode 
  extends MagicDrawUMLStructuredActivityNode
  with UMLConditionalNode[MagicDrawUML] {

  override protected def e: Uml#ConditionalNode
  def getMagicDrawConditionalNode: Uml#ConditionalNode = e
  import ops._

  override def clause
  : Set[UMLClause[Uml]]
  = e.getClause.to[Set]
  
  override def isAssured: Boolean = e.isAssured
    
  override def isDeterminate: Boolean = e.isDeterminate
    
	override def result
  : Seq[UMLOutputPin[Uml]]
  = e.getResult.to[Seq]

}

case class MagicDrawUMLConditionalNodeImpl
(e: MagicDrawUML#ConditionalNode, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLConditionalNode
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLConditionalNodeImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLConditionalNode(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}