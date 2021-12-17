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

    // Getters
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

    public List<Actor> getActors(){
        logger.info("Initialized getActors...");
        logger.debug(this.actors.toString());
        return this.actors;
    }

    public List<Actor> getSystem_actors(){
        logger.info("Initialized getSystem_actors...");
        logger.debug(this.system_actors.toString());
        return this.system_actors;
    }

    // Accept visitor

    @Override
    public void accept(Visitor v){
        v.visit(this);
        logger.info("Passed visitor to Scenario Object");
    }
}
