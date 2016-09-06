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

package org.omg.oti.magicdraw.uml.canonicalXMI.helper

import com.nomagic.magicdraw.core.Project
import org.omg.oti.magicdraw.uml.canonicalXMI.{MagicDrawDocumentOps, MagicDrawDocumentSet, MagicDrawHashIDGenerator, MagicDrawIDGenerator}
import org.omg.oti.magicdraw.uml.characteristics.{MagicDrawOTICharacteristicsDataProvider, MagicDrawOTICharacteristicsProfileProvider}
import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.magicdraw.uml.write.{MagicDrawUMLFactory, MagicDrawUMLUpdate}
import org.omg.oti.json.common._
import org.omg.oti.uml.{RelationTriple, UMLError}
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.canonicalXMI.helper.OTIAdapter
import org.omg.oti.uml.characteristics.OTICharacteristicsProvider
import org.omg.oti.uml.read.api.{UML, UMLComment, UMLElement, UMLPackage}
import org.omg.oti.uml.xmi.Document

import scala.collection.immutable._
import scala.reflect.runtime.universe._
import scala.util.control.Exception._
import scala.{Boolean, None, Option, StringContext}
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
  : Set[java.lang.Throwable] \/ MagicDrawOTICharacteristicsProfileProvider
  = \/-(MagicDrawOTICharacteristicsProfileProvider()(otiCharacterizations, umlOps))

  def magicDrawOTIDataCharacteristicsCreator
  (data: Vector[OTIDocumentSetConfiguration])
  (umlOps: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ MagicDrawOTICharacteristicsDataProvider
  = \/-(MagicDrawOTICharacteristicsDataProvider(data)(umlOps))

  def magicDrawUMLFactoryCreator
  (umlOps: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ MagicDrawUMLFactory
  = \/-(MagicDrawUMLFactory(umlOps))

  def magicDrawUMLUpdateCreator
  (umlOps: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ MagicDrawUMLUpdate
  = \/-(MagicDrawUMLUpdate(umlOps))

  def initializeWithProfileCharacterizations
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]])
  (umlOpsCreator
   : => Set[java.lang.Throwable] \/ MagicDrawUMLUtil
   = magicDrawUMLOpsCreator(p),
   otiCharacteristicsCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawOTICharacteristicsProfileProvider
   = magicDrawOTIProfileCharacteristicsCreator(otiCharacterizations),
   factoryCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawUMLFactory
   = magicDrawUMLFactoryCreator,
   updateCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawUMLUpdate
   = magicDrawUMLUpdateCreator)
  : Set[java.lang.Throwable] \/ MagicDrawOTIProfileAdapter
  = {

    val result
    : Set[java.lang.Throwable] \/ MagicDrawOTIProfileAdapter
    = OTIAdapter.initialize[ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsProfileProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate ](umlOpsCreator, otiCharacteristicsCreator, factoryCreator, updateCreator)

    result
  }

  def initializeWithDataCharacterizations
  (p: Project,
   data: Vector[OTIDocumentSetConfiguration])
  (umlOpsCreator
   : => Set[java.lang.Throwable] \/ MagicDrawUMLUtil
   = magicDrawUMLOpsCreator(p),
   otiCharacteristicsCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawOTICharacteristicsDataProvider
   = magicDrawOTIDataCharacteristicsCreator(data),
   factoryCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawUMLFactory
   = magicDrawUMLFactoryCreator,
   updateCreator
   : MagicDrawUMLUtil => Set[java.lang.Throwable] \/ MagicDrawUMLUpdate
   = magicDrawUMLUpdateCreator)
  : Set[java.lang.Throwable] \/ MagicDrawOTIDataAdapter
  = OTIAdapter.initialize(umlOpsCreator, otiCharacteristicsCreator, factoryCreator, updateCreator)

  // =================================================================================================================

  def magicDrawDocumentOpsCreator
  [OCP <: OTICharacteristicsProvider[MagicDrawUML],
   OA <: OTIAdapter[ MagicDrawUML, MagicDrawUMLUtil, OCP, MagicDrawUMLFactory, MagicDrawUMLUpdate ]]
  (oa: OA)
  : Set[java.lang.Throwable] \/ MagicDrawDocumentOps
  = \/-(new MagicDrawDocumentOps()(oa.umlOps, oa.otiCharacteristicsProvider, oa.umlF, oa.umlU))

  val omgCL = classOf[UML].getClassLoader
  val mdCL = classOf[MagicDrawUML].getClassLoader
  
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
  : Set[java.lang.Throwable] \/ (CatalogURIMapper, CatalogURIMapper) 
  = {

    val omgCatalogResourceURIs
    : Seq[java.net.URI]
    = omgCatalogResourceURLs.flatMap { urlPath =>
      Option.apply(omgCL.getResource(urlPath)).map(_.toURI)
    }

    val mdCatalogResourceURIs
    : Seq[java.net.URI]
    = mdCatalogResourceURLs.flatMap { urlPath =>
      Option.apply(mdCL.getResource(urlPath)).map(_.toURI)
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

  def withInitialDocumentSetForProfileAdapter
  (oa: MagicDrawOTIProfileAdapter,
   catalogsInitializer
   : () => Set[java.lang.Throwable] \/ (CatalogURIMapper, CatalogURIMapper)
   = () => getMDCatalogs(),
   documentOpsCreator
   : MagicDrawOTIProfileAdapter => Set[java.lang.Throwable] \/ MagicDrawDocumentOps
   = magicDrawDocumentOpsCreator[MagicDrawOTICharacteristicsProfileProvider, MagicDrawOTIProfileAdapter] _,
   documentSetInitializer
   : (CatalogURIMapper, CatalogURIMapper) => MagicDrawDocumentOps => Set[java.lang.Throwable] \&/ MagicDrawDocumentSet
   = magicDrawDocumentSetInitializer)
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : Set[java.lang.Throwable] \&/ MagicDrawOTIDocumentSetAdapterForProfileProvider
  = catalogsInitializer().toThese.flatMap { case (builtInCatalog, mdCatalog) =>
      oa.withInitialDocumentSet(
        documentOpsCreator,
        documentSetInitializer(builtInCatalog, mdCatalog))
  }


  def withInitialDocumentSetForDataAdapter
  (oa: MagicDrawOTIDataAdapter,
   catalogsInitializer
   : () => Set[java.lang.Throwable] \/ (CatalogURIMapper, CatalogURIMapper)
   = () => getMDCatalogs(),
   documentOpsCreator
   : MagicDrawOTIDataAdapter => Set[java.lang.Throwable] \/ MagicDrawDocumentOps
   = magicDrawDocumentOpsCreator[MagicDrawOTICharacteristicsDataProvider, MagicDrawOTIDataAdapter] _,
   documentSetInitializer
   : (CatalogURIMapper, CatalogURIMapper) => MagicDrawDocumentOps => Set[java.lang.Throwable] \&/ MagicDrawDocumentSet
   = magicDrawDocumentSetInitializer)
  ( implicit
    nodeT: TypeTag[Document[MagicDrawUML]],
    edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
  : Set[java.lang.Throwable] \&/ MagicDrawOTIDocumentSetAdapterForDataProvider
  = catalogsInitializer().toThese.flatMap { case (builtInCatalog, mdCatalog) =>
      oa.withInitialDocumentSet(
        documentOpsCreator,
        documentSetInitializer(builtInCatalog, mdCatalog))
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