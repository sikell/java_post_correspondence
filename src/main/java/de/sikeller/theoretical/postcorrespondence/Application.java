package de.sikeller.theoretical.postcorrespondence;

import de.sikeller.theoretical.postcorrespondence.calc.BruteForceImpl;
import de.sikeller.theoretical.postcorrespondence.calc.CorrespondenceCalculator;
import de.sikeller.theoretical.postcorrespondence.model.Block;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;

public class Application {

    public static void main(String[] args) {
        BlockSet blockSet = BlockSet.builder()
                .add(new Block("a", "baa"))
                .add(new Block("ab", "aa"))
                .add(new Block("bba", "bb"))
                .build();
        CorrespondenceCalculator calculator = new BruteForceImpl();
        System.out.println(calculator.calc(blockSet));
    }


}
