package PackedStrings;

import java.nio.ByteBuffer;

public abstract class PackedBase {
    protected int get(final byte[] ar, final int index) {
        return index < ar.length ? ar[index] : 0;
    }
    
    protected abstract ByteBuffer toByteBuffer();
    
    protected String toString(final ByteBuffer bbuf) {
        final byte[] ar = bbuf.array();
        
        int last = ar.length - 1;
        while(last > 0 && ar[last] == 0) {
            --last;
        }
        return new String(ar, 0, last + 1, PackedString.getUsAscii());
    }
    
    public String toString() {
        return toString(toByteBuffer());
    }
}
