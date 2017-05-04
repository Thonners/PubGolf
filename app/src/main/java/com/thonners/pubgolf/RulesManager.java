package com.thonners.pubgolf;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manage getting / saving cutsom rule sets
 *
 * @author M Thomas
 * @since 03/05/17
 */

public class RulesManager {

    private final String LOG_TAG = "RulesManager" ;

    public final static String STANDARD_RULES = "PGA Rules" ;

    private final static String rulesDirName = "rules" ;
    private final static String defaultRulesSharedPrefKey = "com.thonners.pubgolf.DEFAULT_RULES" ;

    private final HashMap<String, String[]> ruleSets = new HashMap<>(3, 0.9f) ;
    private File rulesDir ;
    private SharedPreferences defaultRulesSharedPrefs ;

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
        ruleSets.put(STANDARD_RULES,standardRulesArray) ;
        // Get an instance of the rules directory so we can read/write from/to it
        rulesDir = new File(context.getFilesDir(),rulesDirName) ;
        // create it if it doesn't exist
        if (!rulesDir.isDirectory()) {
            if (rulesDir.mkdirs()) {
                Log.d(LOG_TAG,"Rules directory created") ;
            } else {
                Log.d(LOG_TAG,"Rules directory not created") ;
            }
        } else {
                Log.d(LOG_TAG,"Rules directory is a directory") ;
            }
        // Get an instance of the SharedPrefs so we can get / save the default rule title
        defaultRulesSharedPrefs = context.getSharedPreferences(defaultRulesSharedPrefKey,Context.MODE_PRIVATE) ;
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
        if (ruleFiles == null) return;
        for (int i = 0 ; i < ruleFiles.length ; i++) {
            loadRules(ruleFiles[i]);
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
        } else {
            try {
                rulesFile.createNewFile();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error creating rules file: " + saveFileName) ;
                e.printStackTrace();
            }
        }
        // Write the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rulesFile))) {
            Log.d(LOG_TAG, "Saving \"" + title + "\" to: " + rulesFile.getName()) ;
            for (int i = 0 ; i < ruleSet.length; i++) {
                bw.write(ruleSet[i]);
                bw.newLine();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error reading rules file: " + rulesFile.getName()) ;
            e.printStackTrace();
        }
        // re-load the rules
        getLocalRules();
    }

    public String[] getRuleSet(String ruleSetTitle) {
        if (ruleSets.containsKey(ruleSetTitle)) {
            return ruleSets.get(ruleSetTitle) ;
        } else {
            Log.d(LOG_TAG, "Unable to find rules titled: " + ruleSetTitle  + " so returning standard rule set.") ;
            return ruleSets.get(STANDARD_RULES) ;
        }
    }

    public String[] getDefaultRuleSet() {
        String defaultRulesTitle = defaultRulesSharedPrefs.getString(defaultRulesSharedPrefKey, STANDARD_RULES) ;
        return getRuleSet(defaultRulesTitle) ;
    }

    public void deleteLocalRuleSet(String ruleSetTitle) {

    }
}
