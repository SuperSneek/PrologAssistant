package Prolog.Unification.UnificationClauses.ListReexecution;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SolutionFinderTest {

    @Test
    public void testFindSimpleSolution() {
        SolutionFinder Finder = new SolutionFinder(3, 3, 0, 3);
        assertTrue(Finder.hasNext());
        assertTrue(Arrays.equals(Finder.next(), new int[] {3,0, 0}));
    }

}