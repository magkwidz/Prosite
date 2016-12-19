import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class RepeatedEater implements ProteinEater {

    private final ProteinEater eater;
    private final int repeats;

    RepeatedEater(ProteinEater eater, int repeats) {
        this.eater = eater;
        this.repeats = repeats;
    }

    @Override
    public Collection<Integer> eat(String protein, int start) {
        for (int repeat = 0; repeat < repeats; repeat++) {
            if (eater.eat(protein, start + repeat).isEmpty()) {
                return emptyList();
            }
        }
        return singletonList(repeats);
    }
}
