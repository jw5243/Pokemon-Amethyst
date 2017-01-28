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
import gnu.trove.TDoubleCollection;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.iterator.TByteDoubleIterator;
import gnu.trove.map.TByteDoubleMap;
import gnu.trove.procedure.TByteDoubleProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.set.TByteSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableByteDoubleMap implements TByteDoubleMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    
    private final TByteDoubleMap m;
    private transient TByteSet          keySet = null;
    private transient TDoubleCollection values = null;
    
    public TUnmodifiableByteDoubleMap(TByteDoubleMap m) {
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
    
    public byte getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public double getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public double put(byte key, double value) {
        throw new UnsupportedOperationException();
    }
    
    public double putIfAbsent(byte key, double value) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(Map<? extends Byte, ? extends Double> map) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(TByteDoubleMap m) {
        throw new UnsupportedOperationException();
    }
    
    public double get(byte key) {
        return m.get(key);
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return m.isEmpty();
    }
    
    public double remove(byte key) {
        throw new UnsupportedOperationException();
    }
    
    public int size() {
        return m.size();
    }
    
    public TByteSet keySet() {
        if(keySet == null) {
            keySet = TCollections.unmodifiableSet(m.keySet());
        }
        return keySet;
    }
    
    public byte[] keys() {
        return m.keys();
    }
    
    public byte[] keys(byte[] array) {
        return m.keys(array);
    }
    
    public TDoubleCollection valueCollection() {
        if(values == null) {
            values = TCollections.unmodifiableCollection(m.valueCollection());
        }
        return values;
    }
    
    public double[] values() {
        return m.values();
    }
    
    public double[] values(double[] array) {
        return m.values(array);
    }
    
    public boolean containsValue(double val) {
        return m.containsValue(val);
    }
    
    public boolean containsKey(byte key) {
        return m.containsKey(key);
    }
    
    public TByteDoubleIterator iterator() {
        return new TByteDoubleIterator() {
            TByteDoubleIterator iter = m.iterator();
            
            public byte key() {
                return iter.key();
            }
            
            public double value() {
                return iter.value();
            }
            
            public double setValue(double val) {
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
    
    public boolean forEachKey(TByteProcedure procedure) {
        return m.forEachKey(procedure);
    }
    
    public boolean forEachValue(TDoubleProcedure procedure) {
        return m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(TByteDoubleProcedure procedure) {
        return m.forEachEntry(procedure);
    }
    
    public void transformValues(TDoubleFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(TByteDoubleProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(byte key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(byte key, double amount) {
        throw new UnsupportedOperationException();
    }
    
    public double adjustOrPutValue(byte key, double adjust_amount, double put_amount) {
        throw new UnsupportedOperationException();
    }
}
