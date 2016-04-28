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

import org.apache.xml.resolver.CatalogManager

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._

import org.omg.oti.magicdraw.uml.read._

import scala.collection.immutable._
import scala.reflect.ClassTag
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