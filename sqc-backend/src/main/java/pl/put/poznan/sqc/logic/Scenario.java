package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Scenario extends  Element{
    private static final Logger logger = LoggerFactory.getLogger(Scenario.class);
    private String title;
    private List<Actor> actors;
    private List<Actor> system_actors;
    private List<Step> steps;

    public Scenario(String title, List<Actor> actors, List<Actor> system_actors, List<Step> steps){
        this.title = title;
        this.actors = actors;
        this.system_actors = system_actors;
        this.steps = steps;
        logger.info("Created New Scenario Object");
        logger.debug("title: " + title + " actors: " + actors.toString() + " system_actors: " + system_actors.toString() + " steps: " + steps.toString());
    }

    public String getTitle(){
        logger.info("Initialized getTitle...");
        logger.debug(this.title);
        return  this.title;
    }

    public List<Step> getSteps(){
        logger.info("Initialized getSteps...");
        logger.debug(this.steps.toString());
        return this.steps;
    }

    public Integer countSteps(List<Step> steps){
        logger.info("Initialized countSteps...");
        Integer i = 0;
        for (Step step: steps){
            i++;
            if (step.getSubScenario().size() > 0){
                i += this.countSteps(step.getSubScenario());
            }
        }
        logger.debug(i.toString());
        return i;
    }

    public Integer countKeyword(List<Step> steps){
        logger.info("Initialized countKeyword...");
        Integer i = 0;
        for (Step step: steps){
            if (step.getKeyword().length() > 0){
                i++;
            }

            if (step.getSubScenario().size() > 0){
                i += this.countKeyword(step.getSubScenario());
            }
        }
        logger.debug(i.toString());
        return i;
    }

    public List<String> findNoActorSteps(List<Step> steps, Integer count){
        logger.info("Initialized findNoActorSteps...");
        Integer i = count;
        List<String> result = new ArrayList<>();

        for (Step step: steps){
            i++;
            if (step.NoActor(this.actors, this.system_actors) == Boolean.TRUE){
                result.add(i + ". " + step.getContent());
            }

            if (step.getSubScenario().size() > 0){
                result.addAll(this.findNoActorSteps(step.getSubScenario(), i));
                i += step.getSubScenario().size();
            }
        }
        logger.debug(result.toString());
        return result;
    }

    public String returnScenario(){
        logger.info("Initialized returnScenario...");
        String result = "";
        result += "Tytuł: " + this.title + "\n";
        result += "Aktorzy: ";
        for (Actor actor: this.actors){
            result += actor.getName() + " ";
        }
        result += "\n";
        result += "Aktorzy Systemowi: ";
        for (Actor actor: this.system_actors){
            result += actor.getName() + " ";
        }
        result += "\n\n";

        for (Step step: this.steps){
            result += step.printStep(0);
            result += "\n";
        }
        logger.debug(result);
        return result;
    }

    @Override
    public void accept(Visitor v){
        v.visit(this);
        logger.info("Passed visitor to Scenario Object");
        if (this.actors.size() == 0) {
            // throw no_actors
        }

        for (Actor a: this.actors){
            a.accept(v); // pass actor to visitor
        }

        if (this.system_actors.size() == 0) {
            // throw no_system_actors
        }

        for (Actor sa: this.system_actors){
            sa.accept(v); // pass system actor to visitor
        }

        if (this.steps.size() == 0) {
            // throw no_steps
        }

        for (Step s: this.steps){
            s.accept(v); // pass steps to visitor
        }
    }
}
