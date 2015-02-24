package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInputPin 
  extends UMLInputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#InputPin
  import ops._

	def value_linkEndData: Option[UMLLinkEndData[Uml]] =
    Option.apply( e.get_linkEndDataOfValue )
  
	def value_qualifierValue: Option[UMLQualifierValue[Uml]] =
    Option.apply( e.get_qualifierValueOfValue )

}
