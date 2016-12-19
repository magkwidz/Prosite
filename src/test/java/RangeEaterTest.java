import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class RangeEaterTest {

    @Test
    public void shouldEatBothLengths() throws Exception {
        Collection<Integer> eaten = new RangeEater(
                new AcidEater('B'),
                2,
                4
        ).eat("ABBBC", 1);

        assertThat(eaten, containsInAnyOrder(2, 3));
    }

}