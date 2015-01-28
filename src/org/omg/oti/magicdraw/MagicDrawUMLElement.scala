package org.omg.oti.magicdraw

import scala.collection.JavaConversions.asScalaIterator
import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.language.implicitConversions
import scala.language.postfixOps

import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable

import org.eclipse.emf.ecore.EStructuralFeature
import org.omg.oti.UMLElement
import org.omg.oti.UMLStereotype

trait MagicDrawUMLElement extends UMLElement[MagicDrawUML] {

  type Uml = MagicDrawUML

  implicit val ops: MagicDrawUMLUtil
  import ops._

  protected def e: Uml#Element

  override def ownedComments = e.getOwnedComment.toSeq
  override def annotatedElementOfComments = e.get_commentOfAnnotatedElement.toSeq

  override def owner = Option.apply( e.getOwner )
  override def ownedElements = umlElement( e.getOwnedElement.toSet )

  override def allOwnedElements = e.eAllContents.toIterator selectByKindOf { case e: Uml#Element => umlElement( e ) } toStream

  override def getContainedElement_eContainingFeature: EStructuralFeature = e.eContainingFeature
  override def getElementContainer_eFeatureValue( f: EStructuralFeature ) = e.eContainer.eGet( f ) match {
    case values: java.util.Collection[_] => values.toIterator.selectByKindOf( { case e: Uml#Element => umlElement( e ) } )
  }

  override def relatedElementOfRelationships = e.get_relationshipOfRelatedElement.toSet[Uml#Relationship]

  override def sourceOfDirectedRelationships = e.get_directedRelationshipOfSource.toSet[Uml#DirectedRelationship]
  override def targetOfDirectedRelationships = e.get_directedRelationshipOfTarget.toSet[Uml#DirectedRelationship]

  override def id: String = e.getID

  override def hasStereotype( s: UMLStereotype[Uml] ) = s.isStereotypeApplied(e)    

  override def isAncestorOf( other: UMLElement[Uml] ) =
    ( e == other.e ) ||
      ( other.owner match {
        case None           => false
        case Some( parent ) => isAncestorOf( parent )
      } )

  def selectInContainmentTreeRunnable: Runnable = new SelectInContainmentTreeRunnable( e )
}
