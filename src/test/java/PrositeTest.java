import org.junit.Test;

import java.util.Collection;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class PrositeTest {

    private final Prosite sut = new Prosite();


    @Test
    public void shouldFindBothAcids() throws Exception {
        assertThat(search("JHKH", "H"), contains(1, 3));
    }

    @Test
    public void shouldFindNoAcids() throws Exception {
        assertThat(search("ABCD", "X"), is(empty()));
    }

    @Test
    public void shouldFindAllAcidCopies() throws Exception {
        assertThat(search("DDD", "D"), contains(0, 1, 2));
    }

    @Test
    public void shouldRejectEmptyPattern() throws Exception {
        catchException(sut).searchIndex("ABCD", "");

        assertThat(caughtException().getMessage(), is("Pattern cannot be empty"));
    }

    @Test
    public void shouldFindFirstIndex() throws Exception {
        assertThat(search("ABCD", "x"), contains(0, 1, 2, 3));
    }

    @Test
    public void shouldFindBothSequences() throws Exception {
        assertThat(search("ABCDREWXABCE", "A-B-C"), contains(0, 8));
    }

    @Test
    public void shouldFindOneSequence() throws Exception {
        assertThat(search("SABXDREWXABCE", "A-B-C"), contains(9));
    }

    @Test
    public void shouldFindSequencesWithAlternativeAcids() throws Exception {
        assertThat(search("SABXXABCE", "A-B-[XC]"), contains(1, 5));
    }

    @Test
    public void shouldFindSequencesWithNoOfAcids() throws Exception {
        assertThat(search("SABCXABXE", "A-B-{HC}"), contains(5));
    }

    @Test
    public void shouldFindSequencesWithAllConditions() throws Exception {
        assertThat(
                search(
                        "SRSLKMRGQAFVIFKEVSSAT", //RGQAFVIF
                        "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]"
                ),
                contains(6)
        );
    }
    @Test
    public void shouldFindSequencesWithAllConditions2() throws Exception {
        assertThat(
                search(
                        "KLTGRPRGVAFVRYNKREEAQ", //RGVAFVRY
                        "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]"
                ),
                contains(6)
        );
    }

    private Collection<Integer> search(String protein, String pattern) {
        return sut.searchIndex(protein, pattern);
    }
}
