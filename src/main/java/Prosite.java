import java.util.*;

import static java.lang.String.valueOf;

class Prosite {

    Collection<Integer> searchIndex(String protein, String pattern) {
        if (pattern.isEmpty()) {
            throw new RuntimeException("Pattern cannot be empty");
        }
        Collection<Integer> indexes = new HashSet<>();
        List<String> patternElements = SplitPattern(pattern);

        for (int proteinIndex = 0; proteinIndex < protein.length(); proteinIndex++) {
            for (int patternIndex = 0; patternIndex < patternElements.size(); patternIndex++) {
                if (proteinIndex + patternIndex < protein.length() ) {
                    String patternElement = patternElements.get(patternIndex);
                    boolean proteinMatches;

                    if (patternElement.contains("x")) {   //case 1: wildcard
                        proteinMatches = wildcardMatches(patternElement);
                    } else if (patternElement.contains("[")) { //case 2: one acid from bracket
                        proteinMatches = oneFromBracketMachers(protein, proteinIndex, patternIndex, patternElement);
                    } else if (patternElement.contains("{")) {   //case 3: not acid in bracket
                        proteinMatches = notInFromBracketMachers(protein, proteinIndex, patternIndex, patternElement);
                    } else {  //case 4: acidMatches
                        proteinMatches = acidMatches(protein, proteinIndex, patternIndex, patternElement);
                    }
                    if (proteinMatches) {
                        if (patternIndex == patternElements.size() - 1) {
                            indexes.add(proteinIndex);
                        }
                    } else {
                        patternIndex = patternElements.size();
                    }
                }
            }
        }
        return indexes;
    }

    private boolean wildcardMatches(String patternElement) {
        return patternElement.equals("x");
    }

    private boolean acidMatches(String protein, int proteinIndex, int patternIndex, String patternElement) {
        return Objects.equals(
                valueOf(protein.charAt(proteinIndex + patternIndex)),
                patternElement
        );
    }

    private boolean oneFromBracketMachers(String protein, int proteinIndex, int patternIndex, String patternElement) {

        String acids = patternElement.substring(1, patternElement.length() - 1);
        return !acids.isEmpty() && acids.contains(valueOf(protein.charAt(proteinIndex + patternIndex)));
    }

    private boolean notInFromBracketMachers(String protein, int proteinIndex, int patternIndex, String patternElement) {
        String acids = patternElement.substring(1, patternElement.length() - 1);
        return !acids.isEmpty() && !acids.contains(valueOf(protein.charAt(proteinIndex + patternIndex)));
    }

    private List<String> SplitPattern(String pattern) {
        List<String> patternElements = new ArrayList<>();
        if (pattern.contains("-")) {
            patternElements = Arrays.asList(pattern.split("-"));
        } else {
            patternElements.add(pattern);
        }
        return patternElements;

    }
}