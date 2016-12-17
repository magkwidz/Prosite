class PrositeValidator {


    boolean validate(String pattern) {
        return validateCharacters(pattern) && validateBraces(pattern);
    }

    private boolean validateCharacters(String pattern) {
        String allowedCharacters = "-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789[](){},x";
        if (pattern.isEmpty()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (allowedCharacters.indexOf(pattern.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
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
