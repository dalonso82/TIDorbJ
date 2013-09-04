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
package es.tid.TIDorbj.core.util;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TypeCode;

import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.typecode.TypeCodeFactory;

/**
 * Particular Holder for WString, Java Mapping does not give one.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

final public class WStringHolder
    implements org.omg.CORBA.portable.Streamable
{

    public TypeCode wstring_type;

    public String value = null;

    public WStringHolder()
    {
        wstring_type = TypeCodeFactory.tc_wstring;
    }

    public WStringHolder(TypeCode type)
    {
        wstring_type = type;
    }

    public WStringHolder(TypeCode type, String initial)
    {
        wstring_type = type;
        value = initial;
    }

    public WStringHolder(String initial)
    {
        wstring_type = TypeCodeFactory.tc_wstring;
        value = initial;
    }

    public void _read(org.omg.CORBA.portable.InputStream is)
    {
        value = is.read_wstring();
        try {
            if (wstring_type.length() != 0)
                if (value.length() > (wstring_type.length() / CDR.WCHAR_SIZE))
                    throw new MARSHAL("String out of bounds.",
                                      0,
                                      CompletionStatus.COMPLETED_NO);
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {}
    }

    public void _write(org.omg.CORBA.portable.OutputStream os)
    {
        try {
            if (wstring_type.length() != 0)
                if (value.length() > (wstring_type.length() / CDR.WCHAR_SIZE))
                    throw new BAD_PARAM("String out of bounds.",
                                        0,
                                        CompletionStatus.COMPLETED_NO);
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {}

        os.write_wstring(value);
    }

    public org.omg.CORBA.TypeCode _type()
    {
        return wstring_type;
    }

}