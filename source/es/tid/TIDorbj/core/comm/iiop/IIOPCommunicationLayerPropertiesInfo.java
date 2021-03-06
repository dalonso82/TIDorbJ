/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 453 $
* Date: $Date: 2010-04-27 16:52:41 +0200 (Tue, 27 Apr 2010) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telefnica Investigacin y Desarrollo
*     S.A.Unipersonal (Telefnica I+D)
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.tid.TIDorbj.core.comm.PropertyInfo;

/**
 * @author jprojas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//TODO:change to IIOPCommunicationLayerPropertiesInfo
public class IIOPCommunicationLayerPropertiesInfo {
    
    /**
     * Assures that a oneway request could be forwarded. "true" or "false".
     */
    public final static String RELIABLE_ONEWAY  
        = "es.tid.TIDorbj.iiop.reliable_oneway";
    public final static String DEFAULT_RELIABLE_ONEWAY 
        = "false";
    
    
    /**
     * Fragment size Property name.
     */
    public final static String FRAGMENT_SIZE 
        = "es.tid.TIDorbj.iiop.fragment_size";
    public final static String DEFAULT_FRAGMENT_SIZE 
        = "4096";

    
    /**
     * GIOP version Property name. Values: "1.0", "1.1" and "1.2"
     */
    public final static String GIOP_VERSION = 
        "es.tid.TIDorbj.iiop.GIOPVersion";
    public final static String DEFAULT_GIOP_VERSION 
        = "1.2"; 

    /**
     * ORB's maximum opened connections number. Value must be greater or equals
     * to 0. Default value: <code>30</code>.
     */
    public final static String MAX_OPENED_CONNECTIONS 
        = "es.tid.TIDorbj.iiop.max_connections";
    public final static String DEFAULT_MAX_OPENED_CONNECTIONS 
        = "30";

        
    /**
     * DefaultORB's maximum recovering a communication tries. Default value: 
     * <code>3</code>.
     * TODO: max_comm_recovering_times -> max_comm_recovering_tries, update doc
     */
    public final static String MAX_COMM_RECOVERING_TRIES 
        = "es.tid.TIDorbj.iiop.max_comm_recovering_tries"; 
    public final static String DEFAULT_MAX_COMM_RECOVERING_TRIES = "3";

    
    /**
     * ORB's maximum time upon recovering a communication. Values must be 
     * greater or equal than 0.
     */
    public final static String COMM_RECOVERING_TIME 
        = "es.tid.TIDorbj.iiop.comm_recover_time";
    public final static String DEFAULT_COMM_RECOVERING_TIME 
        = "1000";

    /**
     * Maximum uncompleted messages per IIOPConnection. Default value: 
     * <code>25</code>.
     */
    public final static String MAX_UNCOMPLETED_MESSAGES =
        "es.tid.TIDorbj.iiop.max_uncompleted_messages";
    public final static String DEFAULT_MAX_UNCOMPLETED_MESSAGES = "25";

    /**
     * IIOP hostname used for generated IORs and URLs. Default value: null 
     */
    public final static String HOST_NAME 
        = "es.tid.TIDorbj.iiop.host";
    public final static String DEFAULT_HOST_NAME 
        = null;

    /**
     * IP address used for generated IORs and URLs. Default value: null 
     */
    public final static String HOST_ADDRESS 
        = "es.tid.TIDorbj.iiop.address";
    public final static String DEFAULT_HOST_ADDRESS 
        = null;

    /**
     * IIOP port used for generated IORs and URLs. Default value: 0 
     */
    public final static String PORT 
        = "es.tid.TIDorbj.iiop.port";
    public final static String DEFAULT_PORT 
        = "0";


    /**
     * Sockets SO_LIGER property. Values must be greater or equal than -1. 
     * Default value <code>-1</code> (no linger)
     */
    public final static String SOCKET_LINGER
        = "es.tid.TIDorbj.iiop.socket_linger";
    public final static String DEFAULT_SOCKET_LINGER 
        = "-1";

    /**
     * Sockets SO_TIMEOUT property. Values must be greater or equal than -1. 
     * Default value <code>0</code> (no timeout)
     */
    public final static String SOCKET_TIMEOUT
        = "es.tid.TIDorbj.iiop.socket_timeout";
    public final static String DEFAULT_SOCKET_TIMEOUT 
        = "0";


    /**
     * Sockets TCP_NODELAY property. Values must be "true" or "false".
     */
    public final static String TCP_NODELAY 
        = "es.tid.TIDorbj.iiop.tcp_nodelay";
    public final static String DEFAULT_TCP_NODELAY 
        = "true";

    /**
     * Sockets TCP_CONNECT_TIMEOUT property. Values must be greater or equal to 
     * 0. Default value 1000.
     */
    public final static String SOCKET_CONNECT_TIMEOUT
        = "es.tid.TIDorbj.iiop.socket_connect_timeout";
    public final static String DEFAULT_SOCKET_CONNECT_TIMEOUT 
        = "1000";

    /**
     * Sockets TCP_WRITE_TIMEOUT property. Values must be greater or equal to 0.
     * Default value 2000.
     */
    public final static String SOCKET_WRITE_TIMEOUT
        = "es.tid.TIDorbj.iiop.socket_write_timeout";
    public final static String DEFAULT_SOCKET_WRITE_TIMEOUT 
        = "2000";

    /**
     * Server sockets BACKLOG property. Values must be greater or equal to 0. 
     * Default value. 10.
     */
    public final static String SERVER_SOCKET_BACKLOG
        = "es.tid.TIDorbj.iiop.server_socket_backlog";
    public final static String DEFAULT_SERVER_SOCKET_BACKLOG 
        = "10";

    /**
     * Server sockets RECONNECT property. Values must be greater or equal to 
     * 0. Default value: 0
     */
    public final static String SERVER_SOCKET_RECONNECT
        = "es.tid.TIDorbj.iiop.server_socket_reconnect";
    public final static String DEFAULT_SERVER_SOCKET_RECONNECT
        = "0";
    
    /**
     * mcpg - IPV6 property. Values must be "true" or "false".
     */
    public final static String IPV6 
        = "es.tid.TIDorbj.iiop.ipv6";
    public final static String DEFAULT_IPV6 
        = "false";  
        
    /**
     * mcpg - IFACEo property. Only need if there are an comunication of client in TIDorbC.
     */
    public final static String IFACE
        = "es.tid.TIDorbj.iiop.iface";
    public final static String DEFAULT_IFACE 
        = "eth0";            
        
    
    public PropertyInfo reliableOneWay;
    public PropertyInfo fragmentSize;
    public PropertyInfo giopVersion;
    public PropertyInfo maxOpenedConnections;
    public PropertyInfo maxCommRecoveringTries;
    public PropertyInfo commRecoveringTime;
    public PropertyInfo maxUncompletedMessages;
    public PropertyInfo hostName;
    public PropertyInfo hostAddress;
    public PropertyInfo port;
    public PropertyInfo socketLinger;
    public PropertyInfo socketTimeout;
    public PropertyInfo socketNoDelay;
    public PropertyInfo connectTimeout;
    public PropertyInfo writeTimeout;
    public PropertyInfo serverSocketBacklog;
    public PropertyInfo serverSocketReconnect;

    public PropertyInfo ipv6;
    public PropertyInfo iface;
    
    public HashMap map;

    public IIOPCommunicationLayerPropertiesInfo() {

        map = new HashMap( 20 );
        
        /**
         * CONNECTION STUFF
         */
        reliableOneWay = new PropertyInfo( 
            RELIABLE_ONEWAY,
            DEFAULT_RELIABLE_ONEWAY
        );
        reliableOneWay.setDescription( 
            "Whether oneway request should be reliable or not." 
        );
        reliableOneWay.setRequired( false );
        reliableOneWay.setChoices ( null );
        map.put( RELIABLE_ONEWAY, reliableOneWay );
        

        /**
         * TRANSMISSION STUFF
         */
        fragmentSize = new PropertyInfo( 
            FRAGMENT_SIZE,
            DEFAULT_FRAGMENT_SIZE
        );
        fragmentSize.setDescription( 
            "Maximum fragment message size. Must be > 1024 and multiple of 8." 
        );
        fragmentSize.setRequired( false );
        fragmentSize.setChoices ( null );
        map.put( FRAGMENT_SIZE, fragmentSize );
        
        
        giopVersion = new PropertyInfo( 
            GIOP_VERSION,
            DEFAULT_GIOP_VERSION
        );
        giopVersion.setDescription( 
            "Default GIOP version used for communications. One of " +
            "1.0 1.1 or 1.2" 
        );
        giopVersion.setRequired( false );
        giopVersion.setChoices ( new String[]{ "1.0", "1.1", "1.2" } );
        map.put( GIOP_VERSION, giopVersion );
        
        
        maxOpenedConnections = new PropertyInfo( 
            MAX_OPENED_CONNECTIONS,
            DEFAULT_MAX_OPENED_CONNECTIONS
        );
        maxOpenedConnections.setDescription( 
            "ORB's maximum opened connections number"
        );
        maxOpenedConnections.setRequired( false );
        maxOpenedConnections.setChoices ( null );
        map.put( MAX_OPENED_CONNECTIONS, maxOpenedConnections );        
        
        
        maxCommRecoveringTries = new PropertyInfo( 
            MAX_COMM_RECOVERING_TRIES,
            DEFAULT_MAX_COMM_RECOVERING_TRIES
        );
        maxCommRecoveringTries.setDescription( 
            "DefaultORB's maximum communication recovering tries." 
        );
        maxCommRecoveringTries.setRequired( false );
        maxCommRecoveringTries.setChoices ( null );
        map.put( MAX_COMM_RECOVERING_TRIES, maxCommRecoveringTries );        

        
        commRecoveringTime = new PropertyInfo( 
            COMM_RECOVERING_TIME,
            DEFAULT_COMM_RECOVERING_TIME
        );
        commRecoveringTime.setDescription( 
            "ORB's maximum time upon recovering a communication." 
        );
        commRecoveringTime.setRequired( false );
        commRecoveringTime.setChoices ( null );
        map.put( COMM_RECOVERING_TIME, commRecoveringTime );        


        maxUncompletedMessages = new PropertyInfo( 
            MAX_UNCOMPLETED_MESSAGES,
            DEFAULT_MAX_UNCOMPLETED_MESSAGES
        );
        maxUncompletedMessages.setDescription( 
            "Maximum uncompleted messages allowed upon connections." 
        );
        maxUncompletedMessages.setRequired( false );
        maxUncompletedMessages.setChoices ( null );
        map.put( MAX_UNCOMPLETED_MESSAGES, maxUncompletedMessages );        


        hostName = new PropertyInfo( 
            HOST_NAME,
            DEFAULT_HOST_NAME
        );
        hostName.setDescription( 
            "Hostname used when generating IOR's and URL's." 
        );
        hostName.setRequired( false );
        hostName.setChoices ( null );
        map.put( HOST_NAME, hostName );        


        hostAddress = new PropertyInfo( 
            HOST_ADDRESS,
            DEFAULT_HOST_ADDRESS
        );
        hostAddress.setDescription( 
            "Address used when generating IOR's and URL's." 
        );
        hostAddress.setRequired( false );
        hostAddress.setChoices ( null );
        map.put( HOST_ADDRESS, hostAddress );        
        

        port = new PropertyInfo( 
            PORT,
            DEFAULT_PORT
        );
        port.setDescription( 
            "Port used when generating IOR's and URL's." 
        );
        port.setRequired( false );
        port.setChoices ( null );
        map.put( PORT, port );        


        socketLinger = new PropertyInfo( 
            SOCKET_LINGER,
            DEFAULT_SOCKET_LINGER
        );
        socketLinger.setDescription( 
            "Sockets SO_LIGER property." 
        );
        socketLinger.setRequired( false );
        socketLinger.setChoices ( null );
        map.put( SOCKET_LINGER, socketLinger );
        

        socketTimeout = new PropertyInfo( 
            SOCKET_TIMEOUT,
            DEFAULT_SOCKET_TIMEOUT
        );
        socketTimeout.setDescription( 
            "Sockets timeout." 
        );
        socketTimeout.setRequired( false );
        socketTimeout.setChoices ( null );
        map.put( SOCKET_TIMEOUT, socketTimeout );

        
        socketNoDelay = new PropertyInfo( 
            TCP_NODELAY,
            DEFAULT_TCP_NODELAY
        );
        socketNoDelay.setDescription( 
            "Sockets NO_DELAY property." 
        );
        socketNoDelay.setRequired( false );
        socketNoDelay.setChoices ( null );
        map.put( TCP_NODELAY, socketNoDelay );


        connectTimeout = new PropertyInfo( 
            SOCKET_CONNECT_TIMEOUT,
            DEFAULT_SOCKET_CONNECT_TIMEOUT
        );
        connectTimeout.setDescription( 
            "Sockets connect timeout." 
        );
        connectTimeout.setRequired( false );
        connectTimeout.setChoices ( null );
        map.put( SOCKET_CONNECT_TIMEOUT, connectTimeout );
        

        writeTimeout = new PropertyInfo( 
            SOCKET_WRITE_TIMEOUT,
            DEFAULT_SOCKET_WRITE_TIMEOUT
        );
        writeTimeout.setDescription( 
            "Sockets write timeout." 
        );
        writeTimeout.setRequired( false );
        writeTimeout.setChoices ( null );
        map.put( SOCKET_WRITE_TIMEOUT, writeTimeout );
        

        serverSocketBacklog = new PropertyInfo( 
            SERVER_SOCKET_BACKLOG,
            DEFAULT_SERVER_SOCKET_BACKLOG
        );
        serverSocketBacklog.setDescription( 
            "Server sockets BACKLOG." 
        );
        serverSocketBacklog.setRequired( false );
        serverSocketBacklog.setChoices ( null );
        map.put( SERVER_SOCKET_BACKLOG, serverSocketBacklog );


        serverSocketReconnect = new PropertyInfo( 
            SERVER_SOCKET_RECONNECT,
            DEFAULT_SERVER_SOCKET_RECONNECT
        );
        serverSocketReconnect.setDescription( 
            "Server sockets RECONNECT." 
        );
        serverSocketReconnect.setRequired( false );
        serverSocketReconnect.setChoices ( null );
        map.put( SERVER_SOCKET_RECONNECT, serverSocketReconnect );

        ipv6 = new PropertyInfo( 
            IPV6,
            DEFAULT_IPV6
        );
        ipv6.setDescription( 
            "IPV6 property." 
        );
        ipv6.setRequired( false );
        ipv6.setChoices ( null );
        map.put( IPV6, ipv6 );
        
        //mcpg
        iface = new PropertyInfo( 
            IFACE,
            DEFAULT_IFACE
        );
        iface.setDescription( 
            "IFACE property." 
        );
        iface.setRequired( false );
        iface.setChoices ( null );
        map.put( IFACE, iface );
        
    }
    
    public static IIOPCommunicationLayerPropertiesInfo getInstance(){
        return new IIOPCommunicationLayerPropertiesInfo();
    }

    public void dump(java.io.PrintWriter writer)
    {
        writer.println("IIOPCommunicationLayer properties:");

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            writer.print('\t');
            writer.print(e.getKey());
            writer.print('=');
            writer.print( ((PropertyInfo)e.getValue()).getValue());
            writer.println();
        }
    }
}
