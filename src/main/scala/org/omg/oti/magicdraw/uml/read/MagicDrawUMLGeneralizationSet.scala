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
import scala.{Boolean,Option}

trait MagicDrawUMLGeneralizationSet 
  extends MagicDrawUMLPackageableElement
  with UMLGeneralizationSet[MagicDrawUML] {

  override protected def e: Uml#GeneralizationSet
  def getMagicDrawGeneralizationSet = e
  override implicit val umlOps = ops
  import umlOps._

  // 9.14
  override def generalization
  : Set[UMLGeneralization[Uml]]
  = e.getGeneralization.to[Set]
  
  // 9.14
  override def isCovering: Boolean = e.isCovering
  
  // 9.14
  override def isDisjoint: Boolean = e.isDisjoint
  
  // 9.14
  override def powertype
  : Option[UMLClassifier[Uml]]
  =  for { result <- Option( e.getPowertype ) } yield result

}

case class MagicDrawUMLGeneralizationSetImpl
(e: MagicDrawUML#GeneralizationSet, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLGeneralizationSet