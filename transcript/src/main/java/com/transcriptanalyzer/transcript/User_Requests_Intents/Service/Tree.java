package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

// Assume we are producing one tree for a certain transcript in the ArrayList of transcripts
@Service
public class Tree {
    private Node treeRootNode;
    private HashMap<String, Integer> counts;

    // class for tree representing intents as it's nodes
    // intents organized in order of their turn in their respective transcript
    public Tree(ArrayList<ArrayList<ArrayList<String>>> transcripts) {
        // root of tree
        this.treeRootNode = new Node("Root", null);
        Node start = this.treeRootNode;
        this.counts = new HashMap<String, Integer>();

        // go through each transcript, then each turn (representing each message intent pair)
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
            // at the end of each transcript bring treeNode back to top
            this.treeRootNode = start;
        }
    }

    // class for each individual intent
    public static class Node {
        private final String intent;
        // # of number it has appeared in its respective turn
        private int occurrences;
        private Node parent;
        private ArrayList<Node> children;

        public Node(String intent, Node parent){
            this.intent = intent;
            this.occurrences = 1;
            this.parent = parent;
            this.children = new ArrayList<Node>();
        }

        public Node addSubIntent(String intent) {
            // if the intent already exists as a child of the parent node
            if (this.hasChild(intent) != null) {
                Node node = this.hasChild(intent);
                // increment occurrences of this node
                node.occurrences += 1;
                return node;
                // if the intent is not a child of the node
            } else {
                // create the new node and add it as a child of the parent node
                Node newChildNode = new Node(intent, this);
                this.children.add(newChildNode);
                return newChildNode;
            }
        }

        // return subNode if it matches intent, o.w return None
        public Node hasChild(String intent){
            for (Node child : this.children) {
                if (Objects.equals(intent, child.intent)) {
                    return child;
                }
            }
            return null;
        }

        // return a hashmap mapping the 3 leaf intents with most occurrences to their occurrences in descending order
        public LinkedHashMap<String, Integer> getBestLeafData() {
            // create a list for the best leaf data of all children
            LinkedHashMap<String, Integer> leafData = new LinkedHashMap<>();
            // create a placeholder list for the top three leaf intents
            LinkedHashMap<String, Integer> bestLeafData = new LinkedHashMap<>();

            // if this is a leaf node (has no children), set the best leaf data to its own intent and occurrences
            // so that it may be returned
            if (this.children.isEmpty()) {
                bestLeafData.put(this.intent, this.occurrences);
            }
            // otherwise, get the best 3 intents and their occurrences from this node's children
            else {
                // for each child, get its best leaf data and merge it all into leafData
                for (Node child: this.children) {
                    // take the best leaf data of the current child
                    LinkedHashMap<String, Integer> childBestLeaves = child.getBestLeafData();

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

                // move the leaves into a list
                List<Map.Entry<String, Integer>> leafDataEntries = new ArrayList<>(leafData.entrySet());

                // sort the list using the Map.Entry comparing by value comparator in reverse order
                // this sorts each hash map entry, which is stored in a list, in descending order
                leafDataEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

                // determine how many entries to take from the list
                // take the last index of the entries we will take. no more than 3,
                // and no more than the number of elements in the list
                int last_index = java.lang.Math.min(2, leafDataEntries.size() - 1);

                // for each index of the entries we want
                for (int i = 0; i <= last_index; i++) {
                    // directly extract the key and value at that index
                    String key = leafDataEntries.get(i).getKey();
                    Integer value = leafDataEntries.get(i).getValue();

                    // put them into the new sorted map. Has 0 to 3 values in it. In the context of our problem,
                    // should have 1 to 3.
                    // we can put instead of merging without overwriting because we know the entries in leafDataEntries
                    // are unique; they were created by merging entries.
                    bestLeafData.put(key, value);
                }
            }
            return bestLeafData;
        }
    }

    // get a list of the three nodes with the greatest occurrences sorted in decreasing order
    public List<String> getBestTreeIntents(){
        // compares each value and get the top 3, return key list attributed to these top 3
        return this.counts.entrySet().stream().sorted(
                Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3).map(
                Map.Entry::getKey).collect(Collectors.toList());
    }

    //martin: create top 3 for leaves
    public List<String> getBestLeafIntents() {

        // get a hash map that is mapping intent to occurrences of the highest occurring intents of all leaves.
        // in descending order.
        LinkedHashMap<String, Integer> bestLeafData = this.treeRootNode.getBestLeafData();

        // return a list containing the keys of that hash map; the best intents themselves
        return new ArrayList<>(bestLeafData.keySet());
    }

    // get both sets of the top 3 intents
    public ArrayList<List<String>> getBestIntents(){
        ArrayList<List<String>> options = new ArrayList<>(2);
        options.add(this.getBestTreeIntents());
        options.add(this.getBestLeafIntents());

        return options;
    }
}

/*
ArrayList<ArrayList<ArrayList<String>>>
such that:
Outermost layer: Stores overall result for all transcripts.
Middle Layer: each element is a full transcript.
Inner layer: each element is a turn.
String: either a user intent or bot message in the format "message: " + the actual message (same for intents)."
*/
