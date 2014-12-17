package org.omg.oti.magicdraw.scripts

import java.net.MalformedURLException
import java.net.URL

import scala.Range
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.uml.ClassTypes
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.BehavioralFeature
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Comment
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementImport
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementValue
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Feature
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Generalization
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceValue
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageImport
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.PackageMerge
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Image
import com.nomagic.uml2.ext.magicdraw.mdprofiles.ProfileApplication
import com.nomagic.uml2.ext.magicdraw.metadata.UMLPackage

import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.xml.`type`.util.XMLTypeUtil

/**
 * Scala adaptation of Yves Bernard's MTL utilities for TIWG
 *
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 */
object Util {

  /*
   * 
[query public xmlSafe(this : String) : String = 
-- escaping XML reserved characters 
if self.oclIsUndefined() then
	''
else
	self.substituteAll('&', '&amp;')
		.substituteAll('"', '&quot;')
		.substituteAll('\'', '&apos;')
		.substituteAll('<', '&lt;')
		.substituteAll('>', '&gt;')
endif
 /]
   */
  def xmlSafe( self: String ): String = self match {
    case null => ""
    case s =>
      s.
        replaceAll( "&", "&amp;" ).
        replaceAll( "\"", "&quot;" ).
        replaceAll( "\'", "&apos;" ).
        replaceAll( "<", "&lt;" ).
        replaceAll( ">", "&gt;" )
  }

  /*
   * 
[query public xmlSafeID(this : String) : String = 
-- escaping XML reserved characters 
if self.oclIsUndefined() then
	''
else
	self.xmlSafe().substituteAll(' ', '_')
endif
 /]
   */
  def xmlSafeID( self: String ): String = self match {
    case null => ""
    case s    => getValidNCName( s ) // xmlSafe( s ).replaceAll( " ", "_" )
  }
 
  val ESCAPE = Array(
			"%00",
			"%01",
			"%02",
			"%03",
			"%04",
			"%05",
			"%06",
			"%07",
			"%08",
			"%09",
			"%0A",
			"%0B",
			"%0C",
			"%0D",
			"%0E",
			"%0F",
			"%10",
			"%11",
			"%12",
			"%13",
			"%14",
			"%15",
			"%16",
			"%17",
			"%18",
			"%19",
			"%1A",
			"%1B",
			"%1C",
			"%1D",
			"%1E",
			"%1F",
			"%20",
			null,
			"%22",
			"%23",
			null,
			"%25",
			"%26",
			"%27",
			null,
			null,
			null,
			null,
			"%2C",
			null,
			null,
			"%2F",
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			"%3A",
			null,
			"%3C",
			null,
			"%3E",
			null
		)
  
	def isNCNameStart(c: Char): Boolean = XMLTypeUtil.isNCNameStart(c)

	def isNCNamePart(c: Char): Boolean = XMLTypeUtil.isNCNamePart(c)

	def getValidNCName(name: String): String = {
   val buff = new StringBuffer()
   getValidNCName( name, buff )
   buff.toString()
  }
  
