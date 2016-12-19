import java.util.Collection;

class Prosite {

    private final ProteinSearcher searcher;
    private final ProteinParser parser;

    Prosite(ProteinSearcher searcher, ProteinParser parser) {
        this.searcher = searcher;
        this.parser = parser;
    }

    Collection<Integer> searchIndex(String protein, String pattern) {
        Sequence sequence = parser.parse(pattern);
        return searcher.search(protein, sequence);
    }
}