package com.transcriptanalyzer.transcript;

/*
This implementation of converting a string to a nested arraylist was not our group's implementation,
it is a solution online used with slight modification in order to reduce redundant code used to
create sufficient test data for test cases.
Credit to this code can be found here:
https://stackoverflow.com/questions/37235428/convert-string-into-nested-arraylist-in-java
*/

import java.util.*;

public class StringToArrayList {
    public static class Bean {
        int token;
        Object obj;
        public Bean(int token, Object o) {
            super();
            this.token = token;
            this.obj = o;
        }
    }
    public static void main(String[] args) {
        String input = "[" +
                "[[MESSAGE, Order], [MESSAGE, Shoes], [MESSAGE, Jordan's], [MESSAGE]], " +
                "[[MESSAGE, Career], [MESSAGE, Management]], " +
                "[[MESSAGE, Career], [MESSAGE, Employee]], " +
                "[[MESSAGE, Order], [MESSAGE, Shoes], [MESSAGE, Nike], [MESSAGE]], " +
                "[[MESSAGE, Order], [MESSAGE, Jacket], [MESSAGE, Nike], [MESSAGE]], " +
                "[[MESSAGE, Sales], [MESSAGE, Shoes], [MESSAGE, Adidas], [MESSAGE]] " +
                "[[MESSAGE, Refund], [MESSAGE, Shoes], [MESSAGE, Nike], [MESSAGE]] " +
                "[[MESSAGE, Order], [MESSAGE, Shoes], [MESSAGE, Jordan's], [MESSAGE]]]";

        ArrayList<ArrayList<ArrayList<String>>> list = getList(input);
        System.out.println(list);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<ArrayList<ArrayList<String>>> getList(String input1) {
        Deque<Object> stack = new ArrayDeque<Object>();
        char[] chararray = input1.toCharArray();
        StringBuilder temp = new StringBuilder();
        for (char c : chararray) {
            if (c == ']') {
                if (!temp.toString().equals("")) {
                    stack.push(new Bean(0, temp.toString()));
                    temp = new StringBuilder();
                }
                ArrayList<Object> tmplist = new ArrayList<>();
                while (true) {
                    Object object = stack.pop();
                    if (object instanceof Bean) {
                        Bean b = (Bean) object;
                        if (b.token == 1)
                            break;
                        tmplist.add(b.obj);
                    } else {
                        tmplist.add(object);
                        if (stack.isEmpty())
                            break;
                    }
                }
                Collections.reverse(tmplist);
                stack.push(tmplist);
            } else {
                if (c == '[') {
                    stack.push(new Bean(1, Character.toString(c)));
                } else if (c == ',') {
                    if (!temp.toString().equals("")) {
                        stack.push(new Bean(0, temp.toString()));
                        temp = new StringBuilder();
                    }
                } else {
                    temp.append(Character.toString(c));
                }
            }
        }
        return (ArrayList<ArrayList<ArrayList<String>>>) stack.pop();
    }
}
