import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ProteinSearcherTest {

    private ProteinSearcher sut = new ProteinSearcher();

    @Test
    public void shouldFindComplexSequence() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new BlackListEater(
                                asList(
                                        new AcidEater('X'),
                                        new AcidEater('Y')
                                )
                        ),
                        new AcidEater('A'),
                        new WildcardEater(),
                        new RepeatedEater(
                                new AcidEater('C'),
                                3
                        ),
                        new WhiteListEater(
                                asList(
                                        new AcidEater('K'),
                                        new AcidEater('L')
                                )
                        )
                )
        );

        Collection<Integer> offsets = sut.search("XABCLZZZABCCCKPPABCCC", matcher);

        assertThat(offsets, contains(7));
    }


    @Test
    public void shouldFindRangeOfAcids() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new AcidEater('A'),
                        new RangeEater(
                                new AcidEater('B'),
                                2,
                                4
                        ),
                        new AcidEater('C')
                )
        );

        Collection<Integer> offsets = sut.search("ABBBC", matcher);

        assertThat(offsets, containsInAnyOrder(0));
    }


    @Test
    public void shouldAcidAfterRange() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new RangeEater(
                                new AcidEater('D'),
                                1,
                                3
                        ),
                        new AcidEater('D'),
                        new AcidEater('C')
                )
        );

        Collection<Integer> offsets = sut.search("DDDC", matcher);

        assertThat(offsets, containsInAnyOrder(0, 1));
    }

    @Test
    public void shouldBlackListedAcidAfterRange() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new RangeEater(
                                new AcidEater('D'),
                                1,
                                3
                        ),
                        new BlackListEater(
                                singletonList(
                                        new AcidEater('D')
                                )
                        )
                )
        );

        Collection<Integer> offsets = sut.search("ABCDDDDE", matcher);

        assertThat(offsets, containsInAnyOrder(4, 5, 6));
    }

    @Test
    public void shouldFindTwoRanges() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new AcidEater('A'),
                        new RangeEater(
                                new AcidEater('B'),
                                2,
                                4
                        ),

                        new RangeEater(
                                new AcidEater('C'),
                                2,
                                4
                        ),
                        new AcidEater('D')
                )
        );

        Collection<Integer> offsets = sut.search("ABBBCCCD", matcher);

        assertThat(offsets, containsInAnyOrder(0));
    }

    @Test
    public void shouldFindBlackListedAcidAfterRange() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new AcidEater('A'),
                        new RangeEater(
                                new AcidEater('B'),
                                2,
                                4
                        ),
                        new AcidEater('B'),
                        new BlackListEater(
                                singletonList(
                                        new AcidEater('B')
                                )
                        )
                )
        );

        Collection<Integer> offsets = sut.search("ABBBBBC", matcher);

        assertThat(offsets, containsInAnyOrder(0));
    }

    @Test
    public void shouldFindLongRepetitionSequences() throws Exception {
        Sequence matcher = new Sequence(
                asList(
                        new AcidEater('P'),
                        new RepeatedEater(
                                new WildcardEater(),
                                2
                        ),
                        new AcidEater('G'),
                        new AcidEater('E'),
                        new AcidEater('S'),
                        new RepeatedEater(
                                new AcidEater('G'),
                                5
                        ),
                        new BlackListEater(
                                asList(
                                        new AcidEater('F'),
                                        new AcidEater('G'),
                                        new AcidEater('H')
                                )
                        )
                )
        );

        Collection<Integer> offsets = sut.search("PVSGESGGGGGASHHJPVSGESGGGGGSKLKLPVSGESGGGGGS", matcher);

        assertThat(offsets, containsInAnyOrder(0, 16, 32));
    }
}
