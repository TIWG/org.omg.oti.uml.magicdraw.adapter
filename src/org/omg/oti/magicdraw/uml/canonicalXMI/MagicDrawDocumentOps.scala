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

import java.lang.IllegalArgumentException
import java.lang.System
import java.net.{URI, URL}
import java.io.InputStream

import com.nomagic.magicdraw.core.{Application, Project, ProjectUtilities}
import com.nomagic.ci.persistence.IProject
import com.nomagic.ci.persistence.local.spi.localproject.{LocalAttachedProject,LocalPrimaryProject}
import com.nomagic.magicdraw.teamwork.application.storage.{TeamworkAttachedProject,TeamworkPrimaryProject}

import org.apache.xml.resolver.CatalogManager

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._
import org.omg.oti.uml.xmi._

import scala.{deprecated,AnyVal,Option,None,Some,StringContext}
import scala.collection.immutable._
import scala.util.control.Exception._
import scala.Predef.{Set=>_,Map=>_,_}
import scala.reflect.runtime.universe._

import scalaz._, Scalaz._, syntax.std._

/**
 * MagicDraw-specific adapter for the OTI Canonical XMI DocumentOps
 */
class MagicDrawDocumentOps
(implicit
 umlUtil: MagicDrawUMLUtil,
 otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]])
  extends DocumentOps[MagicDrawUML] {

  implicit val docOps = this

  override def addDocument
  (ds: DocumentSet[MagicDrawUML], d: SerializableDocument[MagicDrawUML])
  : NonEmptyList[UMLError.UException] \/ DocumentSet[MagicDrawUML] =
    MagicDrawDocumentSet.addDocument(ds, d)

  override def createBuiltInDocumentFromBuiltInRootPackage
  (root: UMLPackage[MagicDrawUML])
  : NonEmptyList[UMLError.UException] \/ BuiltInDocument[MagicDrawUML] =
  ???

  def initializeDocumentSet
  ()
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : NonEmptyList[UMLError.UException] \/ DocumentSet[MagicDrawUML] = {

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
        .fold[\/[NonEmptyList[UMLError.UException], URI]] {
          -\/(
            NonEmptyList(
              documentOpsException(
                this,
                "initializeDocumentSet() failed: Cannot find OTI catalog file!")))
        }{ url =>
          catching(nonFatalCatcher)
          .either(url.toURI)
          .fold[\/[NonEmptyList[UMLError.UException], URI]](
            (cause: java.lang.Throwable) =>
              -\/(
                NonEmptyList(
                  documentOpsException(
                    this,
                    s"initializeDocumentSet() failed: ${cause.getMessage}",
                    cause.some))),

            (uri: java.net.URI) =>
              \/-(uri)
          )
        }

      _ <- otiCatalog.parseCatalog(otiURI)

      mdURI <-
      Seq(mdPath1, mdPath2)
        .flatMap { path => Option.apply(mdUMLCL.getResource(path)) }
        .headOption
        .fold[\/[NonEmptyList[UMLError.UException], URI]] {
        -\/(
          NonEmptyList(
            documentOpsException(
              this,
              "initializeDocumentSet() failed: Cannot find MagicDraw catalog file!")))
        }{ url =>
          catching(nonFatalCatcher)
            .either(url.toURI)
            .fold[\/[NonEmptyList[UMLError.UException], URI]](
              (cause: java.lang.Throwable) =>
                -\/(
                  NonEmptyList(
                    documentOpsException(
                      this,
                      s"initializeDocumentSet() failed: ${cause.getMessage}",
                      cause.some))),

              (uri: java.net.URI) =>
                \/-(uri)
            )
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
  : NonEmptyList[UMLError.UException] \/ DocumentSet[MagicDrawUML] = {

    Option.apply(Application.getInstance.getProject)
    .fold[\/[NonEmptyList[UMLError.UException], DocumentSet[MagicDrawUML]]]{
      -\/(
        NonEmptyList(
          documentOpsException(
          this,
          "initializeDocumentSet(documentURIMapper, builtInURIMapper) failed: "+
          "Cannot initialize a MagicDraw OTI DocumentSet without a current Project")))
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
  : NonEmptyList[UMLError.UException] \/ DocumentSet[MagicDrawUML] = {
    \/-(
      MagicDrawDocumentSet(
        serializableDocuments, builtInDocuments, builtInDocumentEdges,
        documentURIMapper, builtInURIMapper, aggregate))
  }

  override def createSerializableDocumentFromExistingRootPackage
  (root: UMLPackage[MagicDrawUML])
  : NonEmptyList[UMLError.UException] \/ SerializableDocument[MagicDrawUML] =
    umlUtil
    .resolvedMagicDrawOTISymbols
    .flatMap { otiMDSymbols =>
      root
      .getEffectiveURI.orElse(
        NonEmptyList(
          documentOpsException(
            docOps,
            "No effective URI to serialize the package",
            UMLError
              .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
              "Needs an effective URI to serialize a package",
              Iterable(root)).some))
        .left)
      .flatMap {
        _.fold[\/[NonEmptyList[UMLError.UException], SerializableDocument[MagicDrawUML]]](
          NonEmptyList(
            documentOpsException(
              docOps,
              "No effective URI to serialize the package",
              UMLError
                .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                "Needs an effective URI to serialize a package",
                Iterable(root)).some))
            .left
        ) { uri =>
          root
            .oti_nsPrefix
            .flatMap {
              _.fold[\/[NonEmptyList[UMLError.UException], SerializableDocument[MagicDrawUML]]](
                NonEmptyList(
                  documentOpsException(
                    docOps,
                    "No effective URI to serialize the package",
                    UMLError
                      .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                      "Needs an effective URI to serialize a package",
                      Iterable(root)).some))
                  .left
              ) { nsPrefix =>
                root
                  .oti_uuidPrefix
                  .flatMap {
                    _.fold[\/[NonEmptyList[UMLError.UException], SerializableDocument[MagicDrawUML]]](
                      NonEmptyList(
                        documentOpsException(
                          docOps,
                          "No UUID prefix to serialize the package",
                          UMLError
                            .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                            "Needs a uuid prefix to serialize a package",
                            Iterable(root)).some))
                        .left
                    ) { uuidPrefix =>
                      root
                        .getDocumentURL
                        .flatMap {
                          _.fold[\/[NonEmptyList[UMLError.UException], SerializableDocument[MagicDrawUML]]](
                            NonEmptyList(
                              documentOpsException(
                                docOps,
                                "No document URL to serialize the package",
                                UMLError
                                  .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                                  "Needs a document URL serialize a package",
                                  Iterable(root)).some))
                              .left
                          ) { documentURL =>
                            createSerializableDocumentFromExistingRootPackage(
                              otiMDSymbols, root, uri, nsPrefix, uuidPrefix, documentURL)
                          }
                        }
                    }
                  }
              }
            }
        }
      }
    }

    def createSerializableDocumentFromExistingRootPackage
    (otiMDSymbols: MagicDrawOTISymbols,
     root: UMLPackage[MagicDrawUML],
     uri: String,
     nsPrefix: String,
     uuidPrefix: String,
     documentURL: String)
    : NonEmptyList[UMLError.UException] \/ SerializableDocument[MagicDrawUML] = {
          val externalDocumentResourceURL = new URI(documentURL)
          val mdPkg = umlUtil.umlMagicDrawUMLPackage(root).getMagicDrawPackage
          import MagicDrawProjectAPIHelper._
          Option.apply(ProjectUtilities.getProject(mdPkg))
            .fold[\/[NonEmptyList[UMLError.UException], Option[MagicDrawLoadURL]]](
            NonEmptyList(
              documentOpsException(
                docOps,
                "No MagicDraw project for the selected package",
                UMLError
                  .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                  "Needs an active MagicDraw project package to serialize",
                  Iterable(root)).some))
              .left
          ) {
            case mdServerProject: TeamworkPrimaryProject =>
              root.oti_artifactKind.flatMap {
                case _@(otiMDSymbols.oti_artifact_kind_specified_profile |
                        otiMDSymbols.oti_artifact_kind_specified_model_library) =>
                  MagicDrawServerProjectLoadURL(
                    externalDocumentResourceURL,
                    magicDrawServerProjectResource = mdServerProject.getResourceURI
                  ).some.right
                case _ =>
                  Option.empty[MagicDrawLoadURL].right
              }
            case mdServerModule: TeamworkAttachedProject =>
              root.oti_artifactKind.flatMap {
                case _@(otiMDSymbols.oti_artifact_kind_specified_profile |
                        otiMDSymbols.oti_artifact_kind_specified_model_library) =>
                  MagicDrawAttachedServerModuleSerializableDocumentLoadURL(
                    externalDocumentResourceURL,
                    magicDrawAttachedServerModuleResource = mdServerModule.getResourceURI
                  ).some.right
                case _ =>
                  Option.empty[MagicDrawLoadURL].right
              }
            case mdLocalProject: LocalPrimaryProject =>
              root.oti_artifactKind.flatMap {
                case _@(otiMDSymbols.oti_artifact_kind_specified_profile |
                        otiMDSymbols.oti_artifact_kind_specified_model_library) =>
                  MagicDrawLocalProjectLoadURL(
                    externalDocumentResourceURL,
                    magicDrawLocalProjectResource = mdLocalProject.getResourceURI
                  ).some.right
                case _ =>
                  Option.empty[MagicDrawLoadURL].right
              }
            case mdLocalModule: LocalAttachedProject =>
              root.oti_artifactKind.flatMap {
                case _@(otiMDSymbols.oti_artifact_kind_specified_profile |
                        otiMDSymbols.oti_artifact_kind_specified_model_library) =>
                  MagicDrawAttachedLocalModuleSerializableDocumentLoadURL(
                    externalDocumentResourceURL,
                    magicDrawAttachedLocalModuleResource = mdLocalModule.getResourceURI
                  ).some.right
                case _ =>
                  Option.empty[MagicDrawLoadURL].right
              }
            case _ =>
              Option.empty[MagicDrawLoadURL].right
          }
            .flatMap {
              _.fold[ \/[NonEmptyList[UMLError.UException], SerializableDocument[MagicDrawUML]]](
                NonEmptyList(
                  documentOpsException(
                    docOps,
                    "No OTI Document for this package",
                    UMLError
                      .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                      "Needs an active MagicDraw project package to serialize",
                      Iterable(root)).some))
                  .left
              ) { mdDocumentURL =>

                System.out.println(s"mdDocumentURL: $mdDocumentURL")
                MagicDrawSerializableDocument(
                  new URI(uri), nsPrefix, uuidPrefix, mdDocumentURL, scope = root
                ).right
              }
            }
        }

  override def createSerializableDocumentFromImportedRootPackage
  (uri: URI,
   nsPrefix: String,
   uuidPrefix: String,
   documentURL: MagicDrawUML#LoadURL,
   scope: UMLPackage[MagicDrawUML])
  (implicit ds: DocumentSet[MagicDrawUML])
  : NonEmptyList[UMLError.UException] \/ SerializableDocument[MagicDrawUML] =
  \/-(
    MagicDrawSerializableDocument(uri, nsPrefix, uuidPrefix, documentURL, scope)
  )

  override def getExternalDocumentURL(lurl: MagicDrawUML#LoadURL)
  : NonEmptyList[UMLError.UException] \/ URI =
    lurl.externalDocumentResourceURL.right

  override def openExternalDocumentStreamForImport
  (lurl: MagicDrawUML#LoadURL)
  : NonEmptyList[UMLError.UException] \/ java.io.InputStream =
    lurl.externalDocumentResourceURL.toURL.openStream().right

}

/**
 * Helper for accessing MagicDraw's Project APIs
 *
 * Note: In MD 18.0, the Project APIs used below are marked Internal and deprecated.
 * These APIs are marked Open in MD 18.2
 *
 * Strict compilation with Scala (i.e., warn for deprecated APIs & treat warnings as errors)
 * requires selectively hiding intentional use of deprecated APIs.
 * Adapted from: https://issues.scala-lang.org/browse/SI-7934
 *
 * Note: In MD 18.0, the
 *
 * @see http://jdocs.nomagic.com/182/com/nomagic/ci/persistence/IProject.html
 * @see http://jdocs.nomagic.com/182/com/nomagic/ci/persistence/IProject.html#getLocationURI()
 */
@deprecated("", "")
class MagicDrawProjectAPIHelper(val iProject: IProject) extends AnyVal {

  def getResourceURI: URI =
    new URI(iProject.getLocationURI.toString)

}

object MagicDrawProjectAPIHelper {

  implicit def toAPIHelper(iProject: IProject): MagicDrawProjectAPIHelper =
    new MagicDrawProjectAPIHelper(iProject)

}
