package com.wandercosta.dumbconverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main class.
 *
 * @author Wander Costa (www.wandercosta.com)
 * @version 1.0
 */
public class Main {

    private static final String WRONG_ARGUMENT = "Must contain 4 parameters: filepath fromUnit toUnit factor";

    public static void main(String[] args) {
        try {
            if (args == null || args.length != 4) {
                throw new IllegalArgumentException(WRONG_ARGUMENT);
            }

            String witsml = new String(Files.readAllBytes(Paths.get(args[0])));
            String fromUnit = args[1];
            String toUnit = args[2];
            float factor = Float.valueOf(args[3]);

            DumbConverter converter = new DumbConverter();
            System.out.println(converter.convertUnits(witsml, fromUnit, toUnit, factor));
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error while reading file " + args[0] + ": " + ex);
            System.exit(1);
        }
    }

}
