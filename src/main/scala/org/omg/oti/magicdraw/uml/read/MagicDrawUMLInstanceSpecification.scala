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

import scala.{Boolean,Option}
import scala.collection.immutable._
import scala.collection.Iterable
import scala.collection.JavaConversions._

import org.omg.oti.uml.read.api._

trait MagicDrawUMLInstanceSpecification 
  extends MagicDrawUMLPackageableElement
  with MagicDrawUMLDeploymentTarget
  with MagicDrawUMLDeployedArtifact
  with UMLInstanceSpecification[MagicDrawUML] {

  override protected def e: Uml#InstanceSpecification
  def getMagicDrawInstanceSpecification = e

  override implicit val umlOps = ops
  import umlOps._
  
  def isMagicDrawUMLAppliedStereotypeInstance: Boolean =
    e == e.getOwner.getAppliedStereotypeInstance
  
  override def specification: Option[UMLValueSpecification[Uml]] =
    for { result <- Option( e.getSpecification ) } yield result

  override def classifier: Iterable[UMLClassifier[Uml]] =
    e.getClassifier.to[Seq]
  
  override def instance_instanceValue: Set[UMLInstanceValue[Uml]] =
    e.get_instanceValueOfInstance.to[Set]

}

case class MagicDrawUMLInstanceSpecificationImpl
(e: MagicDrawUML#InstanceSpecification, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLInstanceSpecification