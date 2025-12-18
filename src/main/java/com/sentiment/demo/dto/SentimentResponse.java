package com.sentiment.demo.dto;

/**
 * Esta clase es un contrato
 * Define exactamentelo que vamos a responder al usuario 
 */

public class SentimentResponse {
    private String prevision; // positivo, negativo, neutral
    private double probabilidad; // Ejemplo: 0.85
    //private String error; // si algo llegaq a salir mal
    
    // Constructor vacio necesario para las erializacion de json
    public SentimentResponse() {}

    // Constructor
    public SentimentResponse(String prevision, double probabilidad){
        this.prevision = prevision;
        this.probabilidad = probabilidad;
        //this.error = null;    
    }

    public String getPrevision() {
        return prevision;
    }

    public void setPrevision(String prevision) {
        this.prevision = prevision;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    /**
     * // Constructor para el caso de error
    public String getPrevision() {return prevision; }
    public double getProbabilidad() { return probabilidad; }
    public String getError() { return error;}
    */

    
}
