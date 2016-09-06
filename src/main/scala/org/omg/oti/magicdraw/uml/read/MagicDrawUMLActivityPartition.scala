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

trait MagicDrawUMLActivityPartition 
  extends MagicDrawUMLActivityGroup
  with UMLActivityPartition[MagicDrawUML] {

  override protected def e: Uml#ActivityPartition
  def getMagicDrawActivityPartition = e
  override implicit val umlOps = ops
  import umlOps._

  override def edge: Set[UMLActivityEdge[Uml]] =
    e.getEdge.to[Set]
  
  override def isDimension =
    e.isDimension
    
  override def isExternal =
    e.isExternal
    
  override def node: Set[UMLActivityNode[Uml]] =
    e.getNode.to[Set]
  
  override def represents: Option[UMLElement[Uml]] =
    for { result <- Option.apply( e.getRepresents ) } yield result
    
  override def subpartition: Set[UMLActivityPartition[Uml]] =
    e.getSubpartition.to[Set]
  
  override def superPartition: Option[UMLActivityPartition[Uml]] =
    for { result <- Option.apply( e.getSuperPartition ) } yield result
    
  override def partition_activity: Option[UMLActivity[Uml]] =
    for { result <- Option.apply( e.get_activityOfPartition ) } yield result

}

case class MagicDrawUMLActivityPartitionImpl
(e: MagicDrawUML#ActivityPartition, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLActivityPartition