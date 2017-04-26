package com.wandercosta.dumbconverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DumbConverter converts units in WitsML files, by replacing the String and explicitly applying a conversion factor.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class DumbConverter {

    private static final String INVALID_WITSML = "Witsml must be provided.";
    private static final String INVALID_FROM_UNIT = "FromUnit must be provided.";
    private static final String INVALID_TO_UNIT = "ToUnit must be provided.";
    private static final String INVALID_FACTOR = "Factor must be provided and different to 0.";

    public String convertUnits(String witsml, String fromUnit, String toUnit, Float factor) {
        validateString(witsml, INVALID_WITSML);
        validateString(fromUnit, INVALID_FROM_UNIT);
        validateString(toUnit, INVALID_TO_UNIT);
        validateFloat(factor, INVALID_FACTOR);

        Pattern pattern = Pattern.compile("uom=\"" + fromUnit + "\">[-+]?[0-9]*\\.?[0-9]*</");
        Matcher matcher = pattern.matcher(witsml);

        int pos = 0;
        String group;
        String numStr;
        float numFt;
        float numM;
        int pointer = 0;

        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            pos = matcher.start();
            group = matcher.group();
            numStr = witsml.substring(pos + group.indexOf(">") + 1, pos + group.indexOf("<"));
            numFt = Float.valueOf(numStr);
            numM = factor * numFt;
            output = output.append(witsml.substring(pointer, pos));
            output = output.append("uom=\"").append(toUnit).append("\">");
            output = output.append(String.valueOf(numM));
            output = output.append("</");
            pointer = pos + group.length();
        }
        output = output.append(witsml.substring(pointer));
        return output.toString();
    }

    private void validateString(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateFloat(Float value, String message) {
        if (value == null || value == 0) {
            throw new IllegalArgumentException(message);
        }
    }

}
