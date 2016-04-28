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

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.collection.Iterable
import scala.{Option,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLConstraint 
  extends MagicDrawUMLPackageableElement
  with UMLConstraint[MagicDrawUML] {

  override protected def e: Uml#Constraint
  def getMagicDrawConstraint = e
  import ops._
  
  override def constrainedElement: Seq[UMLElement[Uml]] =
    for { c <- e.getConstrainedElement.to[Seq] } yield umlElement( c )
  
  override def bodyCondition_bodyContext: Option[UMLOperation[Uml]] =
    for { result <- Option.apply( e.getBodyContext ) } yield result

  override def context: Option[UMLNamespace[Uml]] =
    for { result <- Option.apply( e.getContext ) } yield result
    
  override def guard_transition: Option[UMLTransition[Uml]] =
    for { result <- Option.apply( e.get_transitionOfGuard ) } yield result

  override def localPostcondition_action: Option[UMLAction[Uml]] =
    for { result <- Option.apply( e.get_actionOfLocalPostcondition ) } yield result

  override def localPrecondition_action: Option[UMLAction[Uml]] =
    for { result <- Option.apply( e.get_actionOfLocalPrecondition ) } yield result
    
  override def postCondition_owningTransition: Option[UMLProtocolTransition[Uml]] =
    for { result <- Option.apply( e.getOwningTransition ) } yield result
    
  override def postcondition_behavior: Option[UMLBehavior[Uml]] =
    for { result <- Option.apply( e.get_behaviorOfPrecondition ) } yield result
    
  override def postcondition_postContext: Option[UMLOperation[Uml]] =
    for { result <- Option.apply( e.getPostContext ) } yield result

  override def precondition_behavior: Option[UMLBehavior[Uml]] =
    for { result <- Option.apply( e.get_behaviorOfPrecondition ) } yield result
    
  override def precondition_preContext: Option[UMLOperation[Uml]] =
    for { result <- Option.apply( e.getPreContext ) } yield result
    
  override def specification: Option[UMLValueSpecification[Uml]] =
    for { result <- Option.apply( e.getSpecification ) } yield result
    
  override def stateInvariant_owningState: Option[UMLState[Uml]] =
    for { result <- Option.apply( e.getOwningState ) } yield result

}

case class MagicDrawUMLConstraintImpl
(e: MagicDrawUML#Constraint, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLConstraint
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLConstraint(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}