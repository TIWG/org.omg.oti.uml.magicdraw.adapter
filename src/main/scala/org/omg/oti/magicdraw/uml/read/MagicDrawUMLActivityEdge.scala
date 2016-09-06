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
import scala.Option

trait MagicDrawUMLActivityEdge 
  extends MagicDrawUMLRedefinableElement
  with UMLActivityEdge[MagicDrawUML] {

  override protected def e: Uml#ActivityEdge
  def getMagicDrawActivityEdge = e
  override implicit val umlOps = ops
  import umlOps._

  override def guard: Option[UMLValueSpecification[Uml]] =
    for { result <- Option.apply( e.getGuard ) } yield result
    
  override def inGroup: Set[UMLActivityGroup[Uml]] =
    e.getInGroup.to[Set]
  
  override def interrupts: Option[UMLInterruptibleActivityRegion[Uml]] =
    for { result <- Option.apply( e.getInterrupts ) } yield result
  
  override def source: Option[UMLActivityNode[Uml]] =
    for { result <- Option.apply( e.getSource ) } yield result
    
  override def target: Option[UMLActivityNode[Uml]] =
    for { result <- Option.apply( e.getTarget ) } yield result
      
  override def weight: Option[UMLValueSpecification[Uml]] =
    for { result <- Option.apply( e.getWeight ) } yield result

  override def realizingActivityEdge_informationFlow: Set[UMLInformationFlow[Uml]] =
    e.get_informationFlowOfRealizingActivityEdge.to[Set]
  
}