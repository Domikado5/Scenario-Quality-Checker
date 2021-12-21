package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sqc.rest.ScenarioQualityCheckerController;

import java.util.List;

/**
 * Class stores parametres and calls selected visitors
 */

public class Step{
    /**
     * Stores logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Step.class);


    /**
     * @param Keyword string Stores parameter keyword
     */
    private String Keyword;




    /**
     * @param Content string Stores parameter content
     */
    private String Content;


    /**
     * @param SubScenerio Stores List of Steps
     */
    private List<Step> SubScenario;


    /**
     * Class constructor sets private parameters
     * @param keyword string Stores parameter keyword
     * @param content string Stores parameter content
     * @param subscenario list of steps Stores List of Steps (sub scenario)
     */
    public Step(String keyword, String content, List<Step> subscenario) {
        this.Keyword = keyword;
        this.Content = content;
        this.SubScenario = subscenario;
        logger.info("Created New Step Object");
        logger.debug("keyword: " + keyword + " content: " + content + "subscenario: " + subscenario);
    }

    /**
     * Method getKeyword
     * @return keyword
     */
    public String getKeyword(){
        logger.info("Returned keyword");
        return this.Keyword;
    }

    /**
     * Method getContent
     * @return content
     */
    public String getContent(){
        logger.info("Returned content");
        return  this.Content;
    }

    /**
     * Method getSubScenerio
     * @return subscenerio
     */
    public List<Step> getSubScenario(){
        logger.info("Returned Sub Scenario");
        return  this.SubScenario;
    }
}