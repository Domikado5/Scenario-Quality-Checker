package pl.put.poznan.sqc.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sqc.rest.ScenarioQualityCheckerController;

import java.util.ArrayList;
import java.util.List;

public class Visitor{
    private static final Logger logger = LoggerFactory.getLogger(Visitor.class);
    private List<String> errors = new ArrayList<>();
    private String raport = "";

    public String getErrors(){
        logger.info("Initialized getErrors...");
        String result = "";

        for (String error: this.errors){
            result += error + "\n";
        }
        logger.debug(result);
        return result;
    }

    public String getRaport(){
        logger.info("Initialized getRaport...");
        logger.debug(this.raport);
        return this.raport + this.getErrors();
    }

    public void visit(Scenario s){
        logger.info("Visiting Scenario Object...");
        logger.debug(s.toString());
        if (s.getTitle().length() == 0){
            errors.add("Scenariusz: TYTUL NIE MOŻE BYĆ PUSTY");
        }
        this.raport = "";
        this.raport += "Scenariusz:\n\n";
        this.raport += s.returnScenario();
        this.raport += "Raport:\n";
        this.raport += "Liczba kroków: " + s.countSteps(s.getSteps()) + "\n";
        this.raport += "Liczba słów kluczowych: " + s.countKeyword(s.getSteps()) + "\n";
        this.raport += "Niepoprawne kroki:\n";
        List<String> badSteps = s.findNoActorSteps(s.getSteps(), 0);
        for (String step : badSteps){
            this.raport += step + "\n";
        }
        this.raport += "Błędy: \n";
    }


    public void visit(Actor a){
        logger.info("Visiting Actor Object...");
        logger.debug(a.toString());
        if (a.getName().length() == 0){
            if (a.getSystem()){
                errors.add("Aktor: NAZWA AKTORA SYSTEMOWEGO NIE MOŻE BYĆ PUSTA");
            }else {
                errors.add("Aktor: NAZWA AKTORA NIE MOŻE BYĆ PUSTA");
            }
        }
    }

    public void visit(Step s){
        logger.info("Visiting Step Object...");
        logger.debug(s.toString());
        if (s.getKeyword().length() > 0){
            if (!s.getKeyword().equals("IF") && !s.getKeyword().equals("ELSE") && !s.getKeyword().equals("FOR EACH")){
                errors.add("Krok: KEYWORD MUSI NALEŻEĆ DO ZBIORU (IF, ELSE, FOR EACH)");
            }
            if (s.getSubScenario().size() == 0){
                errors.add("Krok: KROK MUSI POSIADAĆ POD-SCENARIUSZ (GDY KEYWORD NIE JEST PUSTY)");
            }
        }

        if (s.getSubScenario().size() > 0 && s.getKeyword().length() == 0){
            errors.add("Krok: KEYWORD NIE MOŻE BYĆ PUSTY (GDY POSIADA POD-SCENARIUSZ)");
        }

        if (s.getContent().length() == 0){
            errors.add("Krok: KROK NIE MOŻE BYĆ PUSTY");
        }
    }

    public void visit(List<Step> s){
        logger.info("Visiting List<Step> Object...");
        logger.debug(s.toString());
        for (Step ss: s){
            this.visit(ss);
        }
    }
}
