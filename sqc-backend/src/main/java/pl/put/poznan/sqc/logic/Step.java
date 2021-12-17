package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sqc.rest.ScenarioQualityCheckerController;

import java.util.List;

public class Step{
    private static final Logger logger = LoggerFactory.getLogger(Step.class);

    private String Keyword;
    private String Content;
    private List<Step> SubScenario;

    // Add validation and throwbacks for Step constructor
    public Step(String keyword, String content, List<Step> subscenario) {
        this.Keyword = keyword;
        this.Content = content;
        this.SubScenario = subscenario;
        logger.info("Created New Step Object");
        logger.debug("keyword: " + keyword + " content: " + content + "subscenario: " + subscenario);
    }

    public String getKeyword(){
        logger.info("Returned keyword");
        return this.Keyword;
    }

    public String getContent(){
        logger.info("Returned content");
        return  this.Content;
    }

    public List<Step> getSubScenario(){
        logger.info("Returned Sub Scenario");
        return  this.SubScenario;
    }
}
