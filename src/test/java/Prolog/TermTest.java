package Prolog;

import Prolog.Terms.Atom;
import Prolog.Terms.Compound;
import Prolog.Terms.PList;
import Prolog.Terms.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

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

    @Test
    public void TestMatchCompoundList() {
        Term c = (new Compound("test", new Term[] {Term.textToTerm("[a.b.c]")}));
        assertTrue(c.equals(Term.textToTerm("test([a.b.c])")));
    }
    @Test
    public void TestMatchLists() {
        Term c = (new PList(new Atom("amogus"), new PList(new Atom("testatom"), new PList(null))));
        assertTrue(c.equals(Term.textToTerm("[amogus.testatom]")));
    }

    @Test
    public void TestSubstitution() {
        Term c = (new Compound("test", new Term[] {Term.textToTerm("[a.A.c]")}));
        HashMap<String, Term> m = new HashMap<>();
        m.put("A", new Atom("dogie"));
        assertTrue(c.substitute(m).equals(new Compound("test", new Term[] {Term.textToTerm("[a.dogie.c]")})));
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