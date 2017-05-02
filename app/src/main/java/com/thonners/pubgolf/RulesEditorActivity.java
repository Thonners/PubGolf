package com.thonners.pubgolf;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class RulesEditorActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_editor);

        // Toolbar admin
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setTitle(R.string.rules_editor_title);

    }

    /**
     * Create the menu from the rules_editor menu file.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_rules_editor, menu);
        return true;
    }

    /**
     * Handle menu clicks (or up/back clicks)
     * @param item The menu item clicked
     * @return Whether the click has been dealt with(?)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                // Someone pressed 'back' in the toolbar - check they want to exit without saving
                showExitConfirmationDialog() ;
                return true ;
            case R.id.action_save:
                // Save the results, then exit
                saveRules() ;
                return true ;
            case R.id.action_info:
                // Show instructions dialog
                showInstructions() ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Shows an alert dialog warning that going back will not save the rules. Offers option to save,
     * quit without saving, or keep editing. Sets 'this' as the onClickListeners for the buttons.
     */
    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        builder.setPositiveButton(R.string.dialog_save, this)
                .setNegativeButton(R.string.dialog_dont_save, this)
                .setNeutralButton(R.string.dialog_keep_editing, this)
                .setTitle(R.string.dialog_save_rules_title)
                //.setMessage(R.string.dialog_save_rules_message)   // Message not requires as title says enough.
                .show();
    }

    /**
     * Saves the rules locally then closes this activity
     */
    private void saveRules() {
        // Save the rules

        // Go back
        this.onBackPressed();
    }

    /**
     * Show a dialog with instructions on how to use the editor.
     */
    private void showInstructions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        builder.setTitle(R.string.dialog_rule_editor_instructions_title)
                .setMessage(R.string.dialog_rule_editor_instructions_message)
                .show();
    }

    /**
     * OnClickListener for the dialog buttons
     * @param dialog
     * @param buttonID
     */
    @Override
    public void onClick(DialogInterface dialog, int buttonID) {
        switch (buttonID) {
            case BUTTON_POSITIVE:
                // Save
                saveRules();
                break;
            case BUTTON_NEGATIVE:
                // Quit without saving
                this.onBackPressed();
                break;
            case BUTTON_NEUTRAL:
                // Keep editing >> do nothing
                dialog.dismiss();
                break;
        }
    }
}
