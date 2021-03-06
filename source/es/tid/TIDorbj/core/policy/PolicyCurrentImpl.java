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
package es.tid.TIDorbj.core.policy;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyCurrentLocalBase;

public class PolicyCurrentImpl extends PolicyCurrentLocalBase
{

    PolicyContextManager m_manager;

    final static Policy[] st_empty_list = {};

    public PolicyCurrentImpl(PolicyContextManager manager)
    {
        m_manager = manager;
    }

    public org.omg.CORBA.Policy[] get_policy_overrides(int[] ts)
    {
        PolicyContext context = 
            m_manager.tryToGetThreadContext(Thread.currentThread());

        if (context == null)
            return st_empty_list;

        return context.getPolicies(ts);
    }

    public void set_policy_overrides(org.omg.CORBA.Policy[] policies,
                                     org.omg.CORBA.SetOverrideType set_add)
        throws org.omg.CORBA.InvalidPolicies
    {
        PolicyContext context =
            m_manager.getThreadContext(Thread.currentThread());

        context.setPolicies(policies, set_add);
    }

    public PolicyContext getPolicyContext()
    {
        return m_manager.tryToGetThreadContext(Thread.currentThread());
    }

}