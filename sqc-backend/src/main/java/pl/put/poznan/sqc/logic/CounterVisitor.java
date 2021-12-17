package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CounterVisitor implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(CounterVisitor.class);
    private Integer keywordCount = 0;
    private Integer stepsCount = 0;

    public Integer getKeywordCount(){
        return  this.keywordCount;
    }

    public Integer getStepsCount() {
        return this.stepsCount;
    }

    private Integer countSteps(List<Step> steps){
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

    private Integer countKeyword(List<Step> steps){
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

    @Override
    public void visit(Scenario s){
        logger.info("Visiting Scenario Object...");
        this.keywordCount = this.countKeyword(s.getSteps());
        this.stepsCount = this.countSteps(s.getSteps());
        logger.debug("Steps Count: " + this.stepsCount.toString() + " Keywords Count: " + this.keywordCount.toString());
    };
}
