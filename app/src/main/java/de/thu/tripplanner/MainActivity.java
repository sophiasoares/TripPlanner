package de.thu.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Spinner destinationSpinner;
    private TextView yourTripToTV, countdownTV;
    private EditText dateET;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private SupportMapFragment mapFrag;
    private double latitudeDest, longitudeDest;
    private GoogleMap map;
    private Notifier notifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Spinner settings
        destinationSpinner = (Spinner) findViewById(R.id.destination_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.destinations_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        destinationSpinner.setAdapter(adapter);

        // Set views and button
        yourTripToTV = findViewById(R.id.yourTripTo_textView);
        countdownTV = findViewById(R.id.countdown_textView);
        ImageButton tipsIB = (ImageButton) findViewById(R.id.tips_imageButton);
        ImageButton checklistIB = (ImageButton) findViewById(R.id.checklist_imageButton);
        dateET = (EditText) findViewById(R.id.date_editText);
        Button updateButton = (Button) findViewById(R.id.update_button);

        // Map settings
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.my_map);
        initGoogleMap(savedInstanceState);
    }

    // Called when Update Trip button is pressed
    public void updateTrip(View view) {
        String selectedDestination = destinationSpinner.getSelectedItem().toString();

        // Set "Your trip to" text
        yourTripToTV.setText("Your trip to " + selectedDestination + "!");

        // Set countdown number
        String enteredDate = dateET.getText().toString();
        String dateDifference = getDateDifference(enteredDate);

        // Convert countdown days to Long to use for notifications
        long dateDifferenceLong = Long.parseLong(dateDifference);

        if (dateET != null && isDateValid(enteredDate, view)) {
            if (dateDifferenceLong <= 1) {
                countdownTV.setText(dateDifference + " day to your trip!");
            } else {
                countdownTV.setText(dateDifference + " days to your trip!");
            }
        }

        // Generate notification
        notifier = new Notifier(this, dateDifferenceLong);
        generateNotification(dateDifferenceLong);

        // Set location
        switch (selectedDestination) {
            case "Innsbruck":
                latitudeDest = 47.259659;
                longitudeDest = 11.400375;
                break;
            case "London":
                latitudeDest = 51.500700;
                longitudeDest = -0.124500;
                break;
            case "Rio de Janeiro":
                latitudeDest = -22.951916;
                longitudeDest = -43.210487;
                break;
            default:
                break;
        }

        // Delete previous marker
        map.clear();
        // Add destination marker
        map.addMarker(new MarkerOptions().position(new LatLng(latitudeDest, longitudeDest)).title("Your destination!"));
        // Move camera to the new marker
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitudeDest, longitudeDest)));
    }

    // Generate a notification for the user if the trip is in 7 days or less
    private void generateNotification(long dateDifference) {
        if (dateDifference <= 7) {
            notifier.showOrUpdateNotification();
        } else {
            notifier.removeNotification();
        }
    }

    // Check if the date input by the user is valid
    private boolean isDateValid(String dateStr, View view) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            // Show a popup window

            // Inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);

            // Create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // Taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // Show the popup window
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            // Dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
            return false;
        }
        return true;
    }

    // Called when the tips image is pressed
    public void showTips(View view) {
        // Take me to the Tips activity
        Intent intent = new Intent(MainActivity.this, TipsActivity.class);

        // Check before starting the activity to see if Intent can be resolved
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Send the destination to the other intent
            intent.putExtra("destination", destinationSpinner.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    // Called then the checklist image is pressed
    public void showChecklist(View view) {
        // Take me to the Checklist activity
        Intent intent = new Intent(MainActivity.this, ChecklistActivity.class);

        // Check before starting the activity to see if Intent can be resolved
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Send the destination to the other intent
            intent.putExtra("destination", destinationSpinner.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    // Get the countdown from today to the day of the trip
    private String getDateDifference(String enteredDate) {
        String dayDifference = "x";
        try {
            SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

            // Get today's date
            Date today = Calendar.getInstance().getTime();

            // Setting dates
            Date date1 = dates.parse(dates.format(today));
            Date date2 = dates.parse(enteredDate);

            // Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            // Convert long to String
            dayDifference = Long.toString(differenceDates);

        } catch (Exception exception) {
            Log.e("DATE DIFFERENCE CALC", "Something went wrong: " + exception);
        }

        return dayDifference;
    }

    // Save data input by the user
    @Override
    protected void onPause() {
        mapFrag.onPause();
        super.onPause();

        // Obtain preferences - Alternative: getSharedPreferences
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        // Get editing access
        SharedPreferences.Editor editor = prefs.edit();

        // Get values
        String destination = destinationSpinner.getSelectedItem().toString();
        String countdown = countdownTV.getText().toString();
        String tripDate = dateET.getText().toString();
        String yourTripTo = yourTripToTV.getText().toString();
        double latDest = latitudeDest;
        double longDest = longitudeDest;

        // Store key-value-pair
        editor.putString("Destination", destination);
        editor.putString("Countdown", countdown);
        editor.putString("Trip Date", tripDate);
        editor.putString("Your Trip To", yourTripTo);
        editor.putFloat("Latitude", (float) latDest);
        editor.putFloat("Longitude", (float) longDest);

        // Persist data in XML file
        editor.apply();
    }

    // Restore data input by the user
    @Override
    protected void onResume() {
        super.onResume();
        mapFrag.onResume();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        // Retrieve values from preferences
        String destination = prefs.getString("Destination", null);
        if (destination != null) {
            setSpinText(destinationSpinner, destination);
        }

        String countdown = prefs.getString("Countdown", null);
        if (countdown != null) {
            countdownTV.setText(countdown);
        }

        String tripDate = prefs.getString("Trip Date", null);
        if (tripDate != null) {
            dateET.setText(tripDate);
        }

        String yourTripTo = prefs.getString("Your Trip To", null);
        if (yourTripTo != null) {
            yourTripToTV.setText(yourTripTo);
        }

        float latDest = prefs.getFloat("Latitude", 0f);
        if (latDest != 0) {
            latitudeDest = latDest;
        }

        float longDest = prefs.getFloat("Longitude", 0f);
        if (longDest != 0) {
            longitudeDest = longDest;
        }

    }

    // Set the text of the spinner when I retrieve their value from Preferences
    private void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
            }
        }
    }

    // Called when the map is ready
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        // Set map
        this.map = map;
        // Delete previous marker
        map.clear();
        // Add markers on map
        map.addMarker(new MarkerOptions().position(new LatLng(latitudeDest, longitudeDest)).title("Your destination!"));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitudeDest, longitudeDest)));

        // When clicking on the marker, it takes you to Google Maps app
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Take me to Google Maps (more specifically to the chosen city)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0`?q=" +
                        destinationSpinner.getSelectedItem().toString()));

                // Check before starting the activity to see if Intent can be resolved
                if(intent.resolveActivity(getPackageManager()) != null) startActivity(intent);

                return false;
            }
        });
    }

    // Initialise map
    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapFrag.onCreate(mapViewBundle);
        mapFrag.getMapAsync(this);
    }

    // Map settings
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapFrag.onSaveInstanceState(mapViewBundle);
    }

    // Map methods
    @Override
    public void onStart() {
        super.onStart();
        mapFrag.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapFrag.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFrag.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFrag.onLowMemory();
    }

}

//android:theme="@style/Theme.TripPlanner"
