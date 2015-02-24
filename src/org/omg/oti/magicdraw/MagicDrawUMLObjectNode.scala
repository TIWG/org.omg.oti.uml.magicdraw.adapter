package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._
import com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum

trait MagicDrawUMLObjectNode
  extends UMLObjectNode[MagicDrawUML]
  with MagicDrawUMLActivityNode
  with MagicDrawUMLTypedElement {

  override protected def e: Uml#ObjectNode
  import ops._

  override def inState: Set[UMLState[Uml]] =
    e.getInState.toSet[Uml#State]
  
  override def isControlType: Boolean =
    e.isControlType
    
  override def exceptionInput_exceptionHandler: Set[UMLExceptionHandler[Uml]] =
    e.get_exceptionHandlerOfExceptionInput.toSet[Uml#ExceptionHandler]

  override def upperBound: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getUpperBound )

  override def selection: Option[UMLBehavior[Uml]] =
    Option.apply( e.getSelection )

  override def ordering: UMLObjectNodeOrderingKind.Value =
    e.getOrdering match {
      case ObjectNodeOrderingKindEnum.FIFO      => UMLObjectNodeOrderingKind.FIFO
      case ObjectNodeOrderingKindEnum.LIFO      => UMLObjectNodeOrderingKind.LIFO
      case ObjectNodeOrderingKindEnum.ORDERED   => UMLObjectNodeOrderingKind.ordered
      case ObjectNodeOrderingKindEnum.UNORDERED => UMLObjectNodeOrderingKind.unordered
    }
}
