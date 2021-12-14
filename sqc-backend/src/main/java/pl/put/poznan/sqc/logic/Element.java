package pl.put.poznan.sqc.logic;

public abstract class Element {
    public abstract  void accept(Visitor v);
}
