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