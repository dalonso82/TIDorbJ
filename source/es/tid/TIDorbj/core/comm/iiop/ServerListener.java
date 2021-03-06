/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 445 $
* Date: $Date: 2010-01-21 18:29:00 +0100 (Thu, 21 Jan 2010) $
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
package es.tid.TIDorbj.core.comm.iiop;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import es.tid.TIDorbj.core.ConfORB;
import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.giop.ServiceContextList;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedComponent;
import es.tid.TIDorbj.core.iop.TaggedProfile;
import es.tid.TIDorbj.core.poa.POAKey;
import es.tid.TIDorbj.util.Trace;

public class ServerListener extends Thread
{

    /**
     * ServerSocket state
     */

    boolean m_shutdowned;

    boolean m_connected;

    /**
     * ServerSocket where the communication layer will accept connections. This
     * socket will be only avalilable when a the ORB's <code>run</code> method
     * had been invoked.
     */
    ServerSocket m_server_socket;

    /**
     * Conection manager.
     */
    IIOPConnectionManager m_manager;

    /**
     * Listen points where the ServerSocket will be listening. This
     * Vector { point(host,port) } will be used to create de local IOR's.
     */
    java.util.Vector m_listen_points;

    /**
     * Service context that contains the information for activating the
     * bidirectional service.
     */
    ServiceContextList m_bidirectional_service;

    es.tid.TIDorbj.core.TIDORB m_orb;
    
    String hostAddress;
    String hostName;
    int port;
    int backlog;
    int reconnect;
    GIOPVersion giopVersion;
    boolean ipv6;
    String  iface;
    String m_listen_points_string;

