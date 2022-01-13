package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrinterVisitor implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(PrinterVisitor.class);
    private List<List<String>> results = new ArrayList<>();

    public List<List<String>> getResults() {
        return results;
    };

    private Map<List<String>, Integer> printStep(Step step, Integer Level, Integer count){
        logger.info("Initialized print Step...");
        if (Level == null){
            Level = 0;
        }
        List<String> result = new ArrayList<>();
        String stepItem = "";
        stepItem += Level + "~" + count + ".";
        count++;
        if (step.getKeyword().length() > 0){
            stepItem += step.getKeyword() + " ";
        }
        stepItem += step.getContent();
        result.add(stepItem);
        Map<List<String>, Integer> hashResult = new HashMap<>();
        if (step.getSubScenario().size() > 0){
            List<String> combinedResult = new ArrayList<>();
            Map.Entry<List<String>, Integer> entry = this.printSubScenario(step.getSubScenario(), Level+1, count).entrySet().iterator().next();
            count = entry.getValue();
            combinedResult.addAll(result);
            combinedResult.addAll(entry.getKey());
            logger.debug(combinedResult.toString());
            hashResult.put(combinedResult, count);
        }else{
            logger.debug(result.toString());
            hashResult.put(result, count);
        }

        return hashResult;
    };

    private Map<List<String>, Integer> printSubScenario(List<Step> steps, int Level, int count){
        logger.info("Initialized print SubScenario...");
        List<String> result = new ArrayList<>();
        Map<List<String>, Integer> hashResult = new HashMap<>();
        for (Step step: steps) {
            Map.Entry<List<String>, Integer> entry = this.printStep(step, Level, count).entrySet().iterator().next();
            count = entry.getValue();
            result.addAll(entry.getKey());
        }
        logger.debug(result.toString());
        hashResult.put(result, count);
        return hashResult;
    }

    @Override
    public void visit(Scenario s){
        logger.info("Visiting Scenario Object...");
        // Print title
        List<String> title = new ArrayList<>();
        title.add(s.getTitle());
        results.add(title);
        // Print Actors
        List<String> actors = new ArrayList<>();
        for (Actor actor: s.getActors()){
            actors.add(actor.getName());
        }
        results.add(actors);
        // Print System Actors
        List<String> system_actors = new ArrayList<>();
        for (Actor actor: s.getSystem_actors()){
            system_actors.add(actor.getName());
        }
        results.add(system_actors);
        // Print Steps and Sub-scenarios
        List<String> steps = new ArrayList<>();
        Integer count = 0;
        for (Step step: s.getSteps()){
            Map.Entry<List<String>, Integer> entry = this.printStep(step,0, count).entrySet().iterator().next();
            count = entry.getValue();
            steps.addAll(entry.getKey());
        }
        results.add(steps);
        logger.debug(this.results.toString());
    };
}