  def getValidNCName(name: String, validNCName: StringBuffer): Unit = {
			if (null == name || "" == name) {
				validNCName.insert(0, '_')
			} else {
				for {
           i <- name.length-1 until 0 by -1
           char_i = name.charAt(i)
        } {
					if (isNCNamePart(char_i)) {
						validNCName.insert(0, char_i)
					} else if (char_i < ESCAPE.length) {
						//val index = char_i
						val escape = ESCAPE(char_i)
						if (escape != null) {
							validNCName.insert(0, escape)
						}
					}
				}
				val char_0 = name.charAt(0)
				if (isNCNameStart(char_0)) {
					validNCName.insert(0, char_0)
				} else {
					if (isNCNamePart(char_0)) {
						validNCName.insert(0, char_0)
					}
					validNCName.insert(0, '_')
				}
			}
		}
  /*
   * 
[query public getUUID(this : uml::Element) : String = 
-- Rule#1: any NamedElement which is member of a Namespace is uniquely identified
-- (a) if it is a kind of uml::Feature or of uml::ValueSpecification: by its name prefixed by the name of its defining feature prepended with the underscore
-- character (�_�) and  prefixed by the identifier of its owner, with a dot "." used as separator. In addition, if it is a it is a kind of uml::BehavioralFeature,
-- the list of its parameter type name, separated by underscores characters is appended (a question mark '?' is used for untyped parameters)   
-- (b) otherwise by its name prefixed by the identifier of its Namespace (if any), with a dot "." used as separator

if (self.oclIsKindOf(uml::NamedElement) ) then
	let ne : uml::NamedElement = self.oclAsType(uml::NamedElement)in
	if ne.oclIsKindOf(uml::Model) or ne.owner->isEmpty() then -- stop the recursion at model or root package level
		if ne.name <> 'OMG' then 'OMG.'+ne.name else 'OMG' endif			
	else
		if self.oclIsKindOf(uml::Feature) or self.oclIsKindOf(uml::ValueSpecification) then
			-- case(a)
			let shortID : String = if ne.owner.oclIsKindOf(uml::Slot) then 
				'_'+ne.owner.oclAsType(uml::Slot).value->indexOf(ne).toString()+'_'+ne.getName()
			else
				ne.getName()
			endif in
			ne.owner.getUUID()+'._'+self.eContainingFeature().getName().xmlSafeID()+'.'+shortID.xmlSafeID()
			+(if self.oclIsKindOf(uml::BehavioralFeature) then
				self.oclAsType(uml::BehavioralFeature).ownedParameter->iterate(p; acc : String = '' |
				acc+'_'+(if p.type->notEmpty() then p.type.getName().xmlSafeID() else '?' endif))
			else
				''
			endif)
		else
			-- case (b)
			ne.owner.getUUID()+'.'+(ne.getName().xmlSafeID())
		endif
	endif
else		
	-- Rule#2: any Element on which Rule#1 does not apply and which is owned as an ordered set is uniquely
	-- identified by the name of its containing feature prepended with the underscore character "_",
	-- prefixed by the identifier of its owner and suffixed
	-- by its index within the ordered set. The dot separator "." is used between these parts	 	
	if (not self.eContainingFeature().oclIsUndefined()) and self.eContainingFeature().ordered then
		let cntF : ecore::EStructuralFeature = self.eContainingFeature() in
		let idx : Integer = 0 in
 		'***Rule#2***'+self.owner.getUUID()+'._'+cntF.getName()+'.'+ idx.toString() -- to be improved
	else
		-- Rule#3: any Element on which Rule#2 does not apply and which is 
		-- uml::PackageImport, uml::ElementImport, uml::PackageMerge or uml::Generalization 
		-- is uniquely identified by the name of its containing feature prepended with the underscore character "_",
		-- prefixed by the identifier of its owner 
		-- and suffixed the unique identifier of its target. The dot separator "." is used between these parts
		if (self.oclIsKindOf(uml::PackageImport) or self.oclIsKindOf(uml::ElementImport)
		or self.oclIsKindOf(uml::PackageMerge) or self.oclIsKindOf(uml::Generalization)) then
			self.owner.getUUID()+'._'+self.eContainingFeature().getName().xmlSafeID()+'.'
			+self.oclAsType(uml::DirectedRelationship).target->any(true).getUUID()
		else
			-- Rule#4: any Element on which Rule#3 does not apply and which is uml::Slot  is uniquely identified by
			-- the name of its defining feature, prefixed by the identifier of its owner, with a dot "." used as separator
			if self.oclIsKindOf(uml::Slot) then
				let s : uml::Slot = self.oclAsType(uml::Slot) in
				s.owner.getUUID()+'.'+s.definingFeature.getName().xmlSafeID()
			else
				-- Rule#5: any element on which Rule#4 does not apply and which is an uml::ValueSpecification is uniquely
				-- identified by the name of its containing feature prepended with the underscore character "_",
				-- prefixed by the identifier of its owner, with a dot "." used as separator
				if self.oclIsKindOf(uml::ValueSpecification) then
					self.owner.getUUID()+'._'+self.eContainingFeature().getName().xmlSafeID()
				else
					-- Rule#6: any element on which Rule#5 does not apply is assumed to be a Comment and is uniquely
					-- identified by the name of its containing feature prepended with the underscore character "_",
					-- prefixed by the identifier of its owner, with a dot "." used as separator
					-- suffixed by its index in the comments collection its owners, sorted by alpahbetic order of their content, with a dot "." used as separator
					if self.oclIsKindOf(uml::Comment) then
						self.owner.getUUID()+'._'+self.eContainingFeature().getName().xmlSafeID()
						+'.'+self.owner.ownedComment->asSequence()->sortedBy(_body)->indexOf(self).toString()
					else
						'***Unsupported***'
					endif
				endif
			endif
		endif
	endif
endif
/]
   */
  def getXMI_ID( self: Element )( implicit element2id: scala.collection.mutable.HashMap[Element, String] ): String =
    element2id.getOrElseUpdate(
      self,
      ( self match {
        case root: Package if ( StereotypesHelper.hasStereotype( root, "SpecificationRoot" , "Specifications::OTI" )) =>
          root.getName
        // MagicDraw-specific representation of applied Stereotype instances
        case is: InstanceSpecification if (is.getOwner.getAppliedStereotypeInstance == is) =>
          getXMI_ID( is.getOwner ) + "_" + xmlSafeID( self.eContainingFeature.getName ) + "." + "appliedStereotypeInstance"
        case ne: NamedElement =>
          // Rule #1
          if ( ne.getOwner == null )
            "OMG"
          else
            ne match {
              // MagicDraw-specific extension of the UML metamodel
              case ev: ElementValue =>
                ev.getElement match {
                  case null =>
                    require( false, "ElementValue without Element is not supported" )
                    ???
                  case nev: NamedElement =>
                    require( nev.getName != null && nev.getName.length > 0, "ElementValue must refer to a named NamedElement" )
                    getXMI_ID( ne.getOwner ) + "_" + xmlSafeID( self.eContainingFeature.getName ) + "." + nev.getName
                  case _ =>
                    require( false, "ElementValue must refer to a NamedElement" )
                    ???
                }
              case iv: InstanceValue =>
                iv.getInstance match {
                  case null =>
                    require( false, "InstanceValue without InstanceSpecification is not supported" )
                    ???
                  case is =>
                    require( is.getName != null && is.getName.length > 0, "InstanceValue must refer to a named InstanceSpecification" )
                    getXMI_ID( ne.getOwner ) + "_" + xmlSafeID( self.eContainingFeature.getName ) + "." + is.getName
                }
              case _@ ( _: Feature | _: ValueSpecification ) =>
                // -- case (a)
                val shortID = ne.getOwner match {
                  case s: Slot =>
                    val slotValues = s.getValue
                    if ( s.getDefiningFeature.getUpper > 1 )
                      "_" + slotValues.indexOf( ne ) + "_" + ne.getName
                    else
                      ne.getName
                  case _ =>
                    ne.getName
                }
                val suffix1 = shortID match {
                  case null => ""
                  case ""   => ""
                  case id   => "." + xmlSafeID( id )
                }
                val suffix2 = ne match {
                  case bf: BehavioralFeature =>
                    require( suffix1.length > 0 )
                    ( suffix1 /: bf.getOwnedParameter )( ( s, p ) =>
                      s + ( p.getType match {
                        case null => "?"
                        case t    => "_" + xmlSafeID( t.getName )
                      } ) )
                  case _ =>
                    suffix1
                }
                val suffix3 = ( suffix2, self.eContainingFeature.getUpperBound ) match {
                  case ( "", u ) =>
                    ( self.getOwner, self.getOwner.getOwner ) match {
                      case ( s: Slot, is: InstanceSpecification ) if ( self.eContainingFeature == UMLPackage.eINSTANCE.getSlot_Value ) =>
                        if ( s.getDefiningFeature.getUpper == 1 )
                          ""
                        else {
                          val slotValues = s.getValue.toList
                          require( slotValues.contains( ne ) )
                          slotValues.indexOf( ne ).toString
                        }
                      case ( _, _ ) =>
                        require(
                          u == 1,
                          s"""|Unnamed ${ClassTypes.getShortName( ne.getClassType )} @ ${ne.getID} 
                            | in containing feature: ${self.eContainingFeature.getEContainingClass.getName}::${self.eContainingFeature.getName}[${self.eContainingFeature.getLowerBound}..${self.eContainingFeature.getUpperBound}]
                            | in container ${ClassTypes.getShortName( self.getOwner.getClassType )} @ ${self.getOwner.getID} 
                            | in containing feature: ${self.getOwner.eContainingFeature.getEContainingClass.getName}::${self.getOwner.eContainingFeature.getName}[${self.eContainingFeature.getLowerBound}..${self.eContainingFeature.getUpperBound}]
                            | in container ${ClassTypes.getShortName( self.getOwner.getOwner.getClassType )} @ ${self.getOwner.getOwner.getID} 
                            |""".stripMargin( '|' ) )
                        ""
                    }
                  case ( s, _ ) =>
                    s
                }
                getXMI_ID( ne.getOwner ) + "_" + xmlSafeID( self.eContainingFeature.getName ) + suffix3

              case _ =>
                // -- case (b)
                getXMI_ID( ne.getOwner ) + "." + xmlSafeID( ne.getName )
            }
        case e =>
          e.eContainingFeature match {
            case sf: EStructuralFeature if ( sf.isOrdered ) =>
              // Rule #2
              e.eContainer.eGet( sf ) match {
                case sfCollection: java.util.Collection[_] =>
                  getXMI_ID( e.getOwner ) + "_" + xmlSafeID( sf.getName ) + "." + sfCollection.toList.indexOf( e )
                case null =>
                  require( false )
                  ""
                case x =>
                  require( false )
                  ""
              }
            case f =>
              e match {
                case r @ ( _: Generalization | _: ElementImport | _: PackageImport | _: PackageMerge | _: ProfileApplication ) =>
                  // Rule #3
                  val ts = r.asInstanceOf[DirectedRelationship].getTarget
                  require( ts.size == 1 )
                  val t = ts.head
                  getXMI_ID( e.getOwner ) + "._" + xmlSafeID( f.getName ) + "." + getXMI_ID( t )
                case s: Slot =>
                  // Rule #4
                  getXMI_ID( s.getOwner ) + "." + xmlSafeID( s.getDefiningFeature.getName )
                case v: ValueSpecification =>
                  // Rule #5              
                  getXMI_ID( v.getOwner ) + "._" + xmlSafeID( f.getName )
                case c: Comment =>
                  // Rule #6
                  getXMI_ID( c.getOwner ) + "._" + xmlSafeID( f.getName ) + "." + c.getOwner.getOwnedComment.toList.indexOf( c )
                case i: Image =>
                  // Rule #7
                  getImageLocationURL( i ) match {
                    case Failure( t ) =>
                      require( false, s"Stereotype ${i.get_stereotypeOfIcon.getQualifiedName}: icon Image location is not a URL: ${t.getMessage}" )
                      ???
                    case Success( locationURL ) =>
                      getXMI_ID( i.getOwner ) + "._" + xmlSafeID( f.getName ) + "." + xmlSafeID( locationURL )
                  }
                case _ =>
                  require( false, s"Unsupported: ${ClassTypes.getShortName( e.getClassType )}" )
                  ???
              }
          }
      } ).stripPrefix( "OMG." ) )

