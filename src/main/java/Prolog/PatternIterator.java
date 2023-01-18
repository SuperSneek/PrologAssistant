package Prolog;

import Prolog.Terms.Term;

import java.util.Iterator;
import java.util.List;

public class PatternIterator implements Iterator<PlPattern> {

    private PrologEnv env;
    int progression = 0;
    List<PlPattern> patterns;
    Term match;

    public PatternIterator(PrologEnv env, Term match) {
        patterns = env.loadedPatterns;
        this.match = match;
    }

    private boolean hasNextRecursion(int index) {
        if(index > patterns.size() -1 ) {
            return false;
        }
        if(patterns.get(index).matches(match)) {
            return true;
        }
        return hasNextRecursion(index + 1);
    }

    @Override
    public boolean hasNext() {
       return hasNextRecursion(progression);
    }

    @Override
    public PlPattern next() {
        if(!hasNext()) {
            return null;
        }
        progression++;
        if(patterns.get(progression - 1).matches(match)) {
            return patterns.get(progression - 1);
        }
        return next();
    }
}
