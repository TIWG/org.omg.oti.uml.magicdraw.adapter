package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralReal 
  extends UMLLiteralReal[MagicDrawUML]
  with MagicDrawUMLLiteralSpecification {

  override protected def e: Uml#LiteralReal
  import ops._
  
  override def value = Option.apply(e.getValue) match {
    case None => throw new IllegalArgumentException("a LiteralReal must have a value")
    case Some( d ) => d
  }
  
}