package au.edu.unsw.infs3634.recommendlunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA = "au.edu.unsw.infs3634.recommendlunch.DetailActivity";
    //Initialize variables
    private TextView tvName;
    private TextView tvCuisine;
    private TextView tvLocation;
    private ImageView ivImage;
    int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO for Question 1c)
        // Complete the DetailActivity class so that it displays all details about a restaurant
        // and integrates with all other classes of the app.
        // Implement the activity_detail.xml file accordingly

        //Assign variables
        tvName = findViewById(R.id.tvName);
        tvCuisine = findViewById(R.id.tvCuisine);
        tvLocation = findViewById(R.id.tvLocation);
        ivImage = findViewById(R.id.ivImage);

        //Getting intent message from MainActivity and set the default value to 0
        Intent intent = getIntent();
        mId = intent.getIntExtra(INTENT_EXTRA, 0);

        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pqd82925.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Implement the service interface by using the retrofit.create method
        RestaurantService service = retrofit.create(RestaurantService.class);
        //Implement a Call and Response object "restaurantCall"
        Call<List<Restaurant>> restaurantCall = service.getRestaurants();

        restaurantCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                //Create a List of Restaurants to store the body of the response
                List<Restaurant> restaurants = response.body();
                //For loop to search for all the restaurant in the list
                for(final Restaurant restaurant : restaurants){
                    //If the id of the restaurant in the list match with the id that we get from the intent.getIntExtra method then set text to text views and set image to image view
                    if (restaurant.getId() == mId){
                        //Set title to restaurant name
                        setTitle(restaurant.getName());
                        tvName.setText("Name: " + restaurant.getName());
                        tvCuisine.setText("Cuisine: " +  restaurant.getCuisine());
                        tvLocation.setText("Location: " + restaurant.getLocation());
                        Glide.with(ivImage)
                                .load(restaurant.getImage())
                                .fitCenter()
                                .into(ivImage);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });
    }

}