package pl.put.poznan.sqc.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

class PrinterVisitorTest {
    private PrinterVisitor visitor;

    @BeforeEach
    void setUp(){
        this.visitor = new PrinterVisitor();
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

        order.verify(mockObject, times(1)).getMaxLevel();
        order.verify(mockObject, times(1)).getTitle();
        order.verify(mockObject, atLeastOnce()).getActors();
        order.verify(mockObject, atLeastOnce()).getSystem_actors();
        order.verify(mockObject, atLeastOnce()).getSteps();
    }

    @Test
    void checkStepGetters(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);
        Step mockObject = mock(step.getClass());

        when(mockObject.getContent()).thenReturn("Zbyszek je");
        when(mockObject.getKeyword()).thenReturn("");
        when(mockObject.getSubScenario()).thenReturn(subScenario);

        Actor act = new Actor("Zbyszek", false);

        actors.add(act);
        steps.add(mockObject);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        InOrder order = inOrder(mockObject);

        this.visitor.visit(scenario);

        order.verify(mockObject, atLeastOnce()).getKeyword();
        order.verify(mockObject, atLeastOnce()).getContent();
        order.verify(mockObject, atLeastOnce()).getSubScenario();
    }

    @Test
    void checkActorGetters(){
        List<Actor> actors = new ArrayList<>();
        List<Actor> system_actors = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        List<Step> subScenario = new ArrayList<>();

        Step step = new Step("", "Zbyszek je", subScenario);

        Actor act = new Actor("Zbyszek", false);
        Actor system = new Actor("System", true);
        Actor mockObject = mock(system.getClass());

        when(mockObject.getName()).thenReturn("System");
        when(mockObject.getSystem()).thenReturn(true);

        actors.add(act);
        system_actors.add(mockObject);
        steps.add(step);

        Scenario scenario = new Scenario("", actors, system_actors, steps, 0);

        InOrder order = inOrder(mockObject);

        this.visitor.visit(scenario);

        order.verify(mockObject, atLeastOnce()).getName();
    }
}