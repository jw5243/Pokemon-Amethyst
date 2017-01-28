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
import gnu.trove.iterator.TIntCharIterator;
import gnu.trove.map.TIntCharMap;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TIntCharProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.TIntSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableIntCharMap implements TIntCharMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TIntCharMap m;
    private transient TIntSet         keySet = null;
    private transient TCharCollection values = null;
    
    public TUnmodifiableIntCharMap(TIntCharMap m) {
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
    
    public char getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public char put(int key, char value) {
        throw new UnsupportedOperationException();
    }
    
    public char putIfAbsent(int key, char value) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Integer, ? extends Character> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TIntCharMap m) {
        throw new UnsupportedOperationException();
    }
    
    public char get(int key) {
        return m.get(key);
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public char remove(int key) {
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
    
    public boolean containsKey(int key) {
        return m.containsKey(key);
    }
    
    public TIntCharIterator iterator() {
        return new TIntCharIterator() {
            TIntCharIterator iter = m.iterator();
            
            public int key() {
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
    
    public boolean forEachKey(TIntProcedure procedure) {
        return m.forEachKey(procedure);
    }
    
    public boolean forEachValue(TCharProcedure procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TIntCharProcedure procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TCharFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TIntCharProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(int key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(int key, char amount) {
        throw new UnsupportedOperationException();
    }
    
    public char adjustOrPutValue(int key, char adjust_amount, char put_amount) {
        throw new UnsupportedOperationException();
    }
}
