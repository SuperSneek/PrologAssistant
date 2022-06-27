package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Variable;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VariableDomain extends UnificationClauseCarrier {

    public VariableDomain(List<Variable> variables, PList list) {
        this.variables = variables;
        this.list = list;
        variableLengths = new int[variables.size()];
        size = variables.size();
        listLength = list.length();
        finder = new SolutionFinder(listLength, size);
    }

    public VariableDomain merge(VariableDomain other) {
        variables = Stream.concat(variables.stream(), other.variables.stream())
                .collect(Collectors.toList());
        list = list.concat(other.list);
        return new VariableDomain(variables, list);
    }

    int[] variableLengths;

    private PList list;

    private int size;
    private int listLength;

    private List<Variable> variables;

    SolutionFinder finder;

    @Override
    public boolean hasNext() {
        return finder.hasNext();
    }

    @Override
    public List<UnificationClause> next() {
        //See if we can generate a solution without changing a variable length
        return generateSolution(finder.next());
    }

    public List<UnificationClause> generateSolution(int[] solution) {
        List<UnificationClause> out = new ArrayList<>();
        PList outList = list;
        for (int i = 0; i < solution.length; i++) {
            PList piece = outList.take(solution[i]);
            outList = outList.drop(solution[i]);
            out.add(new UnificationClause(variables.get(i), piece));
        }
        return out;
    }
}
