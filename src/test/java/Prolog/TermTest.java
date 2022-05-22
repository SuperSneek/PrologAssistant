package Prolog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TermTest {

    @BeforeEach
    public void before() {

    }

    @Test
    public void TestMatchAtom() {
        assertTrue(new Atom("test").equals(Term.textToTerm("test")));
    }

    @Test
    public void TestMatchCompoundNormal() {
        Term c = (new Compound("test", new Term[] {new Atom("testatom"), new Atom("doggie")}));
        assertTrue(c.equals(Term.textToTerm("test(testatom,doggie)")));
    }

    //@Test
    //public void s2() {
    //    assertTrue(new Atom("test").equals(Term.textToTerm("test")));
    //}
//
    //@Test
    //public void s3() {
    //    assertTrue(new Atom("test").equals(Term.textToTerm("test")));
    //}
}