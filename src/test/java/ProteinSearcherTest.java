import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ProteinSearcherTest {

    private ProteinSearcher sut = new ProteinSearcher();

    @Test
    public void shouldFindComplexSequence() throws Exception {
        SequenceMatcher matcher = new SequenceMatcher(
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
        SequenceMatcher matcher = new SequenceMatcher(
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

}
