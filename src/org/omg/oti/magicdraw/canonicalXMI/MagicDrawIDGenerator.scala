package org.omg.oti.magicdraw.canonicalXMI

import java.net.URL
import java.net.MalformedURLException

import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import org.omg.oti.api._
import org.omg.oti.canonicalXMI._

import org.omg.oti.magicdraw._

case class MagicDrawIDGenerator(
  val resolvedDocumentSet: ResolvedDocumentSet[MagicDrawUML] )(
    implicit val umlOps: MagicDrawUMLUtil )
  extends IDGenerator[MagicDrawUML] {

  import umlOps._

  val element2id = scala.collection.mutable.HashMap[UMLElement[Uml], Try[String]]()

  // -------------
  val MD_crule0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, is: MagicDrawUMLInstanceSpecification ) if ( is.isMagicDrawUMLAppliedStereotypeInstance ) =>
      Success( ownerID + "_" + IDGenerator.xmlSafeID( cf.getName ) + ".appliedStereotypeInstance" )
  }

  val MD_crule1a0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, ev: MagicDrawUMLElementValue ) =>
      ev.element match {
        case None => Failure( illegalElementException( "ElementValue without Element is not supported", ev ) )
        case Some( nev: UMLNamedElement[Uml] ) =>
          nev.name match {
            case None      => Failure( illegalElementException( "ElementValue must refer to a named NamedElement", ev ) )
            case Some( n ) => Success( ownerID + "_" + IDGenerator.xmlSafeID( cf.getName + "." + n ) )
          }
        case Some( ev: UMLElement[Uml] ) =>
          Failure( illegalElementException( "ElementValue refers to an Element that is not a NamedElement!", ev ) )
      }
  }

  protected val elementRules = List( rule0 )
  protected val containmentRules = List( MD_crule0, crule1, MD_crule1a0, crule1a, crule3, crule1b, crule2, crule4, crule5, crule6 )

}