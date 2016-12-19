import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.singletonList;

class Sequence {

    private final Collection<ProteinEater> eaters;

    Sequence(Collection<ProteinEater> eaters) {
        this.eaters = eaters;
    }

    boolean matches(String protein, int start) {
        Collection<Integer> currentOffsets = singletonList(0);
        for (ProteinEater eater : eaters) {
            Collection<Integer> nextOffsets = new ArrayList<>();
            for (Integer offset : currentOffsets) {
                eater.eat(protein, start + offset).forEach(
                        eaten -> nextOffsets.add(offset + eaten)
                );
            }
            if (nextOffsets.isEmpty()) {
                return false;
            }
            currentOffsets = nextOffsets;
        }
        return true;
    }
}
