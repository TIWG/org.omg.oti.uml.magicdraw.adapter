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

import org.apache.xml.resolver.CatalogManager

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._

import org.omg.oti.magicdraw.uml.read._

import scala.collection.immutable._
import scala.Option
import scala.Predef.{classOf}
import scalaz._, Scalaz._

case class MagicDrawCatalogManager(catalog: CatalogURIMapper) {

  /*
   * to be updated...
  
  def getResource(rs: ResourceSet, uri: String, loadOnDemand: Boolean)
  : Set[java.lang.Throwable] \/ Resource =
    catalog.resolve(uri).flatMap { resolved: Option[String] =>
      val resolvedURI: String = resolved.getOrElse(uri)
      catching(nonFatalCatcher)
        .either(rs.getResource(EURI.createURI(resolvedURI), loadOnDemand))
        .fold[Set[java.lang.Throwable] \/ Resource](
          (cause: java.lang.Throwable) =>
            Set(
              UMLError.UMLAdaptationException(
                s"MagicDrawCatalogManager.getResource(uri=$resolvedURI) failed: ${cause.getMessage}",
                cause)
            ).left,
          (r: Resource) =>
            r.right
        )
    }
  
  def getResourceRootByType[MC <: EModelElement : ClassTag]
  (r: Resource,
   eMetaclass: EClassifier)
  : Set[java.lang.Throwable] \/ MC =
    catching(nonFatalCatcher)
        .either(Option.apply(EcoreUtil.getObjectByType(r.getContents(), eMetaclass)))
        .fold[Set[java.lang.Throwable] \/ MC](
          (cause: java.lang.Throwable) =>
            Set(
              UMLError.UMLAdaptationException(
                s"MagicDrawCatalogManager.getResourceRootbyType(r=${r.getURI}, eMetaclass=${eMetaclass.getName}) failed: ${cause.getMessage}",
                cause)
            ).left,
          (o) =>
            o.fold[Set[java.lang.Throwable] \/ MC](
             Set(
               UMLError.umlAdaptationError(
                s"MagicDrawCatalogManager.getResourceRootbyType(r=${r.getURI}, eMetaclass=${eMetaclass.getName}) failed: no such root")
             ).left
            ){ 
              case root: MC =>
                root.right
              case _ =>
                Set(
                  UMLError.umlAdaptationError(
                    s"MagicDrawCatalogManager.getResourceRootbyType(r=${r.getURI}, eMetaclass=${eMetaclass.getName}) failed: no such root")
                ).left
            }
        )
        
        */
}

object MagicDrawCatalogManager {

  def createMagicDrawCatalogManager(): Set[java.lang.Throwable] \/ MagicDrawCatalogManager = {

    val catalogManager: CatalogManager = new CatalogManager()
    val catalog: CatalogURIMapper = new CatalogURIMapper(catalogManager)

    val magicdrawUMLCL = classOf[MagicDrawUML].getClassLoader
    val catalogPath1 = "resources/md18Catalog/omg.magicdraw.catalog.xml"
    val catalogPath2 = "md18Catalog/omg.magicdraw.catalog.xml"

    val catalogURLs = Seq(catalogPath1, catalogPath2)
      .flatMap { path => Option.apply(magicdrawUMLCL.getResource(path)) }

    val catalogURI: Set[java.lang.Throwable] \/ java.net.URI =
      catalogURLs
        .headOption
        .fold[Set[java.lang.Throwable] \/ java.net.URI](
          Set(
            UMLError.umlAdaptationError("Cannot find MagicDraw catalog file!")).left
        ) { url =>
            url.toURI.right
          }

    catalogURI.flatMap { catalogURI =>

      catalog.parseCatalog(catalogURI).map { _ =>
        MagicDrawCatalogManager(catalog)
      }
    }

  }

}