    public ServerListener(IIOPConnectionManager manager)
    {
        m_shutdowned = true;
        m_connected = false;

        this.m_manager = manager;
        m_orb = manager.orb();

        m_listen_points = new java.util.Vector();
        InetAddress inet;


        this.hostAddress = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.HOST_ADDRESS )
            .getValue(); 
        this.hostName =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.HOST_NAME )
            .getValue(); 
        this.port = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.PORT )
            .getInt(); 
        this.backlog = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.SERVER_SOCKET_BACKLOG )
            .getInt();
        this.reconnect =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.SERVER_SOCKET_RECONNECT )
            .getInt(); 
        this.giopVersion =
            GIOPVersion.fromString(
                m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
                .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.GIOP_VERSION )
                .getString()
            ); 
        this.ipv6 =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.IPV6 )
            .getBoolean();
        this.iface =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.IFACE )
            .getString(); 


        try {
            if ( hostAddress == null) {
                m_server_socket = new ServerSocket( this.port , this.backlog );
            } else {
                m_server_socket = new ServerSocket(
                    this.port, 
                    this.backlog, 
                    InetAddress.getByName( this.hostAddress ) 
                );
            }
            m_server_socket.setReuseAddress(true);

            if (this.hostName != null) {
                m_listen_points.addElement(new ListenPoint(this.hostName, 
                                                           m_server_socket.getLocalPort()));
            } else {

                if (this.ipv6) {
                    if ( hostAddress == null) { 
                        inet = InetAddress.getByName("::1"); //ip6-localhost");
                    }
                    else {
                        inet = InetAddress.getByName(hostAddress);
                    }
                    m_listen_points.addElement(new ListenPoint(
                                                      inet.getHostAddress(),
                                                      m_server_socket.getLocalPort()));
                }

                inet = InetAddress.getLocalHost();
                m_listen_points.addElement(new ListenPoint(
                                                  inet.getHostAddress()+ "%" + iface, 
                                                  m_server_socket.getLocalPort()));
            }

            m_shutdowned = false;
            m_connected = true;

        }
        catch (java.io.IOException e) {
            throw new org.omg.CORBA.INITIALIZE(
                "Can not open ServerSocket: " + e.toString() 
            );
        }

        m_listen_points_string = "";
        for (int i = 0; i < m_listen_points.size(); i++) {
            m_listen_points_string += m_listen_points.elementAt(i).toString();
            m_listen_points_string += " ";
        }


        m_orb.printTrace(
        	Trace.DEBUG, "ServerListener connected at: " + m_listen_points_string
		);
    }

    public java.util.Vector getListenPoints() {
        return m_listen_points;
    }

    public synchronized void shutdown() {
        if (!m_shutdowned) {
            m_shutdowned = true;
            try {
                m_server_socket.close();
            } catch (Throwable th) {}

            m_server_socket = null;

            m_orb.printTrace(
            	Trace.DEBUG, "ServerListener at " + m_listen_points_string + " shutdown!"
			);
        }
    }

    public synchronized void resetServerSocket() {
        if (!m_shutdowned) {
            try {
                if (m_server_socket != null)
                    m_server_socket.close();
            }
            catch (Throwable th) {}

            try {
                if ( this.hostAddress == null) {
                    m_server_socket = new ServerSocket(
                        ((ListenPoint)m_listen_points.elementAt(0)).m_port,
                        this.backlog
                    );
                } else {
                    m_server_socket = new ServerSocket(
                        ((ListenPoint)m_listen_points.elementAt(0)).m_port,
                        this.backlog,
                        InetAddress.getByName( this.hostAddress )
                    );
                }
                m_server_socket.setReuseAddress(true);
            } catch (Throwable th) {
                throw new org.omg.CORBA.INITIALIZE(
                    "Can not open ServerSocket: " + th.toString()
                );
            }	
        }
    }

    public void run()
    {
        while (!m_shutdowned) {
            while (m_connected) {

            	Socket client_socket = null;
                try {
                    client_socket = m_server_socket.accept();
                } catch (Throwable se) {

                    if ( m_shutdowned ){
                        return;
                    }

                    m_connected = false;

                    m_orb.printTrace(
                        Trace.ERROR, "Error in ServerSocket.accept(): ", se
					);
                }

                try {
                    if (client_socket != null) {
                        m_manager.createServerConnection(client_socket);
                    }
                } catch (Throwable e) {
                    try {
                        client_socket.close();
                    } catch (Throwable t) {}

                    m_orb.printTrace( 
                    	Trace.ERROR, "Error creating ServerConnection: ", e 
					);
                }
            }

            if ((!m_connected) && (!m_shutdowned)) {

                // RECONNECT

            	//printTrace will check for a trace handler existence 
                m_orb.printTrace( Trace.DEBUG, "Trying to reconnect server socket ");

                try {
                    resetServerSocket();
                    m_connected = true;

                    if (m_orb.m_trace != null) {
                        m_orb.printTrace(
                        	Trace.DEBUG, "ServerSocket reconnected" 
						);
                    }
                } catch (Throwable th) {
                    m_orb.printTrace(
                    	Trace.ERROR, "ServerSocket reconnect error: ", th 
					);
                    try {
                        Thread.sleep( this.reconnect );
                    } catch (InterruptedException e) {}
                }
            }
        }
    }

    public IOR createIOR(String id, POAKey key,  
                         TaggedComponent[] extraComponents) {
    	
        TaggedProfile[] profiles = new TaggedProfile[m_listen_points.size()];

        TaggedComponent[] components = null;
        
        if(extraComponents != null) {
            components = new TaggedComponent[extraComponents.length + 1];
            System.arraycopy(extraComponents, 0, components, 0, extraComponents.length);
            components[extraComponents.length] = ConfORB.ORB_TYPE;
        } else {
            components = new TaggedComponent[1];
            components[0] = ConfORB.ORB_TYPE;
        }
        
        for (int i = 0; i < m_listen_points.size(); i++) {
            profiles[i] = new IIOPProfile(this.giopVersion, 
                                          (ListenPoint)m_listen_points.elementAt(i),
                                          key,
                                          components);
        }

        return new IIOPIOR(id, profiles);
    }//createIOR
    
    public IOR createIOR(String id, ObjectKey key,  
                         TaggedComponent[] extraComponents) {
    	
        TaggedProfile[] profiles = new TaggedProfile[m_listen_points.size()];

        TaggedComponent[] components = null;
        
        if(extraComponents != null) {
            components = new TaggedComponent[extraComponents.length + 1];
            System.arraycopy(extraComponents, 0, components, 0, extraComponents.length);
            components[extraComponents.length] = ConfORB.ORB_TYPE;
        } else {
            components = new TaggedComponent[1];
            components[0] = ConfORB.ORB_TYPE;
        }

        for (int i = 0; i < m_listen_points.size(); i++) {
            profiles[i] = new IIOPProfile(this.giopVersion, 
                                          (ListenPoint)m_listen_points.elementAt(i),
                                          key,
                                          components);
        }

        return new IIOPIOR(id, profiles);
    }//createIOR
    
}
