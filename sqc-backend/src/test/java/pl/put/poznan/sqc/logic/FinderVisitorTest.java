package pl.put.poznan.sqc.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinderVisitorTest {
    private FinderVisitor visitor;

    @BeforeEach
    void setUp(){
        this.visitor = new FinderVisitor();
    }

    @Test
    void checkOneStepWithoutActor(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();
        List<Step> subScenario2 = new ArrayList<>();

        Step step2 = new Step("", "je zupe", subScenario2);
        subScenario.add(step2);
        Step step = new Step("IF", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getInvalidSteps();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "2. je zupe");
    }

    @Test
    void checkTweStepsWithoutActor(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();
        List<Step> subScenario2 = new ArrayList<>();

        Step step2 = new Step("", "je zupe", subScenario2);
        subScenario.add(step2);
        Step step = new Step("IF", "je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getInvalidSteps();

        Assertions.assertEquals(errors.size(), 2);
        Assertions.assertEquals(errors.get(0), "1. je");
        Assertions.assertEquals(errors.get(1), "2. je zupe");
    }

    @Test
    void checkThreeStepsWithoutActor(){ // two steps dont have actor, one step is valid
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();
        List<Step> subScenario2 = new ArrayList<>();

        Step step2 = new Step("", "Zbyszek je zupe", subScenario2);
        Step step3 = new Step("", "je zupe", subScenario2);
        subScenario.add(step2);
        Step step = new Step("IF", "je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);
        steps.add(step3);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getInvalidSteps();

        Assertions.assertEquals(errors.size(), 2);
        Assertions.assertEquals(errors.get(0), "1. je");
        Assertions.assertEquals(errors.get(1), "3. je zupe");
    }
}