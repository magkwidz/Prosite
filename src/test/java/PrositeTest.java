import org.junit.Test;

import java.util.Collection;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class PrositeTest {

    private final Prosite sut = new Prosite();


    @Test
    public void shouldFindBothAcids() throws Exception {
        assertThat(search("JHKH", "H"), hasItems(1, 3));
    }

    @Test
    public void shouldFindNoAcids() throws Exception {
        assertThat(search("ABCD", "X"), is(empty()));
    }

    @Test
    public void shouldFindAllAcidCopies() throws Exception {
        assertThat(search("DDD", "D"), hasItems(0, 1, 2));
    }

    @Test
    public void shouldRejectEmptyPattern() throws Exception {
        catchException(sut).searchIndex("ABCD", "");

        assertThat(caughtException().getMessage(), is("Pattern cannot be empty"));
    }

    private Collection<Integer> search(String protein, String pattern) {
        return sut.searchIndex(protein, pattern);
    }
}
