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
        assertThat(search("ABCDREWXABCEAB", "A-B-C"), contains(0, 8));
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
    public void shouldFindSequencesWithAllBasicConditions() throws Exception {
        assertThat(
                search(
                        "SRSLKMRGQAFVIFKEVSSAT", //RGQAFVIF
                        "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]"
                ),
                contains(6)
        );
    }
    @Test
    public void shouldFindSequencesWithAllBasicConditions2() throws Exception {
        assertThat(
                search(
                        "KLTGRPRGVAFVRYNKREEAQ", //RGVAFVRY
                        "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]"
                ),
                contains(6)
        );
    }

    @Test
    public void shouldFindSequencesWithAllBasicConditions3() throws Exception {
        assertThat(
                search(
                        "VGCSVHKGFAFVQYVNERNAR", //KGFAFVQY
                        "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]"
                ),
                contains(6)
        );
    }
    @Test
    public void shouldFindRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "ABCAAAGHCAAHBAAAH", //CAAA || BAAA
                        "[ABC]-A(3)"
                ),
                contains(2,12)
        );
    }

    @Test
    public void shouldFindOneRepetitionAndAcidSequences() throws Exception {
        assertThat(
                search(
                        "CAAPFHCAACWCHBAANH",
                        "C-A(2)-P"
                ),
                contains(0)
        );
    }

    @Test
    public void shouldFindOneWildcardRepetitionAndAcidSequences() throws Exception {
        assertThat(
                search(
                        "CAAPFHCAACWCHBAANH",
                        "C-x(2)-[PAC]"
                ),
                contains(0,6,11)
        );
    }

    @Test
    public void shouldFindWildcardRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "BABCXXX", // ABCX
                        "A-x(3)"
                ),
                contains(1)
        );
    }

    @Test
    public void shouldFindOneWildcardRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "BABCXAXX", //ABCX
                        "A-x(3)"
                ),
                contains(1)
        );
    }


    @Test
    public void shouldFindTwoRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "CAAPPFHCAAPPCWCHBAANH",
                        "C-A(2)-P(2)"
                ),
                contains(0,7)
        );
    }

    @Test
    public void shouldFindOneRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "CAAGPPFHCAAFPPCWCHBAANH",
                        "C-A(2)-G-P(2)"
                ),
                contains(0)
        );
    }

    @Test
    public void shouldFindThreeRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "PCKGGGAKKKPCKGGGAHPCKGGGSJK",
                        "P-C-K-G(3)-[AS]"
                ),
                contains(0,10,18)
        );
    }

    @Test
    public void shouldFindLongRepetitionSequences() throws Exception {
        assertThat(
                search(
                        "PVSGESGGGGGASHHJPVSGESGGGGGSKLKLPVSGESGGGGGS",
                        "P-x(2)-G-E-S-G(5)-{FH}"
                ),
                contains(0,16,32)
        );
    }

    private Collection<Integer> search(String protein, String pattern) {
        return sut.searchIndex(protein, pattern);
    }
}