  def getImageLocationURL( i: Image ): Try[String] =
    i.getLocation match {
      case null =>
        Failure( new IllegalArgumentException( "An Image must have a non-null location URL" ) )
      case loc =>
        try {
          val url = new URL( loc ) toString;
          Success( getValidNCName( url ) )
        }
        catch {
          case t: MalformedURLException =>
            Failure( t )
          case t: Throwable =>
            Failure( t )
        }
    }

  /*
   * 
[query public indent(this : OclAny, level : Integer) : String = 
if (level > 0) then 

	let counter : Sequence(Integer)= Sequence{1..level} in
	counter->iterate(c; acc : String ='\n' | acc.concat('\t'))
else
	'\n'
endif /]
   */
  def indent( level: Int ): String =
    if ( level > 0 )
      ( "\n" /: Range( 1, level ) )( ( s, _ ) => s + "\t" )
    else
      "\n"

  /*
   * 
[query public getGOID(this : uml::Element) : String =
--if self.getAppliedStereotype('OMG_Identification::omgid') <> null then
--	self.getValue(getAppliedStereotype('OMG_Identification::omgid'), 'goid')
--else
	if self.oclIsUndefined() then
		''
	else
		self.getUUID()
	endif
--endif
/]
   */
  def getGOID( self: Element )( implicit element2id: scala.collection.mutable.HashMap[Element, String] ): String =
    if ( StereotypesHelper.hasStereotype( self, "OMG_Identitification" ) )
      StereotypesHelper.getStereotypePropertyValueAsString( self, "OMG_Identification", "omgid" ).head
    else
      getXMI_ID( self )

