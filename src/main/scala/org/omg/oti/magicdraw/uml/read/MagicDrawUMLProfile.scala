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
import scala.{Any,Boolean,Int,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLProfile 
  extends MagicDrawUMLPackage
  with UMLProfile[MagicDrawUML] {

  override protected def e: Uml#Profile
  def getMagicDrawProfile: Uml#Profile = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  // 12.12
  override def metamodelReference
  : Set[UMLPackageImport[Uml]]
  = e.getMetamodelReference.to[Set]

  // 12.12
  override def metaclassReference
  : Set[UMLElementImport[Uml]]
  = e.getMetaclassReference.to[Set]
  
  // 12.12
  override def profile_stereotype
  : Set[UMLStereotype[Uml]]
  = allNestedPackages.flatMap(p => p.ownedStereotype).to[Set]

}

case class MagicDrawUMLProfileImpl
(e: MagicDrawUML#Profile, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLProfile
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLProfileImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLProfile(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}