package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLPin 
  extends UMLPin[MagicDrawUML]
  with MagicDrawUMLObjectNode
  with MagicDrawUMLMultiplicityElement {

  override protected def e: Uml#Pin
  import ops._

  override def isControl: Boolean =
    e.isControl
    
	override def upperValue: Option[UMLValueSpecification[Uml]] =
    ( Option.apply(e.getUpperBound), Option.apply(e.getUpperValue) ) match {
    case ( Some( v ), None ) => Some( v )
    case ( None, Some( v ) ) => Some( v )
    case ( Some( v1 ), Some( v2 ) ) => 
      require( v1 == v2, "MagicDraw ambiguity for Pin::upperValue vs. Pin::upperBound")
      Some( v1 )
    case ( None, None ) => None    
  }
}
