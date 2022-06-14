package Prolog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PListTest {

    @Test
    public void testEmptyList() {
        PList test = new PList(null);
        assertFalse(test.hasNext());
    }

    @Test
    public void testEmptyListAdd() {
        PList test = new PList(null);
        assertFalse(test.hasNext());
    }

    @Test
    public void testListReverse() {
        PList test = (PList) Term.textToTerm("[a,b,c]");
        Term out = Term.textToTerm("[c,b,a]");
        assertTrue(test.reverse().equals(out));
    }


}