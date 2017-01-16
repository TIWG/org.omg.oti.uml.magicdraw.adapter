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
import scala.Predef.{int2Integer,String}

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._
import java.lang.Integer

trait MagicDrawUMLOperation 
  extends MagicDrawUMLBehavioralFeature
  with MagicDrawUMLParameterableElement
  with MagicDrawUMLTemplateableElement
  with UMLOperation[MagicDrawUML] {

  override protected def e: Uml#Operation
  def getMagicDrawOperation: Uml#Operation = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  override def bodyCondition
  : Option[UMLConstraint[Uml]]
  = for { result <- Option( e.getBodyCondition ) } yield result
  
  override def isOrdered: Boolean = e.isOrdered
  
  override def isQuery: Boolean = e.isQuery
  
  override def isUnique: Boolean = e.isQuery
  
  override def lower
  : Option[Integer]
  = for { result <- Option( e.getLower ) } yield result
    
  override def ownedParameter
  : Seq[UMLParameter[Uml]]
  = e.getOwnedParameter.to[Seq]
  
  override def postcondition
  : Set[UMLConstraint[Uml]]
  = e.getPostcondition.to[Set]

  override def precondition
  : Set[UMLConstraint[Uml]]
  = e.getPrecondition.to[Set]

  override def templateParameter
  : Option[UMLOperationTemplateParameter[Uml]]
  = for { result <- Option( e.getTemplateParameter ) } yield result
  
  override def _type
  : Option[UMLType[Uml]]
  = for { result <- Option( e.getType ) } yield result
  
  override def upper
  : Option[Integer]
  = for { result <- Option( e.getUpper ) } yield result
  
  override def operation_callOperationAction
  : Set[UMLCallOperationAction[Uml]]
  = e.get_callOperationActionOfOperation.to[Set]
  
  override def referred_protocolTransition
  : Set[UMLProtocolTransition[Uml]]
  = throw UMLError.umlAdaptationError(s"MagicDrawUMLOperation.referred_protocolTransition not available")
  
  override def operation_callEvent
  : Set[UMLCallEvent[Uml]]
  = e.get_callEventOfOperation.to[Set]

}

case class MagicDrawUMLOperationImpl
(e: MagicDrawUML#Operation, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLOperation
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLOperationImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLOperation(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}