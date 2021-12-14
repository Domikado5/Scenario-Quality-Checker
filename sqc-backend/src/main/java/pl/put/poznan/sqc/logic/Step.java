package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sqc.rest.ScenarioQualityCheckerController;

import java.util.List;

public class Step extends Element{
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

    public String printStep(Integer Level){
        logger.info("Initialized print Step...");
        if (Level == null){
            Level = 0;
        }
        String result = "• ";
        if (this.Keyword.length() > 0){
            result += this.Keyword + " ";
        }
        result += this.Content;
        if (this.SubScenario.size() > 0){
            result += this.printSubScenario(Level+1);
        }
        logger.debug(result);
        return result;
    }

    public String printSubScenario(int Level){
        logger.info("Initialized print SubScenario...");
        String result = "";
        for (Step step: this.SubScenario) {
            result += "\n";
            for (int i=0; i<Level; i++){
                result += "\t";
            }
            result += step.printStep(Level);
        }
        logger.debug(result);
        return result;
    }

    public Boolean NoActor(List<Actor> actors, List<Actor> system_actors){
        logger.info("Initialized NoActor...");
        String[] actor = this.Content.split(" ");

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
    public void accept(Visitor v){
        logger.info("Passed visitor to Step Object");
        v.visit(this);
        v.visit(this.SubScenario);
        logger.debug(v.toString());
    }
}
