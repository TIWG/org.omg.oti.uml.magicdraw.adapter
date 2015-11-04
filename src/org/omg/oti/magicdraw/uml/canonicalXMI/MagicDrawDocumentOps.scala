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
import org.omg.oti.uml.characteristics._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._
import org.omg.oti.uml.xmi._

import org.omg.oti.magicdraw.uml.read._

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
(implicit umlUtil: MagicDrawUMLUtil,
 override implicit val otiCharacteristicsProvider: OTICharacteristicsProvider[MagicDrawUML])
  extends DocumentOps[MagicDrawUML] {

  implicit val docOps = this
  import otiCharacteristicsProvider._

  override def addDocument
  (ds: DocumentSet[MagicDrawUML], d: SerializableDocument[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ DocumentSet[MagicDrawUML] =
    MagicDrawDocumentSet.addDocument(ds, d)

  override def createBuiltInDocumentFromBuiltInRootPackage
  (info: OTISpecificationRootCharacteristics,
   documentURL: MagicDrawUML#LoadURL,
   root: UMLPackage[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ BuiltInDocument[MagicDrawUML] =
  ???

  def initializeDocumentSet
  ()
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : NonEmptyList[java.lang.Throwable] \/ DocumentSet[MagicDrawUML] = {

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
        .fold[NonEmptyList[java.lang.Throwable] \/ URI] {
          -\/(
            NonEmptyList(
              documentOpsException(
                this,
                "initializeDocumentSet() failed: Cannot find OTI catalog file!")))
        }{ url =>
          catching(nonFatalCatcher)
          .either(url.toURI)
          .fold[NonEmptyList[java.lang.Throwable] \/ URI](
            (cause: java.lang.Throwable) =>
              -\/(
                NonEmptyList(
                  documentOpsException(
                    this,
                    s"initializeDocumentSet() failed: ${cause.getMessage}",
                    cause))),

            (uri: java.net.URI) =>
              \/-(uri)
          )
        }

      _ <- otiCatalog.parseCatalog(otiURI)

      mdURI <-
      Seq(mdPath1, mdPath2)
        .flatMap { path => Option.apply(mdUMLCL.getResource(path)) }
        .headOption
        .fold[NonEmptyList[java.lang.Throwable] \/ URI] {
        -\/(
          NonEmptyList(
            documentOpsException(
              this,
              "initializeDocumentSet() failed: Cannot find MagicDraw catalog file!")))
        }{ url =>
          catching(nonFatalCatcher)
            .either(url.toURI)
            .fold[NonEmptyList[java.lang.Throwable] \/ URI](
              (cause: java.lang.Throwable) =>
                -\/(
                  NonEmptyList(
                    documentOpsException(
                      this,
                      s"initializeDocumentSet() failed: ${cause.getMessage}",
                      cause))),

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
  : NonEmptyList[java.lang.Throwable] \/ DocumentSet[MagicDrawUML] = {

    Option.apply(Application.getInstance.getProject)
    .fold[NonEmptyList[java.lang.Throwable] \/ DocumentSet[MagicDrawUML]]{
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
  : NonEmptyList[java.lang.Throwable] \/ DocumentSet[MagicDrawUML] = {
    \/-(
      MagicDrawDocumentSet(
        serializableDocuments, builtInDocuments, builtInDocumentEdges,
        documentURIMapper, builtInURIMapper, aggregate))
  }

  def getResultOrError
  (message: String, e: UMLElement[MagicDrawUML]*)
  (result: NonEmptyList[java.lang.Throwable] \/ Option[String])
  : NonEmptyList[java.lang.Throwable] \/ String =
  result.fold[NonEmptyList[java.lang.Throwable] \/ String](
    l = (nels: NonEmptyList[java.lang.Throwable]) =>
      NonEmptyList(
        UMLError.illegalElementException[MagicDrawUML, UMLElement[MagicDrawUML]]("Error while evaluating the " + message, e, nels.head)
      ).left,
    r = (maybe: Option[String]) =>
      maybe.fold[NonEmptyList[java.lang.Throwable] \/ String](
        NonEmptyList(
          UMLError.illegalElementError[MagicDrawUML, UMLElement[MagicDrawUML]]("Error: missing value for the " + message, e)
        ).left
      ){ result =>
        result.right
      }
  )

  def createSerializableDocumentFromExistingRootPackage
  (root: UMLPackage[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] =
    getSpecificationRootCharacteristics(root)
      .flatMap {
      _.fold[NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML]](
        NonEmptyList(
          UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
            s"Package ${root.qualifiedName.get} does not designate an OTI Specification Document artifact",
            Iterable(root)))
          .left
      ){ otiCharacteristics =>
        createSerializableDocumentFromExistingRootPackage(otiCharacteristics, root)
      }
    }

  override def createSerializableDocumentFromExistingRootPackage
  (info: OTISpecificationRootCharacteristics,
   root: UMLPackage[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] =
    \/.fromTryCatchNonFatal(new java.net.URI(info.documentURL.trim))
      .fold[NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML]](
      l = (t: java.lang.Throwable) =>
        NonEmptyList(
          UMLError.illegalElementException[MagicDrawUML, UMLPackage[MagicDrawUML]](
          s"createSerializableDocumentFromExistingRootPackage $info failed",
          Iterable(root), t)
        ).left,
      r = (externalDocumentResourceURL: java.net.URI) =>
        createSerializableDocumentFromExistingRootPackage(externalDocumentResourceURL, info, root)
    )

  def createSerializableDocumentFromExistingRootPackage
  (externalDocumentResourceURL: java.net.URI,
   info: OTISpecificationRootCharacteristics,
   root: UMLPackage[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] ={
    val mdPkg = umlUtil.umlMagicDrawUMLPackage(root).getMagicDrawPackage
    import MagicDrawProjectAPIHelper._
    val mdLoadURLOrError: NonEmptyList[java.lang.Throwable] \/ MagicDrawLoadURL =
      Option.apply(ProjectUtilities.getProject(mdPkg))
        .fold[NonEmptyList[java.lang.Throwable] \/ MagicDrawLoadURL](
        NonEmptyList(
          documentOpsException(
            docOps,
            s"No MagicDraw project for package '${root.qualifiedName.get}'",
            UMLError
              .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
              "No active MagicDraw project",
              Iterable(root))))
          .left
      ) {
        case mdServerProject: TeamworkPrimaryProject =>
          info.artifactKind match {
            case _@(OTISerializableProfileArtifactKind() |
                    OTISerializableModelLibraryArtifactKind()) =>
                MagicDrawServerProjectLoadURL(
                  externalDocumentResourceURL,
                  magicDrawServerProjectResource = mdServerProject.getResourceURI
                ).right
              case otherK =>
                NonEmptyList(
                  UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                    s"artifactKind for TeamworkPrimaryProject package ${root.qualifiedName.get}' should be "+
                    s"'${OTISerializableProfileArtifactKind()}' or "+
                    s"'${OTISerializableModelLibraryArtifactKind()}', "+
                    s"not $otherK",
                    Iterable(root))
                ).left
          }
        case mdServerModule: TeamworkAttachedProject =>
          info.artifactKind match {
            case _@(OTISerializableProfileArtifactKind() |
                    OTISerializableModelLibraryArtifactKind()) =>
                MagicDrawAttachedServerModuleSerializableDocumentLoadURL(
                  externalDocumentResourceURL,
                  magicDrawAttachedServerModuleResource = mdServerModule.getResourceURI
                ).right
              case otherK =>
                NonEmptyList(
                  UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                    s"artifactKind for TeamworkAttachedProject package ${root.qualifiedName.get}' should be "+
                    s"'${OTISerializableProfileArtifactKind()}' or "+
                    s"'${OTISerializableModelLibraryArtifactKind()}', "+
                    s"not $otherK",
                  Iterable(root))
                ).left
          }
        case mdLocalProject: LocalPrimaryProject =>
          info.artifactKind match {
            case _@(OTISerializableProfileArtifactKind() |
                    OTISerializableModelLibraryArtifactKind()) =>
                MagicDrawLocalProjectLoadURL(
                  externalDocumentResourceURL,
                  magicDrawLocalProjectResource = mdLocalProject.getResourceURI
                ).right
              case otherK =>
                NonEmptyList(
                  UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                    s"artifactKind for LocalPrimaryProject package ${root.qualifiedName.get}' should be "+
                    s"'${OTISerializableProfileArtifactKind()}' or "+
                    s"'${OTISerializableModelLibraryArtifactKind()}', "+
                    s"not $otherK",
                    Iterable(root))
                ).left
          }
        case mdLocalModule: LocalAttachedProject =>
          info.artifactKind match {
            case _@(OTISerializableProfileArtifactKind() |
                    OTISerializableModelLibraryArtifactKind()) =>
                MagicDrawAttachedLocalModuleSerializableDocumentLoadURL(
                  externalDocumentResourceURL,
                  magicDrawAttachedLocalModuleResource = mdLocalModule.getResourceURI
                ).right
              case otherK =>
                NonEmptyList(
                  UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                    s"artifactKind for LocalAttachedProject package ${root.qualifiedName.get}' should be "+
                    s"'${OTISerializableProfileArtifactKind()}' or "+
                    s"'${OTISerializableModelLibraryArtifactKind()}', "+
                    s"not $otherK",
                    Iterable(root))
                ).left
          }
        case iProject =>
          NonEmptyList(
            UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
              s"package ${root.qualifiedName.get}' is in an unrecognized kind of MagicDraw IProject: $iProject ",
              Iterable(root))
          ).left
      }

    mdLoadURLOrError
    .flatMap { mdDocumentURL =>
      MagicDrawSerializableDocument(info, mdDocumentURL, scope = root).right
    }
  }

  override def createSerializableDocumentFromImportedRootPackage
  (info: OTISpecificationRootCharacteristics,
   documentURL: MagicDrawUML#LoadURL,
   scope: UMLPackage[MagicDrawUML])
  (implicit ds: DocumentSet[MagicDrawUML])
  : NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] =
  \/-(
    MagicDrawSerializableDocument(info, documentURL, scope)
  )

  override def getExternalDocumentURL(lurl: MagicDrawUML#LoadURL)
  : NonEmptyList[java.lang.Throwable] \/ URI =
    lurl.externalDocumentResourceURL.right

  override def openExternalDocumentStreamForImport
  (lurl: MagicDrawUML#LoadURL)
  : NonEmptyList[java.lang.Throwable] \/ java.io.InputStream =
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
