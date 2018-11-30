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

    /**
     * Используется после подписания SHA алгоритмом RSA, когда получаем значение вида:
     * 123;234; (что соответствует SHA из 2 символов, каждый зашифрован RSA
     */
    public static class AssignedSHA{
        private final String value;

        public AssignedSHA(final String value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static class SHA1Crypter {

        private SHA1Crypter(){

        }

        public static SHA getSHA1(final File file){
            return new SHA("123");
        }

    }
}
