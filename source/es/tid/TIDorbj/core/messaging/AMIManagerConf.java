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
package es.tid.TIDorbj.core.messaging;

import org.omg.CORBA.BAD_PARAM;

/**
 * Configuration set for AMIManagerConf.
 * 
 * @version 1.0
 */
public class AMIManagerConf
{

    private int m_min_threads;

    private int m_max_threads;

    private int m_max_queued_requests;

    private int m_starving_time;

    private AMIManagerConfListener m_listener = null;

    protected AMIManagerConf(int min_threads, int max_threads,
                             int max_queued_requests, int starving_time)
    {
        this.m_min_threads = min_threads;
        this.m_max_threads = max_threads;
        this.m_max_queued_requests = max_queued_requests;
        this.m_starving_time = starving_time;
    }
   
    public void setListener(AMIManagerConfListener l)
    {
        m_listener = l;
    }

    synchronized public void setMinThreads(int min_threads)
    {
        if (min_threads < 0) {
            throw new BAD_PARAM("min_threads can not be negative.");
        }
        if (min_threads > this.m_max_threads) {
            throw new 
            BAD_PARAM("min_threads can not be greater than max_threads.");
        }
        this.m_min_threads = min_threads;
        if (m_listener != null) {
            m_listener.minThreadsHasChanged();
        }
    }

    synchronized public void setMaxThreads(int max_threads)
    {
        if (max_threads < this.m_min_threads) {
            throw new
            BAD_PARAM("max_threads can not be less than min_threads.");
        }
        this.m_max_threads = max_threads;
    }

    synchronized public void setMaxQueuedRequests(int max_queued_requests)
    {
        if (max_queued_requests < this.m_max_queued_requests) {
            throw new 
            BAD_PARAM("max_queued_requests can not be decreased.");
        }
        this.m_max_queued_requests = max_queued_requests;
    }

    synchronized public void setStarvingTime(int millisecs)
    {
        if (millisecs < 0) {
            throw new BAD_PARAM("starving_time can not be negative.");
        }
        this.m_starving_time = millisecs;
    }

    synchronized public int getMaxQueuedRequests()
    {
        return m_max_queued_requests;
    }

    synchronized public int getMaxThreads()
    {
        return m_max_threads;
    }

    synchronized public int getMinThreads()
    {
        return m_min_threads;
    }

    synchronized public int getStarvingTime()
    {
        return m_starving_time;
    }

}