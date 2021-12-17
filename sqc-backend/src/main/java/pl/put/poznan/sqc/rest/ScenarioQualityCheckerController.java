package pl.put.poznan.sqc.rest;
import jdk.dynalink.linker.LinkerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.Counter;
import pl.put.poznan.sqc.logic.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/scenario")
public class ScenarioQualityCheckerController {

    private static final Logger logger = LoggerFactory.getLogger(ScenarioQualityCheckerController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestBody Scenario scenario) {

        logger.info("Got POST request");

        // perform the transformation, you should run your logic here, below is just a silly example
        PrinterVisitor printerVisitor = new PrinterVisitor();
        FinderVisitor finderVisitor = new FinderVisitor();
        CounterVisitor counterVisitor = new CounterVisitor();
        QualityCheckerVisitor qualityCheckerVisitor = new QualityCheckerVisitor();

        scenario.accept(printerVisitor);
        logger.debug(printerVisitor.getResults().toString());
        scenario.accept(finderVisitor);
        logger.debug(finderVisitor.getInvalidSteps().toString());
        scenario.accept(counterVisitor);
        logger.debug("Keyword Count: " + counterVisitor.getKeywordCount().toString() + " Steps Count: " + counterVisitor.getStepsCount().toString());
        scenario.accept(qualityCheckerVisitor);
        logger.debug(qualityCheckerVisitor.getErrors().toString());

        String result = "";
        result += printerVisitor.getResults().toString();
        result += "Keyword Count: " + counterVisitor.getKeywordCount().toString() + " Steps Count: " + counterVisitor.getStepsCount().toString();
        result += finderVisitor.getInvalidSteps().toString();
        result += qualityCheckerVisitor.getErrors().toString();

        return result;
    }



}