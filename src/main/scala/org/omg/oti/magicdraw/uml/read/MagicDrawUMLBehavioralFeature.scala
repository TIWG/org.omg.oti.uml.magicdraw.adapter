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

import scala.{Boolean,Option,None,Some}

import scala.collection.immutable._
import scala.collection.JavaConversions._

trait MagicDrawUMLBehavioralFeature 
  extends MagicDrawUMLNamespace
  with MagicDrawUMLFeature
  with UMLBehavioralFeature[MagicDrawUML] {

  override protected def e: Uml#BehavioralFeature
  def getMagicDrawBehavioralFeature = e
  override implicit val umlOps = ops
  import umlOps._
  
  override def concurrency: Option[UMLCallConcurrencyKind.Value] =
    Option.apply(e.getConcurrency)
    .fold[Option[UMLCallConcurrencyKind.Value]](None) {
      case com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.CONCURRENT =>
        Some(UMLCallConcurrencyKind.concurrent)
      case com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.GUARDED =>
        Some(UMLCallConcurrencyKind.guarded)
      case com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.SEQUENTIAL =>
        Some(UMLCallConcurrencyKind.sequential)
    }
  
  override def isAbstract: Boolean =
    e.isAbstract
  
  override def method: Set[UMLBehavior[Uml]] =
    e.getMethod.to[Set]
  
  override def ownedParameter: Seq[UMLParameter[Uml]] =
    e.getOwnedParameter.to[Seq]
  
  override def raisedException: Set[UMLType[Uml]] =
    e.getRaisedException.to[Set]
  
}