package de.sikeller.theoretical.postcorrespondence.calc;

import de.sikeller.theoretical.postcorrespondence.model.Block;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;
import de.sikeller.theoretical.postcorrespondence.model.CalcConfig;
import de.sikeller.theoretical.postcorrespondence.model.Combinator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BruteForceTreeImpl implements CorrespondenceCalculator {

    private static final int MAX_SOLUTIONS = 100;
    private static final int MAX_EXECUTION_TIME = 15000;

    @Override
    public CalcResult calc(CalcConfig config) {
        BlockSet blockSet = config.getBlocks();
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
        final int maxSolutions = Math.max(1, Math.min(config.getSolutions(), MAX_SOLUTIONS));
        while (!possibleResults.isEmpty()
                && System.currentTimeMillis() - startMillis < MAX_EXECUTION_TIME
                && result.size() < maxSolutions) {
            Set<Combinator> newPossibles = new HashSet<>();
            for (Combinator combinator : possibleResults) {
                steps++;
                if(System.currentTimeMillis() - startMillis >= MAX_EXECUTION_TIME
                        || result.size() >= maxSolutions) {
                    break;
                }
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
