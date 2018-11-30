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
    public static Pair<BigInteger, BigInteger> getRSAKeyFromString(final String rsaInString) {
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
            final BigInteger openValue = new BigInteger("78372302473975201232520941672424531562688659199192444130198471516637472726425448612477961442432802357144767667650332904045451365904880530861677156661612182665333610626794170836488812439550128618027116853878641406302304075268156376675683643452103966192714695027080012013938170739255755205243349386779100924871903977765592763321903770593256195808445708732735045814756837217926983788864555199274208621580420458835913203002291674893716179216259455048970431394431488660093114653054561761759194531056243289641860783568345656798360113754257119530726462544000805100809925476578803541133147245583681834282892841711419399197");
            final BigInteger closeKey = new BigInteger("51536650264987583689867856586146870949783593014254953533087710841679894516598835858010799547747640460534886131645549652854790976023053734302036729477869489768254397649526127904150230748186660453099543767725643570681281668172445243806188855032706499169528340309636272491655839902467792333409050764068747300779553242034466960057119284306773831185489451227890568586213650989676502557849016264233969326552308182999860670806676352836097223025127213645953867467015845596049754910808629087628829816473464211003581589628055828979809774866128411742243868724824916255440653966870342555903682205997021110880530137963217791105");
            final BigInteger closeValue = new BigInteger("78372302473975201232520941672424531562688659199192444130198471516637472726425448612477961442432802357144767667650332904045451365904880530861677156661612182665333610626794170836488812439550128618027116853878641406302304075268156376675683643452103966192714695027080012013938170739255755205243349386779100924871903977765592763321903770593256195808445708732735045814756837217926983788864555199274208621580420458835913203002291674893716179216259455048970431394431488660093114653054561761759194531056243289641860783568345656798360113754257119530726462544000805100809925476578803541133147245583681834282892841711419399197");

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

        public static final String SEPARATOR = ";";

        private Assigner() {

        }

        /**
         * @param text - текст для deassign-а
         * @param openRSA  - закрытый rsa-ключ для чтения подписи
         */
        public static String deassign(final String text, final Pair<BigInteger, BigInteger> openRSA) {
            final String[] assignedWords = text.split(SEPARATOR);
            final StringBuilder encryptedText = new StringBuilder();

            for (int i = 0; i < assignedWords.length; i++) {
                final BigInteger a = new BigInteger(assignedWords[i]);
                final BigInteger newCode = a.modPow(openRSA.getKey(), openRSA.getValue());

                encryptedText.append((char)newCode.intValueExact());
            }

            return encryptedText.toString();
        }

        /**
         * @param text - текст для assign-а
         * @param closeRSA  - открытый rsa-ключ для подписи
         */
        public static String assign(final String text, final Pair<BigInteger, BigInteger> closeRSA) {
            final StringBuilder decryptedKey = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
                int curCode = Character.codePointAt(text, i);
                final BigInteger integer = new BigInteger(String.valueOf(curCode));
                final BigInteger newCode = integer.modPow(closeRSA.getKey(), closeRSA.getValue());
                decryptedKey.append(newCode.toString()).append(i == (text.length() - 1) ? "" : SEPARATOR);
            }

            return decryptedKey.toString();
        }
    }

    @Override
    public String toString() {
        return openRSA.toString()+"\n"+closeRSA.toString();
    }
}
