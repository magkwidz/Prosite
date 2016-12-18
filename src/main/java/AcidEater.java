import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class AcidEater implements ProteinEater {

    private final char acid;

    AcidEater(char acid) {
        this.acid = acid;
    }

    @Override
    public Collection<Integer> eat(String protein, int start) {
        if (start < protein.length() && protein.charAt(start) == acid) {
            return singletonList(1);
        } else {
            return emptyList();
        }
    }
}
