package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

import scala.collection.JavaConversions._

trait MagicDrawUMLBehavioralFeature 
  extends UMLBehavioralFeature[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLFeature {

  override protected def e: Uml#BehavioralFeature
  import ops._
  
  override def concurrency = e.getConcurrency match {
    case com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.CONCURRENT => UMLCallConcurrencyKind.concurrent
    case com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.GUARDED => UMLCallConcurrencyKind.guarded
    case com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.SEQUENTIAL => UMLCallConcurrencyKind.sequential
  }
  
  override def isAbstract = e.isAbstract
  
  override def method = e.getMethod.toSet[Uml#Behavior]
  
  override def ownedParameter = e.getOwnedParameter.toSeq
  
  override def raisedException = e.getRaisedException.toSet[Uml#Type]
  
}