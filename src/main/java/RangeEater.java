import java.util.ArrayList;
import java.util.Collection;

class RangeEater implements ProteinEater {

    private final ProteinEater eater;
    private final int minRepeats;
    private final int maxRepeats;

    RangeEater(ProteinEater eater, int minRepeats, int maxRepeats) {
        this.eater = eater;
        this.minRepeats = minRepeats;
        this.maxRepeats = maxRepeats;
    }

    @Override
    public Collection<Integer> eat(String protein, int start) {
        Collection<Integer> matches = new ArrayList<>();
        for (int eaten = 1; eaten <= maxRepeats; eaten++) {
            if (!eater.eat(protein, start + eaten - 1).isEmpty()) {
                if (eaten >= minRepeats && eaten <= maxRepeats) {
                    matches.add(eaten);
                }
            }
        }
        return matches;
    }
}
