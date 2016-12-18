import java.util.ArrayList;
import java.util.Collection;

class ProteinSearcher {
    Collection<Integer> search(String protein, SequenceMatcher matcher) {
        Collection<Integer> foundOffsets = new ArrayList<>();
        for (int start = 0; start < protein.length(); start++) {
            if (matcher.matches(protein, start)) {
                foundOffsets.add(start);
            }
        }
        return foundOffsets;
    }
}
