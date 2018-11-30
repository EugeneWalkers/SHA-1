package utilities;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

    private BigInteger value;

    public SHA(final BigInteger value) {
        this.value = value;
    }

    public SHA(final String value) {
        this.value = new BigInteger(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Используется после подписания SHA алгоритмом RSA, когда получаем значение вида:
     * 123;234; (что соответствует SHA из 2 символов, каждый зашифрован RSA
     */
    public static class AssignedSHA {
        private final String value;

        public AssignedSHA(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static class SHA1Crypter {


        private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

        private SHA1Crypter() {

        }

        private static String getHexString(final byte[] array){
            final StringBuilder r = new StringBuilder(array.length*2);

            for (byte b:array){
                r.append(hexCode[(b>>4)&0xF]);
                r.append(hexCode[(b&0xF)]);
            }

            return r.toString();
        }

        public static SHA getSHA1(final File file) {
            String text = DataKeeper.getStringFromFile(file);
            try {
                MessageDigest sha1 = MessageDigest.getInstance("SHA1");
                sha1.update(text.getBytes());
                byte[] hash = sha1.digest();

                final BigInteger integer = new BigInteger(getHexString(hash), 16);

                return new SHA(integer);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}
