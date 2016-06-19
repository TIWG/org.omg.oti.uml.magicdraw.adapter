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
import com.nomagic.task.ProgressStatus
import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.json.common._
import org.omg.oti.magicdraw.uml.canonicalXMI.MagicDrawDocumentSet
import org.omg.oti.magicdraw.uml.characteristics.{MagicDrawOTICharacteristicsDataProvider, MagicDrawOTICharacteristicsProfileProvider}
import org.omg.oti.magicdraw.uml.write.{MagicDrawUMLFactory, MagicDrawUMLUpdate}
import org.omg.oti.uml._
import org.omg.oti.uml.canonicalXMI.DocumentResolverProgressTelemetry
import org.omg.oti.uml.canonicalXMI.helper.OTIAdapter
import org.omg.oti.uml.characteristics.OTICharacteristicsProvider
import org.omg.oti.uml.read.api.{UML, UMLComment, UMLElement, UMLPackage}
import org.omg.oti.uml.xmi.Document

import scala.collection.immutable.{Map, Set, Vector}
import scala.{Boolean, Int, Long, None, Option, StringContext, Unit}
import scala.Predef.String
import scalaz._

object MagicDrawOTIHelper {

  def defaultExtentOfPkg
  (pkg: UMLPackage[MagicDrawUML])
  : Set[UMLElement[MagicDrawUML]]
  = pkg.allOwnedElements

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
   data: Vector[OTIDocumentSetConfiguration])
  : Set[java.lang.Throwable] \/ MagicDrawOTIDataAdapter
  = MagicDrawOTIAdapters.initializeWithDataCharacterizations(p, data)()

  def getOTIMagicDrawAdapterForDataCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \/ MagicDrawOTIDataAdapter
  = getOTIMagicDrawDataAdapter(p, data=Vector.empty[OTIDocumentSetConfiguration])

  def getOTIMagicDrawProfileInfo
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
   extentOfPkg: UMLPackage[MagicDrawUML] => Set[UMLElement[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider
  = getOTIMagicDrawProfileAdapter(p, otiCharacterizations).toThese.flatMap { oa =>
    getOTIMagicDrawProfileResolvedDocumentSetAdapter(oa, specificationRootPackages, extentOfPkg)
  }

  def getOTIMagicDrawInfoForProfileCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider
  = getOTIMagicDrawProfileInfo(
    p,
    otiCharacterizations=Option.empty[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
    specificationRootPackages=Set[UMLPackage[MagicDrawUML]](),
    extentOfPkg = defaultExtentOfPkg)

  def getOTIMagicDrawProfileDocumentSetAdapter
  (oa: MagicDrawOTIProfileAdapter,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
   extentOfPkg: UMLPackage[MagicDrawUML] => Set[UMLElement[MagicDrawUML]])
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

      documents <- odsa1.documentOps.createDocumentsFromExistingRootPackages(specificationRootPackages, extentOfPkg)

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
   extentOfPkg: UMLPackage[MagicDrawUML] => Set[UMLElement[MagicDrawUML]],
   ignoreCrossReferencedElementFilter
   : UMLElement[MagicDrawUML] => Boolean
   = MagicDrawOTIAdapters.magicDrawIgnoreCrossReferencedElementFilter,
   unresolvedElementMapper
   : UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]
   = MagicDrawOTIAdapters.magicDrawUnresolvedElementMapper,
   includeAllForwardRelationTriple
   : (Document[MagicDrawUML], RelationTriple[MagicDrawUML], Document[MagicDrawUML]) => Boolean
   = MagicDrawOTIAdapters.magicDrawIncludeAllForwardRelationTriple,
   progressTelemetry: DocumentResolverProgressTelemetry
   = DocumentResolverProgressTelemetry.printTelemetry)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider
  = for {
    odsa <- getOTIMagicDrawProfileDocumentSetAdapter(oa, specificationRootPackages, extentOfPkg)

    t0 = java.lang.System.currentTimeMillis()
    ordsa <- odsa.resolve(ignoreCrossReferencedElementFilter, unresolvedElementMapper, includeAllForwardRelationTriple, progressTelemetry)

    t1 = java.lang.System.currentTimeMillis()
    _ =
    System.out.println(
      s"MagicDrawOTIAdapters.resolve " +
        s" in ${prettyFiniteDuration(t1 - t0, TimeUnit.MILLISECONDS)}")

  } yield ordsa

  def getOTIMagicDrawDataInfo
  (p: Project,
   data: Vector[OTIDocumentSetConfiguration],
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
   extentOfPkg: UMLPackage[MagicDrawUML] => Set[UMLElement[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForDataProvider
  = getOTIMagicDrawDataAdapter(p, data).toThese.flatMap { oa =>
    getOTIMagicDrawDataResolvedDocumentSetAdapter(oa, specificationRootPackages, extentOfPkg)
  }

  def getOTIMagicDrawInfoForDataCharacteristics
  (p: Project)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForDataProvider
  = getOTIMagicDrawDataInfo(
    p,
    data=Vector.empty[OTIDocumentSetConfiguration],
    specificationRootPackages=Set[UMLPackage[MagicDrawUML]](),
    extentOfPkg = defaultExtentOfPkg)

  def getOTIMagicDrawDataDocumentSetAdapter
  (oa: MagicDrawOTIDataAdapter,
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]],
   extentOfPkg: UMLPackage[MagicDrawUML] => Set[UMLElement[MagicDrawUML]])
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

      documents <- odsa1.documentOps.createDocumentsFromExistingRootPackages(specificationRootPackages, extentOfPkg)

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
   extentOfPkg: UMLPackage[MagicDrawUML] => Set[UMLElement[MagicDrawUML]],
   ignoreCrossReferencedElementFilter
   : UMLElement[MagicDrawUML] => Boolean
   = MagicDrawOTIAdapters.magicDrawIgnoreCrossReferencedElementFilter,
   unresolvedElementMapper
   : UMLElement[MagicDrawUML] => Option[UMLElement[MagicDrawUML]]
   = MagicDrawOTIAdapters.magicDrawUnresolvedElementMapper,
   includeAllForwardRelationTriple
   : (Document[MagicDrawUML], RelationTriple[MagicDrawUML], Document[MagicDrawUML]) => Boolean
   = MagicDrawOTIAdapters.magicDrawIncludeAllForwardRelationTriple,
   progressTelemetry: DocumentResolverProgressTelemetry
   = DocumentResolverProgressTelemetry.printTelemetry)
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapterForDataProvider
  = for {
    odsa <- getOTIMagicDrawDataDocumentSetAdapter(oa, specificationRootPackages, extentOfPkg)

    t0 = java.lang.System.currentTimeMillis()

    ordsa <- odsa.resolve(ignoreCrossReferencedElementFilter, unresolvedElementMapper, includeAllForwardRelationTriple, progressTelemetry)

    t1 = java.lang.System.currentTimeMillis()
    _ =
    System.out.println(
      s"MagicDrawOTIAdapters.resolve " +
        s" in ${prettyFiniteDuration(t1 - t0, TimeUnit.MILLISECONDS)}")

  } yield ordsa

  def progressTelemetry(p: ProgressStatus)
  : DocumentResolverProgressTelemetry
  = {
    import DocumentResolverProgressTelemetry.{NumberOfElements, NumberOfDocuments, DocumentURL}
    import DocumentResolverProgressTelemetry.{NumberOfHyperEdges, NumberOfTriples}
    import DocumentResolverProgressTelemetry.{Duration, NumberOfUnresolvedCrossReferences}

    def scanStarted
    (nd: Int @@ NumberOfDocuments)
    : Unit
    = {
      val s = NumberOfDocuments.unwrap(nd)
      p.setDescription(s"Begin scanning $s documents...")
      p.setMax(s.toLong)
      p.setCurrent(0)
      DocumentResolverProgressTelemetry.scanStarted(nd)
    }

    def scanDocumentStarted
    (url: String @@ DocumentURL)
    : Unit
    = {
      p.setDescription(s"Begin scanning document: $url")
      DocumentResolverProgressTelemetry.scanDocumentStarted(url)
    }

    def scanDocumentEnded
    (ne: Int @@ NumberOfElements, d: String @@ Duration)
    : Unit
    = {
      p.setDescription(s"=> Scanned $ne elements in $d")
      p.increase()
      DocumentResolverProgressTelemetry.scanDocumentEnded(ne, d)
    }

    def scanEnded
    (nd: Int @@ NumberOfDocuments,
     ne: Int @@ NumberOfElements,
     d: String @@ Duration)
    : Unit
    = {
      p.setDescription(s"Found $ne elements scanning all $nd documents.")
      DocumentResolverProgressTelemetry.scanEnded(nd, ne, d)
    }

    def resolveStarted
    (nd: Int @@ NumberOfDocuments)
    : Unit
    = {
      val s = NumberOfDocuments.unwrap(nd)
      p.setDescription(s"\n# Begin resolving $nd documents...")
      p.setMax(s.toLong)
      p.setCurrent(0)
      DocumentResolverProgressTelemetry.resolveStarted(nd)
    }

    def resolveDocumentStarted
    (url: String @@ DocumentURL,
     ne: Int @@ NumberOfElements)
    : Unit
    = {
      p.setDescription(s"# Begin resolving $ne elements in document $url")
      DocumentResolverProgressTelemetry.resolveDocumentStarted(url, ne)
    }

    def resolveDocumentStepped
    (ne: Int @@ NumberOfElements,
     neTotal: Int @@ NumberOfElements,
     nt: Int @@ NumberOfTriples,
     d: String @@ Duration)
    : Unit
    = {
      p.init(
        s"# $ne / $neTotal document elements, $nt total triples => $d",
        0,
        NumberOfElements.unwrap(neTotal).toLong,
        NumberOfElements.unwrap(ne).toLong)
      DocumentResolverProgressTelemetry.resolveDocumentStepped(ne, neTotal, nt, d)
    }

    def resolveDocumentEnded
    (ne: Int @@ NumberOfElements,
     nt: Int @@ NumberOfTriples,
     d: String @@ Duration)
    : Unit
    = {
      p.setDescription(s"# Finished resolving $ne document elements, cummulative triple count: $nt in $d")
      DocumentResolverProgressTelemetry.resolveDocumentEnded(ne, nt, d)
    }

    def resolveEnded
    (nd: Int @@ NumberOfDocuments,
     ne: Int @@ NumberOfElements,
     nt: Int @@ NumberOfTriples,
     nh: Int @@ NumberOfHyperEdges,
     nu: Int @@ NumberOfUnresolvedCrossReferences,
     d: String @@ Duration)
    : Unit
    = {
      p.setDescription(
        s"# Created $nh hyper edges ($nt triples) among $nd document hyper nodes ($ne elements) " +
          s"with $nu unresolved cross-reference triples")
      DocumentResolverProgressTelemetry.resolveEnded(nd, ne, nt, nh, nu, d)
    }

    DocumentResolverProgressTelemetry(
      scanStarted _,
      scanDocumentStarted _,
      scanDocumentEnded _,
      scanEnded _,
      resolveStarted _,
      resolveDocumentStarted _,
      resolveDocumentStepped _,
      resolveDocumentEnded _,
      resolveEnded _)
  }
}