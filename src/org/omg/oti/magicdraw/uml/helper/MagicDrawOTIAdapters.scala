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
package org.omg.oti.magicdraw.uml.helper

import java.io.File

import com.nomagic.magicdraw.core.Project
import gov.nasa.jpl.dynamicScripts.magicdraw.utils.MDUML
import org.omg.oti.magicdraw.uml.canonicalXMI.{MagicDrawDocumentOps, MagicDrawDocumentSet, MagicDrawHashIDGenerator, MagicDrawIDGenerator}
import org.omg.oti.magicdraw.uml.characteristics.MagicDrawOTICharacteristicsProfileProvider
import org.omg.oti.magicdraw.uml.read.{MagicDrawFileChooser, MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.magicdraw.uml.write.{MagicDrawUMLFactory, MagicDrawUMLUpdate}
import org.omg.oti.uml.{RelationTriple, UMLError}
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.characteristics.OTICharacteristicsProvider
import org.omg.oti.uml.helper.OTIAdapter
import org.omg.oti.uml.read.api.{UML, UMLComment, UMLElement, UMLPackage}
import org.omg.oti.uml.read.operations.UMLOps
import org.omg.oti.uml.write.api.{UMLFactory, UMLUpdate}
import org.omg.oti.uml.xmi.{Document, IDGenerator}

import scala.collection.immutable._
import scala.reflect.runtime.universe._
import scala.util.control.Exception._
import scala.{Boolean, Option, None, StringContext}
import scala.Predef.{String, classOf}
import scalaz._
import Scalaz._

object MagicDrawOTIAdapters {

  def magicDrawUMLOpsCreator
  (p: Project)
  ()
  : Set[java.lang.Throwable] \/ MagicDrawUMLUtil
  = nonFatalCatch[Set[java.lang.Throwable] \/ MagicDrawUMLUtil]
    .withApply{
      (cause: java.lang.Throwable) =>
        Set(
          UMLError.UMLAdaptationException(
            s"Error magicDrawUMLOpsCreator: ${cause.getMessage}",
            cause)
        ).left
    }
    .apply(\/-(MagicDrawUMLUtil(p)))


  def magicDrawOTIProfileCharacteristicsCreator
  (otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]])
  (umlOps: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ OTICharacteristicsProvider[MagicDrawUML]
  = \/-(MagicDrawOTICharacteristicsProfileProvider()(otiCharacterizations, umlOps))

  def magicDrawUMLFactoryCreator
  (umlOps: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ MagicDrawUMLFactory
  = \/-(MagicDrawUMLFactory(umlOps))

  def magicDrawUMLUpdateCreator
  (umlOps: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ MagicDrawUMLUpdate
  = \/-(MagicDrawUMLUpdate(umlOps))

  def initialize
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]])
  (umlOpsCreator
   : => Set[java.lang.Throwable] \/ MagicDrawUMLUtil
   = magicDrawUMLOpsCreator(p),
   otiCharacteristicsCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ OTICharacteristicsProvider[MagicDrawUML]
   = magicDrawOTIProfileCharacteristicsCreator(otiCharacterizations),
   factoryCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawUMLFactory
   = magicDrawUMLFactoryCreator,
   updateCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawUMLUpdate
   = magicDrawUMLUpdateCreator)
  : Set[java.lang.Throwable] \/ MagicDrawOTIAdapter
  = OTIAdapter.initialize(umlOpsCreator, otiCharacteristicsCreator, factoryCreator, updateCreator)

  // =================================================================================================================

  def magicDrawDocumentOpsCreator
  (oa: MagicDrawOTIAdapter)
  : Set[java.lang.Throwable] \/ MagicDrawDocumentOps
  = \/-(new MagicDrawDocumentOps()(oa.umlOps, oa.otiCharacteristicsProvider, oa.umlF, oa.umlU))

  def getMDCatalogs
  (omgCatalogResourcePath: String =
   "dynamicScripts/org.omg.oti.uml.core/resources/omgCatalog/omg.local.catalog.xml",
   omgCatalogResourceURLs: Seq[String] =
   Seq("resources/omgCatalog/omg.local.catalog.xml", "omgCatalog/omg.local.catalog.xml"),
   mdCatalogResourcePath: String =
   "dynamicScripts/org.omg.oti.uml.magicdraw.adapter/resources/md18Catalog/omg.magicdraw.catalog.xml",
   mdCatalogResourceURLs: Seq[String] =
   Seq("resources/md18Catalog/omg.magicdraw.catalog.xml", "md18Catalog/omg.magicdraw.catalog.xml"))
  ()
  : Set[java.lang.Throwable] \/ (CatalogURIMapper, CatalogURIMapper) = {

    val defaultOMGCatalogFile =
      new File(MDUML.getApplicationInstallDir.toURI.resolve(omgCatalogResourcePath))

    val omgCatalogResourceURIs
    : Seq[java.net.URI]
    = if (defaultOMGCatalogFile.exists())
      Seq(defaultOMGCatalogFile.toURI)
    else {
      val omgCL = classOf[UML].getClassLoader
      val uris
      : Seq[java.net.URI]
      = omgCatalogResourceURLs.flatMap { urlPath =>
        Option.apply(omgCL.getResource(urlPath)).map(_.toURI)
      }
      if (uris.nonEmpty)
        uris
      else
        MagicDrawFileChooser.chooseCatalogFile("Select the OMG UML 2.5 *.catalog.xml file").map(_.toURI).to[Seq]
    }

    val defaultMDCatalogFile =
      new File(MDUML.getApplicationInstallDir.toURI.resolve(mdCatalogResourcePath))

    val mdCatalogResourceURIs
    : Seq[java.net.URI]
    = if (defaultMDCatalogFile.exists())
      Seq(defaultMDCatalogFile.toURI)
    else {
      val mdCL = classOf[MagicDrawUML].getClassLoader
      val uris
      : Seq[java.net.URI]
      = mdCatalogResourceURLs.flatMap { urlPath =>
        Option.apply(mdCL.getResource(urlPath)).map(_.toURI)
      }
      if (uris.nonEmpty)
        uris
      else
        MagicDrawFileChooser.chooseCatalogFile("Select the MagicDraw UML 2.5 *.catalog.xml file").map(_.toURI).to[Seq]
    }

    CatalogURIMapper.createMapperFromCatalogURIs(omgCatalogResourceURIs)
      .flatMap { omgCatalogMapper: CatalogURIMapper =>
        CatalogURIMapper.createMapperFromCatalogURIs(mdCatalogResourceURIs)
          .map { mdCatalogMapper: CatalogURIMapper =>
            (omgCatalogMapper, mdCatalogMapper)
          }
      }
  }

  def magicDrawDocumentSetInitializer
  (documentURIMapper: CatalogURIMapper,
   builtInURIMapper: CatalogURIMapper)
  (dOps: MagicDrawDocumentOps)
  : Set[java.lang.Throwable] \&/ MagicDrawDocumentSet
  = dOps
    .initializeDocumentSet(documentURIMapper, builtInURIMapper)

  def withInitialDocumentSet
  (oa: MagicDrawOTIAdapter,
   catalogsInitializer
   : () => Set[java.lang.Throwable] \/ (CatalogURIMapper, CatalogURIMapper)
   = () => getMDCatalogs(),
   documentOpsCreator
   : MagicDrawOTIAdapter => Set[java.lang.Throwable] \/ MagicDrawDocumentOps
   = magicDrawDocumentOpsCreator,
   documentSetInitializer
   : (CatalogURIMapper, CatalogURIMapper) => MagicDrawDocumentOps => Set[java.lang.Throwable] \&/ MagicDrawDocumentSet
   = magicDrawDocumentSetInitializer)
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : Set[java.lang.Throwable] \&/ MagicDrawOTIDocumentSetAdapter
  = catalogsInitializer().toThese.flatMap { case (builtInCatalog, mdCatalog) =>
    oa.withInitialDocumentSet(documentOpsCreator, documentSetInitializer(builtInCatalog, mdCatalog))
  }

  // =================================================================================================================

  def magicDrawIgnoreCrossReferencedElementFilter
  : UMLElement[MagicDrawUML] => Boolean
  = (_: UMLElement[MagicDrawUML]) => false

  def magicDrawUnresolvedElementMapper
  : UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]
  = (_: UMLElement[MagicDrawUML]) => None

  def magicDrawIncludeAllForwardRelationTriple
  : (Document[MagicDrawUML], RelationTriple[MagicDrawUML], Document[MagicDrawUML]) => Boolean
  = (_: Document[MagicDrawUML], _: RelationTriple[MagicDrawUML], _: Document[MagicDrawUML]) => true

  // =================================================================================================================

  def magicDrawIDGenerator
  (ds: DocumentSet[MagicDrawUML])
  : Set[java.lang.Throwable] \/ MagicDrawIDGenerator
  = \/-(MagicDrawIDGenerator()(ds))

  def magicDrawHashIDGenerator
  (ds: DocumentSet[MagicDrawUML])
  : Set[java.lang.Throwable] \/ MagicDrawHashIDGenerator
  = \/-(MagicDrawHashIDGenerator()(ds))

  def withIDGenerator
  (ordsa: MagicDrawOTIResolvedDocumentSetAdapter,
   idGeneratorCreator
   : DocumentSet[MagicDrawUML] => Set[java.lang.Throwable] \/ MagicDrawIDGenerator
   = magicDrawIDGenerator)
  : Set[java.lang.Throwable] \/ MagicDrawOTIResolvedDocumentSetIDGeneratorAdapter
  = ordsa.withIDGenerator(idGeneratorCreator)

  def withHashIDGenerator
  (ordsa: MagicDrawOTIResolvedDocumentSetAdapter,
   hashIdGeneratorCreator
   : DocumentSet[MagicDrawUML] => Set[java.lang.Throwable] \/ MagicDrawHashIDGenerator
   = magicDrawHashIDGenerator)
  : Set[java.lang.Throwable] \/ MagicDrawOTIResolvedDocumentSetHashIDGeneratorAdapter
  = ordsa.withIDGenerator(hashIdGeneratorCreator)

}