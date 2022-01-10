package de.thu.tripplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TipsActivity extends AppCompatActivity  {

    private Spinner destinationSpinner;
    private String selectedDestination;
    private TextView yourTipsFor;
    private TextView tip1TV, tip2TV, tip3TV, tip4TV;
    private ImageView tip1IV, tip2IV, tip3IV, tip4IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        // Get value passed to the intent
        Intent intent = getIntent();
        selectedDestination = intent.getStringExtra("destination");

        // Set toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Set spinner and adapter
        destinationSpinner = (Spinner) findViewById(R.id.otherLocations_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.destinations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(adapter);

        // Initialise text views and image views
        yourTipsFor = findViewById(R.id.showingTips_textView);
        tip1TV = findViewById(R.id.tip1_textView);
        tip2TV = findViewById(R.id.tip2_textView);
        tip3TV = findViewById(R.id.tip3_textView);
        tip4TV = findViewById(R.id.tip4_textView);
        tip1IV = (ImageView) findViewById(R.id.tip1_imageView);
        tip2IV = (ImageView) findViewById(R.id.tip2_imageView);
        tip3IV = (ImageView) findViewById(R.id.tip3_imageView);
        tip4IV = (ImageView) findViewById(R.id.tip4_imageView);

        // Set text views and image views
        yourTipsFor.setText("Tips for your trip to " + selectedDestination + "!");
        setTips();
        setImages();
    }

    // Called when the user wants to see tips for another destination
    public void showTipsForOtherLocation(View view) {
        selectedDestination = destinationSpinner.getSelectedItem().toString();
        // Set text view
        yourTipsFor.setText("Your tips for your trip to " + selectedDestination + "!");
        // Set tips and images
        setTips();
        setImages();
    }

    // Called when return to main menu button is pressed
    public void returnToMainMenu(View view) {
        // Take me to the main activity
        Intent intent = new Intent(TipsActivity.this, MainActivity.class);

        // Check before starting the activity to see if Intent can be resolved
        if(intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }

    // Set tips depending on the destination
    private void setTips() {
        switch (selectedDestination) {
            case "Innsbruck":
                tip1TV.setText("The city and its surroundings are best to travel by public transport. " +
                        "Catching a taxi on the street of the city is extremely problematic, so, if you need one, " +
                        "it’s better to call to order it beforehand.");
                tip2TV.setText("The majority of stores and shopping malls are open every day except for Sundays. " +
                        "Many stores close for lunch on daytime.");
                tip3TV.setText("Those, who expect to ski a lot during their rest in Innsbruck, do not have to take " +
                        "sports equipment with them. You will find many sports equipment rentals in the city.");
                tip4TV.setText("In order to save on attending exhibitions and cultural events, you can purchase " +
                        "special \"city-card\". Besides numerous benefits, the card gives the right to enjoy a reduced " +
                        "fare in public transport.");
                break;
            case "London":
                tip1TV.setText("If it is your first time in London, the underground can be slightly daunting " +
                        "but is by far the best way to get around the city. Taxis are too expensive, buses " +
                        "are too slow and the city is far too big to spend all day walking around.");
                tip2TV.setText("Money can be withdrawn from any ATM but to avoid transaction charges, head " +
                        "to a ATM outside a chain supermarket or a bank. Some hotels and larger stores in " +
                        "London may accept the Euro.");
                tip3TV.setText("Book yourself an Afternoon Tea at one of the fancy hotels, go to a small " +
                        "local café for a Full English Breakfast and don’t forget to try some fish ’n’ chips.");
                tip4TV.setText("If you are looking to save some money, check out the budget chain hotels " +
                        "like Travelodge, Premier Inn or Ibis – just make sure to check reviews online " +
                        "before you go because chain hotels in London can be hit or miss. ");
                break;
            case "Rio de Janeiro":
                tip1TV.setText("High season is, of course, summer (December-March). But you don't " +
                        "have to wait for a specific season to visit Rio de Janeiro, since temperatures " +
                        "are nice and warm year round!");
                tip2TV.setText("In Rio de Janeiro, you need to call a bus just like you would do " +
                        "with a taxi. Even if you’re at a bus stop, it won’t pick you up unless you " +
                        "raise your hand and manifest your interest.");
                tip3TV.setText("Do not drink tap water! Even locals don’t do that. You’ll easily find " +
                        "bottled water everywhere. I know it’s really not eco-friendly, so you can also " +
                        "use any purifying device you have and a reusable bottle. In fact, please choose that second option.");
                tip4TV.setText("Make sure to include time for travel between" +
                        " destinations on your itinerary. Ask for a metro and bus guide as soon as you arrive. " +
                        "You can use the subway for the main attractions, but there's also a city train.");
                break;
            default:
                break;
        }
    }

    // Set images depending on the destination
    private void setImages() {
        switch (selectedDestination) {
            case "Innsbruck":
                tip1IV.setImageResource(R.drawable.innsbruck1);
                tip2IV.setImageResource(R.drawable.innsbruck2);
                tip3IV.setImageResource(R.drawable.innsbruck3);
                tip4IV.setImageResource(R.drawable.innsbruck4);
                break;
            case "London":
                tip1IV.setImageResource(R.drawable.london1);
                tip2IV.setImageResource(R.drawable.london2);
                tip3IV.setImageResource(R.drawable.london3);
                tip4IV.setImageResource(R.drawable.london4);
                break;
            case "Rio de Janeiro":
                tip1IV.setImageResource(R.drawable.rio1);
                tip2IV.setImageResource(R.drawable.rio2);
                tip3IV.setImageResource(R.drawable.rio3);
                tip4IV.setImageResource(R.drawable.rio4);
                break;
            default:
                break;
        }
    }

}
