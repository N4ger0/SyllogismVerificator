import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolysyllogismTest {
    @Test
    void testEquals() {
        Polysyllogism p1 = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("c", "d", false, true),
                new Proposition("c", "a", true, true)
        ));

        Polysyllogism p2 = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("c", "d", false, true),
                new Proposition("c", "a", true, true)
        ));

        assertEquals(p1, p2);
    }

    @Test
    void testNotEqualsSameStructure() {
        Polysyllogism p1 = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("c", "c", false, true),
                new Proposition("c", "a", true, true)
        ));

        Polysyllogism p2 = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("c", "d", false, true),
                new Proposition("c", "a", true, true)
        ));

        assertNotEquals(p1, p2);
    }

    @Test
    void testNotEqualsDifferentStructure() {
        Polysyllogism p1 = new Polysyllogism(List.of(
                new Proposition("c", "d", false, true),
                new Proposition("a", "b", true, true),
                new Proposition("c", "a", true, true)
        ));

        Polysyllogism p2 = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("c", "d", false, true),
                new Proposition("c", "a", true, true)
        ));

        assertNotEquals(p1, p2);
    }

    @Test
    void testSort() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("c", "d", false, true),
                new Proposition("d", "e", false, true),
                new Proposition("a", "b", true, true),
                new Proposition("g", "h", false, true),
                new Proposition("b", "c", false, true),
                new Proposition("e", "f", false, true),
                new Proposition("f", "g", false, true),
                new Proposition("a", "h", true, true)
        ));

        assertTrue(polysyllogism.sort());

        assertEquals(polysyllogism,
                new Polysyllogism(List.of(
                        new Proposition("a", "b", true, true),
                        new Proposition("b", "c", false, true),
                        new Proposition("c", "d", false, true),
                        new Proposition("d", "e", false, true),
                        new Proposition("e", "f", false, true),
                        new Proposition("f", "g", false, true),
                        new Proposition("g", "h", false, true),
                        new Proposition("a", "h", true, true)
                ))
        );
    }

    @Test
    void testSortInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("b", "a", false, true),
                new Proposition("d", "b", false, true),
                new Proposition("a", "c", true, true)
        ));

        assertFalse(polysyllogism.sort());
    }

    @Test
    void testSortInvalid2() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("b", "a", false, true),
                new Proposition("c", "b", false, true),
                new Proposition("a", "f", true, true)
        ));

        assertFalse(polysyllogism.sort());
    }

    @Test
    void testSortInvalid3() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("f", "g", false, true),
                new Proposition("c", "b", false, true),
                new Proposition("a", "c", true, true)
        ));

        assertFalse(polysyllogism.sort());
    }
}