  /*
   * 
[query public getXMI_ID(uuid : String) : String = 
if uuid.size() > 5 and uuid.substring(1,3) = 'OMG' then
	uuid.substring(5, uuid.size())
else
	uuid
endif
/]
   */
  def getOMG_UUID( e: Element )( implicit element2id: scala.collection.mutable.HashMap[Element, String] ): String =
    // TODO Check if there is an OMG TWIG UUID stereotype applied....    
    "OMG_UUID_" + getXMI_ID( e )

  /*
   * 
[query public generateLiteral(this : String, tag : String, level : Integer = 0) : String =
let indStr : String = indent(level) in
if this.oclIsUndefined() then -- /!\ cannot use "self" for this test
	''
else
	indStr+'<'+tag+'>'+self.xmlSafe()+'</'+tag+'>'
endif
 /]
   */

  /*
   * 
[query public getTypeID(this : uml::Element) : String = 'uml:'+self.eClass().getName() /]

[query public generateRefTag(this : uml::Element, tag : String, level : Integer = 0) : String =
let indStr : String = indent(level) in
let	idrefStr : String = ' xmi:idref="'+self.getGOID().getXMI_ID()+'"'in
indStr + '<' + tag + idrefStr + '/>'
 /]
   */

  /*
   * 
[query public generateTag(this : uml::Element, tag : String, isHeaderOnly : Boolean, level : Integer = 0) : String =
let indStr : String = indent(level),
	uuid : String = self.getGOID() in
let	idStr : String = ' xmi:id="'+uuid.getXMI_ID()+'"',
	uuidStr : String = '',--' xmi:uuid="'+uuid+'"',
	typeIDStr : String = ' xmi:type="'+self.getTypeID()+'"',
	endStr : String = if isHeaderOnly then '>' else '/>' endif
in
indStr+'<'+tag+idStr+uuidStr+typeIDStr+endStr
 /]
   */

