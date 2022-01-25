package pl.put.poznan.sqc.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

class CounterVisitorTest {
    private CounterVisitor visitor;

    @BeforeEach
    void setUp(){
        this.visitor = new CounterVisitor();
    }

    @Test
    void checkCountSteps1(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        Assertions.assertEquals(this.visitor.getStepsCount(), 1);
    }

    @Test
    void checkCountSteps2(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Step step2 = new Step("", "Zbyszek śpi", subScenario);
        Step step3 = new Step("", "Zbyszek gra", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);
        steps.add(step2);
        steps.add(step3);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        Assertions.assertEquals(this.visitor.getStepsCount(), 3);
    }

    @Test
    void checkCountSteps3(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();
        List<Step> subScenario2 = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Step step2 = new Step("", "Zbyszek nie śpi", subScenario);
        subScenario2.add(step2);
        Step step3 = new Step("IF", "Zbyszek gra", subScenario2);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);
        steps.add(step3);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        Assertions.assertEquals(this.visitor.getStepsCount(), 3);
    }

    @Test
    void checkCountKeywords1(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();
        List<Step> subScenario2 = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Step step2 = new Step("", "Zbyszek nie śpi", subScenario);
        subScenario2.add(step2);
        Step step3 = new Step("IF", "Zbyszek gra", subScenario2);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);
        steps.add(step3);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        Assertions.assertEquals(this.visitor.getKeywordCount(), 1);
    }

    @Test
    void checkCountKeywords2(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        Assertions.assertEquals(this.visitor.getKeywordCount(), 0);
    }
}