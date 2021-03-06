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
package es.tid.TIDorbj.dynAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;

import es.tid.TIDorbj.core.TIDORB;

/**
 * DynStruct implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynStructImpl extends DynStructBase
    implements org.omg.DynamicAny.DynStruct
{

    /**
     * Empty Constructor for generate copies.
     */

    protected DynStructImpl(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
    }

    /**
     * Constructor. Gets an any object for reading its value. It assumes that
     * the any contains an struct TypeCode.
     * 
     * @param any
     *            the any value.
     */

    protected DynStructImpl(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                            TypeCode real_type)
    {
        super(factory, orb, any, real_type);

        try {
            m_component_count = real_type.member_count();
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE();
        }

        if (m_component_count == 0) {
            m_current_index = -1;
        } else {
            m_current_index = 0;
        }

    }

    /**
     * Constructor. Gets a simple TypeCode to create a new value. Warning: It
     * assumes that the TypeCode is tk_struct or tk_exception
     * 
     * @param type
     *            the TypeCode value.
     */

    protected DynStructImpl(DynAnyFactoryImpl factory, TIDORB orb,
                            TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);
        try {
            m_component_count = real_type.member_count();
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE();
        }

        if (m_component_count == 0) {
            m_current_index = -1;
        } else {
            m_current_index = 0;
        }

    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        DynStructImpl new_dyn = new DynStructImpl(m_factory, m_orb, m_dyn_type,
                                                  m_base_type);

        copyTo(new_dyn);

        return new_dyn;
    }

    // Object methods

    public boolean _is_a(java.lang.String repositoryIdentifier)
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        if (repositoryIdentifier == null)
            throw new BAD_PARAM("Null string reference");

        if (repositoryIdentifier.equals("IDL:omg.org/DynamicAny/DynStruct:1.0"))
            return true;

        return super._is_a(repositoryIdentifier);

    }
}