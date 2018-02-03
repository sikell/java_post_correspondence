package de.sikeller.theoretical.postcorrespondence.calc;

import de.sikeller.theoretical.postcorrespondence.model.Block;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;
import de.sikeller.theoretical.postcorrespondence.model.Combinator;

import java.util.HashSet;
import java.util.Set;

public class BruteForceImpl implements CorrespondenceCalculator {

    private static final int MAX_RECURSION = 100;

    @Override
    public CalcResult calc(BlockSet blockSet) {
        HashSet<Combinator> result = new HashSet<>();
        HashSet<Combinator> broken = new HashSet<>();
        test(blockSet, new Combinator(), result, broken, MAX_RECURSION);
        return new CalcResult(result, broken);
    }

    private static void test(BlockSet blockSet, Combinator combinator, Set<Combinator> result, Set<Combinator> broken, int maxRecursion) {
        if (maxRecursion < 0) {
            broken.add(combinator);
            System.err.println("Max recursion reached: " + combinator);
            return;
        }
        int newMaxRecursion = maxRecursion - 1;
        if (combinator.finished()) {
            result.add(combinator);
            return;
        }
        for (Integer i : blockSet.getSet().keySet()) {
            Block block = blockSet.get(i);
            if (combinator.valid(block)) {
                Combinator newCombinator = combinator.copy();
                newCombinator.add(i, block);
                test(blockSet, newCombinator, result, broken, newMaxRecursion);
            }
        }
    }

}
