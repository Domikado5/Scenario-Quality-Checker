package pl.put.poznan.sqc.rest;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.dynalink.linker.LinkerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ObjectNode post(@RequestBody Scenario scenario) {

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


        ObjectNode objectNode = mapper.createObjectNode();
        List<List<String>> text = printerVisitor.getResults();

        objectNode.put("title", text.get(0).get(0)); // JSON title
        ArrayNode actors = objectNode.putArray("actors"); // JSON actors
        for (String item: text.get(1)){
            actors.add(item);
        }
        ArrayNode system_actors = objectNode.putArray("system_actors"); // JSON system actors
        for (String item: text.get(2)){
            system_actors.add(item);
        }
        ArrayNode steps = objectNode.putArray("steps"); // JSON steps
        for (String item: text.get(3)){
            steps.add(item);
        }
        objectNode.put("keyword_count", counterVisitor.getKeywordCount()); // JSON keyword count
        objectNode.put("steps_count", counterVisitor.getStepsCount()); // JSON steps count
        ArrayNode invalidStepsNode = objectNode.putArray("invalid_steps"); // JSON invalid steps
        for (String item: finderVisitor.getInvalidSteps()){
            invalidStepsNode.add(item);
        }
        ArrayNode errorsNode = objectNode.putArray("errors"); // JSON errors
        for (String item : qualityCheckerVisitor.getErrors()){
            errorsNode.add(item);
        }

        return objectNode;
    }



}