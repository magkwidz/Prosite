import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RepeatedEaterTest {
    @Test
    public void shouldNotEatTheTail() throws Exception {
        Collection<Integer> eaten = new RepeatedEater(
                new WildcardEater(),
                3
        ).eat("ABCXAXX", 5);

        assertThat(eaten, is(empty()));
    }

}
