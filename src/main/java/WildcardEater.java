import java.util.Collection;

import static java.util.Collections.singletonList;

class WildcardEater implements ProteinEater {

    @Override
    public Collection<Integer> eat(String protein, int start) {
        return singletonList(1);
    }
}
