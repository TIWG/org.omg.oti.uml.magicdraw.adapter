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

import scala.Option
import scala.Predef.???
import scala.collection.immutable._

trait MagicDrawUMLClause 
  extends MagicDrawUMLElement
  with UMLClause[MagicDrawUML] {

  override protected def e: Uml#Clause
  def getMagicDrawClause = e

	override def body: Set[UMLExecutableNode[Uml]] = ???
  
  override def bodyOutput: Seq[UMLOutputPin[Uml]] = ???
  
  override def decider: Option[UMLOutputPin[Uml]] = ???
  
  override def predecessorClause: Set[UMLClause[Uml]] = ???
  
  override def successorClause: Set[UMLClause[Uml]] = ???
  
	override def test: Set[UMLExecutableNode[Uml]] = ???
  
  
  


}

case class MagicDrawUMLClauseImpl
(e: MagicDrawUML#Clause, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLClause