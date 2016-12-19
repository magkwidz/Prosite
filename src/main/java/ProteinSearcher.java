import java.util.ArrayList;
import java.util.Collection;

class ProteinSearcher {
    Collection<Integer> search(String protein, Sequence sequence) {
        Collection<Integer> foundOffsets = new ArrayList<>();
        for (int start = 0; start < protein.length(); start++) {
            if (sequence.matches(protein, start)) {
                foundOffsets.add(start);
            }
        }
        return foundOffsets;
    }
}
