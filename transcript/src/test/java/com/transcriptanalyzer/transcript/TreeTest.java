package com.transcriptanalyzer.transcript;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.Tree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    public ArrayList<ArrayList<ArrayList<String>>> getTestPizzaData(){
        ArrayList<ArrayList<ArrayList<String>>> testData = new ArrayList<>();

        // init test data for transcript 1
        ArrayList<String> turn1T1 = new ArrayList<String>();
        ArrayList<String> turn2T1 = new ArrayList<String>();
        ArrayList<String> turn3T1 = new ArrayList<String>();
        ArrayList<String> turn4T1 = new ArrayList<String>();

        // init test data for transcript 2
        ArrayList<String> turn1T2 = new ArrayList<String>();
        ArrayList<String> turn2T2 = new ArrayList<String>();
        ArrayList<String> turn3T2 = new ArrayList<String>();
        ArrayList<String> turn4T2 = new ArrayList<String>();

        // init test data for transcript 3
        ArrayList<String> turn1T3 = new ArrayList<String>();
        ArrayList<String> turn2T3 = new ArrayList<String>();

        // init test data for transcript 1
        ArrayList<ArrayList<String>> transcript1 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> transcript2 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> transcript3 = new ArrayList<ArrayList<String>>();

        // creating first transcript
        turn1T1.add("Hi, welcome to Generic Pizza Place (tm)! What can I help you with today?");
        turn1T1.add("Remake");
        transcript1.add(turn1T1);
        turn2T1.add("Sorry, could you rephrase that for me?");
        turn2T1.add("Order");
        transcript1.add(turn2T1);
        turn3T1.add("We have 3 different kinds of pizzas available with one being on special per day. The choices are chesse, veggie, and pepperoni.");
        turn3T1.add("Veggie");
        transcript1.add(turn3T1);
        turn4T1.add("We will make you a veggie pizza. Please come pick it up in 20 minutes.");
        transcript1.add(turn4T1);

        // creating second transcript
        turn1T2.add("Hi, welcome to Generic Pizza Place (tm)! What can I help you with today?");
        turn1T2.add("Remake");
        transcript2.add(turn1T2);
        turn2T2.add("Sorry, could you rephrase that for me?");
        turn2T2.add("Order");
        transcript2.add(turn2T2);
        turn3T2.add("We have 3 different kinds of pizzas available with one being on special per day. The choices are chesse, veggie, and pepperoni.");
        turn3T2.add("Pepperoni");
        transcript2.add(turn3T2);
        turn4T2.add("We will make you a pepperoni pizza. Please come pick it up in 20 minutes.");
        transcript2.add(turn4T2);

        // creating third transcript
        turn1T3.add("Hi, welcome to Generic Pizza Place (tm)! What can I help you with today?");
        turn1T3.add("Order pizza");
        transcript3.add(turn1T3);
        turn2T3.add("Sorry, could you rephrase that for me?");
        turn2T3.add("Order pizza");
        transcript3.add(turn2T3);

        // adding the transcripts
        testData.add(transcript1);
        testData.add(transcript2);
        testData.add(transcript3);

        return testData;
    }

    @Test
        // a test of transcripts from conversations with a pizza ordering service
        // 3 transcripts with 2-3 turns in interactions
    void testPizzaTree(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestPizzaData();
        var IntentTree = new Tree(testData);
        assertEquals("[[Order pizza, Order, Remake], [Veggie, Pepperoni, Order pizza]]",
                IntentTree.getBestIntents().toString());
    }
}