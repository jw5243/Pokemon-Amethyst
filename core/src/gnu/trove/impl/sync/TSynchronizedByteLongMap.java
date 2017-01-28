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


import gnu.trove.TLongCollection;
import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TByteLongIterator;
import gnu.trove.map.TByteLongMap;
import gnu.trove.procedure.TByteLongProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.set.TByteSet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;


public class TSynchronizedByteLongMap implements TByteLongMap, Serializable {
    private static final long serialVersionUID = 1978198479659022715L;
    final         Object       mutex;    // Object on which to synchronize
    private final TByteLongMap m;     // Backing Map
    private transient TByteSet        keySet = null;
    private transient TLongCollection values = null;
    
    public TSynchronizedByteLongMap(TByteLongMap m) {
        if(m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        mutex = this;
    }
    
    public TSynchronizedByteLongMap(TByteLongMap m, Object mutex) {
        this.m = m;
        this.mutex = mutex;
    }
    
    // these are unchanging over the life of the map, no need to lock
    public byte getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public long getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public long put(byte key, long value) {
        synchronized(mutex) {
            return m.put(key, value);
        }
    }
    
    public long putIfAbsent(byte key, long value) {
        synchronized(mutex) {
            return m.putIfAbsent(key, value);
        }
    }
    
    public void putAll(Map<? extends Byte, ? extends Long> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void putAll(TByteLongMap map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public long get(byte key) {
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
    
    public long remove(byte key) {
        synchronized(mutex) {
            return m.remove(key);
        }
    }
    
    public int size() {
        synchronized(mutex) {
            return m.size();
        }
    }
    
    public TByteSet keySet() {
        synchronized(mutex) {
            if(keySet == null) {
                keySet = new TSynchronizedByteSet(m.keySet(), mutex);
            }
            return keySet;
        }
    }
    
    public byte[] keys() {
        synchronized(mutex) {
            return m.keys();
        }
    }
    
    public byte[] keys(byte[] array) {
        synchronized(mutex) {
            return m.keys(array);
        }
    }
    
    public TLongCollection valueCollection() {
        synchronized(mutex) {
            if(values == null) {
                values = new TSynchronizedLongCollection(m.valueCollection(), mutex);
            }
            return values;
        }
    }
    
    public long[] values() {
        synchronized(mutex) {
            return m.values();
        }
    }
    
    public long[] values(long[] array) {
        synchronized(mutex) {
            return m.values(array);
        }
    }
    
    public boolean containsValue(long value) {
        synchronized(mutex) {
            return m.containsValue(value);
        }
    }
    
    public boolean containsKey(byte key) {
        synchronized(mutex) {
            return m.containsKey(key);
        }
    }
    
    public TByteLongIterator iterator() {
        return m.iterator(); // Must be manually synched by user!
    }
    
    public boolean forEachKey(TByteProcedure procedure) {
        synchronized(mutex) {
            return m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(TLongProcedure procedure) {
        synchronized(mutex) {
            return m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(TByteLongProcedure procedure) {
        synchronized(mutex) {
            return m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(TLongFunction function) {
        synchronized(mutex) {
            m.transformValues(function);
        }
    }
    
    public boolean retainEntries(TByteLongProcedure procedure) {
        synchronized(mutex) {
            return m.retainEntries(procedure);
        }
    }
    
    public boolean increment(byte key) {
        synchronized(mutex) {
            return m.increment(key);
        }
    }
    
    public boolean adjustValue(byte key, long amount) {
        synchronized(mutex) {
            return m.adjustValue(key, amount);
        }
    }
    
    public long adjustOrPutValue(byte key, long adjust_amount, long put_amount) {
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
