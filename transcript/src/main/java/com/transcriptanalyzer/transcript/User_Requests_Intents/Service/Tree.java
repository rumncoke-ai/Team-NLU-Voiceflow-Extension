package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Tree class deals with representing transcript data in a Tree data structure
 * which is used to organize intents
 *
 * Includes methods to extract the best (the highest occurrence) intents from the entire tree,
 * and also the best intents from just the leaf nodes
 */
@Service
public class Tree {

    // Dependeny: Node class; Used to represent chatbot intents as tree nodes
    private Node treeRootNode;


    // Intents organized in order of their turn in their respective transcript
    // NOTE: A turn is represented by a message intent pair)
    private HashMap<String, Integer> counts;

    public Tree(ArrayList<ArrayList<ArrayList<String>>> transcripts) {
        this.treeRootNode = new Node("Insufficient Data");
        Node start = this.treeRootNode;
        this.counts = new HashMap<String, Integer>();

        // Parse through each transcript followed by each turn
        // and add it as a subIntent node of the treeNode, then update treeNode down

        for (ArrayList<ArrayList<String>> transcript : transcripts) {
            for (ArrayList<String> turn : transcript) {
                if (turn.size() == 2) {
                    String currIntent = turn.get(1);
                    // dont create node if it's none
                    if (!Objects.equals(currIntent, "None")) {
                        Node newRoot = this.treeRootNode.addSubIntent(currIntent);
                        // add intent if doesnt exist, increment existing count if exists
                        this.counts.merge(newRoot.intent, 1, Integer::sum);
                        this.treeRootNode = newRoot;
                    }
                }
            }
            // At the end of each transcript bring treeNode back to top
            this.treeRootNode = start;
        }
    }

    // Class for each individual intent
    public static class Node {
        private final String intent;

        // Number of occurrences for the intent
        private int occurrences;
        private ArrayList<Node> children;

        public Node(String intent){
            this.intent = intent;
            this.occurrences = 1;
            this.children = new ArrayList<Node>();
        }

        public Node addSubIntent(String intent) {
            // If the intent already exists as a child of the parent node then increment the occurences by 1
            if (this.hasChild(intent) != null) {
                Node node = this.hasChild(intent);
                node.occurrences += 1;
                return node;

            }
            // If the intent is not a child of the node create the new node and add it as a child of the parent node
            else {
                Node newChildNode = new Node(intent);
                this.children.add(newChildNode);
                return newChildNode;
            }
        }

        // Return subNode if it matches intent, otherwise return None
        public Node hasChild(String intent){
            for (Node child : this.children) {
                if (Objects.equals(intent, child.intent)) {
                    return child;
                }
            }
            return null;
        }

        // Helper methods for determining best leaf nodes (highest occurrences)
        public LinkedHashMap<String, Integer> mergeLeafData(LinkedHashMap<String, Integer> leafData){
            // for each child, get its best leaf data and merge it all into leafData
            for (Node child: this.children) {
                LinkedHashMap<String, Integer> childBestLeaves = child.getBestLeafData(); // take the best leaf data of the current child
                // for each entry of this child's best leaf data, merge the entry into leafData
                for (Map.Entry<String, Integer> leaf: childBestLeaves.entrySet()) {
                    // get the intent and occurrence of the current entry
                    String intent = leaf.getKey();
                    Integer occurrences = leaf.getValue();
                    // merge it into leaf data. If an occurrence is new, it is added to the LinkedHashMap.
                    // If it already exists, their values are summed using Integer::sum
                    leafData.merge(intent, occurrences, Integer::sum);
                }
            }
            return leafData;
        }

        public LinkedHashMap<String, Integer> organizeBestChildren(int last_index, List<Map.Entry<String, Integer>> leafDataEntries, LinkedHashMap<String, Integer> bestLeafData){
            // For each index of the entries we want directly extract the key and value at that index and
            // put the extracted key value pair into the new sorted map.
            for (int i = 0; i <= last_index; i++) {
                String key = leafDataEntries.get(i).getKey();
                Integer value = leafDataEntries.get(i).getValue();
                bestLeafData.put(key, value);
            }
            return bestLeafData;
        }

        // Return a hashmap mapping the 3 leaf intents with most occurrences to their occurrences in descending order
        public LinkedHashMap<String, Integer> getBestLeafData() {
            // create a list for the best leaf data of all children
            LinkedHashMap<String, Integer> leafData = new LinkedHashMap<>();
            // create a placeholder list for the top three leaf intents
            LinkedHashMap<String, Integer> bestLeafData = new LinkedHashMap<>();

            // if this is a leaf node (has no children), set the best leaf data to its own intent and occurrences to return it
            if (this.children.isEmpty()) {
                bestLeafData.put(this.intent, this.occurrences);
            }
            // otherwise, get the best 3 intents and their occurrences from this node's children
            else {
                // merge leaf data recursively and update hashmap to each intent occurrence pair
                leafData = mergeLeafData(leafData);
                // move the leaves into a list
                List<Map.Entry<String, Integer>> leafDataEntries = new ArrayList<>(leafData.entrySet());
                // sort the list using the Map.Entry comparing by value comparator in reverse order
                // this sorts each hash map entry, which is stored in a list, in descending order
                leafDataEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
                // determine how many entries to take from the list
                // take the last index of the entries we will take. no more than 3,
                // and no more than the number of elements in the list
                int last_index = java.lang.Math.min(2, leafDataEntries.size() - 1);
                // organize best leaf data and update hashmap to obtain the best intent occurrence pairs
                bestLeafData = organizeBestChildren(last_index, leafDataEntries, bestLeafData);
            }
            return bestLeafData;
        }
    }

    // Get a list of the three nodes with the greatest occurrences sorted in decreasing order
    public ArrayList<String> getBestTreeIntents(){
        // compares each value and get the top 3, return key list attributed to these top 3
        List<String> treeIntentsList = this.counts.entrySet().stream().sorted(
                Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3).map(
                Map.Entry::getKey).collect(Collectors.toList());

        ArrayList<String> treeIntentsArrayList = new ArrayList<String>(treeIntentsList);
        return fillArrayList(treeIntentsArrayList);

    }

    //Finds top 3 leaf leaves
    public ArrayList<String> getBestLeafIntents() {
        // get a hash map that is mapping intent to occurrences of the highest occurring intents of all leaves.
        // in descending order.
        LinkedHashMap<String, Integer> bestLeafData = this.treeRootNode.getBestLeafData();

        // return a list containing the keys of that hash map; the best intents themselves
        return fillArrayList(new ArrayList<>(bestLeafData.keySet()));
    }

    //Get both sets of the top 3 intents
    public ArrayList<ArrayList<String>> getBestIntents(){
        ArrayList<ArrayList<String>> options = new ArrayList<>(2);
        options.add(this.getBestTreeIntents());
        options.add(this.getBestLeafIntents());

        return options;
    }

    public ArrayList<String> fillArrayList(ArrayList<String> list) {
        while (list.size() < 3){
            list.add("Insufficient Data");
        }
        return list;
    }
}


