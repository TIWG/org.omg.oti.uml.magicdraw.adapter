package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLParameter 
  extends UMLParameter[MagicDrawUML]
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLMultiplicityElement {

  import ops._
  override protected def e: Uml#Parameter
  
	override def default: Option[String] = 
    e.getDefault match {
    case null => None
    case "" => None
    case s => Some( s )
  }

	// 9.9
	override def defaultValue: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getDefaultValue )
  
  // 9.9
	override def direction: UMLParameterDirectionKind.Value =
    e.getDirection match {
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.IN => UMLParameterDirectionKind.in
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.OUT => UMLParameterDirectionKind.out
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.INOUT => UMLParameterDirectionKind.inout
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.RETURN => UMLParameterDirectionKind._return
  }
  
  // 9.9
	override def effect: Option[UMLParameterEffectKind.Value] = 
    e.getEffect match {
    case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.CREATE => Some( UMLParameterEffectKind.create )
    case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.READ => Some( UMLParameterEffectKind.read )
    case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.UPDATE => Some( UMLParameterEffectKind.update )
    case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.DELETE => Some( UMLParameterEffectKind.delete )
  }
  
  // 9.9
	override def isException: Boolean = 
    e.isException
  
  // 9.9
	override def isStream: Boolean =
    e.isStream
  
  // 9.9
	override def operation: Option[UMLOperation[Uml]] = 
    Option.apply( e.getOperation )
  
  // 9.9
	override def parameterSet: Set[UMLParameterSet[Uml]] =
    e.getParameterSet.toSet[Uml#ParameterSet]
  
  // 9.9
	override def ownedParameter_ownerFormalParam: Option[UMLBehavioralFeature[Uml]] = ???
  
  // 9.9
	override def parameter_activityParameterNode: Set[UMLActivityParameterNode[Uml]] = ???
  
  // 9.9
	override def ownedParameter_behavior: Option[UMLBehavior[Uml]] = ???
  
  // 9.9
	override def result_opaqueExpression: Set[UMLOpaqueExpression[Uml]] = ???

}