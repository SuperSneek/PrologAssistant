package Prolog;

import Prolog.Terms.Term;

import java.util.Map;

/**
 * This class has no purpose but as a placeholder when queries are initialized
 */
public class EmptySolution implements Solution{
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Map<String, Term> next() {
        return null;
    }
}
