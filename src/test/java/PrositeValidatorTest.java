import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PrositeValidatorTest {

    private final PrositeValidator sut = new PrositeValidator();

    @Test
    public void shouldAcceptSimplePattern() throws Exception {
        assertThat(validate("JHKH"), is(true));
    }

    @Test
    public void shouldAcceptComplexPattern() throws Exception {
        assertThat(validate("{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]"), is(true));
    }

    @Test
    public void shouldAcceptRepeats() throws Exception {
        assertThat(validate("G(3)"), is(true));
    }

    @Test
    public void shouldAcceptAllTheCharacters() throws Exception {
        assertThat(validate("[-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789[](){},x]"), is(true));
    }

    @Test
    public void shouldAcceptWildcards() throws Exception {
        assertThat(validate("xxxxxx"), is(true));
    }

    @Test
    public void shouldRejectCharacters() throws Exception {
        assertThat(validate("[FSDFSDFSFSXXJKKJKJ44F3F4.,.,FH3H]"), is(false));
        assertThat(validate("[FSDFSDFSFSXjjhjhjKKJKJ4FH3H]"), is(false));
        assertThat(validate("yeruutr"), is(false));
        assertThat(validate(""), is(false));
    }

    @Test
    public void shouldRejectUnclosedBraces() throws Exception {
        assertThat(validate("[JH"), is(false));
        assertThat(validate("[JH]-G(5"), is(false));
        assertThat(validate("[JH-"), is(false));
        assertThat(validate("[JH]-G(3,5"), is(false));
        assertThat(validate("[SSS-(DDD)-DDD]"), is(false));
        assertThat(validate("]DDD["), is(false));
    }

//    @Test
//    public void shouldRejectUnmatchedNestedBrackets() throws Exception {
//        assertThat(validate("{{DDD}"), is(false));
//        assertThat(validate("[DDD]]"), is(false));
//        assertThat(validate("((DDD)"), is(false));
//    }

    @Test
    public void shouldRejectIntertwinedBraces() throws Exception {
        assertThat(validate("({DDD)}"), is(false));
    }

    @Test
    public void shouldAcceptMatchingBraces() throws Exception {
        assertThat(validate("[JH]"), is(true));
        assertThat(validate("A"), is(true));
        assertThat(validate("[JH]-G(5)"), is(true));
        assertThat(validate("[JH]-G(3,5)"), is(true));
    }

    private boolean validate(String pattern) {
        return sut.validate(pattern);
    }

    @Test
    public void testBracketsInPattern() throws Exception {
        String prositePattern = "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]";


        assertTrue(true);
    }


    public void testPrositePatternLength() throws Exception {


    }


}