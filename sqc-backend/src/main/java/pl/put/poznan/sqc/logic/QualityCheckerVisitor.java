package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QualityCheckerVisitor implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(QualityCheckerVisitor.class);
    private static List<String> errors = new ArrayList<>();

    public List<String> getErrors(){
        return this.errors;
    }

    @Override
    public void visit(Scenario s) {
        logger.info("Visiting Scenario Object...");

        this.errors = new ArrayList<>();

        // Check Title
        if (s.getTitle().length() == 0){
            this.errors.add("Scenariusz: TYTUŁ NIE MOŻE BYĆ PUSTY");
        }

        // Check Actors
        List<Actor> actors = new ArrayList<>(s.getActors());
        actors.addAll(s.getSystem_actors());
        for (Actor a: actors){
            // Check Actor Name
            if (a.getName().length() == 0){
                if (a.getSystem()){
                    errors.add("Aktor: NAZWA AKTORA SYSTEMOWEGO NIE MOŻE BYĆ PUSTA");
                }else {
                    errors.add("Aktor: NAZWA AKTORA NIE MOŻE BYĆ PUSTA");
                }
            }
            // Check If there are duplicate actors
            if (Collections.frequency(actors, a) > 1){
                errors.add("Aktor: AKTOR NIE MOŻE BYĆ POWIELONY");
            }
        }

        // Check Steps
        for (Step step: s.getSteps()){
            if (step.getKeyword().length() > 0){
                if (!step.getKeyword().equals("IF") && !step.getKeyword().equals("ELSE") && !step.getKeyword().equals("FOR EACH")){
                    errors.add("Krok: KEYWORD MUSI NALEŻEĆ DO ZBIORU (IF, ELSE, FOR EACH)");
                }
                if (step.getSubScenario().size() == 0){
                    errors.add("Krok: KROK MUSI POSIADAĆ POD-SCENARIUSZ (GDY KEYWORD NIE JEST PUSTY)");
                }
            }

            if (step.getSubScenario().size() > 0 && step.getKeyword().length() == 0){
                errors.add("Krok: KEYWORD NIE MOŻE BYĆ PUSTY (GDY POSIADA POD-SCENARIUSZ)");
            }

            if (step.getContent().length() == 0){
                errors.add("Krok: KROK NIE MOŻE BYĆ PUSTY");
            }
        }
    }
}
