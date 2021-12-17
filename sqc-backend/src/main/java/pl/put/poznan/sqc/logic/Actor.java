package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sqc.rest.ScenarioQualityCheckerController;

public class Actor{
    private static final Logger logger = LoggerFactory.getLogger(Actor.class);
    private String name;
    private Boolean system;

    public Actor(String name, Boolean system){
        this.name = name;
        this.system = system;
        logger.info("Created New Actor Object");
        logger.debug("name: " + name + " system: " + system.toString());
    }

    public String getName(){
        logger.info("Initialized getName...");
        logger.debug(this.name);
        return  this.name;
    }

    public Boolean getSystem() {
        logger.info("Initialized getSystem...");
        logger.debug(this.system.toString());
        return this.system;
    }
}
