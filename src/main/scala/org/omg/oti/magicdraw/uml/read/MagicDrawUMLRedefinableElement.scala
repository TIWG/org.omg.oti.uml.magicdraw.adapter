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
import scala.collection.Iterable
import scala.Boolean

trait MagicDrawUMLRedefinableElement 
  extends MagicDrawUMLNamedElement
  with UMLRedefinableElement[MagicDrawUML] {

  override protected def e: Uml#RedefinableElement
  def getMagicDrawRedefinableElement: Uml#RedefinableElement = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
    
  override def isLeaf: Boolean = e.isLeaf
  
  override def redefinedElement
  : Set[UMLRedefinableElement[Uml]]
  = e.getRedefinedElement.to[Set]
  
  override def redefinitionContext
  : Iterable[UMLClassifier[Uml]]
  = e.getRedefinitionContext.toIterable
  
  override def redefinedElement_redefinableElement
  : Set[UMLRedefinableElement[Uml]]
  = e.get_redefinableElementOfRedefinedElement.to[Set]
  
}