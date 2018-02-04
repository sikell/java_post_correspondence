package de.sikeller.theoretical.postcorrespondence.calc;

import de.sikeller.theoretical.postcorrespondence.model.Block;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;
import de.sikeller.theoretical.postcorrespondence.model.Combinator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class BruteForceImpl implements CorrespondenceCalculator {

    private static final int MAX_RECURSION = 66;
    private static final int MAX_CALC_STEPS = 100000;
    private static final int MAX_SOLUTIONS = 20;

    @Override
    public CalcResult calc(BlockSet blockSet) {
        Set<Combinator> result = new HashSet<>();
        Set<Combinator> broken = new HashSet<>();
        AtomicLong steps = new AtomicLong();
        test(steps, blockSet, new Combinator(), result, broken, MAX_RECURSION);
        return new CalcResult(result, broken.size(), steps.get());
    }

    private void test(AtomicLong steps, BlockSet blockSet, Combinator combinator, Set<Combinator> result, Set<Combinator> broken, int maxRecursion) {
        if (steps.incrementAndGet() > MAX_CALC_STEPS || maxRecursion < 0 || result.size() >= MAX_SOLUTIONS) {
            broken.add(combinator);
            return;
        }
        int newMaxRecursion = maxRecursion - 1;
        if (combinator.finished()) {
            result.add(combinator);
            return;
        }
        List<Combinator> nextCombinators = new LinkedList<>();
        for (Integer i : blockSet.getSet().keySet()) {
            Block block = blockSet.get(i);
            if (combinator.valid(block)) {
                Combinator newCombinator = combinator.copy();
                newCombinator.add(i, block);
                nextCombinators.add(newCombinator);
            }
        }
        for(Combinator c : nextCombinators) {
            if (combinator.finished()) {
                result.add(combinator);
                return;
            }
            test(steps, blockSet, c, result, broken, newMaxRecursion);
        }
    }

}
