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
import scala.{Boolean, Option}

trait MagicDrawUMLMultiplicityElement 
  extends MagicDrawUMLElement
  with UMLMultiplicityElement[MagicDrawUML] {

  override protected def e: Uml#MultiplicityElement
  def getMagicDrawMultiplicityElement: Uml#MultiplicityElement = e

  implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  override def isOrdered: Boolean = e.isOrdered
  
  override def isUnique: Boolean = e.isUnique

  override def lowerValue
  : Option[UMLValueSpecification[Uml]]
  = for { result <- Option( e.getLowerValue ) } yield result

  override def upperValue
  : Option[UMLValueSpecification[Uml]]
  = for { result <- Option( e.getUpperValue ) } yield result
  
}