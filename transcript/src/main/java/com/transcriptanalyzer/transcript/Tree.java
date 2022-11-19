package com.transcriptanalyzer.transcript;

import java.util.*;
import java.util.stream.Collectors;

// Assume we are producing one tree for a certain transcript in the ArrayList of transcripts
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
                String currIntent = turn.get(1);
                Node newRoot = this.treeRootNode.addSubIntent(currIntent);
                // add intent if doesnt exist, increment existing count if exists
                this.counts.merge(newRoot.intent, 1, Integer::sum);
                this.treeRootNode = newRoot;
            }
            // at the end of each transcript bring treeNode back to top
            this.treeRootNode = start;
        }
    }

    // class for each individual intent
    public class Node {
        private String intent;
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
    }

    // get a list of the three nodes with the greatest occurrences sorted in decreasing order
    public List<String> getTopThree(){
        // compares each value and get the top 3, return key list attributed to these top 3
        return this.counts.entrySet().stream().sorted(
                Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3).map(
                        Map.Entry::getKey).collect(Collectors.toList());
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
