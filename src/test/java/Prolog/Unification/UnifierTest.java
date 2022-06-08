package Prolog.Unification;

import Prolog.PList;
import Prolog.Term;
import Prolog.Variable;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import Prolog.Atom;
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
            Unifier u = new Unifier(Term.textToTerm("[dog,cat,mouse]"), Term.textToTerm("[dog,cat,mouse]"), new HashMap<>());
            assertTrue(u.hasNext());
            assertEquals(u.next(), mgu);
        } catch (UnificationFailureException e) {
            fail();
        }
    }

    @Test
    void testUnifyListWithVariables() {
        mgu.put("X", Term.textToTerm("[dog,cat,mouse]"));
        mgu.put("Y", Term.textToTerm("[]"));
        try {
            Unifier u = new Unifier(Term.textToTerm("[dog,cat,mouse]"), Term.textToTerm("[X,Y]"), new HashMap<>());
            assertTrue(u.hasNext());
            Map<String, Term> mgu2 = u.next();
            assertTrue(collectionEqual(mgu.values(), mgu2.values()));
            assertTrue(collectionEqual(mgu.keySet(), mgu2.keySet()));
        } catch (UnificationFailureException e) {
            fail();
        }
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