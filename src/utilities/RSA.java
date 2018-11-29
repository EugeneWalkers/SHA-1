package utilities;

import javafx.util.Pair;

import java.math.BigInteger;

/**
 * Умеет генерировать рса, шифровать и дешифровать
 */

public class RSA {

    private final Pair<BigInteger, BigInteger> openRSA;
    private final Pair<BigInteger, BigInteger> closeRSA;

    private RSA(final Pair<BigInteger, BigInteger> openRSA, final Pair<BigInteger, BigInteger> closeRSA) {
        this.openRSA = openRSA;
        this.closeRSA = closeRSA;
    }

    /**
     * Парсит строку, чтобы получить пару  BigInteger-BigInteger
     *
     * @param rsaInString строка вида a=b (a и b - строки)
     */
    public static Pair<BigInteger, BigInteger> getRSAKeyFromString(final String rsaInString) { // на всякий случай, чтобы из строки вида a=b получить пару <a, b>
        final String[] rsaInArray = rsaInString.split("=");
        final BigInteger first = new BigInteger(rsaInArray[0]);
        final BigInteger second = new BigInteger(rsaInArray[1]);

        return new Pair<>(first, second);
    }

    public Pair<BigInteger, BigInteger> getOpenRSA() { // геттер
        return openRSA;
    }

    public Pair<BigInteger, BigInteger> getCloseRSA() { // геттер
        return closeRSA;
    }

    public String toStringOpenRSA() { // на всякий случай
        return openRSA.toString();
    }

    public String toStringCloseRSA() { // на всякий случай
        return closeRSA.toString();
    }

    /**
     * Псевдогенератор rsa
     */
    public static class Generator {
        private Generator() {

        }


        /**
         * Возвращает заранее предопределенные числа
         */
        public static RSA generateRSA() {
            final BigInteger openKey = new BigInteger("257");
            final BigInteger openValue = new BigInteger("416078173006107514632783360618804619520815750839159737333389051569984124746550094106122408152072411152243855813224101157277143628564738270311287239091657347387563987896637174877249315764265281414642698254837954302667445094347793675444055219606372786706651537421654235283276614303879730841793212621791916962570946406843647236667966701974736676928734066627265550369063544524895067395849591595382727366078626738762054036400817476685930514369767257677098553318490440856671289726316330582745619862311001328133244795126963208000421136727460681305205775066306630470958559970141307670618343127834785116127482179907254541751");
            final BigInteger closeKey = new BigInteger("239258393440116479624085994128943514207683593443987841525295731238364646869408177797315966329717133855020970446062388407902679320288580890527638794955835923163331329891593265166499952076874611149663690928354409250770482535790896055089466902540524164049493103788287067062641941857859280759347384541604103133554322581032563747104067069475574930453903906734366444909275838781975421772934583509328351075350927526764817197211601969006525910701518138132707326282784675698083186516727200514635683251369054355305958290785655150348073509655585939961103088477182147603008798375535503649506366174605208129108185453475188692289");
            final BigInteger closeValue = new BigInteger("3416078173006107514632783360618804619520815750839159737333389051569984124746550094106122408152072411152243855813224101157277143628564738270311287239091657347387563987896637174877249315764265281414642698254837954302667445094347793675444055219606372786706651537421654235283276614303879730841793212621791916962570946406843647236667966701974736676928734066627265550369063544524895067395849591595382727366078626738762054036400817476685930514369767257677098553318490440856671289726316330582745619862311001328133244795126963208000421136727460681305205775066306630470958559970141307670618343127834785116127482179907254541751");

            final Pair<BigInteger, BigInteger> open = new Pair<>(openKey, openValue);
            final Pair<BigInteger, BigInteger> close = new Pair<>(closeKey, closeValue);

            return new RSA(open, close);
        }
    }

    /**
     * Класс для шифровки/дешифровки (чтения подписи/подписи текста) rsa
     * Метод deassign шифрует либо читает подпись
     * Метод assign дешифрует либо создает подпись
     */
    public static class Assigner { // для шифровки/дешифровки rsa
        private Assigner() {

        }

        /**
         * @param text - текст для deassign-а
         * @param rsa  - закрытый rsa-ключ для чтения подписи
         */
        public static String deassign(final String text, final Pair<BigInteger, BigInteger> rsa) {
            final StringBuilder encryptedText = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
                int curCode = Character.codePointAt(text, i);
                final BigInteger a = new BigInteger(String.valueOf(curCode));
                final BigInteger newCode = a.modPow(rsa.getKey(), rsa.getValue());

                encryptedText.append(newCode.toString()).append(i == text.length() - 1 ? "" : System.lineSeparator());
            }

            return encryptedText.toString();
        }

        /**
         * @param text - текст для assign-а
         * @param rsa  - открытый rsa-ключ для подписи
         */
        public static String assign(final String text, final Pair<BigInteger, BigInteger> rsa) {
            final String[] encryptedKeys = text.split("\n");
            final StringBuilder decryptedKey = new StringBuilder();

            for (int i = 0; i < encryptedKeys.length; i++) {
                final BigInteger integer = new BigInteger(encryptedKeys[i]);
                final BigInteger newCode = integer.modPow(rsa.getKey(), rsa.getValue());
                decryptedKey.append((char) newCode.intValueExact());
            }

            return decryptedKey.toString();
        }
    }
}
