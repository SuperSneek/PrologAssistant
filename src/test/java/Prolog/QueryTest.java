package Prolog;

import Prolog.Unification.UnificationFailureException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    public void testListReexecQuery() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("liste([X.Y.Z])");
        Query q = env.Query(Term.textToTerm("liste([a])"));
        Map<String, Term> s = new HashMap<>();
        s = q.next();
        s = q.next();
        s = q.next();
    }

    @Test
    public void testLogicRule() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("pattern([where.is.X],Z):-known(X,Z)");
        env.LoadPattern("known(markus,einkaufen)");
        Query q = env.Query(Term.textToTerm("pattern([where.is.markus],Z)"));
        assertTrue(q.hasNext());
        Map<String, Term> s = new HashMap<>();
        s = q.next();
        assertNotNull(s);
    }

    @Test
    public void testLogicRuleWith2() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("pattern([where.is.X],Z):-known(X,Z)");
        env.LoadPattern("known(markus,einkaufen)");
        env.LoadPattern("pattern([where.Y],[ich.weiss.nicht])");
        Query q = env.Query(Term.textToTerm("pattern([where.is.markus],Z)"));
        assertTrue(q.hasNext());
        Map<String, Term> s = new HashMap<>();
        s = q.next();
        assertNotNull(s);
        assertTrue(q.hasNext());
        s = q.next();
        assertNotNull(s);
        assertFalse(q.hasNext());
    }

}