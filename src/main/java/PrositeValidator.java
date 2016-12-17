/**
 * Created by magda_000 on 17/12/2016.
 */
public class PrositeValidator {


    public boolean validate(String pattern) {
        return validateCharacters(pattern) && validateBraces(pattern);
    }

    private boolean validateCharacters(String pattern) {
        String allowedChracters = "-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789[](){},x";
        if (pattern.isEmpty()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (allowedChracters.indexOf(pattern.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }


    private boolean validateBraces(String pattern) {
        boolean inBraces = false, inBrackets = false, inParens = false;
        String betweenParens;
        for (int i = 0; i < pattern.length(); i++) {
            switch (pattern.charAt(i)) {
                case '(':
                    if (!inBraces && !inBrackets) {
                        inParens = true;
                        break;
                    }
                    else {
                        return false;
                    }
                case ')' :
                    inParens = false;
                    break;
                case '{':
                    if (!inParens && !inBrackets) {
                        inBraces = true;
                        break;
                    }
                    else {
                        return false;
                    }
                case '}':
                    inBraces = false;
                    break;
                case '[':
                    if (!inParens && !inBraces) {
                        inBrackets = true;
                        break;
                    }
                    else {
                        return false;
                    }
                case ']':
                    inBrackets = false;
                    break;
                default:
                    break;
            }
        }
        if (inParens || inBrackets || inBraces) {
            return false;
        }
        return true;
    }
}
