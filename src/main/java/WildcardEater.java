import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class WildcardEater implements ProteinEater {

    @Override
    public Collection<Integer> eat(String protein, int start) {
        if (start < protein.length()) {
            return singletonList(1);
        } else {
            return emptyList();
        }
    }
}
