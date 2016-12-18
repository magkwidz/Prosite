import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class WhiteListEater implements ProteinEater {

    private final Collection<ProteinEater> whiteList;

    WhiteListEater(Collection<ProteinEater> whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public Collection<Integer> eat(String protein, int start) {
        if (whiteList.stream().anyMatch(eater -> !eater.eat(protein, start).isEmpty())) {
            return singletonList(1);
        } else {
            return emptyList();
        }
    }
}
