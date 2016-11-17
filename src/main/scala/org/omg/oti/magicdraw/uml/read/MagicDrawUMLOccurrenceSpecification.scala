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
import scala.collection.Iterable
import scala.Predef.???

trait MagicDrawUMLOccurrenceSpecification 
  extends MagicDrawUMLInteractionFragment
  with UMLOccurrenceSpecification[MagicDrawUML] {

  override protected def e: Uml#OccurrenceSpecification
  def getMagicDrawOccurrenceSpecification = e

	override def covered: Iterable[UMLLifeline[Uml]] = ???
  
  override def toAfter: Set[UMLGeneralOrdering[Uml]] = ???
  
  override def toBefore: Set[UMLGeneralOrdering[Uml]] = ???
  
  override def finish_executionSpecification: Set[UMLExecutionSpecification[Uml]] = ???
  
  override def start_executionSpecification: Set[UMLExecutionSpecification[Uml]] = ???
  

}

case class MagicDrawUMLOccurrenceSpecificationImpl
(e: MagicDrawUML#OccurrenceSpecification, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLOccurrenceSpecification