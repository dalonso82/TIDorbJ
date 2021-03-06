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
package es.tid.TIDorbj.core.iop;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.IOP.TAG_INTERNET_IOP;

import es.tid.TIDorbj.core.ConfORB;
import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.BufferCDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.ChunkCDR;
import es.tid.TIDorbj.core.comm.iiop.TargetAddress;
import es.tid.TIDorbj.util.Base16Codec;

/**
 * Representation of an Internet Object Reference (IOR). An IOR is compounded
 * by;
 * <ul>
 * <li>at least one <code>TaggedProfile</code>
 * <li>the repositoryId of the reference
 * </ul>
 * In addition, the ior has its stringified representation and its marshaled
 * value.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 2.0
 */

public abstract class IOR implements IDLEntity { /*extends org.omg.IOP.IOR*/


    private static IOR nullIOR;

    String toString;

    
    public java.lang.String type_id;
    public TaggedProfile[] profiles;


    int hash_code = -1;

    public IOR() {
        toString = null;
    }

    public IOR(String type_id, TaggedProfile[] profiles) {
        this.type_id = type_id;
        this.profiles = profiles;
        toString = null;
    }

    public static IOR nullIOR() {
        if ( nullIOR == null ) {
            nullIOR = new DefaultIOR("", new TaggedProfile[0]){
                
            };
        }
        return nullIOR;
    }
    
    public synchronized void addProfile(TaggedProfile profile)
    {
        if(this.profiles == null) {
            this.profiles = new TaggedProfile[1];
            this.profiles[0] = profile;
        } else {
            TaggedProfile[] tmp = new TaggedProfile[this.profiles.length + 1];
            System.arraycopy(this.profiles, 0, tmp, 0, this.profiles.length);
            tmp[this.profiles.length] = profile;
            this.profiles = tmp;
        }
        this.toString = null;
    }

    public synchronized void assign(IOR ior) {
        if ( ior != null ) {
            this.profiles = ior.profiles;
            this.type_id  = ior.type_id;
            this.toString = ior.toString;
        } else {
            this.profiles = null;
            this.type_id  = null;
            this.toString = null;
        }
    }

    public String getTypeId() {
        return this.type_id;
    }

    public int memberCount() {
        return ( this.profiles == null )?0:this.profiles.length;
    }

    public TaggedProfile getProfile(int index) {
        return (index < 0) || (index >= profiles.length)?
                null:profiles[index];
    }

    public boolean equivalent(IOR ior) {
        if ((profiles == null) || (ior.profiles == null))
            return false;

        if (!type_id.equals(ior.type_id))
            return false;

        IOR ior_max, ior_min;

        if (memberCount() > ior.memberCount()) {
            ior_max = this;
            ior_min = ior;
        } else {
            ior_max = ior;
            ior_min = this;
        }

        int size_min = ior_min.memberCount();
        int size_max = ior_max.memberCount();

        for (int i = 0; i < size_min; i++) {
            if (ior_min.profiles[i].tag == TAG_INTERNET_IOP.value) {
                for (int j = 0; j < size_max; j++) {
                    if (ior_max.profiles[j].tag == TAG_INTERNET_IOP.value) {
                        if ( ior_min.profiles[i].equal( ior_max.profiles[j] ) ){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String toString() {
        return toString(null);
    }
    
    public int hashCode() {
        if (hash_code == -1) {
            hash_code = toString().hashCode();
        }
        return hash_code;
    }

    public synchronized String toString(TIDORB orb) {
        if (toString == null) {
            CDROutputStream ior_output = null;

            if (orb == null) {
                ior_output = new CDROutputStream(
                    orb,
                    ConfORB.DEFAULT_BLOCK_SIZE
                );
            } else {
                ior_output = new CDROutputStream(orb, orb.m_conf.block_size);
            }

            ior_output.write_boolean(ior_output.getByteOrder());

            write(ior_output);

            // compound the buffer string

            BufferCDR cdr_buffer = ior_output.getBuffer();

            int available = cdr_buffer.getAvailable();

            char[] buffer = new char[(2 * available) + 4];

            int i = 0;
            buffer[i++] = 'I';
            buffer[i++] = 'O';
            buffer[i++] = 'R';
            buffer[i++] = ':';

            int num_chunks = cdr_buffer.getNumAvailableChunks();

            ChunkCDR cdr_chunk = null;

            int octet = 0;
            int chunk_available;
            byte[] ior_data;

            int chunk_no = 0;

            while (i < buffer.length) {
                cdr_chunk = cdr_buffer.getChunk(chunk_no++);
                chunk_available = cdr_chunk.getAvailable();
                ior_data = cdr_chunk.getBuffer();

                for (int j = 0; j < chunk_available; j++) {
                    octet = ior_data[j];
                    buffer[i++] = Base16Codec.toBase16[(octet & 0xf0) >> 4];
                    buffer[i++] = Base16Codec.toBase16[octet & 0xf];
                }
            }

            toString = new String(buffer);
        }

        return toString;
    }

    public void write(es.tid.TIDorbj.core.cdr.CDROutputStream out) {
        // write type_id
        out.write_string(type_id);

        // write profiles sequence

        out.write_ulong(profiles.length);

        for (int i = 0; i < profiles.length; i++) {
            ((TaggedProfile)profiles[i]).write(out);
        }
    }
    

    public void read(CDRInputStream input) {
        type_id = input.read_string();

        int size = input.read_ulong();

        if (size < 0) {
            throw new INV_OBJREF("Invalid Profile sequence length.", 0,
                                 CompletionStatus.COMPLETED_NO);
        }
        
        profiles = new TaggedProfile[size];

        int tag;

        for (int i = 0; i < profiles.length; i++) {
            profiles[i] = TaggedProfileReader.read(input);
        }
    }
    
    

    public static void skip(CDRInputStream input) {
        input.skipString();

        int size = input.read_ulong();

        if (size < 0)
            throw new INV_OBJREF("Invalid Profile sequence length.", 0,
                                 CompletionStatus.COMPLETED_NO);

        for (int i = 0; i < size; i++) {
            TaggedProfile.skip(input);
        }

    }

    public abstract String toURL();

    
    public abstract ObjectKey getObjectKey();

    public abstract TargetAddress toObjectKeyAddress();

    public abstract TargetAddress toProfileAddress();

    public abstract TargetAddress toIORAddress();
    
}