package de.sikeller.theoretical.postcorrespondence.rest;

import de.sikeller.theoretical.postcorrespondence.calc.CalcResult;
import de.sikeller.theoretical.postcorrespondence.calc.CorrespondenceCalculator;
import de.sikeller.theoretical.postcorrespondence.model.BlockSet;
import de.sikeller.theoretical.postcorrespondence.model.CalcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private CorrespondenceCalculator correspondenceCalculator;

    @Autowired
    public Controller(CorrespondenceCalculator correspondenceCalculator) {
        this.correspondenceCalculator = correspondenceCalculator;
    }

    @PostMapping("/calc")
    public CalcResult calc(@RequestBody CalcConfig input) {
        return correspondenceCalculator.calc(input);
    }

}
