/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 45 $
* Date: $Date: 2007-02-12 16:07:48 +0100 (Mon, 12 Feb 2007) $
* Last modified by: $Author: iredondo $
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

import java.util.Enumeration;
import java.util.Vector;

public class UseTable extends HashedLinkedList
{

    public UseTable(int maximumSize)
    {
        super(maximumSize);
        m_use = new int[maximumSize];
        m_round = 0;
        m_used_conections = 0;
        m_removed_objects = null;
    }

    synchronized public void append(Object object)
        throws FullUseTableException
    {
        append(object, object);
    }

    synchronized public void append(Object key, Object item)
        throws FullUseTableException
    {
        // Remove elements if needed
        m_removed_objects = removeOldObjects();

        /*
         * if (_removedObjects != null) {
         * System.out.println("Round: " + _round + 
         * "Eliminando conexiones: " + _removedObjects.length); }
         */

        if (getSize() == getMaximumSize()) {
            throw new 
            FullUseTableException("Not enough space to append a new object");
        }

        // Append new element
        try {
            super.append(key, item);
        }
        catch (Exception e) {
            // should never happen!!
        }
        if (m_round == Integer.MAX_VALUE) {
            // Should check if count == maxInteger !!
        }
        // Initialize round
        m_use[getLast()] = m_round - 1;
    }

    synchronized public void use(Object key)
    {
        // Update round
        int i = getIndex(key);

        if (i < 0)
            return;

        if (m_use[i] != m_round) {
            m_used_conections++;
            if (m_used_conections > (getMaximumSize() / 2)) {
                m_round++;
                m_used_conections = 0;
            }
        }

        // Update linked list of elements
        Object removed = remove(key);
        try {
            append(key, removed);
        }
        catch (Exception e) {
            // should never happen!
        }
        m_use[getLast()] = m_round;
    }

    synchronized public Object[] getRemovedObjects()
    {
        return m_removed_objects;
    }

    protected Object[] removeOldObjects()
    {

        Vector removed = new Vector();
        Vector reinsertedKeys = new Vector();
        Vector reinsertedObjects = new Vector();

        if (getSize() > getMaximumSize() * 0.8) {
            do {
                int firstRound = m_use[getFirst()];
                int maxRemoved = (int) (getMaximumSize() * 0.5);
                for (Enumeration myenum = getKeys(); myenum.hasMoreElements();) {
                    Object obj = myenum.nextElement();
                    int i = getIndex(obj);
                    if (m_use[i] <= (m_round - 4)) {
                        removed.addElement(obj);
                    }
                }

                for (int j = 0; j < removed.size(); j++) {
                    Object key = removed.elementAt(j);
                    if (key instanceof RemovableObject) {
                        RemovableObject removableKey = (RemovableObject) key;
                        if (!removableKey.canBeRemoved()) {
                            Object obj = get(key);
                            reinsertedKeys.addElement(key);
                            reinsertedObjects.addElement(obj);
                            removed.removeElement(key);
                        }
                    }
                    remove(key);
                }

                if ((getSize() + reinsertedKeys.size()) >= getMaximumSize()) {
                    m_round++;
                }
            } while ((getSize() > 0)
                     && (getSize() + reinsertedKeys.size()) 
                     >= getMaximumSize());
        }

        try {
            for (int j = 0; j < reinsertedKeys.size(); j++) {
                super.append(reinsertedKeys.elementAt(j),
                             reinsertedObjects.elementAt(j));
            }
        }
        catch (Exception e) {
            // should never happen!!
        }

        if (reinsertedKeys.size() == getSize()) {
            return null;
        }

        if (removed.size() != 0) {
            Object[] res = new Object[removed.size()];
            removed.copyInto(res);
            return res;
        } else
            return null;
    }

    public void dump(java.io.PrintStream os)
    {
        super.dump(os);
        int i = getFirst();
        os.print("Uses: [");
        while (i > NULL) {
            os.print(m_use[i]);
            i = getNext(i);
            if (i > NULL)
                os.print(", ");
        }
        os.println("]");
    }

    private int[] m_use;

    private int m_round;

    private int m_used_conections;

    private Object[] m_removed_objects;

    /*
     * public static void main(String[] args) { try {
     * 
     * UseTable l = new UseTable(10); l.append(new Integer(1), new String("a"));
     * l.append(new Integer(2), new String("b")); l.append(new Integer(3), new
     * String("c")); l.append(new Integer(4), new String("d")); l.append(new
     * Integer(5), new String("e")); l.append(new Integer(6), new String("f"));
     * l.append(new Integer(7), new String("g")); l.append(new Integer(8), new
     * String("h")); System.out.println("-------"); l.dump(System.out);
     * System.out.println("Objeto: " + l.get(new Integer(5)));
     * 
     * l.use(new Integer(4)); System.out.println("-------"); l.dump(System.out);
     * l.use(new Integer(5)); System.out.println("-------"); l.dump(System.out);
     * l.use(new Integer(1)); System.out.println("-------"); l.dump(System.out);
     * l.use(new Integer(7)); System.out.println("-------"); l.dump(System.out);
     * l.use(new Integer(2)); System.out.println("-------"); l.dump(System.out);
     * l.use(new Integer(3)); System.out.println("-------"); l.dump(System.out);
     * 
     * l.append(new Integer(9), new String("i")); l.append(new Integer(10), new
     * String("j")); System.out.println("-------"); l.dump(System.out);
     * l.append(new Integer(11), new String("k"));
     * System.out.println("-------"); l.dump(System.out); l.append(new
     * Integer(12), new String("p")); System.out.println("-------");
     * l.dump(System.out); l.append(new Integer(13), new String("q")); } catch
     * (Throwable t) { t.printStackTrace(); }
     * 
     * 
     * l.remove(new Integer(5)); System.out.println("-------");
     * l.dump(System.out); l.use(new Integer(6)); System.out.println("-------");
     * l.dump(System.out); l.use(new Integer(11));
     * System.out.println("-------"); l.dump(System.out);
     * System.out.println("Tam: " + l.getSize()); }
     */
}