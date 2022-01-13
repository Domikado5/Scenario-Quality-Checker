package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class stores parameters and calls selected Visitors
 */
public class Scenario extends  Element{
    /**
     * Stores logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Scenario.class);
    /**
     * Attribute that store scenario title
     */
    private String title;
    /**
     * Attribute that store scenario list of actors
     */
    private List<Actor> actors;
    /**
     * Attribute that store scenario list of system actors
     */
    private List<Actor> system_actors;
    /**
     * Attribute that store scenario list of steps
     */
    private List<Step> steps;
    /**
     * Attribute that store max level of steps
     */
    private Integer maxLevel;

    /**
     * Class constructor - sets private attributes
     * @param title string - scenario title
     * @param actors List of Actors Class - list of actors
     * @param system_actors List of System Actors Class - list of system actors
     * @param steps List of Steps - list of steps
     * @param maxLevel Max Level - max level of steps to display
     */
    public Scenario(String title, List<Actor> actors, List<Actor> system_actors, List<Step> steps, Integer maxLevel){
        this.title = title;
        this.actors = actors;
        this.system_actors = system_actors;
        this.steps = steps;
        this.maxLevel = maxLevel;
        logger.info("Created New Scenario Object");
        logger.debug("title: " + title + " actors: " + actors.toString() + " system_actors: " + system_actors.toString() + " steps: " + steps.toString() + " maxLevel: " + maxLevel);
    }

    // Getters

    /**
     * Method getter returns title
     * @return string - scenario title
     */
    public String getTitle(){
        logger.info("Initialized getTitle...");
        logger.debug(this.title);
        return  this.title;
    }
    /**
     * Method getter returns list of steps
     * @return list of steps class - list of steps
     */
    public List<Step> getSteps(){
        logger.info("Initialized getSteps...");
        logger.debug(this.steps.toString());
        return this.steps;
    }
    /**
     * Method getter returns list of actors
     * @return list of actors class - list of actors
     */
    public List<Actor> getActors(){
        logger.info("Initialized getActors...");
        logger.debug(this.actors.toString());
        return this.actors;
    }
    /**
     * Method getter returns list of system actors
     * @return list of system actors class - list of system actors
     */
    public List<Actor> getSystem_actors(){
        logger.info("Initialized getSystem_actors...");
        logger.debug(this.system_actors.toString());
        return this.system_actors;
    }
    /**
     * Method getter returns max level of steps
     * @return integer - number that indicates how deep it should go when printing
     */
    public Integer getMaxLevel(){
        logger.info("Initialized getMaxLevel...");
        logger.debug(this.maxLevel.toString());
        return this.maxLevel;
    }

    // Accept visitor

    /**
     * Inherited Method that call certain Visitor
     * @param v Visitor interface class
     */
    @Override
    public void accept(Visitor v){
        v.visit(this);
        logger.info("Passed visitor to Scenario Object");
    }
}
