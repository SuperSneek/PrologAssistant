package Prolog;

public class Rule extends Term {

    Term left;
    Term right;

    public Rule(Term left, Term right) {
        this.left = left;
        this.right = right;
        name = left.name;
    }

    @Override
    public String toString() {
        return left.toString() + ":-" + right.toString();
    }

    @Override
    public boolean hasSolution() {
        return right.hasSolution();
    }
}
