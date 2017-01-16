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

trait MagicDrawUMLObservation 
  extends MagicDrawUMLPackageableElement
  with UMLObservation[MagicDrawUML] {

  override protected def e: Uml#Observation
  def getMagicDrawObservation: Uml#Observation = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def observation_timeExpression
  : Option[UMLTimeExpression[Uml]]
  = for { result <- Option( e.get_timeExpressionOfObservation ) } yield result
    
  override def observation_duration
  : Option[UMLDuration[Uml]]
  = for { result <- Option( e.get_durationOfObservation ) } yield result
    
}