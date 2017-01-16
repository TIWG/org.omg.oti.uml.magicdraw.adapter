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

package org.omg.oti.magicdraw.uml.canonicalXMI

import org.omg.oti.json.common.OTISpecificationRootCharacteristics
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.xmi._

import org.omg.oti.magicdraw.uml.read._

import scala.{Any,Boolean,Int}
import scala.collection.immutable._

/**
 * MagicDraw-specific adaptation of the OTI Document API
 *
 * The OTI Document kind (Loadable, BuiltIn, Serializable) x (Mutable, Immutable)
 * and artifact kind (Metamodel, Profile, ModelLibrary) are orthogonal to 
 * MagicDraw's persistence architecture based on MagicDraw's `IProject`
 *
 * (see: com.nomagic.ci.persistence.{IProject, IAttachedProject, IPrimaryProject})
 */
sealed abstract trait MagicDrawDocument extends Document[MagicDrawUML]

case class MagicDrawBuiltInImmutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 builtInExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with BuiltInImmutableDocument[MagicDrawUML] {
  
  override val extent
  : Set[UMLElement[MagicDrawUML]]
  = builtInExtent

  override val hashCode: Int = (info, documentURL, scope, builtInExtent).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawBuiltInImmutableDocument =>
      (this.info == that.info) &&
        (this.documentURL == that.documentURL) &&
        (this.scope == that.scope) &&
        (this.builtInExtent == that.builtInExtent)
    case _ =>
      false
  }
}

case class MagicDrawBuiltInMutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 builtInExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with BuiltInMutableDocument[MagicDrawUML] {
  
  override def extent
  : Set[UMLElement[MagicDrawUML]]
  = builtInExtent

  override val hashCode: Int = (info, documentURL, scope).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawBuiltInMutableDocument =>
      (this.info == that.info) &&
        (this.documentURL == that.documentURL) &&
        (this.scope == that.scope) &&
        (this.builtInExtent == that.builtInExtent)
    case _ =>
      false
  }
}

case class MagicDrawLoadingMutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML]) 
(implicit val ops: MagicDrawUMLUtil)
 extends MagicDrawDocument with LoadingMutableDocument[MagicDrawUML] {
  
  override def extent
  : Set[UMLElement[MagicDrawUML]]
  = scope.allOwnedElements + scope

  override val hashCode: Int = (info, documentURL, scope).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawLoadingMutableDocument =>
      (this.info == that.info) &&
        (this.documentURL == that.documentURL) &&
        (this.scope == that.scope)
    case _ =>
      false
  }
}

case class MagicDrawSerializableImmutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 serializableExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with SerializableImmutableDocument[MagicDrawUML] {

  override val extent
  : Set[UMLElement[MagicDrawUML]]
  = serializableExtent

  override val hashCode: Int = (info, documentURL, scope, serializableExtent).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawSerializableImmutableDocument =>
      (this.info == that.info) &&
        (this.documentURL == that.documentURL) &&
        (this.scope == that.scope) &&
        (this.serializableExtent == that.serializableExtent)
    case _ =>
      false
  }
}

case class MagicDrawSerializableMutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 serializableExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with SerializableMutableDocument[MagicDrawUML] {

  override def extent
  : Set[UMLElement[MagicDrawUML]]
  = serializableExtent

  override val hashCode: Int = (info, documentURL, scope).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawSerializableMutableDocument =>
      (this.info == that.info) &&
        (this.documentURL == that.documentURL) &&
        (this.scope == that.scope) &&
        (this.serializableExtent == that.serializableExtent)
    case _ =>
      false
  }
}
