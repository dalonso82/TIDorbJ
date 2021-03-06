/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 395 $
* Date: $Date: 2009-05-27 16:10:32 +0200 (Wed, 27 May 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telef�nica Investigaci�n y Desarrollo
*     S.A.Unipersonal (Telef�nica I+D)
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
package es.tid.TIDorbj.core.comm.giop;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;

import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * Represents the 1.0, 1.1 and 1.2 GIOP version message headers.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPHeader
{

    GIOPVersion m_version;

    boolean m_byte_order;

    boolean m_more_fragments;

    MsgType m_message_type;

    int message_size;

    String m_str = null;

    boolean m_compressed;

    public final static int HEADER_SIZE = 12;

    public GIOPHeader()
    {}

    public GIOPHeader(GIOPVersion ver, MsgType type)
    {
        m_version = ver;
        m_byte_order = CDR.LOCAL_BYTE_ORDER;
        m_more_fragments = false;
        m_message_type = type;
        message_size = 0;
        m_compressed = false;
    }

    public static GIOPHeader fromByteArray(byte[] array)
        throws org.omg.CORBA.MARSHAL
    {
        GIOPHeader header = new GIOPHeader();
        if (array.length < HEADER_SIZE)
            return null;
        CDRInputStream input = new CDRInputStream(null, array);
        header.read(input);
        input = null;
        return header;
    }

    public GIOPVersion getVersion()
    {
        return m_version;
    }

    public MsgType getMsgType()
    {
        return m_message_type;
    }

    public void setByteOrder(boolean order)
    {
        m_byte_order = order;
    }

    public boolean getByteOrder()
    {
        return m_byte_order;
    }

    public void setMoreFragments(boolean more)
    {
        m_more_fragments = more;
    }

    public boolean hasMoreFragments()
    {
        if (m_version.minor == 0)
            return false;
        else
            return m_more_fragments;
    }

    public void setSize(int size)
    {
        message_size = size;
    }

    public int getSize()
    {
        return message_size;
    }

    public void setCompressed(boolean compressed)
    {
        m_compressed = compressed;
    }

    public boolean getCompressed()
    {
        return m_compressed;
    }

    public void toByteArray(byte[] header_buffer)
    {
        CDROutputStream output = new CDROutputStream(null, header_buffer);
        write(output);
        output = null;
    }

    public void write(org.omg.CORBA.portable.OutputStream output)
    {
        if (m_compressed)
            output.write_octet((byte) 'Z');
        else
            output.write_octet((byte) 'G');
        output.write_octet((byte) 'I');
        output.write_octet((byte) 'O');
        output.write_octet((byte) 'P');

        output.write_octet((byte) m_version.major);
        output.write_octet((byte) m_version.minor);

        if (m_version.minor == 0)
            output.write_boolean(m_byte_order);
        else {
            int flag = 0;
            if (m_byte_order)
                flag |= 0x1;
            if (m_more_fragments)
                flag |= 0x2;
            output.write_octet((byte) flag);
        }

        output.write_octet((byte) m_message_type.m_value);
        output.write_ulong(message_size);
    }

    public void read(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        byte first_magic_char = input.read_octet();

        if ( ( (first_magic_char != (byte) 'G') && (first_magic_char != (byte) 'Z'))
            || (input.read_octet() != (byte) 'I')
            || (input.read_octet() != (byte) 'O')
            || (input.read_octet() != (byte) 'P'))

            throw new 
                MARSHAL("Bad GIOP Message header: Invalid header identifier.",
                        0, CompletionStatus.COMPLETED_NO);

        m_compressed = (first_magic_char == 'Z');
        
        m_version = GIOPVersion.read(input);

        if (m_version == null)
            throw new 
                MARSHAL("Bad GIOP Message header: Invalid version number.",
                        0, CompletionStatus.COMPLETED_NO);

        byte flag = input.read_octet();

        if (m_version.minor == 0)
            m_byte_order = (flag != 0);
        else {
            m_byte_order = ((flag & 0x1) != 0);
            m_more_fragments = ((flag & 0x2) != 0);
        }

        input.setByteOrder(m_byte_order);
        m_message_type = MsgType.from_int(input.read_octet());

        if (m_message_type == null)
            throw new MARSHAL("Bad GIOP Message header: Invalid message type.",
                              0, CompletionStatus.COMPLETED_NO);

        message_size = input.read_ulong();

    }

    public String toString()
    {
        if (m_str == null)
            m_str = MsgType.msgName(m_message_type.m_value) + " ("
                    + m_version.toString() + ") "
                    + ((m_more_fragments) ? "[FRAGMENTED] " : "")
                    + ((m_byte_order) ? "[LITTLE_ENDIAN] " : "[BIG_ENDIAN] ")
                    + "[SIZE: " + message_size + "]";

        return m_str;

    }

}
