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

import java.io.File
import java.lang.IllegalArgumentException


import com.nomagic.magicdraw.core.{ApplicationEnvironment, Application}
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import org.omg.oti.magicdraw.uml.characteristics.MagicDrawOTISymbols

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.characteristics._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.xmi._

import org.omg.oti.magicdraw.uml.read._

import scala.{AnyRef, Boolean, Function1, Option, None, Some}
import scala.collection.immutable._
import scala.collection.Iterable
import scala.reflect.runtime.universe._
import scalaz._, Scalaz._

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
  : NonEmptyList[java.lang.Throwable] \/ 
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
      -\/(NonEmptyList(
          UMLError.umlAdaptationError(
              "asBuiltInMutableDocument: not in DocumentSet: "+d.info)))
  }
  
  override def freezeBuiltInMutableDocument
  (d: BuiltInMutableDocument[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ 
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
      -\/(NonEmptyList(
          UMLError.umlAdaptationError(
              "freezeBuiltInMutableDocument: "+
              "not in DocumentSet or not a MagicDrawBuiltInMutableDocument: "+
              d.info)))
  }
  
  override def asSerializableMutableDocument
  (d: LoadingMutableDocument[MagicDrawUML],
   artifactKind: OTIArtifactKind)
  : NonEmptyList[java.lang.Throwable] \/ 
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
      -\/(NonEmptyList(
          UMLError.umlAdaptationError(
              "asBuiltInMutableDocument: not in DocumentSet: "+d.info)))
  }

  override def freezeSerializableMutableDocument
  (d: SerializableMutableDocument[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ 
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
      -\/(NonEmptyList(
          UMLError.umlAdaptationError(
              "freezeSerializableMutableDocument: "+
              "not in DocumentSet or not a MagicDrawSerializableMutableDocument: "+
              d.info)))
  }

}

object MagicDrawDocumentSet {

  def addDocument
  (ds: DocumentSet[MagicDrawUML], d: Document[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ MagicDrawDocumentSet =
    (ds, d) match {
      case (mds: MagicDrawDocumentSet, md: MagicDrawSerializableImmutableDocument) =>
        \/-(
          mds
          .copy(serializableImmutableDocuments = mds.serializableImmutableDocuments + md)
          (mds.ops, mds.documentOps, mds.nodeT, mds.edgeT)
        )
        // @todo Add other combinations of (s, b) x (m, im)...
    }

  type MagicDrawDocumentSetInfo =
  ( ResolvedDocumentSet[MagicDrawUML],
    MagicDrawDocumentSet,
    Iterable[UnresolvedElementCrossReference[MagicDrawUML]])

  implicit def Package2OTISpecificationRootCharacteristicsMapSemigroup
  : Semigroup[Map[UMLPackage[MagicDrawUML], OTISpecificationRootCharacteristics]] =
    Semigroup.instance(_ ++ _)

    /*
     * to be updated...
    
  def createMagicDrawProjectDocumentSet
  (additionalSpecificationRootPackages: Option[Set[UMLPackage[MagicDrawUML]]] = None,
   documentURIMapper: CatalogURIMapper,
   builtInURIMapper: CatalogURIMapper,
   ignoreCrossReferencedElementFilter: (UMLElement[MagicDrawUML] => Boolean),
   unresolvedElementMapper: (UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]))
  (implicit
   otiCharacteristicsProvider: OTICharacteristicsProvider[MagicDrawUML],
   nodeT: TypeTag[Document[MagicDrawUML]],
   edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]])
  : NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSetInfo =

    Option.apply(Application.getInstance().getProject)
    .fold[NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSetInfo] {
      \&/.This(
        NonEmptyList(
          new DocumentSetException(
            "createMagicDrawProjectDocumentSet failed: Cannot construct a MagicDrawDocumentSet without an active MagicDraw project")))
    }{ p =>

      implicit val umlUtil = MagicDrawUMLUtil( p )
      import umlUtil._

      val allSpecificationRootPackages
      : NonEmptyList[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics] =
        additionalSpecificationRootPackages
        .fold[NonEmptyList[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics]](
          otiCharacteristicsProvider.getAllOTISerializableDocumentPackages
        ){ pkgs =>
          val p0: NonEmptyList[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics] =
            \&/.That(Map[UMLPackage[Uml], OTISpecificationRootCharacteristics]())
          val pN: NonEmptyList[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics] =
            (p0 /: pkgs) { (pi, pkg) =>
              pi append
              otiCharacteristicsProvider.getSpecificationRootCharacteristics(pkg)
              .toThese
              .map {
                _.fold[Map[UMLPackage[Uml], OTISpecificationRootCharacteristics]](
                  Map[UMLPackage[Uml], OTISpecificationRootCharacteristics]()
                ){ info =>
                  Map[UMLPackage[Uml], OTISpecificationRootCharacteristics](pkg -> info)
                }
              }
          }

          pN
        }

      val mdBuiltIns: Set[BuiltInDocument[Uml]] =
        Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile )

      val mdBuiltInEdges: Set[DocumentEdge[Document[Uml]]] =
        Set( MDBuiltInUML2PrimitiveTypes, MDBuiltInStandardProfile2UML )

      implicit val documentOps = new MagicDrawDocumentOps()(umlUtil, otiCharacteristicsProvider)

      allSpecificationRootPackages.flatMap { pkg2info =>

        DocumentSet.constructDocumentSetCrossReferenceGraph[Uml](
          specificationRootPackages=pkg2info,
          documentURIMapper, builtInURIMapper,
          mdBuiltIns,
          mdBuiltInEdges,
          ignoreCrossReferencedElementFilter,
          unresolvedElementMapper,
          MagicDrawDocumentSetAggregate())
          .flatMap { case ((resolved, unresolved)) =>
            resolved.ds match {
              case mdDS: MagicDrawDocumentSet =>
                \&/.That((resolved, mdDS, unresolved))
            }
          }
      }
    }
    
    */
}