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
import gnu.trove.TShortCollection;
import gnu.trove.function.TShortFunction;
import gnu.trove.iterator.TShortShortIterator;
import gnu.trove.map.TShortShortMap;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TShortShortProcedure;
import gnu.trove.set.TShortSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableShortShortMap implements TShortShortMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TShortShortMap m;
    private transient TShortSet        keySet = null;
    private transient TShortCollection values = null;
    
    public TUnmodifiableShortShortMap(TShortShortMap m) {
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
    
    public short getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public short getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public short put(short key, short value) {
        throw new UnsupportedOperationException();
    }
    
    public short putIfAbsent(short key, short value) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Short, ? extends Short> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TShortShortMap m) {
        throw new UnsupportedOperationException();
    }
    
    public short get(short key) {
        return m.get(key);
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public short remove(short key) {
        throw new UnsupportedOperationException();
    }
    
    public int size() {
        return m.size();
    }
    
    public TShortSet keySet() {
        if(keySet == null) {
            keySet = TCollections.unmodifiableSet(m.keySet());
        }
        return keySet;
    }
    
    public short[] keys() {
        return m.keys();
    }
    
    public short[] keys(short[] array) {
        return m.keys(array);
    }
    
    public TShortCollection valueCollection() {
        if(values == null) {
            values = TCollections.unmodifiableCollection(m.valueCollection());
        }
        return values;
    }
    
    public short[] values() {
        return m.values();
    }
    
    public short[] values(short[] array) {
        return m.values(array);
    }
    
    public boolean containsValue(short val) {
        return m.containsValue(val);
    }
    
    public boolean containsKey(short key) {
        return m.containsKey(key);
    }
    
    public TShortShortIterator iterator() {
        return new TShortShortIterator() {
            TShortShortIterator iter = m.iterator();
            
            public short key() {
                return iter.key();
            }
            
            public short value() {
                return iter.value();
            }
            
            public short setValue(short val) {
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
    
    public boolean forEachKey(TShortProcedure procedure) {
        return m.forEachKey(procedure);
    }
    
    public boolean forEachValue(TShortProcedure procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TShortShortProcedure procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TShortFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TShortShortProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(short key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(short key, short amount) {
        throw new UnsupportedOperationException();
    }
    
    public short adjustOrPutValue(short key, short adjust_amount, short put_amount) {
        throw new UnsupportedOperationException();
    }
}
