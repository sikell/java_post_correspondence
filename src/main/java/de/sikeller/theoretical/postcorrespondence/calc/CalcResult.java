package de.sikeller.theoretical.postcorrespondence.calc;

import de.sikeller.theoretical.postcorrespondence.model.Combinator;
import lombok.Data;

import java.util.Set;

@Data
public class CalcResult {
    private final Set<Combinator> results;
    private final long stopped;
    private final long calculationSteps;
}
