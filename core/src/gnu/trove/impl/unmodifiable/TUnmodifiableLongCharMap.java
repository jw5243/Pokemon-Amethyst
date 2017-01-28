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


import gnu.trove.TCharCollection;
import gnu.trove.TCollections;
import gnu.trove.function.TCharFunction;
import gnu.trove.iterator.TLongCharIterator;
import gnu.trove.map.TLongCharMap;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TLongCharProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.set.TLongSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableLongCharMap implements TLongCharMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TLongCharMap m;
    private transient TLongSet        keySet = null;
    private transient TCharCollection values = null;
    
    public TUnmodifiableLongCharMap(TLongCharMap m) {
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
    
    public long getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public char getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public char put(long key, char value) {
        throw new UnsupportedOperationException();
    }
    
    public char putIfAbsent(long key, char value) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Long, ? extends Character> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TLongCharMap m) {
        throw new UnsupportedOperationException();
    }
    
    public char get(long key) {
        return m.get(key);
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public char remove(long key) {
        throw new UnsupportedOperationException();
    }
    
    public int size() {
        return m.size();
    }
    
    public TLongSet keySet() {
        if(keySet == null) {
            keySet = TCollections.unmodifiableSet(m.keySet());
        }
        return keySet;
    }
    
    public long[] keys() {
        return m.keys();
    }
    
    public long[] keys(long[] array) {
        return m.keys(array);
    }
    
    public TCharCollection valueCollection() {
        if(values == null) {
            values = TCollections.unmodifiableCollection(m.valueCollection());
        }
        return values;
    }
    
    public char[] values() {
        return m.values();
    }
    
    public char[] values(char[] array) {
        return m.values(array);
    }
    
    public boolean containsValue(char val) {
        return m.containsValue(val);
    }
    
    public boolean containsKey(long key) {
        return m.containsKey(key);
    }
    
    public TLongCharIterator iterator() {
        return new TLongCharIterator() {
            TLongCharIterator iter = m.iterator();
            
            public long key() {
                return iter.key();
            }
            
            public char value() {
                return iter.value();
            }
            
            public char setValue(char val) {
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
    
    public boolean forEachKey(TLongProcedure procedure) {
        return m.forEachKey(procedure);
    }
    
    public boolean forEachValue(TCharProcedure procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TLongCharProcedure procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TCharFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TLongCharProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(long key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(long key, char amount) {
        throw new UnsupportedOperationException();
    }
    
    public char adjustOrPutValue(long key, char adjust_amount, char put_amount) {
        throw new UnsupportedOperationException();
    }
}
