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
import scala.Option
import scala.collection.immutable._

trait MagicDrawUMLExecutableNode 
  extends MagicDrawUMLActivityNode
  with UMLExecutableNode[MagicDrawUML] {

  override protected def e: Uml#ExecutableNode
  def getMagicDrawExecutableNode: Uml#ExecutableNode = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

	override def test_loopNode
  : Option[UMLLoopNode[Uml]]
  = for { result <- Option.apply( e.get_loopNodeOfTest ) } yield result
    
  override def bodyPart_loopNode
  : Option[UMLLoopNode[Uml]]
  = for { result <- Option.apply( e.get_loopNodeOfBodyPart ) } yield result
    
  override def test_clause
  : Option[UMLClause[Uml]]
  = for { result <- Option.apply( e.get_clauseOfTest ) } yield result
    
  override def setupPart_loopNode
  : Option[UMLLoopNode[Uml]]
  = for { result <- Option.apply( e.get_loopNodeOfSetupPart ) } yield result
     
	override def handlerBody_exceptionHandler
  : Set[UMLExceptionHandler[Uml]]
  = for { result <- e.get_exceptionHandlerOfHandlerBody.to[Set] } yield result
  
  override def body_clause
  : Option[UMLClause[Uml]]
  = for { result <- Option.apply( e.get_clauseOfBody ) } yield result

}