package com.transcriptanalyzer.transcript;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.Tree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeTest {
    /*
    There are no real messages from the bot in the transcripts as these do not affect the tests (fake MESSAGE used)
    This is for simplification as they have no effect on intent calculation, only the intent part of transcripts are relevant
    */
    // example of pizza store chatbot data with 3 transcripts
    public ArrayList<ArrayList<ArrayList<String>>> getTestPizzaData(){
        String strTestData = "[" +
                "[[MESSAGE,Remake],[MESSAGE,Order], [MESSAGE,Veggie], [MESSAGE]], " +
                "[[MESSAGE,Remake],[MESSAGE,Order], [MESSAGE,Pepperoni], [MESSAGE]], " +
                "[[MESSAGE,Order pizza],[MESSAGE,Order pizza]]" +
                "]";

        return StringToArrayList.getList(strTestData);
    }

    // example of retailer chatbot data with 8 transcripts
    public ArrayList<ArrayList<ArrayList<String>>> getTestRetailerData(){
        String strTestData = "[" +
                "[[MESSAGE,Order], [MESSAGE,Shoes], [MESSAGE,Jordan's], [MESSAGE]], " +
                "[[MESSAGE,Career], [MESSAGE,Management]], " +
                "[[MESSAGE,Career], [MESSAGE,Employee]], " +
                "[[MESSAGE,Order], [MESSAGE,Shoes], [MESSAGE,Nike], [MESSAGE]], " +
                "[[MESSAGE,Order], [MESSAGE,Jacket], [MESSAGE,Nike], [MESSAGE]], " +
                "[[MESSAGE,Sales], [MESSAGE,Shoes], [MESSAGE,Adidas], [MESSAGE]] " +
                "[[MESSAGE,Refund], [MESSAGE,Shoes], [MESSAGE,Nike], [MESSAGE]] " +
                "[[MESSAGE,Order], [MESSAGE,Shoes], [MESSAGE,Jordan's], [MESSAGE]]]";

        return StringToArrayList.getList(strTestData);
    }

    // example of edge case conversation where data is insufficient (not enough nodes)
    // Note: intents called "None" are ignored in tree creation
    public ArrayList<ArrayList<ArrayList<String>>> getInsufficientData(){
        String strTestData = "[" +
                "[[MESSAGE,Order], [MESSAGE,Shoes]], " +
                "[[MESSAGE,None]]]";

        return StringToArrayList.getList(strTestData);
    }

    // Pizza ordering service tests
    @Test
    void testPizzaTreeIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestPizzaData();
        var IntentTree = new Tree(testData);
        assertEquals("[Order pizza, Order, Remake]",
                IntentTree.getBestTreeIntents().toString());
    }

    @Test
    void testPizzaTreeLeafIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestPizzaData();
        var IntentTree = new Tree(testData);
        assertEquals("[Veggie, Pepperoni, Order pizza]",
                IntentTree.getBestLeafIntents().toString());
    }

    @Test
    void testPizzaTreeCombinedIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestPizzaData();
        var IntentTree = new Tree(testData);
        assertEquals("[[Order pizza, Order, Remake], [Veggie, Pepperoni, Order pizza]]",
                IntentTree.getBestIntents().toString());
    }

    // Retailer service tests
    @Test
    void testRetailerTreeIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestRetailerData();
        var IntentTree = new Tree(testData);
        assertEquals("[Shoes, Order, Nike]",
                IntentTree.getBestTreeIntents().toString());
    }

    @Test
    void testRetailerTreeLeafIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestRetailerData();
        var IntentTree = new Tree(testData);
        assertEquals("[Nike, Jordan's, Management]",
                IntentTree.getBestLeafIntents().toString());
    }

    @Test
    void testRetailerTreeCombinedIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getTestRetailerData();
        var IntentTree = new Tree(testData);
        assertEquals("[[Shoes, Order, Nike], [Nike, Jordan's, Management]]",
                IntentTree.getBestIntents().toString());
    }

    // Insufficient data tests
    @Test
    void testInsufficientTreeIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getInsufficientData();
        var IntentTree = new Tree(testData);
        assertEquals("[Order, Shoes, Insufficient Data]",
                IntentTree.getBestTreeIntents().toString());
    }

    @Test
    void testInsufficientTreeLeafIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getInsufficientData();
        var IntentTree = new Tree(testData);
        assertEquals("[Shoes, Insufficient Data, Insufficient Data]",
                IntentTree.getBestLeafIntents().toString());
    }

    @Test
    void testInsufficientTreeCombinedIntents(){
        ArrayList<ArrayList<ArrayList<String>>> testData = getInsufficientData();
        var IntentTree = new Tree(testData);
        assertEquals("[[Order, Shoes, Insufficient Data], [Shoes, Insufficient Data, Insufficient Data]]",
                IntentTree.getBestIntents().toString());
    }

}
