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
package org.omg.oti.magicdraw.uml.canonicalXMI.helper

import java.lang.System
import java.util.concurrent.TimeUnit

import com.nomagic.magicdraw.core.Project
import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.json.common._
import org.omg.oti.magicdraw.uml.canonicalXMI.MagicDrawDocumentSet
import org.omg.oti.magicdraw.uml.characteristics.{MagicDrawOTICharacteristicsDataProvider, MagicDrawOTICharacteristicsProfileProvider}
import org.omg.oti.magicdraw.uml.write.{MagicDrawUMLFactory, MagicDrawUMLUpdate}
import org.omg.oti.uml._
import org.omg.oti.uml.canonicalXMI.helper.OTIAdapter
import org.omg.oti.uml.characteristics.OTICharacteristicsProvider
import org.omg.oti.uml.read.api.{UML, UMLComment, UMLElement, UMLPackage}
import org.omg.oti.uml.xmi.Document

import scala.collection.immutable.{Map, Set}
import scala.{Boolean, Long, None, Option, StringContext}
import scalaz._

object MagicDrawOTIHelper {
  
  def getOTIMagicDrawProfileAdapter
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]])
  : Set[java.lang.Throwable] \/ MagicDrawOTIProfileAdapter
  = MagicDrawOTIAdapters.initializeWithProfileCharacterizations(p, otiCharacterizations)()

  def getOTIMagicDrawAdapterForProfileCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \/ MagicDrawOTIProfileAdapter
  = getOTIMagicDrawProfileAdapter(p, otiCharacterizations = None)

  def getOTIMagicDrawDataAdapter
  (p: Project,
   data: OTIDocumentSetConfiguration)
  : Set[java.lang.Throwable] \/ MagicDrawOTIDataAdapter
  = MagicDrawOTIAdapters.initializeWithDataCharacterizations(p, data)()

  def getOTIMagicDrawAdapterForDataCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \/ MagicDrawOTIDataAdapter
  = getOTIMagicDrawDataAdapter(p, data=OTIDocumentSetConfiguration.empty)

  def getOTIMagicDrawProfileInfo
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider
  = getOTIMagicDrawProfileAdapter(p, otiCharacterizations).toThese.flatMap { oa =>
    getOTIMagicDrawProfileResolvedDocumentSetAdapter(oa, specificationRootPackages)
  }

  def getOTIMagicDrawInfoForProfileCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider
  = getOTIMagicDrawProfileInfo(
    p,
    otiCharacterizations=Option.empty[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
    specificationRootPackages=Set[UMLPackage[MagicDrawUML]]())

  def getOTIMagicDrawProfileDocumentSetAdapter
  (oa: MagicDrawOTIProfileAdapter,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIDocumentSetAdapterForProfileProvider
  = {
    val t0: Long = java.lang.System.currentTimeMillis()
    for {
      odsa1 <- MagicDrawOTIAdapters.withInitialDocumentSetForProfileAdapter(oa)

      t1 = java.lang.System.currentTimeMillis()
      _ = {
        System.out.println(
          s"MagicDrawOTIAdapters.withInitialDocumentSet "+
            s"in ${prettyFiniteDuration(t1 - t0, TimeUnit.MILLISECONDS)}")
      }

      documents <- odsa1.documentOps.createDocumentsFromExistingRootPackages(specificationRootPackages)

      odsa2 <- odsa1.documentOps.addDocuments(odsa1.ds, documents).flatMap {
        case mdSet: MagicDrawDocumentSet =>
          \&/.That(odsa1.copy(ds=mdSet))
        case dSet =>
          \&/.This(Set[java.lang.Throwable](
            UMLError.umlAdaptationError(s"The document set, $dSet, should be a MagicDrawDocumentSet")
          ))
      }

      t2 = java.lang.System.currentTimeMillis()
      _ = {
        System.out.println(
          s"MagicDrawOTIAdapters.createDocumentsFromExistingRootPackages(${specificationRootPackages.size}) "+
            s" in ${prettyFiniteDuration(t2 - t1, TimeUnit.MILLISECONDS)}")
      }

    } yield odsa2

  }

  def getOTIMagicDrawProfileResolvedDocumentSetAdapter
  (oa: MagicDrawOTIProfileAdapter,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
   ignoreCrossReferencedElementFilter
   : UMLElement[MagicDrawUML] => Boolean
   = MagicDrawOTIAdapters.magicDrawIgnoreCrossReferencedElementFilter,
   unresolvedElementMapper
   : UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]
   = MagicDrawOTIAdapters.magicDrawUnresolvedElementMapper,
   includeAllForwardRelationTriple
   : (Document[MagicDrawUML], RelationTriple[MagicDrawUML], Document[MagicDrawUML]) => Boolean
   = MagicDrawOTIAdapters.magicDrawIncludeAllForwardRelationTriple)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider
  = for {
    odsa <- getOTIMagicDrawProfileDocumentSetAdapter(oa, specificationRootPackages)

    t0 = java.lang.System.currentTimeMillis()
    ordsa <- odsa.resolve(ignoreCrossReferencedElementFilter, unresolvedElementMapper, includeAllForwardRelationTriple)

    t1 = java.lang.System.currentTimeMillis()
    _ =
    System.out.println(
      s"MagicDrawOTIAdapters.resolve " +
        s" in ${prettyFiniteDuration(t1 - t0, TimeUnit.MILLISECONDS)}")

  } yield ordsa

  def getOTIMagicDrawDataInfo
  (p: Project,
   data: OTIDocumentSetConfiguration,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForDataProvider
  = getOTIMagicDrawDataAdapter(p, data).toThese.flatMap { oa =>
    getOTIMagicDrawDataResolvedDocumentSetAdapter(oa, specificationRootPackages)
  }

  def getOTIMagicDrawInfoForDataCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForDataProvider
  = getOTIMagicDrawDataInfo(
    p,
    data=OTIDocumentSetConfiguration.empty,
    specificationRootPackages=Set[UMLPackage[MagicDrawUML]]())

  def getOTIMagicDrawDataDocumentSetAdapter
  (oa: MagicDrawOTIDataAdapter,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIDocumentSetAdapterForDataProvider
  = {
    val t0: Long = java.lang.System.currentTimeMillis()
    for {
      odsa1 <- MagicDrawOTIAdapters.withInitialDocumentSetForDataAdapter(oa)

      t1 = java.lang.System.currentTimeMillis()
      _ = {
        System.out.println(
          s"MagicDrawOTIAdapters.withInitialDocumentSet "+
            s"in ${prettyFiniteDuration(t1 - t0, TimeUnit.MILLISECONDS)}")
      }

      documents <- odsa1.documentOps.createDocumentsFromExistingRootPackages(specificationRootPackages)

      odsa2 <- odsa1.addDocuments(documents).flatMap {
        case mdSet: MagicDrawDocumentSet =>
          \&/.That(odsa1.copy(ds=mdSet))
        case dSet =>
          \&/.This(Set[java.lang.Throwable](
            UMLError.umlAdaptationError(s"The document set, $dSet, should be a MagicDrawDocumentSet")
          ))
      }

      t2 = java.lang.System.currentTimeMillis()
      _ = {
        System.out.println(
          s"MagicDrawOTIAdapters.createDocumentsFromExistingRootPackages(${specificationRootPackages.size}) "+
            s" in ${prettyFiniteDuration(t2 - t1, TimeUnit.MILLISECONDS)}")
      }

    } yield odsa2

  }

  def getOTIMagicDrawDataResolvedDocumentSetAdapter
  (oa: MagicDrawOTIDataAdapter,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
   ignoreCrossReferencedElementFilter
   : UMLElement[MagicDrawUML] => Boolean
   = MagicDrawOTIAdapters.magicDrawIgnoreCrossReferencedElementFilter,
   unresolvedElementMapper
   : UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]
   = MagicDrawOTIAdapters.magicDrawUnresolvedElementMapper,
   includeAllForwardRelationTriple
   : (Document[MagicDrawUML], RelationTriple[MagicDrawUML], Document[MagicDrawUML]) => Boolean
   = MagicDrawOTIAdapters.magicDrawIncludeAllForwardRelationTriple)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForDataProvider
  = for {
    odsa <- getOTIMagicDrawDataDocumentSetAdapter(oa, specificationRootPackages)

    t0 = java.lang.System.currentTimeMillis()

    ordsa <- odsa.resolve(ignoreCrossReferencedElementFilter, unresolvedElementMapper, includeAllForwardRelationTriple)

    t1 = java.lang.System.currentTimeMillis()
    _ =
    System.out.println(
      s"MagicDrawOTIAdapters.resolve " +
        s" in ${prettyFiniteDuration(t1 - t0, TimeUnit.MILLISECONDS)}")

  } yield ordsa

}