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
import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String

trait MagicDrawUMLGeneralization 
  extends MagicDrawUMLDirectedRelationship
  with UMLGeneralization[MagicDrawUML] {

  override protected def e: Uml#Generalization
  def getMagicDrawGeneralization: Uml#Generalization = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  /**
   * BUG: in UML 2.5, isSubstituable:Boolean[0..1] = true
   * there should be 3 values: None, Some(true), Some(false)
   * but the MD API only gives 2: true, false
   */
  override def isSubstitutable
  : Option[Boolean]
  = e.isSubstitutable match {
    case true => None
    case false => Some( false )
  }
    
  // 9.14
  override def generalizationSet
  : Set[UMLGeneralizationSet[Uml]]
  = e.getGeneralizationSet.to[Set]
  

}

case class MagicDrawUMLGeneralizationImpl
(e: MagicDrawUML#Generalization, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLGeneralization
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLGeneralizationImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLGeneralization(ID=${e.getID})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}