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


import gnu.trove.TByteCollection;
import gnu.trove.function.TByteFunction;
import gnu.trove.iterator.TCharByteIterator;
import gnu.trove.map.TCharByteMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TCharByteProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.set.TCharSet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;


public class TSynchronizedCharByteMap implements TCharByteMap, Serializable {
    private static final long serialVersionUID = 1978198479659022715L;
    final         Object       mutex;    // Object on which to synchronize
    private final TCharByteMap m;     // Backing Map
    private transient TCharSet        keySet = null;
    private transient TByteCollection values = null;
    
    public TSynchronizedCharByteMap(TCharByteMap m) {
        if(m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        mutex = this;
    }
    
    public TSynchronizedCharByteMap(TCharByteMap m, Object mutex) {
        this.m = m;
        this.mutex = mutex;
    }
    
    // these are unchanging over the life of the map, no need to lock
    public char getNoEntryKey() {
        return m.getNoEntryKey();
    }
    
    public byte getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public byte put(char key, byte value) {
        synchronized(mutex) {
            return m.put(key, value);
        }
    }
    
    public byte putIfAbsent(char key, byte value) {
        synchronized(mutex) {
            return m.putIfAbsent(key, value);
        }
    }
    
    public void putAll(Map<? extends Character, ? extends Byte> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void putAll(TCharByteMap map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public byte get(char key) {
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
    
    public byte remove(char key) {
        synchronized(mutex) {
            return m.remove(key);
        }
    }
    
    public int size() {
        synchronized(mutex) {
            return m.size();
        }
    }
    
    public TCharSet keySet() {
        synchronized(mutex) {
            if(keySet == null) {
                keySet = new TSynchronizedCharSet(m.keySet(), mutex);
            }
            return keySet;
        }
    }
    
    public char[] keys() {
        synchronized(mutex) {
            return m.keys();
        }
    }
    
    public char[] keys(char[] array) {
        synchronized(mutex) {
            return m.keys(array);
        }
    }
    
    public TByteCollection valueCollection() {
        synchronized(mutex) {
            if(values == null) {
                values = new TSynchronizedByteCollection(m.valueCollection(), mutex);
            }
            return values;
        }
    }
    
    public byte[] values() {
        synchronized(mutex) {
            return m.values();
        }
    }
    
    public byte[] values(byte[] array) {
        synchronized(mutex) {
            return m.values(array);
        }
    }
    
    public boolean containsValue(byte value) {
        synchronized(mutex) {
            return m.containsValue(value);
        }
    }
    
    public boolean containsKey(char key) {
        synchronized(mutex) {
            return m.containsKey(key);
        }
    }
    
    public TCharByteIterator iterator() {
        return m.iterator(); // Must be manually synched by user!
    }
    
    public boolean forEachKey(TCharProcedure procedure) {
        synchronized(mutex) {
            return m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(TByteProcedure procedure) {
        synchronized(mutex) {
            return m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(TCharByteProcedure procedure) {
        synchronized(mutex) {
            return m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(TByteFunction function) {
        synchronized(mutex) {
            m.transformValues(function);
        }
    }
    
    public boolean retainEntries(TCharByteProcedure procedure) {
        synchronized(mutex) {
            return m.retainEntries(procedure);
        }
    }
    
    public boolean increment(char key) {
        synchronized(mutex) {
            return m.increment(key);
        }
    }
    
    public boolean adjustValue(char key, byte amount) {
        synchronized(mutex) {
            return m.adjustValue(key, amount);
        }
    }
    
    public byte adjustOrPutValue(char key, byte adjust_amount, byte put_amount) {
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
