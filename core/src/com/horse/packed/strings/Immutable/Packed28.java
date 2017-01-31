package com.horse.packed.strings.Immutable;

import com.horse.packed.strings.PackedBase;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Packed28 extends PackedBase {
    private final int f1;
    private final int f2;
    private final int f3;
    private final int f4;
    private final int f5;
    private final int f6;
    private final int f7;
    
    public Packed28(final byte[] ar) {
        f1 = get(ar, 3) | get(ar, 2) << 8 | get(ar, 1) << 16 | get(ar, 0) << 24;
        f2 = get(ar, 7) | get(ar, 6) << 8 | get(ar, 5) << 16 | get(ar, 4) << 24;
        f3 = get(ar, 11) | get(ar, 10) << 8 | get(ar, 9) << 16 | get(ar, 8) << 24;
        f4 = get(ar, 15) | get(ar, 14) << 8 | get(ar, 13) << 16 | get(ar, 12) << 24;
        f5 = get(ar, 19) | get(ar, 18) << 8 | get(ar, 17) << 16 | get(ar, 16) << 24;
        f6 = get(ar, 23) | get(ar, 22) << 8 | get(ar, 21) << 16 | get(ar, 20) << 24;
        f7 = get(ar, 27) | get(ar, 26) << 8 | get(ar, 25) << 16 | get(ar, 24) << 24;
    }
    
    protected ByteBuffer toByteBuffer() {
        final ByteBuffer bbuf = ByteBuffer.allocate(28);
        bbuf.putInt(f1);
        bbuf.putInt(f2);
        bbuf.putInt(f3);
        bbuf.putInt(f4);
        bbuf.putInt(f5);
        bbuf.putInt(f6);
        bbuf.putInt(f7);
        return bbuf;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(f1, f2, f3, f4, f5, f6, f7);
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Packed28 packed28 = (Packed28)(o);
        return f1 == packed28.f1 && f2 == packed28.f2 && f3 == packed28.f3 && f4 == packed28.f4 && f5 == packed28.f5 && f6 == packed28.f6 && f7 == packed28.f7;
    }
}
