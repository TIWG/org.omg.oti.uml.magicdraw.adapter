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

import java.net.{URI, URL}
import java.io.InputStream

import com.nomagic.magicdraw.core.{Application, Project}

import org.apache.xml.resolver.CatalogManager

import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._
import org.omg.oti.uml.xmi._

import scala.{Option,StringContext}
import scala.collection.immutable._
import scala.Predef.{Set=>_,Map=>_,_}
import scala.reflect.runtime.universe._
import scala.util.{Failure,Success,Try}

import java.lang.IllegalArgumentException

/**
 * MagicDraw-specific adapter for the OTI Canonical XMI DocumentOps
 */
class MagicDrawDocumentOps
(implicit umlUtil: MagicDrawUMLUtil)
  extends DocumentOps[MagicDrawUML] {

  implicit val docOps = this

  override def addDocument
  (ds: DocumentSet[MagicDrawUML], d: SerializableDocument[MagicDrawUML])
  : Try[DocumentSet[MagicDrawUML]] =
    MagicDrawDocumentSet.addDocument(ds, d)

  override def createBuiltInDocumentFromBuiltInRootPackage
  (root: UMLPackage[MagicDrawUML])
  : Option[BuiltInDocument[MagicDrawUML]] = ???

  def initializeDocumentSet
  ()
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : Try[DocumentSet[MagicDrawUML]] = {

    val catalogManager: CatalogManager = new CatalogManager()
    catalogManager.setUseStaticCatalog(false)

    val otiCatalog: CatalogURIMapper = new CatalogURIMapper(catalogManager)
    val otiUMLCL = classOf[Document[_]].getClassLoader
    val otiPath1 = "resources/omgCatalog/omg.local.catalog.xml"
    val otiPath2 = "omgCatalog/omg.local.catalog.xml"

    val mdCatalog: CatalogURIMapper = new CatalogURIMapper(catalogManager)
    val mdUMLCL = classOf[MagicDrawUML].getClassLoader
    val mdPath1 = "resources/md18Catalog/omg.magicdraw.catalog.xml"
    val mdPath2 = "md18Catalog/omg.magicdraw.catalog.xml"

    for {
      otiURI <-
      Seq(otiPath1, otiPath2)
        .flatMap { path => Option.apply(otiUMLCL.getResource(path)) }
        .headOption
        .fold[Try[URI]] {
          Failure(new IllegalArgumentException(s"Cannot find OTI catalog file!"))
        }{ url =>
          Try(url.toURI)
        }

      _ <- otiCatalog.parseCatalog(otiURI)

      mdURI <-
      Seq(mdPath1, mdPath2)
        .flatMap { path => Option.apply(mdUMLCL.getResource(path)) }
        .headOption
        .fold[Try[URI]] {
          Failure(new IllegalArgumentException(s"Cannot find MagicDraw catalog file!"))
        }{ url =>
          Try(url.toURI)
        }

      _ <- mdCatalog.parseCatalog(mdURI)

      ds <- initializeDocumentSet(otiCatalog, mdCatalog)
    } yield ds

  }

  override def initializeDocumentSet
  (documentURIMapper: CatalogURIMapper,
   builtInURIMapper: CatalogURIMapper )
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : Try[DocumentSet[MagicDrawUML]] = {

    Option.apply(Application.getInstance.getProject)
    .fold[Try[DocumentSet[MagicDrawUML]]]{
      Failure(new IllegalArgumentException("Cannot initialize a MagicDraw OTI DocumentSet without a current Project"))
    }{ p =>
        implicit val umlUtil = MagicDrawUMLUtil(p)
        import umlUtil._

        createDocumentSet(
          serializableDocuments = Set(),
          builtInDocuments = Set( MDBuiltInPrimitiveTypes, MDBuiltInUML, MDBuiltInStandardProfile ),
          builtInDocumentEdges = Set( MDBuiltInUML2PrimitiveTypes, MDBuiltInStandardProfile2UML ),
          documentURIMapper,
          builtInURIMapper,
          aggregate = MagicDrawDocumentSetAggregate())
      }
  }

  override def createDocumentSet
  ( serializableDocuments: Set[SerializableDocument[MagicDrawUML]],
    builtInDocuments: Set[BuiltInDocument[MagicDrawUML]],
    builtInDocumentEdges: Set[DocumentEdge[Document[MagicDrawUML]]],
    documentURIMapper: CatalogURIMapper,
    builtInURIMapper: CatalogURIMapper,
    aggregate: MagicDrawUML#DocumentSetAggregate)
  ( implicit
    ops: UMLOps[MagicDrawUML],
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : Try[DocumentSet[MagicDrawUML]] = {
    Success(
      MagicDrawDocumentSet(
        serializableDocuments, builtInDocuments, builtInDocumentEdges,
        documentURIMapper, builtInURIMapper, aggregate))
  }

  override def createSerializableDocumentFromExistingRootPackage
  (root: UMLPackage[MagicDrawUML])
  : Option[SerializableDocument[MagicDrawUML]] = ???

  override def createSerializableDocumentFromImportedRootPackage
  (uri: URI,
   nsPrefix: String,
   uuidPrefix: String,
   documentURL: MagicDrawUML#LoadURL,
   scope: UMLPackage[MagicDrawUML])
  (implicit ds: DocumentSet[MagicDrawUML])
  : Try[SerializableDocument[MagicDrawUML]] =
  Success(
    MagicDrawSerializableDocument(uri, nsPrefix, uuidPrefix, documentURL, scope)
  )

  override def getExternalDocumentURL(lurl: MagicDrawUML#LoadURL): URI =
    lurl.externalDocumentResourceURL

  override def openExternalDocumentStreamForImport
  (lurl: MagicDrawUML#LoadURL)
  : java.io.InputStream =
    lurl.externalDocumentResourceURL.toURL.openStream()


}