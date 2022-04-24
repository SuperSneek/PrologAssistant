package Prolog;

import java.util.Arrays;

public class Compound extends Term {

    Term[] values;

    public Compound(String name, Term[] values) {
        this.name = name;
        this.values = values;
    }

    @Override
    public String toString() {
        return name + "(" + Arrays.toString(values) + ")";
    }

    @Override
    public boolean hasSolution() {
        for (Term t: values) {
            if(!t.hasSolution()) {
                return false;
            }
        }
        return true;
    }
}
