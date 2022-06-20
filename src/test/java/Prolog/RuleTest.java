package Prolog;

import Prolog.Unification.UnificationFailureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    public void testRuleUnification() throws UnificationFailureException {
        PrologEnv env = new PrologEnv();
        env.LoadPattern("test(X):-cat(X)");
        assertThrows(UnificationFailureException.class, (Executable) env.Query(Term.textToTerm("test(dog)")));
        ass
    }

}