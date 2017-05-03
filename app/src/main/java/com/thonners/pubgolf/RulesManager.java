package com.thonners.pubgolf;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manage getting / saving cutsom rule sets
 *
 * @author M Thomas
 * @since 03/05/17
 */

public class RulesManager {

    private static String rulesDirName = "rules" ;
    private static String standardRulesTitle = "PGA Rules" ;

    private final HashMap<String, String[]> ruleSets = new HashMap<>(3, 0.9f) ;
    private File rulesDir ;

    public RulesManager(Context context) {
        initialise(context);
        getLocalRules() ;
    }

    /**
     * Loads the standard rule set into memory and saves it in the HashMap
     * @param context
     */
    private void initialise(Context context) {
        // Get the standard rules
        String[] standardRulesArray = context.getResources().getStringArray(R.array.rules_entries) ;
        ruleSets.put(standardRulesTitle,standardRulesArray) ;
        // Get an instance of the rules directory so we can read/write from/to it
        rulesDir = new File(context.getFilesDir(),rulesDirName) ;
    }

    /**
     * Reads the rules directory for any files. If found, loads the rules according to their title.
     *
     * Titles are saved as the file name, with spaces replaced by underscores. The String[] is split
     * by line.
     */
    private void getLocalRules() {
        File[] ruleFiles = rulesDir.listFiles() ;
        // Check if there are any custom rules, if not, return
        //if (ruleFiles.length == 0) return;
        for (int i = 0 ; i < ruleFiles.length ; i++) {
            loadRules(ruleFiles[i]) ;
        }
    }

    /**
     * Loads rules from file and adds to ruleSets collection
     * @param rulesFile the file containing the rules
     */
    private void loadRules(File rulesFile) {
        // Get the rule set title, replacing all underscores with spaces
        String ruleSetTitle = rulesFile.getName().replaceAll("_", " ");
        // Initialise the collection
        final ArrayList<String> ruleSetList = new ArrayList<>();
        // Try-with-resources to read file
        try (BufferedReader br = new BufferedReader(new FileReader(rulesFile))) {
            String line ;
            while((line = br.readLine()) != null) {
                ruleSetList.add(line) ;
            }
        } catch (Exception e) {
            Log.e("RulesManager", "Error reading rules file: " + rulesFile.getName()) ;
        }
        // Convert to Array
        String[] ruleSet = new String[ruleSetList.size()] ;
        ruleSet = ruleSetList.toArray(ruleSet) ;

        // Show warning that it's overwriting stuff
        if (ruleSets.containsKey(ruleSetTitle)) {
            Log.d("RulesManager", "ruleSets already contains a rules set of title: " + ruleSetTitle) ;
        }
        // Add it to the collection - overwrite it if already there.
        ruleSets.put(ruleSetTitle,ruleSet) ;
    }

    public void saveRules(String title, String[] ruleSet) {
        String saveFileName = title.replaceAll(" ", "_") ;
        File rulesFile = new File(rulesDir, saveFileName) ;
        // Check if the file already exists
        if (rulesFile.exists()) {
            // TODO: Check overwrite?
        }
        // Write the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rulesFile))) {
            Log.d("RulesManager", "Saving \"" + title + "\" to: " + rulesFile.getName()) ;
            for (int i = 0 ; i < ruleSet.length; i++) {
                bw.write(ruleSet[i]);
                bw.newLine();
            }
        } catch (Exception e) {
            Log.e("RulesManager", "Error reading rules file: " + rulesFile.getName()) ;
        }
        // re-load the rules
        getLocalRules();
    }
}
