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

import scala.Boolean
import scala.Option

trait MagicDrawUMLAddStructuralFeatureValueAction 
  extends MagicDrawUMLWriteStructuralFeatureAction
  with UMLAddStructuralFeatureValueAction[MagicDrawUML] {

  override protected def e: Uml#AddStructuralFeatureValueAction
  def getMagicDrawAddStructuralFeatureValueAction = e
  override implicit val umlOps = ops
  import umlOps._

  override def insertAt: Option[UMLInputPin[Uml]] =
    for { result <- Option.apply( e.getInsertAt ) } yield result
    
  override def isReplaceAll: Boolean =
    e.isReplaceAll

}

case class MagicDrawUMLAddStructuralFeatureValueActionImpl
(e: MagicDrawUML#AddStructuralFeatureValueAction, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLAddStructuralFeatureValueAction