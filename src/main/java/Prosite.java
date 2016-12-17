import java.util.Collection;
import java.util.HashSet;

class Prosite {

    Collection<Integer> searchIndex(String protein, String pattern) {
        if (pattern.isEmpty()) {
            throw new RuntimeException("Pattern cannot be empty");
        }
        Collection<Integer> indexes = new HashSet<>();
        Integer patternIterator = 0;
        for (int i = 0; i < protein.length(); i++) {
            if (protein.charAt(i) == pattern.charAt(patternIterator)) {
                indexes.add(i);
            } else {
                patternIterator = 0;
            }

        }
        return indexes;
    }
}