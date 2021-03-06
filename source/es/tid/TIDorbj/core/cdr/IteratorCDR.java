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
package es.tid.TIDorbj.core.cdr;

import org.omg.CORBA.CompletionStatus;

/**
 * PositionCDR class gets track of byte array covering for marshaling and
 * unmarshaling.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

class IteratorCDR
{

    /**
     * Byte order of data in stream: big-endian if <code>true</code>, or
     * little-endian if <code>false</code>
     */
    protected boolean m_byte_order;

    /**
     * The buffer controled by the buffer;
     */

    BufferCDR m_buffer_cdr;

    /**
     * Actual Chunk Number.
     */
    protected int m_current_chunk_num;

    /**
     * The chunk covered
     */
    protected ChunkCDR m_chunk;

    /**
     * The actual position in the chunk
     */
    protected int m_position;

    /**
     * It is in an encapsulation.
     */
    protected boolean m_encapsulation;

    /**
     * Actual stream aligment offset.
     */

    protected AlignmentOffset m_alignment_offset;

    /**
     * Last alignment calculated.
     */

    protected int m_last_alignment;

    /**
     * Encapsulation context list.
     */

    protected ContextCDR m_context;

    /**
     * Last mark for reset
     */
    protected MarkCDR m_last_reset_mark;

    /**
     * Starting mark for rewind
     */
    protected MarkCDR m_starting_mark;

    // Constructor

    // Forbides the illegal use of a unitialized iterator.

    private IteratorCDR()
    {}

    /**
     * Constructor: gets a byte array referencen an places its position to 0
     */
    public IteratorCDR(BufferCDR buffer)
    {
        // Iterator in the buffer with root context
        // generate the root position with root context an initial pointer,
        m_buffer_cdr = buffer;
        m_current_chunk_num = 0;
        m_position = 0;

        PointerCDR initial_pointer = new PointerCDR(m_buffer_cdr,
                                                    m_current_chunk_num,
                                                    m_position);

        m_context = new ContextCDR(initial_pointer);

        // sets the iterator state ready to start at the beginnig of buffer.
        fixStarting();

        rewind();
    }

    /**
     * Gets a byte array referencen an places its to the initial position.
     */

    public IteratorCDR(ContextCDR initial_context)
    {
        // Initial position values:

        m_context = initial_context;

        // sets the iterator state ready to start at the beginnig of buffer.
        fixStarting();

        rewind();
    }

    /**
     * Sets the iterator possition to the starting position.
     */

    public void rewind()
    {
        goBack(m_starting_mark);
    }

    protected MarkCDR getMark()
    {
        return new MarkCDR(m_context, getPointer());
    }

    protected void goBack(MarkCDR mark)
    {
        // context values
        m_context = mark.getContext();

        m_byte_order = m_context.getByteOrder();
        m_encapsulation = m_context.inAnEncapsulation();
        m_alignment_offset = m_context.getOffset();
        m_last_alignment = CDR.OCTET_SIZE;

        // pointer values;
        PointerCDR initial_pointer = mark.getPointer();

        m_current_chunk_num = initial_pointer.getNumChunk();
        m_buffer_cdr = initial_pointer.getBuffer();
        m_position = initial_pointer.getPosition();

        m_chunk = m_buffer_cdr.getChunk(m_current_chunk_num);

        m_last_reset_mark = null;
    }

    public void reset()
        throws java.io.IOException
    {
        // state checking
        if (m_last_reset_mark == null)
            throw new java.io.IOException("Stream has no been marked");

        goBack(m_last_reset_mark);
    }

    public IteratorCDR copy()
    {

        IteratorCDR new_iterator = new IteratorCDR();

        new_iterator.m_context = m_context;
        new_iterator.m_byte_order = m_context.getByteOrder();
        new_iterator.m_encapsulation = m_context.inAnEncapsulation();
        new_iterator.m_alignment_offset = m_context.getOffset();

        // marks

        new_iterator.m_starting_mark = m_starting_mark;
        new_iterator.m_last_reset_mark = null;

        new_iterator.m_current_chunk_num = m_current_chunk_num;
        new_iterator.m_buffer_cdr = m_buffer_cdr;
        new_iterator.m_position = m_position;

        new_iterator.m_chunk = m_chunk;

        return new_iterator;
    }

    /**
     * Returns the buffer CDR.
     */
    public BufferCDR getBuffer()
    {

        return m_buffer_cdr;
    }

    /**
     * @return the chunk pointered.
     */
    public ChunkCDR getChunk()
    {

        return m_chunk;
    }

    /**
     * @return <code>true</code> if little-endian or <code>false</code> if
     *         big-endian
     */

    public boolean getByteOrder()
    {
        return m_byte_order;
    }

    /**
     * Sets the byte order.
     * 
     * @param byte_order
     *            is <code>true</code> if little-endian or <code>false</code>
     *            if big-endian
     */

    public void setByteOrder(boolean byte_order)
    {
        this.m_byte_order = byte_order;
        m_context.setByteOrder(byte_order);
    }

    /**
     * @return the remaining bytes of the current chunk.
     */

    public int available()
    {
        int av = m_chunk.getAvailable() - m_position;
        if (av < 0)
            return 0;
        else
            return av;
    }

    /**
     * @return the remaining bytes of the buffer.
     */

    public int bufferAvailable()
    {
        int av = available();
        int num_chunks = m_buffer_cdr.getNumAvailableChunks();

        for (int i = num_chunks - 1; i > m_current_chunk_num; i--)
            av = m_buffer_cdr.getChunk(i).getAvailable();

        return av;
    }

    /**
     * @return a pointer to actual position in the buffer,
     */

    PointerCDR getPointer()
    {
        return new PointerCDR(m_buffer_cdr, m_current_chunk_num, m_position);
    }

    public void fixStarting()
    {
        m_starting_mark = getMark();
    }

    public void mark()
    {
        m_last_reset_mark = getMark();
    }

    /**
     * Changes the alignment offset for a new Encapsulation, taking the actual
     * position as the 0 positon of the encapsulation. Also, saves the actual
     * aligment offset that will be restored when the
     * <code> exitEncapsulation</code> were called.
     */

    public void enterEncapsulation()
    {
        ContextCDR new_context;

        m_encapsulation = true;
        AlignmentOffset new_alignment_offset;

        new_alignment_offset = AlignmentOffset.calculateOffsetFrom(m_position);

        new_context = new ContextCDR(getPointer(), m_context,
                                     new_alignment_offset);
        m_context = new_context;
        m_alignment_offset = new_alignment_offset;
    }

    public void setAlignmentOffset(int relative_position)
    {

        m_alignment_offset = 
            AlignmentOffset.calculateOffsetFrom(relative_position);

        m_context.setOffset(m_alignment_offset);

    }

    /**
     * Restores the alignment offset for the Encapsulation containing the actual
     * Encapsulation that it has been exited.
     */

    public void exitEncapsulation()
    {
        m_context = m_context.getFather();

        m_byte_order = m_context.getByteOrder();
        m_alignment_offset = m_context.getOffset();
        m_encapsulation = m_context.inAnEncapsulation();
    }

    /**
     * Says if it is in a encapsulation or in the main stream.
     * 
     * @return <code>true</code> if it is in an encapsulation or
     *         <code>false</code> otherwise
     */
    public boolean inAnEncapsulation()
    {
        return m_encapsulation;
    }

    public void nextChunk()
        throws org.omg.CORBA.MARSHAL
    {
        m_current_chunk_num++;

        if (m_current_chunk_num >= m_buffer_cdr.getNumChunks())
            throw new org.omg.CORBA.MARSHAL("END OF BUFFER", 0,
                                            CompletionStatus.COMPLETED_NO);

        m_chunk = m_buffer_cdr.getChunk(m_current_chunk_num);
        m_position = 0;
    }

    /**
     * Sets the covering position. It assumes if the new position is less than
     * actual, the position is in a father encapsulation, of if greater is in
     * the same encapsulation.
     * 
     * @return <code>true</code> if OK, or <code>false</code> if it is a bad
     *         position.
     */

    public boolean setPosition(AbsolutePosition new_position)
    {
        // search the context

        int n = new_position.getValue();
        while (n < m_context.getStartPointer().getAbsolutePosition().getValue())
            exitEncapsulation();

        // sets the chunk and position

        PointerCDR pointer = m_buffer_cdr.getPointer(n);

        if (pointer != null) {
            m_current_chunk_num = pointer.getNumChunk();
            m_position = pointer.getPosition();
            return true;
        }
        // error bad position
        return false;
    }

    public void skip(int n)
    {
        m_position += n;
    }

    /**
     * Calculates the aligned position for the given type size. The aligment is
     * refered at the actual encapsulation beginning.
     * 
     * @param type_size
     *            the data type size.
     */
    protected int alignPosition(int type_size)
    {
        int gap, rest;
        rest = (m_position + m_alignment_offset.m_value) % type_size;
        gap = (rest == 0) ? 0 : (type_size - rest);
        return (m_position + gap);
    }

    /**
     * Says if the data must be aligned.
     * 
     * @param type_size
     *            the data type size
     */

    public boolean mustAlign(int type_size)
    {
        return (m_position != alignPosition(type_size));
    }

    /**
     * Changes aligns the position for the given type size.The aligment is
     * refered at the actual encapsulation beginning.
     * 
     * @param type_size
     *            the data type size.
     */

    public void align(int type_size)
    {
        m_position = alignPosition(type_size);
    }

    /**
     * Says if there is enougth space in the actual chunk for writting data from
     * a given size.
     * 
     * @param type_size the data type size.
     * @return <code>true<\code> if there is enougth space or <code>false<\code> 
     * otherwise.
     */

    public boolean enoughSpace(int type_size)
    {
        return ((m_chunk.m_buffer.length - alignPosition(type_size)) 
            	>= type_size);
    }

}