package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FinderVisitor implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(PrinterVisitor.class);
    private List<String> InvalidSteps = new ArrayList<>();

    public List<String> getInvalidSteps() {
        return InvalidSteps;
    }

    private List<String> findNoActorSteps(List<Step> steps, Integer count, List<Actor> actors, List<Actor> system_actors){
        logger.info("Initialized findNoActorSteps...");
        Integer i = count;
        List<String> result = new ArrayList<>();

        for (Step step: steps){
            i++;
            if (this.NoActor(step, actors, system_actors) == Boolean.TRUE){
                result.add(i + ". " + step.getContent());
            }

            if (step.getSubScenario().size() > 0){
                result.addAll(this.findNoActorSteps(step.getSubScenario(), i, actors, system_actors));
                i += step.getSubScenario().size();
            }
        }
        logger.debug(result.toString());
        return result;
    }

    private Boolean NoActor(Step step, List<Actor> actors, List<Actor> system_actors){
        logger.info("Initialized NoActor...");
        String[] actor = step.getContent().split(" ");

        for (Actor a: actors){
            if (a.getName().equals(actor[0])){
                logger.debug("FALSE");
                return Boolean.FALSE;
            }
        }

        for (Actor a: system_actors){
            if (a.getName().equals(actor[0])){
                logger.debug("FALSE");
                return Boolean.FALSE;
            }
        }
        logger.debug("TRUE");
        return  Boolean.TRUE;
    }

    @Override
    public void visit(Scenario s) {
        logger.info("Visiting Scenario Object...");
        this.InvalidSteps = this.findNoActorSteps(s.getSteps(), 0, s.getActors(), s.getSystem_actors());
        logger.debug(this.InvalidSteps.toString());
    }
}
