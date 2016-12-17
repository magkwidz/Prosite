import static org.apache.commons.lang3.StringUtils.contains;

class PrositeValidator {


    boolean validate(String pattern) {
        return validateCharacters(pattern) && validateBraces(pattern);
    }

    private boolean validateCharacters(String pattern) {
        return !pattern.isEmpty()
                && pattern.chars().allMatch(c -> contains("-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789[](){},x", c));
    }


    private boolean validateBraces(String pattern) {
        boolean inBraces = false, inBrackets = false, inParens = false;
        for (int i = 0; i < pattern.length(); i++) {
            switch (pattern.charAt(i)) {
                case '(':
                    if (!inBraces && !inBrackets && !inParens) {
                        inParens = true;
                        break;
                    } else {
                        return false;
                    }
                case ')':
                    if (inParens) {
                        inParens = false;
                        break;
                    } else {
                        return false;
                    }
                case '{':
                    if (!inBraces && !inBrackets && !inParens) {
                        inBraces = true;
                        break;
                    } else {
                        return false;
                    }
                case '}':
                    if (inBraces) {
                        inBraces = false;
                        break;
                    } else {
                        return false;
                    }
                case '[':
                    if (!inBraces && !inBrackets && !inParens) {
                        inBrackets = true;
                        break;
                    } else {
                        return false;
                    }
                case ']':
                    if (inBrackets) {
                        inBrackets = false;
                        break;
                    } else {
                        return false;
                    }

                default:
                    break;
            }
        }
        return !(inParens || inBrackets || inBraces);
    }
}
