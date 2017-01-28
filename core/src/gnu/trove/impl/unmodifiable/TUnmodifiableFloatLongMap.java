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


import gnu.trove.TCollections;
import gnu.trove.TLongCollection;
import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TFloatLongIterator;
import gnu.trove.map.TFloatLongMap;
import gnu.trove.procedure.TFloatLongProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.set.TFloatSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableFloatLongMap implements TFloatLongMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TFloatLongMap m;
    private transient TFloatSet       keySet = null;
    private transient TLongCollection values = null;
    
    public TUnmodifiableFloatLongMap(TFloatLongMap m) {
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
    
    public float getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public long getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public long put(float key, long value) {
        throw new UnsupportedOperationException();
    }
    
    public long putIfAbsent(float key, long value) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Float, ? extends Long> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TFloatLongMap m) {
        throw new UnsupportedOperationException();
    }
    
    public long get(float key) {
        return m.get(key);
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public long remove(float key) {
        throw new UnsupportedOperationException();
    }
    
    public int size() {
        return m.size();
    }
    
    public TFloatSet keySet() {
        if(keySet == null) {
            keySet = TCollections.unmodifiableSet(m.keySet());
        }
        return keySet;
    }
    
    public float[] keys() {
        return m.keys();
    }
    
    public float[] keys(float[] array) {
        return m.keys(array);
    }
    
    public TLongCollection valueCollection() {
        if(values == null) {
            values = TCollections.unmodifiableCollection(m.valueCollection());
        }
        return values;
    }
    
    public long[] values() {
        return m.values();
    }
    
    public long[] values(long[] array) {
        return m.values(array);
    }
    
    public boolean containsValue(long val) {
        return m.containsValue(val);
    }
    
    public boolean containsKey(float key) {
        return m.containsKey(key);
    }
    
    public TFloatLongIterator iterator() {
        return new TFloatLongIterator() {
            TFloatLongIterator iter = m.iterator();
            
            public float key() {
                return iter.key();
            }
            
            public long value() {
                return iter.value();
            }
            
            public long setValue(long val) {
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
    
    public boolean forEachKey(TFloatProcedure procedure) {
        return m.forEachKey(procedure);
    }
    
    public boolean forEachValue(TLongProcedure procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TFloatLongProcedure procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TLongFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TFloatLongProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(float key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(float key, long amount) {
        throw new UnsupportedOperationException();
    }
    
    public long adjustOrPutValue(float key, long adjust_amount, long put_amount) {
        throw new UnsupportedOperationException();
    }
}
