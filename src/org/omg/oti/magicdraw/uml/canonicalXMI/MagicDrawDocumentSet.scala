/*
 *
 *  License Terms
 *
 *  Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 *  U.S. Government sponsorship acknowledged.
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *
 *   *   Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *   *   Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the
 *       distribution.
 *
 *   *   Neither the name of Caltech nor its operating division, the Jet
 *       Propulsion Laboratory, nor the names of its contributors may be
 *       used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.uml.canonicalXMI

import java.io.File

import com.nomagic.magicdraw.core.{ApplicationEnvironment, Application}
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin

import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._
import org.omg.oti.uml.xmi._

import scala.{AnyRef, Boolean, Function1, Option, None, Some}
import scala.collection.immutable._
import scala.collection.Iterable
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

import java.lang.IllegalArgumentException

/**
 * MagicDraw-specific OTI DocumentSet
 */
case class MagicDrawDocumentSet
(serializableDocuments: Set[SerializableDocument[MagicDrawUML]],
 builtInDocuments: Set[BuiltInDocument[MagicDrawUML]],
 builtInDocumentEdges: Set[DocumentEdge[Document[MagicDrawUML]]],
 documentURIMapper: CatalogURIMapper,
 builtInURIMapper: CatalogURIMapper,
 override val aggregate: MagicDrawUML#DocumentSetAggregate)
(override implicit val ops: UMLOps[MagicDrawUML],
 override implicit val otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
 override implicit val documentOps: MagicDrawDocumentOps,
 override implicit val nodeT: TypeTag[Document[MagicDrawUML]],
 override implicit val edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]])
  extends DocumentSet[MagicDrawUML]

object MagicDrawDocumentSet {

  def addDocument
  (ds: DocumentSet[MagicDrawUML], d: SerializableDocument[MagicDrawUML])
  : Try[MagicDrawDocumentSet] =
    (ds, d) match {
      case (pds: MagicDrawDocumentSet, pd: MagicDrawSerializableDocument) =>
        Success(
          pds
            .copy(serializableDocuments = pds.serializableDocuments + pd)(
              pds.ops, pds.otiCharacterizations, pds.documentOps, pds.nodeT, pds.edgeT)
        )
    }

  type MagicDrawDocumentSetInfo =
  (MagicDrawOTISymbols,
    ResolvedDocumentSet[MagicDrawUML],
    MagicDrawDocumentSet,
    Iterable[UnresolvedElementCrossReference[MagicDrawUML]])

  def createMagicDrawProjectDocumentSet
  (documentURIMapper: CatalogURIMapper,
   builtInURIMapper: CatalogURIMapper,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
   ignoreCrossReferencedElementFilter: (UMLElement[MagicDrawUML] => Boolean),
   unresolvedElementMapper: (UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]))
  (implicit
   nodeT: TypeTag[Document[MagicDrawUML]],
   edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]])
  : Try[MagicDrawDocumentSetInfo] =

    Option.apply(Application.getInstance().getProject)
    .fold[Try[MagicDrawDocumentSetInfo]] {
      Failure(
        DocumentSetException(
        "createMagicDrawProjectDocumentSet failed",
        new IllegalArgumentException(
        "Cannot construct a MagicDrawDocumentSet without an active MagicDraw project")))
    }{ p =>

      implicit val umlUtil = MagicDrawUMLUtil( p )
      import umlUtil._

      resolvedMagicDrawOTISymbols
      .fold[Try[MagicDrawDocumentSetInfo]] {
      Failure(
        DocumentSetException(
        "createMagicDrawProjectDocumentSet failed",
        new IllegalArgumentException(
        "Failed to resolve all the necessary OTI/MagicDraw profile stereotypes & properties")))
      }{ mdOTISymbols =>
        val mdBuiltIns: Set[BuiltInDocument[Uml]] =
          Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile )

        val mdBuiltInEdges: Set[DocumentEdge[Document[Uml]]] =
          Set( MDBuiltInUML2PrimitiveTypes, MDBuiltInStandardProfile2UML )

        implicit val mdDocOps = new MagicDrawDocumentOps()(umlUtil, otiCharacterizations)
        implicit val otiC = otiCharacterizations

        DocumentSet.constructDocumentSetCrossReferenceGraph[Uml](
          specificationRootPackages = getAllOTISerializableDocumentPackages(mdOTISymbols),
          documentURIMapper, builtInURIMapper,
          builtInDocuments = mdBuiltIns,
          builtInDocumentEdges = mdBuiltInEdges,
          ignoreCrossReferencedElementFilter,
          unresolvedElementMapper,
          aggregate = MagicDrawDocumentSetAggregate() )
        .flatMap { case (( resolved, unresolved )) =>
          resolved.ds match {
            case mdDS: MagicDrawDocumentSet =>
              Success((mdOTISymbols, resolved, mdDS, unresolved))
          }
        }
      }
    }
}