import java.util.*;

import static java.lang.String.valueOf;

class Prosite {

    Collection<Integer> searchIndex(String protein, String pattern) {
        if (pattern.isEmpty()) {
            throw new RuntimeException("Pattern cannot be empty");
        }
        Collection<Integer> indexes = new ArrayList<>();
        List<String> patternElements = SplitPattern(pattern);

        for (int proteinIndex = 0; proteinIndex < protein.length(); proteinIndex++) {
            Integer repetitionOffset = 0;
            for (int patternIndex = 0; patternIndex < patternElements.size(); patternIndex++) {
                if (proteinIndex + patternIndex < protein.length()) {
                    String patternElement = patternElements.get(patternIndex);
                    boolean proteinMatches = false;
                    if (patternElement.contains("(") && !(patternElement.contains(","))) {
                        String acid = patternElement.substring(0, 1);
                        Integer repetitionSize = Integer.parseInt(patternElement.substring(2, patternElement.length() - 1));
                        proteinMatches = repetitionExactlyITimesMatches(protein, proteinIndex, patternIndex, acid, repetitionSize, repetitionOffset);
                        if (proteinMatches) {
                            repetitionOffset += repetitionSize - 1;
                        }
                    } else if (patternElement.contains(",")) {
                        String acid = patternElement.substring(0, 1);
                        String repetitionString = patternElement.substring(2, patternElement.length() - 1);
                        List<String> numbers = Arrays.asList(repetitionString.split(","));
                        Integer lowerRange = Integer.parseInt(numbers.get(0));
                        Integer upperRange = Integer.parseInt(numbers.get(1));
                        Integer repetitionRangeTimesMatchesOffset = repetitionRangeTimesMatches(
                                protein,
                                proteinIndex,
                                patternIndex,
                                acid,
                                lowerRange,
                                upperRange,
                                repetitionOffset
                        );
                        if (repetitionRangeTimesMatchesOffset > 0) {
                            proteinMatches = true;
                            repetitionOffset += repetitionRangeTimesMatchesOffset;
                        }
                    } else if (patternElement.contains("[")) {
                        proteinMatches = oneFromBracketMatches(protein, proteinIndex + repetitionOffset, patternIndex, patternElement);
                    } else if (patternElement.contains("{")) {
                        proteinMatches = notInFromBracketMatches(protein, proteinIndex + repetitionOffset, patternIndex, patternElement);
                    } else {
                        proteinMatches = acidMatches(protein, proteinIndex, patternIndex, repetitionOffset, patternElement);
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

    private boolean acidMatches(String protein, int proteinIndex, int patternIndex, int repetitionOffset, String patternElement) {
        Integer index = proteinIndex + patternIndex + repetitionOffset;
        if (index < protein.length()) {
            if (patternElement.contains("x")) {   //case 1: wildcard
                return wildcardMatches(patternElement);
            } else {
                return Objects.equals(
                        valueOf(protein.charAt(proteinIndex + patternIndex + repetitionOffset)),
                        patternElement
                );
            }
        }
        else {
            return false;
        }
    }

    private boolean oneFromBracketMatches(String protein, int proteinIndex, int patternIndex, String patternElement) {
        String acids = patternElement.substring(1, patternElement.length() - 1);
        return !acids.isEmpty() && acids.contains(valueOf(protein.charAt(proteinIndex + patternIndex)));
    }

    private boolean notInFromBracketMatches(String protein, int proteinIndex, int patternIndex, String patternElement) {
        String acids = patternElement.substring(1, patternElement.length() - 1);
        return !acids.isEmpty() && !acids.contains(valueOf(protein.charAt(proteinIndex + patternIndex)));
    }

    private boolean repetitionExactlyITimesMatches(
            String protein,
            int proteinIndex,
            int patternIndex,
            String patternElement,
            Integer repetitionSize,
            Integer repetitionOffset) {
        boolean repetitionMatches = false;
        for (int repetitionIndex = 0; repetitionIndex < repetitionSize; repetitionIndex++) {
            if (acidMatches(protein, proteinIndex, patternIndex, repetitionIndex + repetitionOffset, patternElement)) {
                if ((repetitionIndex == repetitionSize - 1) && (proteinIndex + repetitionIndex < protein.length())) {
                    repetitionMatches = true;
                }
            } else {
                repetitionIndex = repetitionSize;
            }
        }
        return repetitionMatches;
    }

    private Integer repetitionRangeTimesMatches(
            String protein,
            int proteinIndex,
            int patternIndex,
            String patternElement,
            Integer lowerRange,
            Integer upperRange,
            Integer repetitionOffset) {
        Integer repetitionMatchesOffset = 0;
        for (int repetitionIndex = 0; repetitionIndex < upperRange; repetitionIndex++) {
            if (acidMatches(protein, proteinIndex, patternIndex, repetitionIndex + repetitionOffset, patternElement)) {
                if (( lowerRange-1 <= repetitionIndex && repetitionIndex <= upperRange-1) && (proteinIndex + lowerRange - 1 < protein.length())) {
                    repetitionMatchesOffset = repetitionIndex;
                    repetitionIndex = upperRange;
                }
            } else {
                repetitionIndex = upperRange;
            }
        }
        return repetitionMatchesOffset;
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