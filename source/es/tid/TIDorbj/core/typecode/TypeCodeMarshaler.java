/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 2 $
* Date: $Date: 2005-12-19 08:58:21 +0100 (Mon, 19 Dec 2005) $
* Last modified by: $Author: caceres $
*
* (C) Copyright 2004 Telefónica Investigación y Desarrollo
*     S.A.Unipersonal (Telefónica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/Licensing
*/    
package es.tid.TIDorbj.core.typecode;

import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * General TypeCode marshalling operations.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class TypeCodeMarshaler
{

    /**
     * Marshal the given typecode in a
     * <code>es.tid.TIDorbj.core.CDRInputStream</code>. This method will
     * alwais be invoked by this stream.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the <code>es.tid.TIDorbj.core.CDRInputStream</code>
     */

    public static void marshal(TypeCode type, CDROutputStream output)
    {
        putInCache(type, output);

        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                StructTypeCode.marshal(type, output);
            break;
            case TCKind._tk_objref:
                ObjectRefTypeCode.marshal(type, output);
            break;
            case TCKind._tk_union:
                UnionTypeCode.marshal(type, output);
            break;
            case TCKind._tk_enum:
                EnumTypeCode.marshal(type, output);
            break;
            case TCKind._tk_string:
                StringTypeCode.marshal(type, output);
            break;
            case TCKind._tk_sequence:
                SequenceTypeCode.marshal(type, output);
            break;
            case TCKind._tk_array:
                ArrayTypeCode.marshal(type, output);
            break;
            case TCKind._tk_alias:
                AliasTypeCode.marshal(type, output);
            break;
            case TCKind._tk_except:
                ExceptionTypeCode.marshal(type, output);
            break;
            case TCKind._tk_wstring:
                WStringTypeCode.marshal(type, output);
            break;
            case TCKind._tk_fixed:
                FixedTypeCode.marshal(type, output);
            break;
            case TCKind._tk_value:
                ValueTypeCode.marshal(type, output);
            break;
            case TCKind._tk_value_box:
                ValueBoxTypeCode.marshal(type, output);
            break;
            case TCKind._tk_native:
                NativeTypeCode.marshal(type, output);
            break;
            case TCKind._tk_abstract_interface:
                AbstractInterfaceTypeCode.marshal(type, output);
            break;
            default:
                TypeCodeImpl.marshal(type, output);
        }
    }

    /**
     * Marshal the given typecode params in a
     * <code>es.tid.TIDorbj.core.CDRInputStream</code>. This method will
     * alwais be invoked by this stream.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the <code>es.tid.TIDorbj.core.CDRInputStream</code>
     */

    public static void writeParams(TypeCode type, CDROutputStream output)
    {
        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                StructTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_objref:
                ObjectRefTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_union:
                UnionTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_enum:
                EnumTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_alias:
                AliasTypeCode.write_params(type, output);
            break;
            case TCKind._tk_except:
                ExceptionTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_value:
                ValueTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_value_box:
                ValueBoxTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_native:
                NativeTypeCode.writeParams(type, output);
            break;
            case TCKind._tk_abstract_interface:
                AbstractInterfaceTypeCode.writeParams(type, output);
            break;
            default:
                throw new org.omg.CORBA.INTERNAL("TypeCode has not params");
        }
    }

    public static void skipParams(TCKind kind, CDRInputStream input)
    {
        switch (kind.value())
        {
            case TCKind._tk_struct:
            case TCKind._tk_objref:
            case TCKind._tk_union:
            case TCKind._tk_enum:
            case TCKind._tk_alias:
            case TCKind._tk_except:
            case TCKind._tk_value:
            case TCKind._tk_value_box:
            case TCKind._tk_native:
            case TCKind._tk_abstract_interface:
                ComplexTypeCode.skipParams(input);
            break;
            case TCKind._tk_sequence:
                SequenceTypeCode.skip_params(input);
            break;
            case TCKind._tk_array:
                ArrayTypeCode.skip_params(input);
            break;
            case TCKind._tk_string:
                StringTypeCode.skipParams(input);
            break;
            case TCKind._tk_wstring:
                WStringTypeCode.skipParams(input);
            break;
            case TCKind._tk_fixed:
                FixedTypeCode.skipParams(input);
            break;
        }
    }

    /**
     * Copies and remarshals the given typecode value marshaled in an
     * InputStream to a <code>es.tid.TIDorbj.core.CDRInputStream</code>. This
     * method will alwais be invoked by this stream.
     * 
     * @param type
     *            the value <code>TypeCode</code>
     * @param input
     *            the <code>InputStream</code> where the value is marshaled
     * @param output
     *            the <code>es.tid.TIDorbj.core.CDRInputStream</code>
     */

    public static void remarshalValue(org.omg.CORBA.TypeCode type,
                                      InputStream input, OutputStream output)
    {

        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                StructTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_objref:
                ObjectRefTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_union:
                UnionTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_enum:
                EnumTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_string:
                StringTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_sequence:
                SequenceTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_array:
                ArrayTypeCode.remarshal_value(type, input, output);
            break;
            case TCKind._tk_alias:
                AliasTypeCode.remarshal_value(type, input, output);
            break;
            case TCKind._tk_except:
                ExceptionTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_wstring:
                WStringTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_fixed:
                FixedTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_value:
                ValueTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_value_box:
                ValueBoxTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_native:
                NativeTypeCode.remarshalValue(type, input, output);
            break;
            case TCKind._tk_abstract_interface:
                AbstractInterfaceTypeCode.remarshalValue(type, input, output);
            break;
            default:
                TypeCodeImpl.remarshalValue(type, input, output);
        }
    }

    /**
     * Compares two InputStream marshaled values of a given TypeCode to a
     * <code>es.tid.TIDorbj.core.CDRInputStream</code>. This method will
     * alwais be invoked by this stream.
     * 
     * @param type
     *            the value <code>TypeCode</code>
     * @param input_a
     *            the <code>InputStream</code> where one value is marshaled
     * @param input_b
     *            the <code>InputStream</code> where the value value is
     *            marshaled
     */

    public static boolean valuesEqual(org.omg.CORBA.TypeCode type,
                                      InputStream input_a, InputStream input_b)
    {

        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                return StructTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_objref:
                return ObjectRefTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_union:
                return UnionTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_enum:
                return EnumTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_string:
                return StringTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_sequence:
                return SequenceTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_array:
                return ArrayTypeCode.values_equal(type, input_a, input_b);
            case TCKind._tk_alias:
                return AliasTypeCode.values_equal(type, input_a, input_b);
            case TCKind._tk_except:
                return ExceptionTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_wstring:
                return WStringTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_fixed:
                return FixedTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_value:
                return ValueTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_value_box:
                return ValueBoxTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_native:
                return NativeTypeCode.valuesEqual(type, input_a, input_b);
            case TCKind._tk_abstract_interface:
                return AbstractInterfaceTypeCode.valuesEqual(type, input_a,
                                                             input_b);
            default:
                return TypeCodeImpl.valuesEqual(type, input_a, input_b);
        }
    }

    /**
     * Skips the value asociated to the TypeCode. This operation is used by the
     * TIDorb's Any implementation an the subclass <code>skip_value()</code>
     * operations.
     * 
     * @param input
     *            must be alwais a reference to a CDRInputStream object.
     */

    public static boolean skipValue(TypeCode type, CDRInputStream input)
    {
        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                return StructTypeCode.skipValue(type, input);
            case TCKind._tk_objref:
                return ObjectRefTypeCode.skipValue(type, input);
            case TCKind._tk_union:
                return UnionTypeCode.skipValue(type, input);
            case TCKind._tk_enum:
                return EnumTypeCode.skipValue(type, input);
            case TCKind._tk_string:
                return StringTypeCode.skipValue(type, input);
            case TCKind._tk_sequence:
                return SequenceTypeCode.skipValue(type, input);
            case TCKind._tk_array:
                return ArrayTypeCode.skip_value(type, input);
            case TCKind._tk_alias:
                return AliasTypeCode.skip_value(type, input);
            case TCKind._tk_except:
                return ExceptionTypeCode.skip_value(type, input);
            case TCKind._tk_wstring:
                return WStringTypeCode.skipValue(type, input);
            case TCKind._tk_fixed:
                return FixedTypeCode.skipValue(type, input);
            case TCKind._tk_value:
                return ValueTypeCode.skipValue(type, input);
            case TCKind._tk_value_box:
                return ValueBoxTypeCode.skipValue(type, input);
            case TCKind._tk_native:
                return NativeTypeCode.skipValue(type, input);
            case TCKind._tk_abstract_interface:
                return AbstractInterfaceTypeCode.skip_value(type, input);
            default:
                return TypeCodeImpl.skipValue(type, input);
        }
    }

    /**
     * Skips a value array asociated to the TypeCode. This operation is used by
     * the TIDorb's Any implementation an the subclass <code>skip_value()</code>
     * operations.
     * 
     * @param input
     *            must be alwais a reference to a CDRInputStream object.
     */
    public static boolean skipValueArray(TypeCode type, CDRInputStream input,
                                         int n)
    {

        switch (type.kind().value())
        {
            case TCKind._tk_short:
                input.skipShortArray(n);
            break;
            case TCKind._tk_long:
                input.skipLongArray(n);
            break;
            case TCKind._tk_longlong:
                input.skipLonglongArray(n);
            break;
            case TCKind._tk_ushort:
                input.skipUshortArray(n);
            break;
            case TCKind._tk_ulong:
                input.skipUlongArray(n);
            break;
            case TCKind._tk_ulonglong:
                input.skipUlonglongArray(n);
            break;
            case TCKind._tk_float:
                input.skipFloatArray(n);
            break;
            case TCKind._tk_double:
                input.skipDoubleArray(n);
            break;
            case TCKind._tk_boolean:
                input.skipBooleanArray(n);
            break;
            case TCKind._tk_char:
                input.skipCharArray(n);
            break;
            case TCKind._tk_wchar:
                input.skipWcharArray(n);
            break;
            case TCKind._tk_octet:
                input.skipOctetArray(n);
            break;
            default:
                for (int i = 0; i < n; i++)
                    if (!skipValue(type, input))
                        return false;
        }

        return true;
    }

    public static void putInCache(TypeCode type, CDROutputStream output)
    {
        org.omg.CORBA.ORB input_orb = output.orb();

        if (input_orb == null)
            return;

        TypeCodeCache cache = null;

        if (input_orb instanceof TIDORB)
            cache = ((TIDORB) input_orb).getTypeCodeCache();

        if (cache != null) {
            switch (type.kind().value())
            {
                case TCKind._tk_struct:
                case TCKind._tk_objref:
                case TCKind._tk_union:
                case TCKind._tk_enum:
                case TCKind._tk_alias:
                case TCKind._tk_except:
                case TCKind._tk_value:
                case TCKind._tk_value_box:
                case TCKind._tk_native:
                case TCKind._tk_abstract_interface:
                    cache.put(type);
            }
        }
    }
}