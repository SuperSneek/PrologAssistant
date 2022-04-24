package Prolog;

public class Atom extends Term{

    public Atom(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean hasSolution() {
        return true;
    }
}
