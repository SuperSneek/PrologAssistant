package Prolog;

import Prolog.Terms.*;
import Prolog.Unification.UnificationFailureException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class PrologEnv extends Thread {


    List<PlPattern> loadedPatterns = new LinkedList<>();

    public void LoadModule(URL path) {
        try (Stream<String> stream = Files.lines(Path.of(path.toURI()))) {
            stream.forEach(this::LoadPattern);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void LoadPattern(String input) {
        PlPattern p = PlPattern.textToPattern(input, this);
        loadedPatterns.add(p);
    }

    public Query ProcessWords(String[] words) throws UnificationFailureException {
        Stack<Term> s = new Stack<>();
        s.addAll(Arrays.stream(words).map(Atom::new).toList());
        PList l = PList.fromQueue(s);
        return Query(new Compound("pattern", new Term[]{l, new Variable("RESULT")}));
    }

    public Query Query(Term query) throws UnificationFailureException {
        return new Query(query, this);
    }

    public Query Query(Term query, Map<String, Term> vars) throws UnificationFailureException {
        return new Query(query, this, vars);
    }

    public Iterator<PlPattern> findMatchingPatterns(Term match) {
        //return loadedPatterns.stream().filter(x -> (x.matches(match))).iterator();
        return new PatternIterator(this, match);
    }

    @Override
    public java.lang.String toString() {
        StringBuilder sb = new StringBuilder();
        for (PlPattern p :
                loadedPatterns) {
            sb.append(p.toString()).append(", ");
        }
        return sb.toString();
    }
}
