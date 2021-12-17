package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PrinterVisitor implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(PrinterVisitor.class);
    private List<String> results = new ArrayList<>();

    public List<String> getResults() {
        return results;
    };

    private String printStep(Step step, Integer Level){
        logger.info("Initialized print Step...");
        if (Level == null){
            Level = 0;
        }
        String result = "- ";
        if (step.getKeyword().length() > 0){
            result += step.getKeyword() + " ";
        }
        result += step.getContent();
        if (step.getSubScenario().size() > 0){
            result += this.printSubScenario(step.getSubScenario(), Level+1);
        }
        logger.debug(result);
        return result;
    };

    private String printSubScenario(List<Step> steps, int Level){
        logger.info("Initialized print SubScenario...");
        String result = "";
        for (Step step: steps) {
            result += "\n";
            for (int i=0; i<Level; i++){
                result += "\t";
            }
            result += this.printStep(step, Level);
        }
        logger.debug(result);
        return result;
    }

    @Override
    public void visit(Scenario s){
        logger.info("Visiting Scenario Object...");
        // Print title
        results.add("Tytuł: " + s.getTitle());
        // Print Actors
        String actors = "Aktorzy: ";
        for (Actor actor: s.getActors()){
            actors += actor.getName() + " ";
        }
        results.add(actors);
        // Print System Actors
        actors = "Aktorzy Systemowi: ";
        for (Actor actor: s.getSystem_actors()){
            actors += actor.getName() + " ";
        }
        results.add(actors);
        // Print Steps and Sub-scenarios
        String steps = "Kroki: \n";
        for (Step step: s.getSteps()){
            steps += this.printStep(step,0);
            steps += "\n";
        }
        results.add(steps);
        logger.debug(this.results.toString());
    };
}
