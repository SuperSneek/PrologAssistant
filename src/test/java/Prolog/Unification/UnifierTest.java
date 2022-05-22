package Prolog.Unification;

import Prolog.Term;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import Prolog.Atom;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class UnifierTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testUnifyAtoms() {
        HashMap<String, Term> mgu = new HashMap<>();
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

    }
}