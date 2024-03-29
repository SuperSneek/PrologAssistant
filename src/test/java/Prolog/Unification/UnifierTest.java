package Prolog.Unification;

import Prolog.Terms.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class UnifierTest {

    private HashMap<String, Term> mgu;

    @BeforeEach
    void setUp() {
        mgu = new HashMap<>();
    }

    @Test
    void testUnifyAtoms() {
        try {
            Unifier u = new Unifier(new Atom("test"), new Atom("test"), new HashMap<>());
            assertTrue(u.hasNext());
            assertEquals(u.next(), mgu);
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    @Test
    void testDontCare() {
        try {
            Unifier u = new Unifier(new Atom("test"), new DontCare(), new HashMap<>());
            assertTrue(u.hasNext());
            assertEquals(u.next(), mgu);
            assertFalse(u.hasNext());
        } catch (UnificationFailureException e) {
            fail();
        }


    }

    @Test
    void testUnifyVariables() {
        mgu.put("X", new Atom("test"));
        try {
            Unifier u = new Unifier(new Variable("X"), new Atom("test"), new HashMap<>());
            assertTrue(u.hasNext());
            Map<String, Term> mgu2 = u.next();
            assertTrue(collectionEqual(mgu.values(), mgu2.values()));
            assertTrue(collectionEqual(mgu.keySet(), mgu2.keySet()));
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    @Test
    void testUnifyListWithAtoms() {
        try {
            Unifier u = new Unifier(Term.textToTerm("[dog.cat.mouse]"), Term.textToTerm("[dog.cat.mouse]"), new HashMap<>());
            assertTrue(u.hasNext());
            assertEquals(u.next(), mgu);
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    @Test
    void testUnifyListWithVariables() {
        mgu.put("X", Term.textToTerm("[dog.cat.mouse]"));
        mgu.put("Y", Term.textToTerm("[]"));
        try {
            Unifier u = new Unifier(Term.textToTerm("[dog.cat.mouse]"), Term.textToTerm("[X.Y]"), new HashMap<>());
            assertTrue(u.hasNext());
            Map<String, Term> mgu2 = u.next();
            assertTrue(collectionEqual(mgu.values(), mgu2.values()));
            assertTrue(collectionEqual(mgu.keySet(), mgu2.keySet()));
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    @Test
    void testUnifyWithVariablesInCompound() {
        mgu.put("X", Term.textToTerm("[dog.cat.mouse]"));
        mgu.put("Y", Term.textToTerm("[]"));
        try {
            Unifier u = new Unifier(Term.textToTerm("test([dog.cat.mouse])"), Term.textToTerm("test([X.Y])"), new HashMap<>());
            assertTrue(u.hasNext());
            Map<String, Term> mgu2 = u.next();
            assertTrue(collectionEqual(mgu.values(), mgu2.values()));
            assertTrue(collectionEqual(mgu.keySet(), mgu2.keySet()));
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    @Test
    void testReexecution() throws UnificationFailureException {
        Unifier u = new Unifier(Term.textToTerm("[a.b]"), Term.textToTerm("[X.Y]"), new HashMap<>());
        Map<String, Term> s;
        assertTrue(u.hasNext());
        assertTrue(u.hasNext());
        assertTrue(u.hasNext());
        s = u.next(); //0,2
        assertTrue(u.hasNext());
        s=u.next(); //1,1
        assertTrue(u.hasNext());
        s=u.next(); //2,00
        assertFalse(u.hasNext());

    }

    @Test
    public void testEmbeddedVariable() {
        mgu.put("X", Term.textToTerm("b"));
        PList test = (PList) Term.textToTerm("[a.b.c]");
        Term out = Term.textToTerm("[a.X.c]");
        try {
            Unifier u = new Unifier(test, out, new HashMap<>());
            assertTrue(u.hasNext());
            Map<String, Term> mgu2 = u.next();
            assertTrue(collectionEqual(mgu.values(), mgu2.values()));
            assertTrue(collectionEqual(mgu.keySet(), mgu2.keySet()));
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    /**
     * This test makes sure that variables are
     * @throws UnificationFailureException If something very unexpected goes wrong
     */
    @Test
    public void testVariableCarryOver() throws UnificationFailureException {
        PList test = (PList) Term.textToTerm("[a.X.c]");
        Term out = Term.textToTerm("X");
        Unifier u = new Unifier(test, out, new HashMap<>());
        assertTrue(u.hasNext());
        assertNull(u.next());
        assertFalse(u.hasNext());
    }

    /**
     * This test makes sure that variables are
     * @throws UnificationFailureException If something very unexpected goes wrong
     */
    @Test
    public void testGenerateClauses() throws UnificationFailureException {
        PList test = (PList) Term.textToTerm("[a.X.c]");
        Term out = Term.textToTerm("X");
        Unifier u = new Unifier(test, out, new HashMap<>());
        assertTrue(u.hasNext());
        assertNull(u.next());
        assertFalse(u.hasNext());
    }


    private <Term> boolean collectionEqual(Collection<Term> a, Collection<Term> b) {
        for (Term item:
             a) {
            boolean foundMatch = false;
            for (Term otherItem:
                 b) {
                if (item.toString().equals(otherItem.toString())) {
                    foundMatch = true;
                    break;
                }
            }
            if(!foundMatch) {
                return false;
            }
        }
        return true;
    }
}