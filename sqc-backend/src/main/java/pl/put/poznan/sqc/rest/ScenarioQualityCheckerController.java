package pl.put.poznan.sqc.rest;
import jdk.dynalink.linker.LinkerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.Scenario;
import pl.put.poznan.sqc.logic.Visitor;

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
        // log the parameters
        logger.debug(scenario.returnScenario());

        // perform the transformation, you should run your logic here, below is just a silly example
        Visitor visitor = new Visitor();
        scenario.accept(visitor);

        return visitor.getRaport();
    }



}