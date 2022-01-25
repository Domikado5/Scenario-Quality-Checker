package pl.put.poznan.sqc.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class QualityCheckerVisitorTest {
    private QualityCheckerVisitor visitor;

    @BeforeEach
    void setUp(){
        this.visitor = new QualityCheckerVisitor();
    }

    @Test
    void checkScenarioGetters(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);
        Scenario mockObject = mock(scenario.getClass());

        when(mockObject.getMaxLevel()).thenReturn(0);
        when(mockObject.getSteps()).thenReturn(steps);
        when(mockObject.getActors()).thenReturn(actors);
        when(mockObject.getTitle()).thenReturn("XD");
        when(mockObject.getSystem_actors()).thenReturn(system_actors);

        InOrder order = inOrder(mockObject);

        this.visitor.visit(mockObject);

        order.verify(mockObject, times(1)).getTitle();
        order.verify(mockObject, atLeastOnce()).getActors();
        order.verify(mockObject, atLeastOnce()).getSystem_actors();
        order.verify(mockObject, atLeastOnce()).getSteps();
    }

    @Test
    void checkNoTitleTest(){
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

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Scenariusz: TYTUŁ NIE MOŻE BYĆ PUSTY");
    }

    @Test
    void checkEmptySystemActor(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("", true);
        Actor mockObject = mock(act.getClass());

        when(mockObject.getName()).thenReturn("");
        when(mockObject.getSystem()).thenReturn(true);

        system_actors.add(mockObject);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        InOrder order = inOrder(mockObject);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Aktor: NAZWA AKTORA SYSTEMOWEGO NIE MOŻE BYĆ PUSTA");
        order.verify(mockObject, atLeastOnce()).getName();
        order.verify(mockObject, atLeastOnce()).getSystem();
    }

    @Test
    void checkEmptyActor(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Aktor: NAZWA AKTORA NIE MOŻE BYĆ PUSTA");
    }

    @Test
    void checkDuplicateActor(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Aktor: AKTOR NIE MOŻE BYĆ POWIELONY");
    }

    @Test
    void checkBadKeyword(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step2 = new Step("", "Zbyszek je zupe", subScenario);
        subScenario.add(step2);
        Step step = new Step("XD", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Krok: KEYWORD MUSI NALEŻEĆ DO ZBIORU (IF, ELSE, FOR EACH)");
    }

    @Test
    void checkEmptySubscenario(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step2 = new Step("", "Zbyszek je zupe", subScenario);
//        subScenario.add(step2);
        Step step = new Step("IF", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Krok: KROK MUSI POSIADAĆ POD-SCENARIUSZ (GDY KEYWORD NIE JEST PUSTY)");
    }

    @Test
    void checkEmptyKeyword(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step2 = new Step("", "Zbyszek je zupe", subScenario);
        subScenario.add(step2);
        Step step = new Step("", "Zbyszek je", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Krok: KEYWORD NIE MOŻE BYĆ PUSTY (GDY POSIADA POD-SCENARIUSZ)");
    }

    @Test
    void checkEmptyStep(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step2 = new Step("", "Zbyszek je zupe", subScenario);
        subScenario.add(step2);
        Step step = new Step("IF", "", subScenario);
        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(step);

        Scenario scenario = new Scenario("Title", actors, system_actors, steps, 0);

        this.visitor.visit(scenario);

        List<String> errors = this.visitor.getErrors();

        Assertions.assertEquals(errors.size(), 1);
        Assertions.assertEquals(errors.get(0), "Krok: KROK NIE MOŻE BYĆ PUSTY");
    }
}