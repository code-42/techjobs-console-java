package org.launchcode.techjobs.console;

import java.util.*;

/**
 * Created by LaunchCode
 * Edited by Edward Dupre March, 2017
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print SORTED list of skills, employers, etc
                    Collections.sort(results);
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);
//                System.out.println("59.searchField = " + searchField);
                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    // System.out.println("Some fields not yet implemented..");
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

//            choiceIdx = in.nextInt();  // InputMismatchException if not int
//            in.nextLine();             // insert while then get nextInt()

            while(!in.hasNextInt()){
                String input = in.next();
                System.out.println("Invalid choice: " + input + ". Try again. ");
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice: " + choiceIdx + ". Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        // System.out.println("printJobs is not implemented yet");
         if(someJobs.isEmpty()){
            System.out.println("No Results");
        }

        for (HashMap<String, String> row : someJobs) {
            String aValue = row.toString();

            System.out.println("*****");  // separator at top of every job

            for(Map.Entry<String, String> e : row.entrySet()){
                System.out.println(e.getKey() + ": " + e.getValue());
            }
            // print ***** at the bottom of the search results if not empty
            if(!someJobs.isEmpty()) {
                System.out.println("*****");
        }
//        // print ***** at the bottom of the search results if not empty
//        if(!someJobs.isEmpty()) {
//            System.out.println("*****");
        }
    }
}
