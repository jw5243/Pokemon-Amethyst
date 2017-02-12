package com.horse.utility.optimizations.strings.Immutable;

import com.horse.utility.optimizations.strings.PackedBase;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Packed44 extends PackedBase {
    private final int f1;
    private final int f2;
    private final int f3;
    private final int f4;
    private final int f5;
    private final int f6;
    private final int f7;
    private final int f8;
    private final int f9;
    private final int f10;
    private final int f11;
    
    public Packed44(final byte[] ar) {
        f1 = get(ar, 3) | get(ar, 2) << 8 | get(ar, 1) << 16 | get(ar, 0) << 24;
        f2 = get(ar, 7) | get(ar, 6) << 8 | get(ar, 5) << 16 | get(ar, 4) << 24;
        f3 = get(ar, 11) | get(ar, 10) << 8 | get(ar, 9) << 16 | get(ar, 8) << 24;
        f4 = get(ar, 15) | get(ar, 14) << 8 | get(ar, 13) << 16 | get(ar, 12) << 24;
        f5 = get(ar, 19) | get(ar, 18) << 8 | get(ar, 17) << 16 | get(ar, 16) << 24;
        f6 = get(ar, 23) | get(ar, 22) << 8 | get(ar, 21) << 16 | get(ar, 20) << 24;
        f7 = get(ar, 27) | get(ar, 26) << 8 | get(ar, 25) << 16 | get(ar, 24) << 24;
        f8 = get(ar, 31) | get(ar, 30) << 8 | get(ar, 29) << 16 | get(ar, 28) << 24;
        f9 = get(ar, 35) | get(ar, 34) << 8 | get(ar, 33) << 16 | get(ar, 32) << 24;
        f10 = get(ar, 39) | get(ar, 38) << 8 | get(ar, 37) << 16 | get(ar, 36) << 24;
        f11 = get(ar, 43) | get(ar, 42) << 8 | get(ar, 41) << 16 | get(ar, 40) << 24;
    }
    
    protected ByteBuffer toByteBuffer() {
        final ByteBuffer bbuf = ByteBuffer.allocate(44);
        bbuf.putInt(f1);
        bbuf.putInt(f2);
        bbuf.putInt(f3);
        bbuf.putInt(f4);
        bbuf.putInt(f5);
        bbuf.putInt(f6);
        bbuf.putInt(f7);
        bbuf.putInt(f8);
        bbuf.putInt(f9);
        bbuf.putInt(f10);
        bbuf.putInt(f11);
        return bbuf;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11);
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Packed44 packed44 = (Packed44)(o);
        return f1 == packed44.f1 && f2 == packed44.f2 && f3 == packed44.f3 && f4 == packed44.f4 && f5 == packed44.f5 &&
               f6 == packed44.f6 && f7 == packed44.f7 && f8 == packed44.f8 && f9 == packed44.f9 &&
               f10 == packed44.f10 && f11 == packed44.f11;
    }
}
