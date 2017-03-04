package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;
    private static String value;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * MAKE SEARCH METHODS CASE-INSENSITIVE
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * MAKE SEARCH METHODS CASE-INSENSITIVE
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column).toString();  //.toLowerCase();

            if (aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }


    /**
     * IMPLEMENT FINDBYVALUE
     * In the JobData class, create a new (public static) method that will search for a string
     * within each of the columns. Here are a few observations:
     *
     * 1. The method that you write should not contain duplicate jobs. So, for example,
     * if a listing has position type "Web - Front End" and name "Front end web dev"
     * then searching for "web" should not include the listing twice.
     *
     * 2. As with printJobs, you should write your code in a way that if a new column
     * is added to the data, your code will automatically search the new column as well.
     *
     * 3. You should not write code that calls findByColumnAndValue once for each column.
     * Rather, utilize loops and collection methods as you did above.
     *
     * 4. You should, on the other hand, read and understand findByColumnAndValue, since
     * your code will look similar in some ways.
     *
     * 5. MAKE SEARCH METHODS CASE-INSENSITIVE
     *
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {
        //JobData.value = value;
//        System.out.println("118.value = " + value);

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.toString();

            // MAKE SEARCH METHODS CASE-INSENSITIVE
            if (aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }


    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
