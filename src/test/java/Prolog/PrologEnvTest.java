package Prolog;

import Prolog.Terms.Term;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class PrologEnvTest {


    @Test
    public void testIterator() {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("test");
        Iterator<PlPattern> p = env.findMatchingPatterns(Term.textToTerm("test"));
        assertTrue(p.hasNext());
        p.next();
        assertFalse(p.hasNext());
        p = env.findMatchingPatterns(Term.textToTerm("test"));
        assertTrue(p.hasNext());
        p.next();
        assertFalse(p.hasNext());
    }

}