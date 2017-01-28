///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2008, Robert D. Eden All Rights Reserved.
// Copyright (c) 2009, Jeff Randall All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////

package gnu.trove.impl.sync;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

////////////////////////////////////////////////////////////
// THIS IS AN IMPLEMENTATION CLASS. DO NOT USE DIRECTLY!  //
// Access to these methods should be through TCollections //
////////////////////////////////////////////////////////////


import gnu.trove.set.TByteSet;


public class TSynchronizedByteSet extends TSynchronizedByteCollection implements TByteSet {
    
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedByteSet(TByteSet s) {
        super(s);
    }
    
    public TSynchronizedByteSet(TByteSet s, Object mutex) {
        super(s, mutex);
    }
    
    public int hashCode() {
        synchronized(mutex) {
            return c.hashCode();
        }
    }
    
    public boolean equals(Object o) {
        synchronized(mutex) {
            return c.equals(o);
        }
    }
}
