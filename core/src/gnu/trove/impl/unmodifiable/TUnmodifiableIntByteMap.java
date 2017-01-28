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

package gnu.trove.impl.unmodifiable;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

////////////////////////////////////////////////////////////
// THIS IS AN IMPLEMENTATION CLASS. DO NOT USE DIRECTLY!  //
// Access to these methods should be through TCollections //
////////////////////////////////////////////////////////////


import gnu.trove.TByteCollection;
import gnu.trove.TCollections;
import gnu.trove.function.TByteFunction;
import gnu.trove.iterator.TIntByteIterator;
import gnu.trove.map.TIntByteMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TIntByteProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.TIntSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableIntByteMap implements TIntByteMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TIntByteMap m;
    private transient TIntSet         keySet = null;
    private transient TByteCollection values = null;
    
    public TUnmodifiableIntByteMap(TIntByteMap m) {
        if(m == null) {
            throw new NullPointerException();
        }
        this.m = m;
    }
    
    public int hashCode() {
        return m.hashCode();
    }
    
    public boolean equals(Object o) {
        return o == this || m.equals(o);
    }
    
    public String toString() {
        return m.toString();
    }
    
    public int getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public byte getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public byte put(int key, byte value) {
        throw new UnsupportedOperationException();
    }
    
    public byte putIfAbsent(int key, byte value) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Integer, ? extends Byte> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TIntByteMap m) {
        throw new UnsupportedOperationException();
    }
    
    public byte get(int key) {
        return m.get(key);
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public byte remove(int key) {
        throw new UnsupportedOperationException();
    }
    
    public int size() {
        return m.size();
    }
    
    public TIntSet keySet() {
        if(keySet == null) {
            keySet = TCollections.unmodifiableSet(m.keySet());
        }
        return keySet;
    }
    
    public int[] keys() {
        return m.keys();
    }
    
    public int[] keys(int[] array) {
        return m.keys(array);
    }
    
    public TByteCollection valueCollection() {
        if(values == null) {
            values = TCollections.unmodifiableCollection(m.valueCollection());
        }
        return values;
    }
    
    public byte[] values() {
        return m.values();
    }
    
    public byte[] values(byte[] array) {
        return m.values(array);
    }
    
    public boolean containsValue(byte val) {
        return m.containsValue(val);
    }
    
    public boolean containsKey(int key) {
        return m.containsKey(key);
    }
    
    public TIntByteIterator iterator() {
        return new TIntByteIterator() {
            TIntByteIterator iter = m.iterator();
            
            public int key() {
                return iter.key();
            }
            
            public byte value() {
                return iter.value();
            }
            
            public byte setValue(byte val) {
                throw new UnsupportedOperationException();
            }
            
            public void advance() {
                iter.advance();
            }
            
            public boolean hasNext() {
                return iter.hasNext();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean forEachKey(TIntProcedure procedure) {
        return m.forEachKey(procedure);
    }
    
    public boolean forEachValue(TByteProcedure procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TIntByteProcedure procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TByteFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TIntByteProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(int key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(int key, byte amount) {
        throw new UnsupportedOperationException();
    }
    
    public byte adjustOrPutValue(int key, byte adjust_amount, byte put_amount) {
        throw new UnsupportedOperationException();
    }
}
