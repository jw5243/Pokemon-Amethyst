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
import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.procedure.TIntObjectProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.set.TIntSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class TUnmodifiableIntObjectMap<V> implements TIntObjectMap<V>, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TIntObjectMap<V> m;
    private transient TIntSet       keySet = null;
    private transient Collection<V> values = null;
    
    public TUnmodifiableIntObjectMap(TIntObjectMap<V> m) {
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
    
    public int size() {
        return m.size();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public boolean containsKey(int key) {
        return m.containsKey(key);
    }
    
    public boolean containsValue(Object val) {
        return m.containsValue(val);
    }
    
    public V get(int key) {
        return m.get(key);
    }
    
    public V put(int key, V value) {
        throw new UnsupportedOperationException();
    }
    
    public V putIfAbsent(int key, V value) {
        throw new UnsupportedOperationException();
    }
    
    public V remove(int key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Integer, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TIntObjectMap<? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
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
    
    public Collection<V> valueCollection() {
        if(values == null) {
            values = Collections.unmodifiableCollection(m.valueCollection());
        }
        return values;
    }
    
    public Object[] values() {
        return m.values();
    }
    
    public V[] values(V[] array) {
        return m.values(array);
    }
    
    public TIntObjectIterator<V> iterator() {
        return new TIntObjectIterator<V>() {
            TIntObjectIterator<V> iter = m.iterator();
            
            public int key() {
                return iter.key();
            }
            
            public V value() {
                return iter.value();
            }
            
            public V setValue(V val) {
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
    
    public boolean forEachValue(TObjectProcedure<? super V> procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TIntObjectProcedure<? super V> procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TObjectFunction<V, V> function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TIntObjectProcedure<? super V> procedure) {
        throw new UnsupportedOperationException();
    }
}
