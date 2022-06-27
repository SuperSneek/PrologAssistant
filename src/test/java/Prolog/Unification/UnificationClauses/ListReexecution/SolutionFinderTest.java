package Prolog.Unification.UnificationClauses.ListReexecution;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SolutionFinderTest {

    @Test
    public void testFindSimpleSolution() {
        SolutionFinder Finder = new SolutionFinder(3, 1);
        assertTrue(Finder.hasNext());
        assertArrayEquals(Finder.next(), new int[]{3});
        assertFalse(Finder.hasNext());
    }

    @Test
    public void testTwoSolutions() {
        SolutionFinder Finder = new SolutionFinder(1, 2);
        assertTrue(Finder.hasNext());
        assertArrayEquals(Finder.next(), new int[]{0,1});
        assertTrue(Finder.hasNext());
        assertArrayEquals(Finder.next(), new int[]{1, 0});
        assertFalse(Finder.hasNext());
    }

    @Test
    public void testManySolutions() {
        SolutionFinder Finder = new SolutionFinder(3, 3);
        assertTrue(Finder.hasNext());
        assertArrayEquals(Finder.next(), new int[]{0, 0, 3});
        assertTrue(Finder.hasNext());
        assertArrayEquals(Finder.next(), new int[]{0, 1, 2});
        assertTrue(Finder.hasNext());
        assertArrayEquals(Finder.next(), new int[]{0, 2, 1});
        assertTrue(Finder.hasNext());
    }



}