  /*
   * 
[query public generateMultiplicity(this : uml::MultiplicityElement, level : Integer = 0) : String = 
let indStr : String = indent(level) in 
(if self.upperValue.oclIsUndefined() then
	''
else
	self.upperValue.generateTag('upperValue', true, level+1)
	+self.upper.toString().generateLiteral('value', level+1)
	+indStr+'</upperValue>'
endif)
+(if self.lowerValue.oclIsUndefined() then
	''
else
	self.lowerValue.generateTag('lowerValue', true, level )
	+self.lower.toString().generateLiteral('value', level+1)
	+indStr+'</lowerValue>'
endif)
/]
   */

  /*
   * 
[query public generateOpaqueExpr(this : uml::OpaqueExpression, level : Integer = 0) : String =
let indStr : String = indent(level) in 
(if (self.language->notEmpty()) then
	self.language->asSequence()->first().generateLiteral('language', level)
else
	''
endif)+
if (self._body->notEmpty()) then
	self._body->asSequence()->first().generateLiteral('body', level+1)
else
	''
endif
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
/]
   */

  /*
   * 
[query public generateValue(this : uml::ValueSpecification, kind : String, level : Integer = 0) : String =
let indStr : String = indent(level) in
self.generateTag(kind, true, level)
+(if (self.oclIsKindOf(uml::LiteralSpecification)) then
	self.stringValue().generateLiteral('value', level+1)
else
	if (self.oclIsKindOf(uml::InstanceValue)) then
		self.oclAsType(uml::InstanceValue).instance.generateRefTag('instance', level+1)
	else
		if (self.oclIsKindOf(uml::OpaqueExpression)) then
			self.oclAsType(uml::OpaqueExpression).generateOpaqueExpr(level+1)
		else
			'*** unsupported Value ***'
		endif
	endif
endif)
+indStr+'</'+kind+'>'
/]
   */

