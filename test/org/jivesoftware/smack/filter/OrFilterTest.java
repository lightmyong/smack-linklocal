/**
 * $RCSfile$
 * $Revision: 2136 $
 * $Date: 2003-10-13 08:40:11 +0800 (Mon, 13 Oct 2003) $
 *
 * Copyright (C) 2002-2003 Jive Software. All rights reserved.
 * ====================================================================
 * The Jive Software License (based on Apache Software License, Version 1.1)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by
 *        Jive Software (http://www.jivesoftware.com)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Smack" and "Jive Software" must not be used to
 *    endorse or promote products derived from this software without
 *    prior written permission. For written permission, please
 *    contact webmaster@jivesoftware.com.
 *
 * 5. Products derived from this software may not be called "Smack",
 *    nor may "Smack" appear in their name, without prior written
 *    permission of Jive Software.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL JIVE SOFTWARE OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jivesoftware.smack.filter;

import junit.framework.TestCase;
import org.jivesoftware.smack.packet.*;

/**
 * A test case for the OrFilter class.
 */
public class OrFilterTest extends TestCase {

    public void testNullArgs() {
        PacketFilter filter = null;
        try {
            OrFilter or = new OrFilter(filter, filter);
            fail("Should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testAccept() {
        MockPacketFilter trueFilter = new MockPacketFilter(true);
        MockPacketFilter falseFilter = new MockPacketFilter(false);

        MockPacket packet = new MockPacket();

        // Testing TT == T
        OrFilter orFilter = new OrFilter(trueFilter, trueFilter);
        assertTrue(orFilter.accept(packet));

        // Testing TF = F
        orFilter = new OrFilter(trueFilter, falseFilter);
        assertTrue(orFilter.accept(packet));

        // Testing FT = F
        orFilter = new OrFilter(falseFilter, trueFilter);
        assertTrue(orFilter.accept(packet));

        // Testing FF = F
        orFilter = new OrFilter(falseFilter, falseFilter);
        assertFalse(orFilter.accept(packet));

        // Testing TTTT = T
        orFilter = new OrFilter(
            new OrFilter(trueFilter, trueFilter), new OrFilter(trueFilter, trueFilter)
        );
        assertTrue(orFilter.accept(packet));

        // Testing TFTT = F
        orFilter = new OrFilter(
            new OrFilter(trueFilter, falseFilter), new OrFilter(trueFilter, trueFilter)
        );
        assertTrue(orFilter.accept(packet));

        // Testing TTFT = F
        orFilter = new OrFilter(
            new OrFilter(trueFilter, trueFilter), new OrFilter(falseFilter, trueFilter)
        );
        assertTrue(orFilter.accept(packet));

        // Testing TTTF = F
        orFilter = new OrFilter(
            new OrFilter(trueFilter, trueFilter), new OrFilter(trueFilter, falseFilter)
        );
        assertTrue(orFilter.accept(packet));

        // Testing FFFF = F
        orFilter = new OrFilter(
            new OrFilter(falseFilter, falseFilter), new OrFilter(falseFilter, falseFilter)
        );
        assertFalse(orFilter.accept(packet));

        orFilter = new OrFilter();
        orFilter.addFilter(trueFilter);
        orFilter.addFilter(trueFilter);
        orFilter.addFilter(falseFilter);
        orFilter.addFilter(trueFilter);
        assertTrue(orFilter.accept(packet));
    }
}
