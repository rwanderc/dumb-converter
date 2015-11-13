package dumbconverter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DumbConverter is an application that allows you to convert units in WitsML
 * files, by replacing the String and explicitly applying a conversion factor.
 *
 * @author Roberto Wander <rwander at gmail.com>
 */
public class DumbConverter {

    public static String readFile(String path)
            throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;

        try {
            String line;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
            }
        }

        return stringBuilder.toString();

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {

            if (args.length != 4) {
                System.err.println("Must contain 4 parameters: filepath fromUnit toUnit factor");
                System.exit(1);
            }

            String text = readFile(args[0]);
            String textTo = "";
            String fromUnit = args[1];
            String toUnit = args[2];
            float factor = Float.valueOf(args[3]);

            Pattern p = Pattern.compile("uom=\"" + fromUnit + "\">[-+]?[0-9]*\\.?[0-9]*</");
            Matcher m = p.matcher(text);

            int pos = 0;
            String group;
            String numStr;
            float numFt;
            float numM;
            int pointer = 0;

            while (m.find()) {
                pos = m.start();
                group = m.group();
                numStr = text.substring(pos + group.indexOf(">") + 1, pos + group.indexOf("<"));
                numFt = Float.valueOf(numStr);
                numM = factor * numFt;
                textTo = textTo.concat(text.substring(pointer, pos));
                textTo = textTo.concat("uom=\"" + toUnit + "\">");
                textTo = textTo.concat(String.valueOf(numM));
                textTo = textTo.concat("</");
                pointer = pos + group.length();
            }
            textTo = textTo.concat(text.substring(pointer));

            System.out.println(textTo);

        } catch (Throwable ex) {
            System.err.println("Error while processing convertion.");
            ex.printStackTrace();
            System.exit(1);
        }

    }

}
