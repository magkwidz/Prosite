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

                String patternElement = patternElements.get(patternIndex);
                //case 1: wildcard
                boolean wildcardMatches = wildcardMatches(patternElement);
                //case 2: specific value
                boolean acidMatches = acidMatches(protein, proteinIndex, patternIndex, patternElement);
                if (wildcardMatches || acidMatches) {
                    if (patternIndex == patternElements.size() - 1)
                        indexes.add(proteinIndex);
                } else {
                    patternIndex = patternElements.size();
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