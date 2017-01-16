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

import org.omg.oti.uml.read.api._

import scala.{Boolean, Option}
import scala.collection.immutable._

trait MagicDrawUMLAction 
  extends MagicDrawUMLExecutableNode
  with UMLAction[MagicDrawUML] {

  override protected def e: Uml#Action
  def getMagicDrawAction: Uml#Action = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def context
  : Option[UMLClassifier[Uml]]
  = for { result <- Option.apply( e.getContext ) } yield result
  
  override def input
  : Seq[UMLInputPin[Uml]]
  = e.getInput.to[Seq]
  
  override def isLocallyReentrant: Boolean = e.isLocallyReentrant
  
  override def localPostcondition
  : Set[UMLConstraint[Uml]]
  = e.getLocalPostcondition.to[Set]
  
  override def localPrecondition
  : Set[UMLConstraint[Uml]]
  = e.getLocalPrecondition.to[Set]
  
  override def output
  : Seq[UMLOutputPin[Uml]]
  = e.getOutput.to[Seq]

  override def action_actionExecutionSpecification
  : Set[UMLActionExecutionSpecification[Uml]]
  = e.get_actionExecutionSpecificationOfAction.to[Set]
  
}