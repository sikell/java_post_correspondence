package de.sikeller.theoretical.postcorrespondence.calc;

import de.sikeller.theoretical.postcorrespondence.model.Block;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;
import de.sikeller.theoretical.postcorrespondence.model.Combinator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BruteForceTreeImpl implements CorrespondenceCalculator {

    private static final int MAX_SOLUTIONS = 30;
    private static final int MAX_EXECUTION_TIME = 30000;

    @Override
    public CalcResult calc(BlockSet blockSet) {
        Set<Combinator> possibleResults = new HashSet<>();
        Set<Combinator> result = new HashSet<>();
        long startMillis = System.currentTimeMillis();
        int steps = 0;
        for (Integer i : blockSet.getSet().keySet()) {
            Block block = blockSet.get(i);
            Combinator combinator = new Combinator();
            if (combinator.valid(block)) {
                combinator.add(i, block);
                possibleResults.add(combinator);
            }
        }
        while (!possibleResults.isEmpty()
                && System.currentTimeMillis() - startMillis < MAX_EXECUTION_TIME
                && result.size() < MAX_SOLUTIONS) {
            Set<Combinator> newPossibles = new HashSet<>();
            for (Combinator combinator : possibleResults) {
                steps++;
                if (combinator.finished()) {
                    result.add(combinator);
                } else {
                    for (Integer i : blockSet.getSet().keySet()) {
                        Block block = blockSet.get(i);
                        if (combinator.valid(block)) {
                            Combinator newCombinator = combinator.copy();
                            newCombinator.add(i, block);
                            newPossibles.add(newCombinator);
                        }
                    }
                }
            }
            possibleResults = newPossibles;
        }
        return new CalcResult(result, possibleResults.size(), steps);
    }

}
