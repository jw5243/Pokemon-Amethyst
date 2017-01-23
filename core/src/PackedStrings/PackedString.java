package PackedStrings;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class PackedString {
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    
    public static Charset getUsAscii() {
        return US_ASCII;
    }
    
    public static Object convert(final String str) {
        if(str == null || str.length() == 0 || str.length() > 52 || str.indexOf('\0') != -1) {
            return str;
        } else {
            final CharsetEncoder enc        = US_ASCII.newEncoder();
            final CharBuffer     charBuffer = CharBuffer.wrap(str);
            try {
                final ByteBuffer byteBuffer = enc.encode(charBuffer);
                final byte[]     byteArray  = byteBuffer.array();
                if(byteArray.length <= 12) {
                    return new Packed12(byteArray);
                } else if(byteArray.length <= 20) {
                    return new Packed20(byteArray);
                } else if(byteArray.length <= 28) {
                    return new Packed28(byteArray);
                } else if(byteArray.length <= 36) {
                    return new Packed36(byteArray);
                } else if(byteArray.length <= 44) {
                    return new Packed44(byteArray);
                } else if(byteArray.length <= 52) {
                    return new Packed52(byteArray);
                } else {
                    return str;
                }
            } catch(CharacterCodingException e) {
                return str;
            }
        }
    }
    
    public static void main(String[] args) {
        String string = "Test String";
        Object object = PackedString.convert(string);
        if(object instanceof PackedBase) {
            System.out.println(object);
        }
        String string2 = "Test String Test/Test String";
        Object object2 = PackedString.convert(string2);
        if(object2 instanceof PackedBase) {
            System.out.println(object2);
        }
    }
}
