package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class DataKeeper {

    public static void writeTextToFile(final String text, final File file) {
        try (final PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(final File file, final String path) {
        try {
            Files.copy(file.toPath(), new File(path).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringFromFile(final File file) {
        final StringBuilder builder = new StringBuilder();

        try {
            final List<String> textInList = Files.readAllLines(file.toPath());

            for (int i = 0; i < textInList.size(); i++) {
                builder.append(textInList.get(i)).append(i == textInList.size() - 1 ? "" : "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
