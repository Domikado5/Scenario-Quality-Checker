package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sqc.rest.ScenarioQualityCheckerController;

/**
* Class stores parameters
 */
public class Actor{
    /**
     * Stores logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Actor.class);
    /**
     * Attribute that store actor's name
     */
    private String name;
    /**
     * Attribute that store information if actor is of type system
     */
    private Boolean system;

    /**
     * Class constructor - sets private attributes
     * @param name string - actor's name
     * @param system boolean - actor's type (True - System Actor, False - Normal Actor)
     */
    public Actor(String name, Boolean system){
        this.name = name;
        this.system = system;
        logger.info("Created New Actor Object");
        logger.debug("name: " + name + " system: " + system.toString());
    }

    /**
     * Method getter returns name attribute
     * @return string - actor's name
     */
    public String getName(){
        logger.info("Initialized getName...");
        logger.debug(this.name);
        return  this.name;
    }
    /**
     * Method getter returns system information
     * @return boolean - actor's type system
     */
    public Boolean getSystem() {
        logger.info("Initialized getSystem...");
        logger.debug(this.system.toString());
        return this.system;
    }
}
