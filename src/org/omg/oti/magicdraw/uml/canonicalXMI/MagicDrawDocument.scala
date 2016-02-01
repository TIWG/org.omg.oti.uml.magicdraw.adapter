/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2016, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.uml.canonicalXMI

import org.omg.oti.uml.characteristics.OTISpecificationRootCharacteristics
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.xmi._

import org.omg.oti.magicdraw.uml.read._

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
  
  override val extent: Set[UMLElement[MagicDrawUML]] =
    builtInExtent
   
}

case class MagicDrawBuiltInMutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 builtInExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with BuiltInMutableDocument[MagicDrawUML] {
  
  override def extent: Set[UMLElement[MagicDrawUML]] =
    builtInExtent
   
}

case class MagicDrawLoadingMutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML]) 
(implicit val ops: MagicDrawUMLUtil)
 extends MagicDrawDocument with LoadingMutableDocument[MagicDrawUML] {
  
  override def extent: Set[UMLElement[MagicDrawUML]] =
    scope.allOwnedElements + scope

}

case class MagicDrawSerializableImmutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 serializableExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with SerializableImmutableDocument[MagicDrawUML] {

  override lazy val extent: Set[UMLElement[MagicDrawUML]] =
    serializableExtent

}

case class MagicDrawSerializableMutableDocument
(info: OTISpecificationRootCharacteristics,
 documentURL: MagicDrawUML#LoadURL,
 scope: UMLPackage[MagicDrawUML],
 serializableExtent: Set[UMLElement[MagicDrawUML]])
(implicit val ops: MagicDrawUMLUtil)
  extends MagicDrawDocument with SerializableMutableDocument[MagicDrawUML] {

  override lazy val extent: Set[UMLElement[MagicDrawUML]] =
    serializableExtent

}
