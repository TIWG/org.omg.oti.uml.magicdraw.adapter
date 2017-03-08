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

import scala.collection.immutable._

import scala.{Any,Boolean,Int,StringContext}
import scala.Predef.String

trait MagicDrawUMLClass 
  extends MagicDrawUMLEncapsulatedClassifier
  with MagicDrawUMLBehavioredClassifier
  with UMLClass[MagicDrawUML] {

  override protected def e: Uml#Class
  def getMagicDrawClass: Uml#Class = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  override def extension
  : Set[UMLExtension[Uml]]
  = e.getExtension.to[Set]
  
  override def isAbstract: Boolean =
    e.isAbstract
  
  override def isActive: Boolean =
    e.isActive
  
  override def nestedClassifier
  : Seq[UMLClassifier[Uml]]
  = e.getNestedClassifier.to[Seq]
  
  override def ownedAttribute
  : Seq[UMLProperty[Uml]]
  = e.getOwnedAttribute.to[Seq]
  
  override def ownedOperation
  : Seq[UMLOperation[Uml]]
  = e.getOwnedOperation.to[Seq]
  

}

case class MagicDrawUMLClassImpl
(e: MagicDrawUML#Class, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLClass
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLClassImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLClass(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}