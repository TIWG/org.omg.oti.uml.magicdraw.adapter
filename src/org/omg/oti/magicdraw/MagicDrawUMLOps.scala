package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.postfixOps
import scala.reflect.ClassTag

import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.metadata.UMLPackage

import org.eclipse.emf.ecore.EStructuralFeature

trait MagicDrawUMLOps extends org.omg.oti.UMLOps[MagicDrawUML] {
  
  val project: Project
  
  import scala.reflect._
    
  override implicit val tag0 = classTag[MagicDrawUML#Element]
  override implicit val tag1 = classTag[MagicDrawUML#NamedElement]
  override implicit val tag2 = classTag[MagicDrawUML#Comment]
  override implicit val tag3 = classTag[MagicDrawUML#Relationship]
  override implicit val tag4 = classTag[MagicDrawUML#DirectedRelationship]
  override implicit val tag5 = classTag[MagicDrawUML#ElementImport]
  override implicit val tag6 = classTag[MagicDrawUML#PackageImport]
  override implicit val tag7 = classTag[MagicDrawUML#PackageMerge]
  override implicit val tag8 = classTag[MagicDrawUML#Generalization]
  override implicit val tag9 = classTag[MagicDrawUML#ProfileApplication]
  override implicit val tag10 = classTag[MagicDrawUML#Feature]
  override implicit val tag11 = classTag[MagicDrawUML#BehavioralFeature]
  override implicit val tag12 = classTag[MagicDrawUML#StructuralFeature]
  override implicit val tag13 = classTag[MagicDrawUML#PackageableElement]
  override implicit val tag14 = classTag[MagicDrawUML#InstanceSpecification]
  override implicit val tag15 = classTag[MagicDrawUML#TypedElement]
  override implicit val tag16 = classTag[MagicDrawUML#Type]
  override implicit val tag17 = classTag[MagicDrawUML#Parameter]
  override implicit val tag18 = classTag[MagicDrawUML#ValueSpecification]
  override implicit val tag19 = classTag[MagicDrawUML#InstanceValue]
  override implicit val tag20 = classTag[MagicDrawUML#Package]
  override implicit val tag21 = classTag[MagicDrawUML#Slot]
  override implicit val tag22 = classTag[MagicDrawUML#Stereotype]
  override implicit val tag23 = classTag[MagicDrawUML#Image]
    
  override val OTI_SPECIFICATION_ROOT_S = {
    StereotypesHelper.getProfile( project, "OTI") match {
      case null => throw new IllegalArgumentException("Cannot find the MD version of the OTI profile")
      case pf => StereotypesHelper.getStereotype( project, "SpecificationRoot", pf ) match {
        case null => throw new IllegalArgumentException("Cannot find the MD version of the OTI::SpecificationRoot stereotype")
        case s => s
      }
    }
  }
  
  override def getElementID( e: MagicDrawUML#Element ) = e.getID
  
  override def hasStereotype( e: MagicDrawUML#Element, s: MagicDrawUML#Stereotype ) = StereotypesHelper.hasStereotype( e, s )
    
  override def getAllOwnedElementIterator( e: MagicDrawUML#Element ) = e.eAllContents flatMap {
    case e: MagicDrawUML#Element => Some( e )
    case _ => None
  }
  
  override def getContainedElement_eContainingFeature( e: MagicDrawUML#Element ) = e.eContainingFeature
    
  override val SLOT_VALUE = UMLPackage.eINSTANCE.getSlot_Value
  
  override def getElement_owner( e: MagicDrawUML#Element ) = Option.apply( e.getOwner )
  
  override def getElementContainer_eFeatureValue( e: MagicDrawUML#Element, f: EStructuralFeature ) =  
    e.eContainer.eGet( f ) match {
    case values: java.util.Collection[_] =>
      values.toIterable flatMap { 
        case v: MagicDrawUML#Element => Some( v )
        case _ => None
      }
  }
    
  override def getCommentOwnerIndex( c: MagicDrawUML#Comment ) = c.getOwner.getOwnedComment.toList.indexOf( c )
  
  override def getNamedElement_name( ne: MagicDrawUML#NamedElement ) = ne.getName match {
    case null => None
    case "" => None
    case n => Some( n )
  }
  
  override def getBinaryDirectedRelationship_target( dr: MagicDrawUML#DirectedRelationship ) =
    dr.getTarget.toList match {
    case Nil => None
    case t :: ts =>
      require( ts == Nil )
      Some( t )
  }
  
  override def getImage_location( i: MagicDrawUML#Image ) = i.getLocation match {
    case null => None
    case "" => None
    case s => Some( s )
  }
  
  override def getSlot_value( s: MagicDrawUML#Slot ) = s.getValue.toList
  override def getSlot_definingFeature( s: MagicDrawUML#Slot ) = Option.apply( s.getDefiningFeature )
  
  override def getStructuralFeature_upper( sf: MagicDrawUML#StructuralFeature ) = sf.getUpper
  
  override def getInstanceValue_instance( iv: MagicDrawUML#InstanceValue ) = Option.apply( iv.getInstance )

  override def getBehavioralFeature_ownedParameter( bf: MagicDrawUML#BehavioralFeature ) = bf.getOwnedParameter.toList
  override def getTypedElement_type( te: MagicDrawUML#TypedElement ) = Option.apply( te.getType )
  
}