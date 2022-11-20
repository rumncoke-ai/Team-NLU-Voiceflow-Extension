package com.transcriptanalyzer.transcript;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    public ArrayList<ArrayList<ArrayList<String>>> getTestData(){
        ArrayList<ArrayList<ArrayList<String>>> testData = new ArrayList<>();

        ArrayList<String> turn1 = new ArrayList<String>();
        ArrayList<String> turn2 = new ArrayList<String>();
        ArrayList<String> turn3 = new ArrayList<String>();
        ArrayList<String> turn4 = new ArrayList<String>();

        ArrayList<ArrayList<String>> transcript1 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> transcript2 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> transcript3 = new ArrayList<ArrayList<String>>();

        turn1.add("Hi, welcome to Generic Pizza Place (tm)! What can I help you with today?");
        turn1.add("Remake");
        transcript1.add(turn1);
        turn2.add("Sorry, could you rephrase that for me?");
        turn2.add("Order");
        transcript1.add(turn2);
        turn3.add("We have 3 different kinds of pizzas available with one being on special per day. The choices are chesse, veggie, and pepperoni.");
        turn3.add("Veggie");
        transcript1.add(turn3);
        turn4.add("We will make you a veggie pizza. Please come pick it up in 20 minutes.");
        transcript1.add(turn4);
        transcript2.add(turn1);
        transcript2.add(turn2);
        turn3.set(1, "Pepperoni");
        transcript2.add(turn3);
        turn4.set(0, "We will make you a pepperoni pizza. Please come pick it up in 20 minutes.");
        transcript1.add(turn4);

        turn1.set(1, "Order pizza");
        transcript3.add(turn1);
        turn2.set(1, "Order pizza");
        transcript3.add(turn2);

        testData.add(transcript1);
        testData.add(transcript2);
        testData.add(transcript3);

        return testData;
    }

    @Test
        // return type
    void createTree(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestData();
        var IntentTree = new Tree(testData);
    }

    @Test
    // return type
    void twoPlusTwoShouldEqualFour(){
        var calculator = new Tree(new ArrayList<>());
        assertEquals(4, calculator.add(2, 2));
    }

}