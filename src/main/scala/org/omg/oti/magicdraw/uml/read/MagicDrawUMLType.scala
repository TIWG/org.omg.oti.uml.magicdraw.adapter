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
import scala.Option

import org.omg.oti.uml.read.api._

trait MagicDrawUMLType 
  extends MagicDrawUMLPackageableElement
  with UMLType[MagicDrawUML] {

  override protected def e: Uml#Type
  def getMagicDrawType = e
  override implicit val umlOps = ops
  import umlOps._

  override def _package: Option[UMLPackage[Uml]] =
    for { result <- Option(e.getOwningPackage) } yield result

  override def type_operation: Set[UMLOperation[Uml]] =
    type_typedElement
    .selectByKindOf { case op: UMLOperation[Uml] => op }
  
  override def type_typedElement: Set[UMLTypedElement[Uml]] =
    e.get_typedElementOfType.to[Set]
    
  override def raisedException_behavioralFeature: Set[UMLBehavioralFeature[Uml]] =
    e.get_behavioralFeatureOfRaisedException.to[Set]

}