  /*
   * 
[query public generateSpecification(this : uml::ValueSpecification, level : Integer = 0) : String =
let indStr : String = indent(level) in 
if (self.oclIsKindOf(uml::OpaqueExpression)) then
	let expr : uml::OpaqueExpression = self.oclAsType(uml::OpaqueExpression) in
	expr.generateTag('specification', true, level)
	+expr.generateOpaqueExpr(level+1)
	+indStr+'</specification>'
else
	'\n***ERROR***: cannot generate '+self.eClass().getName()+ ' element\n'
endif
 /]
   */

  /*
   * 
[query public generateComment(this : uml::Comment, level : Integer = 0) : String =
let indStr : String = indent(level) in 
self.generateTag('ownedComment', true, level)
--+(if self.annotatedElement->notEmpty() then
	--self.annotatedElement->asSequence()->first().generateRefTag('annotatedElement', level+1)indStr
+self.annotatedElement->iterate(ae; acc : String = '' | acc+ae.generateRefTag('annotatedElement', level+1))
--else
--	''
--endif)
+self._body.generateLiteral('body', level+1)
+indStr+'</ownedComment>'
/]
   */

  /*
   * 
[query public generateConstraint(this : uml::Constraint, level : Integer = 0) : String =
let indStr : String = indent(level) in
self.generateTag('ownedRule', true, level)
+(if (self.name->notEmpty()) then self.name.generateLiteral('name', level+1) else '' endif)
--+(if (self.constrainedElement->notEmpty()) then
--	let str : String = self.constrainedElement->asSequence()->iterate(ce ; acc : String = ''| acc+ce.getGOID().getXMI_ID()+' ') in
--	str.trim().generateLiteral('constrainedElement', level+1)
--else
--	''
--endif)
+self.constrainedElement->iterate(ce ; acc : String = ''| acc+ce.generateRefTag('constrainedElement', level+1))
+self.ownedComment->iterate(com ; acc : String='' | acc+com.generateComment(level+1))
+self.specification.generateSpecification(level+1)
+indStr+'</ownedRule>'
/]
   */

  /*
   * 
[query public generateProperty(this : uml::Property, level : Integer = 0) : String = 
let pKind : String = if (self.owner.oclIsKindOf(uml::Association)) then 'ownedEnd' else 'ownedAttribute' endif in 
let indStr : String = indent(level) in
self.generateTag(pKind, true, level)
+(if (self.name->notEmpty()) then self.name.generateLiteral('name', level+1) else '' endif)
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
+(if (self.type->notEmpty()) then
	self.type.generateRefTag('type', level+1)
else
	''
endif)
+(if (self.isReadOnly) then
	'true'.generateLiteral('isReadOnly', level+1)
else
	''
endif)
+(if (self.isDerived) then
	'true'.generateLiteral('isDerived', level+1)
else
	''
endif)
+(if (self.isDerivedUnion) then
	'true'.generateLiteral('isDerivedUnion', level+1)
else
	''
endif)
+(if (self.aggregation=AggregationKind::composite) then
	'composite'.generateLiteral('aggregation', level+1)
else
	''
endif)
+(if (self.redefinedProperty->notEmpty()) then
	let str : String = self.redefinedProperty->iterate(p; acc : String = ''| acc+p.getGOID().getXMI_ID()+' ') in
	str.trim().generateLiteral('redefinedProperty', level+1)
else
	''
endif)
+(if (self.subsettedProperty->notEmpty()) then
	let str : String = self.subsettedProperty->iterate(p; acc : String = ''| acc+p.getGOID().getXMI_ID()+' ') in
	str.trim().generateLiteral('subsettedProperty', level+1)
else
	''
endif)
+(if (self.association->notEmpty()) then
	self.association.generateRefTag('association', level+1)
else
	''
endif)
+(if (self.upper<>1 or self.lower<>1) then
	generateMultiplicity(level+1)
else
	''
endif)
+(if (self.defaultValue->notEmpty()) then
	self.defaultValue.generateValue('defaultValue', level+1)
else
	''
endif)
+indStr+'</'+pKind+'>'
/]
   */

