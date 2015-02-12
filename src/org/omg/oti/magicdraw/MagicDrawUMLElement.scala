package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.language.postfixOps
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import org.eclipse.emf.ecore.EStructuralFeature
import org.omg.oti._
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.magicdraw.uml.UUIDRegistry

trait MagicDrawUMLElement extends UMLElement[MagicDrawUML] {

  type Uml = MagicDrawUML

  implicit val ops: MagicDrawUMLUtil
  import ops._

  protected def e: Uml#Element

  def getMagicDrawElement = e

  override def ownedComments = e.getOwnedComment.toSeq
  override def annotatedElementOfComments = e.get_commentOfAnnotatedElement.toSeq

  override def owner = Option.apply( e.getOwner )
  override def ownedElements = umlElement( e.getOwnedElement.toSet )

  override def allOwnedElements = e.eAllContents.toStream.selectByKindOf { case e: Uml#Element => umlElement( e ) } toStream

  override def getContainedElement_eContainingFeature: EStructuralFeature = e.eContainingFeature
  override def getElementContainer_eFeatureValue( f: EStructuralFeature ) = e.eContainer.eGet( f ) match {
    case values: java.util.Collection[_] => values.toIterable.selectByKindOf( { case e: Uml#Element => umlElement( e ) } )
  }

  override def relationships = e.get_relationshipOfRelatedElement.toSet[Uml#Relationship]

  override def directedRelationships_source = e.get_directedRelationshipOfSource.toSet[Uml#DirectedRelationship]
  override def directedRelationships_target = e.get_directedRelationshipOfTarget.toSet[Uml#DirectedRelationship]

  override def id: String = e.getID

  override def uuid: Option[String] = Some( UUIDRegistry.getUUID( e ) )

  override def hasStereotype( s: UMLStereotype[Uml] ) = s.isStereotypeApplied( e )

  /**
   * MagicDraw's representation of applied stereotypes on an element E involves a special InstanceSpecification IS owned by E.
   * The applied stereotypes are the classifiers of IS.
   * Values of stereotype properties other than ends of the stereotype's extension are represented in slots of IS.
   * There is no slot corresponding to the "base_<metaclass>" properties of the stereotype's extensions.
   * This means that we have to calculate what these "base_<metaclass>" properties are for each applied stereotype (i.e., classifier of E's IS).
   */
  override def getAppliedStereotypes: Map[UMLStereotype[Uml], UMLProperty[Uml]] = {
    val eMetaclass = e.getClassType
    StereotypesHelper.getStereotypes( e ).toSet[Uml#Stereotype] map { s =>
      val metaProperties = StereotypesHelper.getExtensionMetaProperty( s, false ) filter { p =>
        val pMetaclass = StereotypesHelper.getClassOfMetaClass( p.getType.asInstanceOf[Uml#Class] )
        eMetaclass == pMetaclass || StereotypesHelper.isSubtypeOf( pMetaclass, eMetaclass )
      }
      require( metaProperties.nonEmpty )
      ( umlStereotype(s) -> umlProperty(metaProperties.head) )
    } toMap
  }

  override def isAncestorOf( other: UMLElement[Uml] ) =
    ( e == other.e ) ||
      ( other.owner match {
        case None           => false
        case Some( parent ) => isAncestorOf( parent )
      } )

  def mofMetaclassName: String = StereotypesHelper.getBaseClass( e ).getName

  def tagValues: Map[UMLProperty[Uml], Seq[UMLValueSpecification[Uml]]] =
    Option.apply( e.getAppliedStereotypeInstance ) match {
      case None => Map()
      case Some( is ) =>
        val tv = for {
          s <- is.getSlot
          p = s.getDefiningFeature match { case p: Uml#Property => umlProperty( p ) }
          v = umlValueSpecification( s.getValue ).toSeq
        } yield ( p -> v )
        tv.toMap
    }

  def selectInContainmentTreeRunnable: Runnable = new SelectInContainmentTreeRunnable( e )
}
