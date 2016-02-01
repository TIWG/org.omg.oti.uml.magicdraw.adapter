/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2016, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.uml.read

import scala.{Boolean,Int,Option,StringContext}
import scala.Predef.{Set=>_,Map=>_,_}
import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.collection.Iterable
import scala.language.postfixOps

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._
import java.lang.Integer

trait MagicDrawUMLOperation 
  extends MagicDrawUMLBehavioralFeature
  with MagicDrawUMLParameterableElement
  with MagicDrawUMLTemplateableElement
  with UMLOperation[MagicDrawUML] {

  override protected def e: Uml#Operation
  def getMagicDrawOperation = e
  override implicit val umlOps = ops
  import umlOps._
  
  override def bodyCondition: Option[UMLConstraint[Uml]] =
    for { result <- Option( e.getBodyCondition ) } yield result
  
  override def isOrdered: Boolean =
    e.isOrdered
  
  override def isQuery: Boolean =
    e.isQuery
  
  override def isUnique: Boolean =
    e.isQuery
  
  override def lower: Option[Integer] =
    for { result <- Option( e.getLower ) } yield result
    
  override def ownedParameter: Seq[UMLParameter[Uml]] =
    e.getOwnedParameter.to[Seq]
  
  override def postcondition: Set[UMLConstraint[Uml]] =
    e.getPostcondition.to[Set]

  override def precondition: Set[UMLConstraint[Uml]] =
    e.getPrecondition.to[Set]

  override def templateParameter: Option[UMLOperationTemplateParameter[Uml]] =
    for { result <- Option( e.getTemplateParameter ) } yield result
  
  override def _type: Option[UMLType[Uml]] =
    for { result <- Option( e.getType ) } yield result
  
  override def upper: Option[Integer] =
    for { result <- Option( e.getUpper ) } yield result
  
  override def operation_callOperationAction: Set[UMLCallOperationAction[Uml]] =
    e.get_callOperationActionOfOperation.to[Set]
  
  override def referred_protocolTransition: Set[UMLProtocolTransition[Uml]] =
    throw UMLError.umlAdaptationError(s"MagicDrawUMLOperation.referred_protocolTransition not available")
  
  override def operation_callEvent: Set[UMLCallEvent[Uml]] =
    e.get_callEventOfOperation.to[Set]

}

case class MagicDrawUMLOperationImpl
(e: MagicDrawUML#Operation, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLOperation
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLOperation(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}