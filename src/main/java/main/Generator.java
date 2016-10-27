package main;

import javax.persistence.Persistence;


public class Generator {

    public static void main(String[] args) {
        Persistence.generateSchema("pu", null);
    }
    

}
