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

import org.omg.oti.json.common.OTIArtifactKind
import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.characteristics._
import org.omg.oti.uml.xmi._
import org.omg.oti.magicdraw.uml.read._

import scala.collection.immutable._
import scala.reflect.runtime.universe._
import scalaz._
import Scalaz._

/**
 * MagicDraw-specific OTI DocumentSet
 */
case class MagicDrawDocumentSet
(serializableImmutableDocuments: Set[SerializableImmutableDocument[MagicDrawUML]],
 serializableMutableDocuments: Set[SerializableMutableDocument[MagicDrawUML]],
 loadingMutableDocuments: Set[LoadingMutableDocument[MagicDrawUML]],
 builtInImmutableDocuments: Set[BuiltInImmutableDocument[MagicDrawUML]],
 builtInMutableDocuments: Set[BuiltInMutableDocument[MagicDrawUML]],
 documentURIMapper: CatalogURIMapper,
 builtInURIMapper: CatalogURIMapper,
 override val aggregate: MagicDrawUML#DocumentSetAggregate)
(override implicit val ops: MagicDrawUMLUtil,
 override implicit val documentOps: MagicDrawDocumentOps,
 override implicit val nodeT: TypeTag[Document[MagicDrawUML]],
 override implicit val edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]])
  extends DocumentSet[MagicDrawUML] {

  override implicit val otiCharacteristicsProvider: OTICharacteristicsProvider[MagicDrawUML] =
  documentOps.otiCharacteristicsProvider

  override def asBuiltInMutableDocument
  (d: LoadingMutableDocument[MagicDrawUML],
   artifactKind: OTIArtifactKind)
  : Set[java.lang.Throwable] \/
    (BuiltInMutableDocument[MagicDrawUML], DocumentSet[MagicDrawUML]) = 
  d match {
    case lmD: MagicDrawLoadingMutableDocument if loadingMutableDocuments.contains(lmD) =>
      val bmD = MagicDrawBuiltInMutableDocument(
          info = lmD.info.copy(artifactKind = artifactKind),
          documentURL = lmD.documentURL,
          scope = lmD.scope,
          builtInExtent = lmD.extent.toSet)
      val ds = this.copy(
          loadingMutableDocuments = this.loadingMutableDocuments - d,
          builtInMutableDocuments = this.builtInMutableDocuments + bmD)
      (bmD, ds).right
    case _ =>
      -\/(Set(
          UMLError.umlAdaptationError(
              "asBuiltInMutableDocument: not in DocumentSet: "+d.info)))
  }
  
  override def freezeBuiltInMutableDocument
  (d: BuiltInMutableDocument[MagicDrawUML])
  : Set[java.lang.Throwable] \/
    (BuiltInImmutableDocument[MagicDrawUML], DocumentSet[MagicDrawUML]) =
  d match {
    case mD: MagicDrawBuiltInMutableDocument if builtInMutableDocuments.contains(mD) =>
      val iD = MagicDrawBuiltInImmutableDocument(
          info = mD.info,
          documentURL = mD.documentURL,
          scope = mD.scope,
          builtInExtent = mD.extent.toSet)
      val ds = this.copy(
          builtInImmutableDocuments = this.builtInImmutableDocuments + iD,
          builtInMutableDocuments = this.builtInMutableDocuments - mD)
      (iD, ds).right
    case _ =>
      -\/(Set(
          UMLError.umlAdaptationError(
              "freezeBuiltInMutableDocument: "+
              "not in DocumentSet or not a MagicDrawBuiltInMutableDocument: "+
              d.info)))
  }
  
  override def asSerializableMutableDocument
  (d: LoadingMutableDocument[MagicDrawUML],
   artifactKind: OTIArtifactKind)
  : Set[java.lang.Throwable] \/
    (SerializableMutableDocument[MagicDrawUML], DocumentSet[MagicDrawUML]) = 
  d match {
    case lmD: MagicDrawLoadingMutableDocument if loadingMutableDocuments.contains(lmD) =>
      val smD = MagicDrawSerializableMutableDocument(
          info = lmD.info.copy(artifactKind = artifactKind),
          documentURL = lmD.documentURL,
          scope = lmD.scope,
          serializableExtent = lmD.extent.toSet)
      val ds = this.copy(
          loadingMutableDocuments = this.loadingMutableDocuments - d,
          serializableMutableDocuments = this.serializableMutableDocuments + smD)
      (smD, ds).right
    case _ =>
      -\/(Set(
          UMLError.umlAdaptationError(
              "asBuiltInMutableDocument: not in DocumentSet: "+d.info)))
  }

  override def freezeSerializableMutableDocument
  (d: SerializableMutableDocument[MagicDrawUML])
  : Set[java.lang.Throwable] \/
    (SerializableImmutableDocument[MagicDrawUML], DocumentSet[MagicDrawUML]) =
  d match {
    case mD: MagicDrawSerializableMutableDocument if serializableMutableDocuments.contains(mD) =>
      val iD = MagicDrawSerializableImmutableDocument(
          info = mD.info,
          documentURL = mD.documentURL,
          scope = mD.scope,
          serializableExtent = mD.serializableExtent)
      val ds = this.copy(
          serializableImmutableDocuments = this.serializableImmutableDocuments + iD,
          serializableMutableDocuments = this.serializableMutableDocuments - mD)
      (iD, ds).right
    case _ =>
      -\/(Set(
          UMLError.umlAdaptationError(
              "freezeSerializableMutableDocument: "+
              "not in DocumentSet or not a MagicDrawSerializableMutableDocument: "+
              d.info)))
  }

}

object MagicDrawDocumentSet {

  def addDocument
  (ds: DocumentSet[MagicDrawUML], d: Document[MagicDrawUML])
  : Set[java.lang.Throwable] \/ MagicDrawDocumentSet =
    (ds, d) match {
      case (mds: MagicDrawDocumentSet, md: MagicDrawSerializableImmutableDocument) =>
        \/-(
          mds
          .copy(serializableImmutableDocuments = mds.serializableImmutableDocuments + md)
          (mds.ops, mds.documentOps, mds.nodeT, mds.edgeT)
        )
      case (mds: MagicDrawDocumentSet, md: MagicDrawSerializableMutableDocument) =>
        \/-(
          mds
            .copy(serializableMutableDocuments = mds.serializableMutableDocuments + md)
            (mds.ops, mds.documentOps, mds.nodeT, mds.edgeT)
        )
      case (mds: MagicDrawDocumentSet, md: MagicDrawBuiltInImmutableDocument) =>
        \/-(
          mds
            .copy(builtInImmutableDocuments = mds.builtInImmutableDocuments + md)
            (mds.ops, mds.documentOps, mds.nodeT, mds.edgeT)
        )
      case (mds: MagicDrawDocumentSet, md: MagicDrawBuiltInMutableDocument) =>
        \/-(
          mds
            .copy(builtInMutableDocuments = mds.builtInMutableDocuments + md)
            (mds.ops, mds.documentOps, mds.nodeT, mds.edgeT)
        )
      case (mds: MagicDrawDocumentSet, md: MagicDrawLoadingMutableDocument) =>
        \/-(
          mds
            .copy(loadingMutableDocuments = mds.loadingMutableDocuments + md)
            (mds.ops, mds.documentOps, mds.nodeT, mds.edgeT)
        )
    }

}