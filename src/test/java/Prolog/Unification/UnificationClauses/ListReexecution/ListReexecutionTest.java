package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.Terms.PList;
import Prolog.Terms.Term;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ListReexecutionTest {

    @Test
    public void testAtomReexecution() throws UnificationFailureException {
        UnificationClauseCarrier reexec = new ListReexecution().generateCarrier((PList) Term.textToTerm("[a.b.c]"),
                (PList) Term.textToTerm("[a.b.c]"));
        assertTrue(reexec.hasNext());
        List<UnificationClause> s = reexec.next();
    }

    @Test
    public void testSingleVariable() throws UnificationFailureException {
        UnificationClauseCarrier reexec = new ListReexecution().generateCarrier((PList) Term.textToTerm("[X]"),
                (PList) Term.textToTerm("[a.b.c]"));
        assertTrue(reexec.hasNext());
        List<UnificationClause> s = reexec.next();
        assertFalse(reexec.hasNext());
    }

    @Test
    public void testMultipleVariables() throws UnificationFailureException {
        UnificationClauseCarrier reexec = new ListReexecution().generateCarrier((PList) Term.textToTerm("[X.Y]"),
                (PList) Term.textToTerm("[a.b]"));
        assertTrue(reexec.hasNext());
        List<UnificationClause> s = reexec.next(); //0.2
        assertTrue(reexec.hasNext());
        s = reexec.next(); //1.1
        assertTrue(reexec.hasNext());
        s = reexec.next(); //2.0
        assertFalse(reexec.hasNext());
    }

}