  /*
   * 
[query public generateParameter(this : uml::Parameter, level : Integer = 0) : String = 
let indStr : String = indent(level) in
self.generateTag('ownedParameter', true, level)
+(if (self.name->notEmpty()) then self.name.generateLiteral('name', level+1) else '' endif)
+(if (self.type->notEmpty()) then
	self.type.generateRefTag('type', level+1)
else
	''
endif)
+self.direction.toString().generateLiteral('direction', level+1)
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
+indStr+'</ownedParameter>'
 /]
   */

  /*
   * 
[query public generateOperation(this : uml::Operation, level : Integer = 0) : String = 
let indStr : String = indent(level) in
self.generateTag('ownedOperation', true, level)
+(if (self.name->notEmpty()) then self.name.generateLiteral('name', level+1) else '' endif)
+(if (self.isQuery) then
	'true'.generateLiteral('isQuery', level+1)
else
	''
endif)
+(if (self.redefinedOperation->notEmpty()) then
	let str : String = 	self.redefinedOperation->iterate(op; acc : String = ''| acc+op.getGOID().getXMI_ID()+' ') in
	str.trim().generateLiteral('redefinedOperation', level+1)
else
	''
endif)
+(if (self.bodyCondition->notEmpty()) then
	self.bodyCondition.generateRefTag('bodyCondition', level+1)
else
	''
endif)
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
+self.ownedRule->iterate(rule ; acc : String = ''| acc+rule.generateConstraint(level+1))
+self.ownedParameter->iterate(p ; acc : String = ''| acc+p.generateParameter(level+1))
+indStr+'</ownedOperation>'
 /]
   */

  /*
   * 
[query public generateGeneralization(this : uml::Generalization, level : Integer = 0) : String = 
let indStr : String = indent(level) in
self.generateTag('generalization', true, level)
+self.general.generateRefTag('general', level+1)
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
+indStr+'</generalization>' 
/]
   */

  /*
   * 
[query public generateImport(this : uml::PackageImport, level : Integer = 0) : String = 
let indStr : String = indent(level) in
self.generateTag('packageImport', true, level)
+(if self.importedPackage.URI->notEmpty() then
	self.importedPackage.URI.generateLiteral('importedPackage', level+1)
else
	self.importedPackage.generateRefTag('importedPackage', level+1)
endif)
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
+indStr+'</packageImport>'
/]
   */

  /*
   * 
[query public generateEnumLiteral(this : uml::EnumerationLiteral, level : Integer = 0) : String = 
let indStr : String = indent(level) in
self.generateTag('ownedLiteral', true, level)
+(if (self.name->notEmpty()) then self.name.generateLiteral('name', level+1) else '' endif)
+self.ownedComment->iterate(com ; acc : String = ''| acc+com.generateComment(level+1))
+indStr+'</ownedLiteral>'
/]
   */

  /*
   * 
[query public getStereotypeNSPath(this : uml::Package) : String =
if self.oclIsKindOf(uml::Profile) then
	self.getName()
else
	self.nestingPackage.getStereotypeNSPath()+'.'+self.getName()
endif/]
   */

  /*
   * 
[query public generateStereotypeApp(this : uml::Stereotype, base : uml::Element, level : Integer = 0) : String =
let indStr : String = indent(level),
	uuid : String = base.getGOID()+'._appliedStereotype.'+self.getName(),
	sta : ecore::EObject = base.getStereotypeApplication(self) in
let	idStr : String = ' xmi:id="'+uuid.getXMI_ID()+'"',
	uuidStr : String = '',--' xmi:uuid="'+uuid+'"',
	xmlElemType : String = self.namespace.oclAsType(uml::Package).getStereotypeNSPath()+':'+self.getName(),
	baseName : String = sta.eClass().eAllStructuralFeatures->any(f | sta.eGet(f) = base).getName()
in
indStr+'<'+xmlElemType+idStr+uuidStr+'/>'
+sta.eClass().eAllStructuralFeatures->iterate(f; acc:String='' |
	if sta.eGet(f).oclIsUndefined() then
		acc
	else
		if f.oclIsKindOf(ecore::EReference) then
			acc+sta.eGet(f).oclAsType(uml::Element).generateRefTag(f.getName(), level+1)
		else
			acc+sta.eGet(f).toString().generateLiteral(f.getName(), level+1)
		endif
	endif)
+indStr+'</'+xmlElemType+'>'
 /]
   */

