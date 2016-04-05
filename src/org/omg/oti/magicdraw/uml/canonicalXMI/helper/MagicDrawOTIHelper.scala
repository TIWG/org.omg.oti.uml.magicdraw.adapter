package org.omg.oti.magicdraw.uml.canonicalXMI.helper

import java.util.concurrent.TimeUnit

import com.nomagic.magicdraw.core.Project

import org.omg.oti.magicdraw.uml.canonicalXMI._
import org.omg.oti.magicdraw.uml.read._

import org.omg.oti.uml._
import org.omg.oti.uml.read.api.{UML, UMLComment, UMLElement, UMLPackage}
import org.omg.oti.uml.xmi.Document

import scala.collection.immutable.Set
import scalaz._

object MagicDrawOTIHelper {
  
  def getOTIMagicDrawAdapter
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]] = None)
  : Set[java.lang.Throwable] \/ MagicDrawOTIAdapter
  = MagicDrawOTIAdapters.initialize(p, otiCharacterizations)()

  def getOTIMagicDrawInfo
  (p: Project,
   otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
   specificationRootPackages: Set[UMLPackage[MagicDrawUML]])
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapter
  = getOTIMagicDrawAdapter(p, otiCharacterizations).toThese.flatMap { oa =>
    getOTIMagicDrawInfo(oa, specificationRootPackages)
  }

  def getOTIMagicDrawInfo
  (oa: MagicDrawOTIAdapter,
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
  : Set[java.lang.Throwable] \&/ MagicDrawOTIResolvedDocumentSetAdapter
  = {
    val t0: Long = java.lang.System.currentTimeMillis()
    for {
      odsa1 <- MagicDrawOTIAdapters.withInitialDocumentSet(oa)

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
      ordsa <- odsa2.resolve(ignoreCrossReferencedElementFilter, unresolvedElementMapper, includeAllForwardRelationTriple)

      t3 = java.lang.System.currentTimeMillis()
      _ = {
        System.out.println(
          s"MagicDrawOTIAdapters.resolve "+
            s" in ${prettyFiniteDuration(t3 - t2, TimeUnit.MILLISECONDS)}")
      }

    } yield ordsa

  }
}