import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class BlackListEater implements ProteinEater {

    private final Collection<ProteinEater> blackList;

    BlackListEater(Collection<ProteinEater> blackList) {
        this.blackList = blackList;
    }

    @Override
    public Collection<Integer> eat(String protein, int start) {
        if (blackList.stream().anyMatch(eater -> !eater.eat(protein, start).isEmpty())) {
            return emptyList();
        } else {
            return singletonList(1);
        }
    }
}
