package com.horse.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemoryFinder {
    private static final int LONG_SIZE    = 8;
    private static final int INT_SIZE     = 4;
    private static final int BYTE_SIZE    = 1;
    private static final int BOOLEAN_SIZE = 1;
    private static final int CHAR_SIZE    = 2;
    private static final int SHORT_SIZE   = 2;
    private static final int FLOAT_SIZE   = 4;
    private static final int DOUBLE_SIZE  = 8;
    private static final int ALIGNMENT    = 8;
    
    public static long sizeOf(Object o) {
        try {
            return sizeOf(o, new HashSet<>());
        } catch(IllegalAccessException e) {
            return 0;
        }
    }
    
    private static long sizeOf(Object o, Set<ObjectWrapper> visited) throws IllegalAccessException {
        if(o == null) {
            return 0;
        }
        int           referenceSize = 4;
        int           headerSize    = 8;
        ObjectWrapper objectWrapper = new ObjectWrapper(o);
        if(visited.contains(objectWrapper)) {
            // We have object reference graph with cycles
            return 0;
        }
        visited.add(objectWrapper);
        long  size  = headerSize;
        Class clazz = o.getClass();
        if(clazz.isArray()) {
            if(clazz == long[].class) {
                long[] objs = (long[])o;
                size += objs.length * LONG_SIZE;
            } else if(clazz == int[].class) {
                int[] objs = (int[])o;
                size += objs.length * INT_SIZE;
            } else if(clazz == byte[].class) {
                byte[] objs = (byte[])o;
                size += objs.length * BYTE_SIZE;
            } else if(clazz == boolean[].class) {
                boolean[] objs = (boolean[])o;
                size += objs.length * BOOLEAN_SIZE;
            } else if(clazz == char[].class) {
                char[] objs = (char[])o;
                size += objs.length * CHAR_SIZE;
            } else if(clazz == short[].class) {
                short[] objs = (short[])o;
                size += objs.length * SHORT_SIZE;
            } else if(clazz == float[].class) {
                float[] objs = (float[])o;
                size += objs.length * FLOAT_SIZE;
            } else if(clazz == double[].class) {
                double[] objs = (double[])o;
                size += objs.length * DOUBLE_SIZE;
            } else {
                Object[] objs = (Object[])o;
                for(Object obj : objs) {
                    size += sizeOf(obj, visited) + referenceSize;
                }
            }
            size += INT_SIZE;
        } else {
            List<Field> fields = new ArrayList<>();
            do {
                Field[] classFields = clazz.getDeclaredFields();
                for(Field field : classFields) {
                    if(!Modifier.isStatic(field.getModifiers())) {
                        fields.add(field);
                    }
                }
                clazz = clazz.getSuperclass();
            } while(clazz != null);
            for(Field field : fields) {
                if(!field.isAccessible()) {
                    field.setAccessible(true);
                }
                String fieldType = field.getGenericType().toString();
                if(fieldType.equals("long")) {
                    size += LONG_SIZE;
                } else if(fieldType.equals("int")) {
                    size += INT_SIZE;
                } else if(fieldType.equals("byte")) {
                    size += BYTE_SIZE;
                } else if(fieldType.equals("boolean")) {
                    size += BOOLEAN_SIZE;
                } else if(fieldType.equals("char")) {
                    size += CHAR_SIZE;
                } else if(fieldType.equals("short")) {
                    size += SHORT_SIZE;
                } else if(fieldType.equals("float")) {
                    size += FLOAT_SIZE;
                } else if(fieldType.equals("double")) {
                    size += DOUBLE_SIZE;
                } else {
                    size += sizeOf(field.get(o), visited) + referenceSize;
                }
            }
        }
        if((size % ALIGNMENT) != 0) {
            size = ALIGNMENT * (size / ALIGNMENT + 1);
        }
        return size;
    }
    
    private static final class ObjectWrapper {
        
        private final Object object;
        
        public ObjectWrapper(Object object) {
            this.object = object;
        }
        
        @Override
        public int hashCode() {
            return 141 + System.identityHashCode(object);
        }
        
        @Override
        public boolean equals(Object obj) {
            return obj == this || !(obj == null || obj.getClass() != ObjectWrapper.class) && object == ((ObjectWrapper)obj).object;
        }
    }
}