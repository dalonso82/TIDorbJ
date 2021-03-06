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

public class HashedLinkedList extends LinkedList
{

    public HashedLinkedList(int maximumSize)
    {
        super(maximumSize);
        m_table = new java.util.Hashtable();
    }

    /**
     * @param object
     *            represents the key & item to be saved in the hashtable
     */
    public void append(Object object)
        throws Exception
    {
        append(object, object);
    }

    public void append(Object key, Object item)
        throws Exception
    {
        super.append(item);
        m_table.put(key, new Integer(getLast()));
    }

    public Object get(Object key)
    {
        Integer index = (Integer) m_table.get(key);
        return getObjectAt(index.intValue());
    }

    public boolean object_exist(Object key)
    {
        return (m_table.get(key) != null);
    }

    protected int getIndex(Object key)
    {
        Integer index = (Integer) m_table.get(key);

        if (index == null)
            return -1;

        return index.intValue();
    }

    public Object remove(Object key)
    {

        Integer index = (Integer) m_table.get(key);
        if (index == null) {
            return null;
        }
        Object removed = internalRemove(index.intValue());
        m_table.remove(key);
        return removed;
    }

    public Object removeFirst()
    {
        Object removed = super.removeFirst();
        m_table.remove(removed);
        return removed;
    }

    public Object removeLast()
    {
        Object removed = super.removeLast();
        m_table.remove(removed);
        return removed;
    }

    public java.util.Enumeration getKeys()
    {
        return m_table.keys();
    }

    /*
     * public void dump(java.io.PrintStream os) { super.dump(os);
     * os.print("Keys: ["); for (java.util.Enumeration enum = _table.keys();
     * enum.hasMoreElements(); ) { os.print(enum.nextElement()); if
     * (enum.hasMoreElements()) os.print(", "); } os.println("]"); }
     */

    protected java.util.Hashtable m_table;

    /*
     * public static void main(String[] args) { HashedLinkedList l = new
     * HashedLinkedList(10); l.append(new Integer(1)); l.append(new Integer(2));
     * l.append(new Integer(3)); l.append(new Integer(4)); l.append(new
     * Integer(5)); l.append(new Integer(6)); l.append(new Integer(7));
     * l.append(new Integer(8)); l.append(new Integer(9)); l.append(new
     * Integer(10)); System.out.println("-------"); l.dump(System.out);
     * l.remove(new Integer(7)); System.out.println("-------");
     * l.dump(System.out); l.removeFirst(); System.out.println("-------");
     * l.dump(System.out); System.out.println("Tam: " + l.getSize()); }
     */
}