package Prolog;

import Prolog.Terms.Term;
import Prolog.Unification.UnificationFailureException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {


    @Test
    public void testRuleUnification() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("test(X):-[cat(X)]");
        assertNull(env.Query(Term.textToTerm("test(dog)")).next());
        env.LoadPattern("cat(dog)");
        Query q = env.Query(Term.textToTerm("test(dog)"));
        assertNotNull(q.next());
    }

    @Test
    public void testRuleReexec() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("test(X):-[cat(X).dog(X)]");
        env.LoadPattern("cat(dog)");
        env.LoadPattern("dog(dog)");
        env.LoadPattern("dog(Y)");
        Query q = env.Query(Term.textToTerm("test(dog)"));
        assertNotNull(q.next());
        assertNotNull(q.next());
    }

    @Test
    public void testRuleFailure() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("test(X):-[cat(X)]");
        env.LoadPattern("cat(mouse)");
        Query q = env.Query(Term.textToTerm("test(dog)"));
        assertNull(env.Query(Term.textToTerm("test(dog)")).next());
    }

    @Test
    public void testRuleSuccess() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("test(X):-[cat(X)]");
        env.LoadPattern("cat(mouse)");
        assertNotNull(env.Query(Term.textToTerm("test(mouse)")).next());
    }

}