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
        env.LoadPattern("liste([X,Y,Z])");
        Query q = env.Query(Term.textToTerm("liste([a])"));
        Map<String, Term> s = new HashMap<>();
        s = q.next();
        s = q.next();
        s = q.next();
    }

}