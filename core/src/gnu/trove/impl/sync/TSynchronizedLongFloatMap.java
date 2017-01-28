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


import gnu.trove.TFloatCollection;
import gnu.trove.function.TFloatFunction;
import gnu.trove.iterator.TLongFloatIterator;
import gnu.trove.map.TLongFloatMap;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TLongFloatProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.set.TLongSet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;


public class TSynchronizedLongFloatMap implements TLongFloatMap, Serializable {
    private static final long serialVersionUID = 1978198479659022715L;
    final         Object        mutex;    // Object on which to synchronize
    private final TLongFloatMap m;     // Backing Map
    private transient TLongSet         keySet = null;
    private transient TFloatCollection values = null;
    
    public TSynchronizedLongFloatMap(TLongFloatMap m) {
        if(m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        mutex = this;
    }
    
    public TSynchronizedLongFloatMap(TLongFloatMap m, Object mutex) {
        this.m = m;
        this.mutex = mutex;
    }
    
    // these are unchanging over the life of the map, no need to lock
    public long getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public float getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public float put(long key, float value) {
        synchronized(mutex) {
            return m.put(key, value);
        }
    }
    
    public float putIfAbsent(long key, float value) {
        synchronized(mutex) {
            return m.putIfAbsent(key, value);
        }
    }
    
    public void putAll(Map<? extends Long, ? extends Float> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void putAll(TLongFloatMap map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public float get(long key) {
        synchronized(mutex) {
            return m.get(key);
        }
    }
    
    public void clear() {
        synchronized(mutex) {
            m.clear();
        }
    }
    
    public boolean isEmpty() {
        synchronized(mutex) {
            return m.isEmpty();
        }
    }
    
    public float remove(long key) {
        synchronized(mutex) {
            return m.remove(key);
        }
    }
    
    public int size() {
        synchronized(mutex) {
            return m.size();
        }
    }
    
    public TLongSet keySet() {
        synchronized(mutex) {
            if(keySet == null) {
                keySet = new TSynchronizedLongSet(m.keySet(), mutex);
            }
            return keySet;
        }
    }
    
    public long[] keys() {
        synchronized(mutex) {
            return m.keys();
        }
    }
    
    public long[] keys(long[] array) {
        synchronized(mutex) {
            return m.keys(array);
        }
    }
    
    public TFloatCollection valueCollection() {
        synchronized(mutex) {
            if(values == null) {
                values = new TSynchronizedFloatCollection(m.valueCollection(), mutex);
            }
            return values;
        }
    }
    
    public float[] values() {
        synchronized(mutex) {
            return m.values();
        }
    }
    
    public float[] values(float[] array) {
        synchronized(mutex) {
            return m.values(array);
        }
    }
    
    public boolean containsValue(float value) {
        synchronized(mutex) {
            return m.containsValue(value);
        }
    }
    
    public boolean containsKey(long key) {
        synchronized(mutex) {
            return m.containsKey(key);
        }
    }
    
    public TLongFloatIterator iterator() {
        return m.iterator(); // Must be manually synched by user!
    }
    
    public boolean forEachKey(TLongProcedure procedure) {
        synchronized(mutex) {
            return m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(TFloatProcedure procedure) {
        synchronized(mutex) {
            return m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(TLongFloatProcedure procedure) {
        synchronized(mutex) {
            return m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(TFloatFunction function) {
        synchronized(mutex) {
            m.transformValues(function);
        }
    }
    
    public boolean retainEntries(TLongFloatProcedure procedure) {
        synchronized(mutex) {
            return m.retainEntries(procedure);
        }
    }
    
    public boolean increment(long key) {
        synchronized(mutex) {
            return m.increment(key);
        }
    }
    
    public boolean adjustValue(long key, float amount) {
        synchronized(mutex) {
            return m.adjustValue(key, amount);
        }
    }
    
    public float adjustOrPutValue(long key, float adjust_amount, float put_amount) {
        synchronized(mutex) {
            return m.adjustOrPutValue(key, adjust_amount, put_amount);
        }
    }
    
    public int hashCode() {
        synchronized(mutex) {
            return m.hashCode();
        }
    }
    
    public boolean equals(Object o) {
        synchronized(mutex) {
            return m.equals(o);
        }
    }
    
    public String toString() {
        synchronized(mutex) {
            return m.toString();
        }
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        synchronized(mutex) {
            s.defaultWriteObject();
        }
    }
}
