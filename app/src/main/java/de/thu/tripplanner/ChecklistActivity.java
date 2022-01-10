package de.thu.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChecklistActivity extends AppCompatActivity {

    private Spinner destinationTypeSpinner;
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        // Get value passed to the intent
        Intent intent = getIntent();
        String selectedDestination = intent.getStringExtra("destination");

        destinationTypeSpinner = (Spinner) findViewById(R.id.type_spinner);

        // Set toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Create destinations
        Destination innsbruck = new Destination("Innsbruck", new String[]{"Business", "Mountain", "Cultural"});
        Destination london = new Destination("London", new String[]{"Business", "Cultural"});
        Destination rio = new Destination("Rio de Janeiro", new String[]{"Beach", "Business", "Cultural"});

        ArrayAdapter<CharSequence> adapter;

        // Set adapter depending on the destination
        Destination destination;
        if(selectedDestination.equals(innsbruck.getCity())) {
            destination = innsbruck;
            adapter = ArrayAdapter.createFromResource(this, R.array.innsbruck_types_array, android.R.layout.simple_spinner_item);
        } else if(selectedDestination.equals(london.getCity())) {
            destination = london;
            adapter = ArrayAdapter.createFromResource(this, R.array.london_types_array, android.R.layout.simple_spinner_item);
        } else {
            destination = rio;
            adapter = ArrayAdapter.createFromResource(this, R.array.rio_types_array, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationTypeSpinner.setAdapter(adapter);

        // Set checkboxes
        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);
        cb3 = (CheckBox) findViewById(R.id.checkBox3);
        cb4 = (CheckBox) findViewById(R.id.checkBox4);
        cb5 = (CheckBox) findViewById(R.id.checkBox5);
        cb6 = (CheckBox) findViewById(R.id.checkBox6);
        cb7 = (CheckBox) findViewById(R.id.checkBox7);
        cb8 = (CheckBox) findViewById(R.id.checkBox8);
        cb9 = (CheckBox) findViewById(R.id.checkBox9);
        cb10 = (CheckBox) findViewById(R.id.checkBox10);

        // Make checkboxes invisible
        cb1.setVisibility(View.INVISIBLE);
        cb2.setVisibility(View.INVISIBLE);
        cb3.setVisibility(View.INVISIBLE);
        cb4.setVisibility(View.INVISIBLE);
        cb5.setVisibility(View.INVISIBLE);
        cb6.setVisibility(View.INVISIBLE);
        cb7.setVisibility(View.INVISIBLE);
        cb8.setVisibility(View.INVISIBLE);
        cb9.setVisibility(View.INVISIBLE);
        cb10.setVisibility(View.INVISIBLE);
    }

    // Called when generate list button is pressed
    public void generateChecklist(View view) {
        String destinationType = destinationTypeSpinner.getSelectedItem().toString();

        // Make checkboxes visible
        cb1.setVisibility(View.VISIBLE);
        cb2.setVisibility(View.VISIBLE);
        cb3.setVisibility(View.VISIBLE);
        cb4.setVisibility(View.VISIBLE);
        cb5.setVisibility(View.VISIBLE);
        cb6.setVisibility(View.VISIBLE);
        cb7.setVisibility(View.VISIBLE);
        cb8.setVisibility(View.VISIBLE);
        cb9.setVisibility(View.VISIBLE);
        cb10.setVisibility(View.VISIBLE);

        // Uncheck all checkboxes in case they were previously checked
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        cb6.setChecked(false);
        cb7.setChecked(false);
        cb8.setChecked(false);
        cb9.setChecked(false);
        cb10.setChecked(false);

        // Check type of destination and call respective method to generate the checkboxes
        switch (destinationType) {
            case "Beach":
                generateCheckBoxesForBeach();
                break;
            case "Business":
                generateCheckBoxesForBusiness();
                break;
            case "Mountain":
                generateCheckBoxesForMountain();
                break;
            case "Cultural":
                generateCheckBoxesForCultural();
                break;
            default:
                break;
        }
    }

    // Called when Beach type is selected
    private void generateCheckBoxesForBeach() {
        cb1.setText("Sunscreen");
        cb2.setText("Sunglasses");
        cb3.setText("Bathing suit");
        cb4.setText("Hat/cap");
        cb5.setText("Cellphone and portable charger");
        cb6.setText("Water bottle");
        cb7.setText("Towel");
        cb8.setText("Frisbees and beach balls");
        cb9.setText("Snorkel");
        cb10.setText("Beach umbrella");
    }

    // Called when Business type is selected
    private void generateCheckBoxesForBusiness() {
        cb1.setText("Suitcase and wallet");
        cb2.setText("Journal");
        cb3.setText("Cellphone and portable charger");
        cb4.setText("Travel documents");
        cb5.setText("Laptop or tablet");
        cb6.setText("Business Cards");
        cb7.setText("Pens");
        cb8.setText("Printed handouts");
        cb9.setText("Work clothes");
        cb10.setText("Water bottle");
    }

    // Called when Mountain type is selected
    private void generateCheckBoxesForMountain() {
        cb1.setText("Cellphone and portable charger");
        cb2.setText("Water bottle");
        cb3.setText("Sunscreen");
        cb4.setText("Sunglasses");
        cb5.setText("Insect repellent");
        cb6.setText("First aid kit");
        cb7.setText("Snacks");
        cb8.setText("Backpack");
        cb9.setText("Hiking shoes");
        cb10.setText("Rain jacket");
    }

    // Called when Cultural type is selected
    private void generateCheckBoxesForCultural() {
        cb1.setText("Water bottle");
        cb2.setText("Tickets (theater/museum/tower)");
        cb3.setText("Map");
        cb4.setText("Journal and pen");
        cb5.setText("Camera");
        cb6.setText("Travel documents");
        cb7.setText("Cellphone and portable charger");
        cb8.setText("Comfortable clothes");
        cb9.setText("Accessories");
        cb10.setText("Recommendation annotations");
    }

    // Called when return to main menu button is pressed
    public void returnToMainMenu(View view) {
        // Take me to the main activity
        Intent intent = new Intent(ChecklistActivity.this, MainActivity.class);

        // Check before starting the activity to see if Intent can be resolved
        if(intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }

    // Save data input by the user
    @Override
    protected void onPause() {
        super.onPause();

        // Obtain preferences - Alternative: getSharedPreferences
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        // Get editing access
        SharedPreferences.Editor editor = prefs.edit();

        // Get values
        String destination = destinationTypeSpinner.getSelectedItem().toString();
        String check1 = cb1.getText().toString();
        String check2 = cb2.getText().toString();
        String check3 = cb3.getText().toString();
        String check4 = cb4.getText().toString();
        String check5 = cb5.getText().toString();
        String check6 = cb6.getText().toString();
        String check7 = cb7.getText().toString();
        String check8 = cb8.getText().toString();
        String check9 = cb9.getText().toString();
        String check10 = cb10.getText().toString();

        boolean c1 = cb1.isChecked();
        boolean c2 = cb2.isChecked();
        boolean c3 = cb3.isChecked();
        boolean c4 = cb4.isChecked();
        boolean c5 = cb5.isChecked();
        boolean c6 = cb6.isChecked();
        boolean c7 = cb7.isChecked();
        boolean c8 = cb8.isChecked();
        boolean c9 = cb9.isChecked();
        boolean c10 = cb10.isChecked();

        int myc1 = cb1.getVisibility();
        int myc2 = cb2.getVisibility();
        int myc3 = cb3.getVisibility();
        int myc4 = cb4.getVisibility();
        int myc5 = cb5.getVisibility();
        int myc6 = cb6.getVisibility();
        int myc7 = cb7.getVisibility();
        int myc8 = cb8.getVisibility();
        int myc9 = cb9.getVisibility();
        int myc10 = cb10.getVisibility();

        // Store key-value-pair
        editor.putString("Destination", destination);
        editor.putString("CB1", check1);
        editor.putString("CB2", check2);
        editor.putString("CB3", check3);
        editor.putString("CB4", check4);
        editor.putString("CB5", check5);
        editor.putString("CB6", check6);
        editor.putString("CB7", check7);
        editor.putString("CB8", check8);
        editor.putString("CB9", check9);
        editor.putString("CB10", check10);

        editor.putBoolean("C1", c1);
        editor.putBoolean("C2", c2);
        editor.putBoolean("C3", c3);
        editor.putBoolean("C4", c4);
        editor.putBoolean("C5", c5);
        editor.putBoolean("C6", c6);
        editor.putBoolean("C7", c7);
        editor.putBoolean("C8", c8);
        editor.putBoolean("C9", c9);
        editor.putBoolean("C10", c10);

        editor.putInt("MYC1", myc1);
        editor.putInt("MYC2", myc2);
        editor.putInt("MYC3", myc3);
        editor.putInt("MYC4", myc4);
        editor.putInt("MYC5", myc5);
        editor.putInt("MYC6", myc6);
        editor.putInt("MYC7", myc7);
        editor.putInt("MYC8", myc8);
        editor.putInt("MYC9", myc9);
        editor.putInt("MYC10", myc10);

        // Persist data in XML file
        editor.apply();
    }

    // Restore data input by the user
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        // Retrieve values from preferences
        String restoredDestination = prefs.getString("Destination", null);
        String check1 = prefs.getString("CB1", null);
        String check2 = prefs.getString("CB2", null);
        String check3 = prefs.getString("CB3", null);
        String check4 = prefs.getString("CB4", null);
        String check5 = prefs.getString("CB5", null);
        String check6 = prefs.getString("CB6", null);
        String check7 = prefs.getString("CB7", null);
        String check8 = prefs.getString("CB8", null);
        String check9 = prefs.getString("CB9", null);
        String check10 = prefs.getString("CB10", null);

        boolean c1 = prefs.getBoolean("C1", false);
        boolean c2 = prefs.getBoolean("C2", false);
        boolean c3 = prefs.getBoolean("C3", false);
        boolean c4 = prefs.getBoolean("C4", false);
        boolean c5 = prefs.getBoolean("C5", false);
        boolean c6 = prefs.getBoolean("C6", false);
        boolean c7 = prefs.getBoolean("C7", false);
        boolean c8 = prefs.getBoolean("C8", false);
        boolean c9 = prefs.getBoolean("C9", false);
        boolean c10 = prefs.getBoolean("C10", false);

        int myc1 = prefs.getInt("MYC1", 4); // 4 makes it invisible
        int myc2 = prefs.getInt("MYC2", 4);
        int myc3 = prefs.getInt("MYC3", 4);
        int myc4 = prefs.getInt("MYC4", 4);
        int myc5 = prefs.getInt("MYC5", 4);
        int myc6 = prefs.getInt("MYC6", 4);
        int myc7 = prefs.getInt("MYC7", 4);
        int myc8 = prefs.getInt("MYC8", 4);
        int myc9 = prefs.getInt("MYC9", 4);
        int myc10 = prefs.getInt("MYC10", 4);

        // Make checkboxes visible
        cb1.setVisibility(myc1);
        cb2.setVisibility(myc2);
        cb3.setVisibility(myc3);
        cb4.setVisibility(myc4);
        cb5.setVisibility(myc5);
        cb6.setVisibility(myc6);
        cb7.setVisibility(myc7);
        cb8.setVisibility(myc8);
        cb9.setVisibility(myc9);
        cb10.setVisibility(myc10);

        // Set values
        if (restoredDestination != null) setSpinText(destinationTypeSpinner, restoredDestination);
        cb1.setText(check1);
        cb2.setText(check2);
        cb3.setText(check3);
        cb4.setText(check4);
        cb5.setText(check5);
        cb6.setText(check6);
        cb7.setText(check7);
        cb8.setText(check8);
        cb9.setText(check9);
        cb10.setText(check10);

        cb1.setChecked(c1);
        cb2.setChecked(c2);
        cb3.setChecked(c3);
        cb4.setChecked(c4);
        cb5.setChecked(c5);
        cb6.setChecked(c6);
        cb7.setChecked(c7);
        cb8.setChecked(c8);
        cb9.setChecked(c9);
        cb10.setChecked(c10);
    }

    // Set the text of the spinner when I retrieve their value from Preferences
    private void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
            }
        }
    }


}
