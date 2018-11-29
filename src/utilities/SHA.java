package utilities;

import java.io.File;
import java.math.BigInteger;

public class SHA {

    private BigInteger value;

    public SHA(final BigInteger value){
        this.value = value;
    }

    public SHA(final String value){
        this.value = new BigInteger(value);
    }

    @Override
    public String toString(){
        return value.toString();
    }

    public static class SHA1Crypter {

        public static SHA getSHA1(final File file){
            return null;
        }

    }
}
