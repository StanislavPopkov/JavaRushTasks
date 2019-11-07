package com.javarush.task.task36.task3611;

import java.util.*;

/* 
Сколько у человека потенциальных друзей?
*/

public class Solution {
    private boolean[][] humanRelationships;

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.humanRelationships = generateRelationships();

        Set<Integer> allFriendsAndPotentialFriends = solution.getAllFriendsAndPotentialFriends(4, 2);
        System.out.println(allFriendsAndPotentialFriends);                              // Expected: [0, 1, 2, 3, 5, 7]
        Set<Integer> potentialFriends = solution.removeFriendsFromSet(allFriendsAndPotentialFriends, 4);
        System.out.println(potentialFriends);                                           // Expected: [2, 5, 7]
    }

    public Set<Integer> recursiveIndex(int index, int deep, int indexConstant){
        TreeSet<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();
        boolean[] mainFriend = humanRelationships[index];
        for (int i = 0; i < mainFriend.length-1; i++) {
            if (mainFriend[i] == true & i != indexConstant) {
                set.add(i);
                list.add(i);
            }
        }
        if(deep == 0) {
            return set;
        }
        for (int i = 0; i < humanRelationships.length; i++) {
            if ((i > index) && humanRelationships[i][index] & i != indexConstant) {
                set.add(i);
                list.add(i);
            }
        }
        --deep;
        for (Integer i : list) {
            set.addAll(recursiveIndex(i, deep, indexConstant));
            }
        return set;
    }

    public Set<Integer> getAllFriendsAndPotentialFriends(int index, int deep) {
        //напишите тут ваш код
        TreeSet<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();
        boolean[] mainFriend = humanRelationships[index];
        for (int i = 0; i < mainFriend.length-1; i++) {
            if (mainFriend[i] == true) {
                set.add(i);
                list.add(i);
            }
        }
        for (int i = 0; i < humanRelationships.length; i++) {
            if ((i > index) && humanRelationships[i][index]) {
                set.add(i);
                list.add(i);
            }
        }
        --deep;
        for (Integer i : list) {
            set.addAll(recursiveIndex(i, deep, index));
        }
        return set;
    }

    // Remove from the set the people with whom you already have a relationship
    public Set<Integer> removeFriendsFromSet(Set<Integer> set, int index) {
        for (int i = 0; i < humanRelationships.length; i++) {
            if ((i < index) && (index < humanRelationships.length) && humanRelationships[index][i]) {
                set.remove(i);
            } else if ((i > index) && humanRelationships[i][index]) {
                set.remove(i);
            }
        }
        return set;
    }

    // Return test data
    private static boolean[][] generateRelationships() {
        return new boolean[][]{
                {true},                                                                 //0
                {true, true},                                                           //1
                {false, true, true},                                                    //2
                {false, false, false, true},                                            //3
                {true, true, false, true, true},                                        //4
                {true, false, true, false, false, true},                                //5
                {false, false, false, false, false, true, true},                        //6
                {false, false, false, true, false, false, false, true}                  //7
        };
    }
}