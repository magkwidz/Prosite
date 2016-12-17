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
        assertThat(search("ABCD", "x"), contains(0,1,2,3));
    }

    @Test
    public void shouldFindIndexesOfBothSequence() throws Exception {
        assertThat(search("ABCDREWXABCE", "A-B-C"), contains(0, 8));
    }

    @Test
    public void shouldFindIndexesOfOneSequence() throws Exception {
        assertThat(search("SABXDREWXABCE", "A-B-C"), contains(9));
    }

    private Collection<Integer> search(String protein, String pattern) {
        return sut.searchIndex(protein, pattern);
    }
}
