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
package es.tid.TIDorbj.core.comm.giop;

import es.tid.TIDorbj.core.cdr.BufferCDR;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.comm.iiop.IIOPConnection;

/**
 * Represents the 1.0, 1.1 and 1.2 GIOP version CancelRequest messages.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPCancelRequestMessage extends GIOPMessage
{

    /**
     * Header member.
     */
    private RequestId m_request_id;

    /**
     * Constructor used for message sending.
     */

    public GIOPCancelRequestMessage(GIOPVersion version, RequestId id)
    {
        super(new GIOPHeader(version, MsgType.CancelRequest));
        m_request_id = id;
    }

    /**
     * Constructor used in message reception. First, the message header is
     * readed, and then the message object is created.
     * 
     * @param header
     *            the cancel request header.
     */

    public GIOPCancelRequestMessage(GIOPHeader header)
    {
        super(header);
    }

    public RequestId getRequestId()
    {
        return m_request_id;
    }

    //TODO: giop should not know anything about IIOPConnections!!
    public void receiveBody(IIOPConnection conn, byte[] header_buffer)
    {
        super.receiveBody(conn, header_buffer);

        CDRInputStream id_in = new CDRInputStream(null, m_message_buffer);
        id_in.setByteOrder(m_header.getByteOrder());
        m_request_id = new RequestId(id_in.read_ulong());
        try {
            id_in.close();
        }
        catch (Exception e) {}
        id_in = null;
    }

    public BufferCDR getMessageBuffer()
    {
        if (!m_message_completed) {
            if (m_header.getVersion() == GIOPVersion.VERSION_1_2) {
                m_message_buffer = 
                    new BufferCDR(new byte[GIOPHeader.HEADER_SIZE
                                           + CDR.ULONG_SIZE]);

                // write header
                CDROutputStream out = 
                    new CDROutputStream(null, m_message_buffer);
                m_header.write(out);
                //write
                out.write_ulong(m_request_id.value());

                try {
                    out.close();
                }
                catch (Exception e) {}

                out = null;
                m_message_completed = true;
                m_headers_marshaled = true;
            } else {
                super.writeHeaders();
                m_message_completed = true;
            }
        }

        return m_message_buffer;
    }

    public boolean hasBody()
    {
        return m_header.getVersion() == GIOPVersion.VERSION_1_2;
    }
}