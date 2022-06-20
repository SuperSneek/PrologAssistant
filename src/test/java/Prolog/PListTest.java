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

    @Test
    public void testListConcat() {
        PList test1 = (PList) Term.textToTerm("[a,b,c]");
        PList test2 = (PList) Term.textToTerm("[d,e,f]");
        assertTrue(test1.concat(test2).equals(Term.textToTerm("[a,b,c,d,e,f]")));
    }

    @Test
    public void takeTest() {
        PList test = (PList) Term.textToTerm("[a,b,c]");
        assertTrue(test.take(1).equals(Term.textToTerm("[a]")));
    }


}