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
            Integer repetitionOffset = 0;
            for (int patternIndex = 0; patternIndex < patternElements.size(); patternIndex++) {
                if (proteinIndex + patternIndex < protein.length()) {
                    String patternElement = patternElements.get(patternIndex);
                    boolean proteinMatches;
                    if (patternElement.contains("(") && !(patternElement.contains(","))) {   //case 5: repetition exactly i times
                        String acid = patternElement.substring(0, 1);
                        String repetitionString = patternElement.substring(2, patternElement.length() - 1);
                        Integer repetitionSize = Integer.parseInt(repetitionString);
                        proteinMatches = repetitionExactlyITimesMachers(protein, proteinIndex, patternIndex, acid, repetitionSize, repetitionOffset);
                        if (proteinMatches) {
                            repetitionOffset += repetitionSize-1;
                        }
                    } else if (patternElement.contains("[")) { //case 2: one acid from bracket
                        proteinMatches = oneFromBracketMachers(protein, proteinIndex+repetitionOffset, patternIndex, patternElement);
                    } else if (patternElement.contains("{")) {   //case 3: not acid in bracket
                        proteinMatches = notInFromBracketMachers(protein, proteinIndex + repetitionOffset, patternIndex, patternElement);
                    } else
                        proteinMatches = !patternElement.contains(",") && acidMatches(protein, proteinIndex, patternIndex, repetitionOffset, patternElement);

                    if (proteinMatches) {
                        if (patternIndex == patternElements.size() - 1) {
                            indexes.add(proteinIndex);
                            proteinIndex += repetitionOffset;
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

    private boolean acidMatches(String protein, int proteinIndex, int patternIndex, int repetitionOffset, String patternElement) {
        if (patternElement.contains("x")) {   //case 1: wildcard
            return wildcardMatches(patternElement);
        } else {
            return Objects.equals(
                    valueOf(protein.charAt(proteinIndex + patternIndex + repetitionOffset)),
                    patternElement
            );
        }
    }

    private boolean oneFromBracketMachers(String protein, int proteinIndex, int patternIndex, String patternElement) {
        String acids = patternElement.substring(1, patternElement.length() - 1);
        return !acids.isEmpty() && acids.contains(valueOf(protein.charAt(proteinIndex + patternIndex)));
    }

    private boolean notInFromBracketMachers(String protein, int proteinIndex, int patternIndex, String patternElement) {
        String acids = patternElement.substring(1, patternElement.length() - 1);
        return !acids.isEmpty() && !acids.contains(valueOf(protein.charAt(proteinIndex + patternIndex)));
    }

    private boolean repetitionExactlyITimesMachers(
            String protein,
            int proteinIndex,
            int patternIndex,
            String patternElement,
            Integer repetitionSize,
            Integer repetitionOffset) {
        boolean repetitionMatches = false;
        for (int repetitionIndex = 0; repetitionIndex < repetitionSize; repetitionIndex++) {
            if (acidMatches(protein, proteinIndex, patternIndex, repetitionIndex + repetitionOffset, patternElement)) {
                if ((repetitionIndex == repetitionSize - 1) && (proteinIndex + repetitionSize < protein.length())) {
                    repetitionMatches = true;
                }
            } else {
                repetitionIndex = repetitionSize;
            }
        }
        return repetitionMatches;
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