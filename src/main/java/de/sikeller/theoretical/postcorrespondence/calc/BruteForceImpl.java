package de.sikeller.theoretical.postcorrespondence.calc;

import de.sikeller.theoretical.postcorrespondence.model.Block;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;
import de.sikeller.theoretical.postcorrespondence.model.Combinator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BruteForceImpl implements CorrespondenceCalculator {

    private static final int MAX_RECURSION = 66;
    private static final int MAX_CALC_STEPS = 100000;
    private static final int MAX_SOLUTIONS = 20;
    private static final Random DETERMINISTIC_RANDOM = new Random(5L);

    @Override
    public CalcResult calc(BlockSet blockSet) {
        Set<Combinator> result = new HashSet<>();
        Set<Combinator> broken = new HashSet<>();
        AtomicLong steps = new AtomicLong();
        test(steps, blockSet, new Combinator(), result, broken, MAX_RECURSION);
        return new CalcResult(result, broken.size(), steps.get());
    }

    private void test(AtomicLong steps, BlockSet blockSet, Combinator combinator, Set<Combinator> result, Set<Combinator> broken, int maxRecursion) {
        if (steps.incrementAndGet() > MAX_CALC_STEPS || maxRecursion < 0
                || result.size() >= MAX_SOLUTIONS || combinator.largeDiff()) {
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
        Collections.shuffle(nextCombinators, DETERMINISTIC_RANDOM);
        for(Combinator c : nextCombinators) {
            test(steps, blockSet, c, result, broken, newMaxRecursion);
        }
    }

}