  /*
   * 
[query public generateSlot(this : uml::Slot, level : Integer = 0) : String = 
let indStr : String = indent(level) in 
self.generateTag('slot', true, level)
+self.definingFeature.generateRefTag('definingFeature', level+1)
+self.value->iterate(v; acc : String='' | acc+v.generateValue(v, 'value', level+1))
+indStr+'</slot>'
 /]
   */

  /*
   * 
[query public generatePackagedElement(this : uml::PackageableElement, level : Integer = 0) : String = 
let indStr : String = indent(level) in 
self.generateTag('packagedElement', true, level)
+(if (self.name->notEmpty()) then self.name.generateLiteral('name', level+1) else '' endif)
+(if self.oclIsKindOf(uml::Package) then
	--let uri : String = self.oclAsType(uml::Package).URI in
	--if uri.size() > 0 then uri.generateLiteral('URI', level+1) else '' endif
	self.oclAsType(uml::Package).URI.generateLiteral('URI', level+1)
else
	''
endif)
+self.ownedComment->iterate(j ; acc : String = ''| acc+j.generateComment(level+1))
+(if (self.oclIsKindOf(uml::Namespace)) then
	self.oclAsType(uml::Namespace).ownedRule->iterate(j ; acc : String = ''| acc+j.generateConstraint(level+1))
	+self.oclAsType(uml::Namespace).packageImport->iterate(j ; acc : String = ''| acc+j.generateImport(level+1))
	+(if (self.oclIsKindOf(uml::Package)) then
		self.oclAsType(uml::Package).packagedElement->iterate(j ; acc : String = ''| acc+j.generatePackagedElement(level+1))
	else
		if (self.oclIsKindOf(uml::Classifier)) then
			self.oclAsType(uml::Classifier).generalization->iterate(j ; acc : String = ''| acc+j.generateGeneralization(level+1))
			+(if (self.oclIsKindOf(uml::Class)) then
				self.oclAsType(uml::Class).ownedAttribute->iterate(j ; acc : String = ''| acc+j.generateProperty(level+1))
				+self.oclAsType(uml::Class).ownedOperation->iterate(j ; acc : String = ''| acc+j.generateOperation(level+1))
			else
				if (self.oclIsKindOf(uml::Enumeration)) then
					self.oclAsType(uml::Enumeration).ownedLiteral->iterate(j ; acc : String = ''| acc+j.generateEnumLiteral(level+1))
				else
					if (self.oclIsKindOf(uml::DataType)) then
						self.oclAsType(uml::DataType).ownedAttribute->iterate(j ; acc : String = ''| acc+j.generateProperty(level+1))
						+self.oclAsType(uml::DataType).ownedOperation->iterate(j ; acc : String = ''| acc+j.generateOperation(level+1))
					else
						if (self.oclIsKindOf(uml::Signal)) then
							self.oclAsType(uml::Signal).ownedAttribute->iterate(j ; acc : String = ''| acc+j.generateProperty(level+1))
						else
							if (self.oclIsKindOf(uml::Association)) then
								self.oclAsType(uml::Association).ownedEnd->iterate(j ; acc : String = ''| acc+j.generateProperty(level+1))
							else				
								'\n***ERROR***: cannot generate '+self.eClass().getName()+ ' element\n'
							endif
						endif
					endif
				endif
			endif)
		else
			'\n***ERROR***: cannot generate '+self.eClass().getName()+ ' element\n'
		endif
	endif)
else
	if (self.oclIsKindOf(uml::InstanceSpecification)) then
		self.oclAsType(uml::InstanceSpecification).slot->iterate(s; acc:String='' | acc+s.generateSlot(level+1))
	else
		''
	endif 
endif)
+indStr+'</packagedElement>'
 /]
   */

  /*
   * 
[query public generateMOFTag(this : uml::Element, name : String, value : String) : String =
'\t<mofext:Tag xmi:id="'+self.getGOID().getXMI_ID()+'._moftag" xmi:type="mofext:Tag">'
+name.generateLiteral('name', 2)
+value.generateLiteral('value', 2)
+self.generateRefTag('element', 2)
+'\n\t</mofext:Tag>'
 /]
   */
}