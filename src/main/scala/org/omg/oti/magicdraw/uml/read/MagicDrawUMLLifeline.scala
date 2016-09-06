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

import scala.collection.immutable._
import scala.Option

import org.omg.oti.uml.read.api._

trait MagicDrawUMLLifeline 
  extends MagicDrawUMLNamedElement
  with UMLLifeline[MagicDrawUML] {

  override protected def e: Uml#Lifeline
  def getMagicDrawLifeline = e
  override implicit val umlOps = ops
  import umlOps._

  // 17.6
	override def coveredBy: Set[UMLInteractionFragment[Uml]] =
    e.getCoveredBy.to[Set]
  
  // 17.6
	override def decomposedAs: Option[UMLPartDecomposition[Uml]] =
    for { result <- Option(e.getDecomposedAs) } yield result
  
  // 17.6  
	override def represents: Option[UMLConnectableElement[Uml]] =
    for { result <- Option(e.getRepresents) } yield result
  
  // 17.6
	override def selector: Option[UMLValueSpecification[Uml]] =
    for { result <- Option(e.getSelector) } yield result
  
  // 17.6
	override def covered_events: Seq[UMLOccurrenceSpecification[Uml]] =
    e.get_occurrenceSpecificationOfCovered.to[Seq]

}

case class MagicDrawUMLLifelineImpl
(e: MagicDrawUML#Lifeline, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLLifeline