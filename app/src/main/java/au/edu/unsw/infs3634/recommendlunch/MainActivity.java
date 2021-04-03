package au.edu.unsw.infs3634.recommendlunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RestaurantAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        RestaurantAdapter.RecyclerViewClickListener listener = new RestaurantAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.INTENT_EXTRA, id);
                startActivity(intent);
            }
        };

        mAdapter = new RestaurantAdapter(new ArrayList<Restaurant>(), listener);
        rvList.setAdapter(mAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pqd82925.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // TODO for Question 1a)
        // Implement code that creates a Retrofit call object to receive the list of all restaurants
        //Implement the service interface by using the retrofit.create method
        RestaurantService service = retrofit.create(RestaurantService.class);
        //Implement a Call and Response object "restaurantCall"
        Call<List<Restaurant>> restaurantCall = service.getRestaurants();

        // TODO for Question 1a)
        // Implement code that makes a Retrofit API call and updates the adapter with the received data
        restaurantCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, retrofit2.Response<List<Restaurant>> response) {
                //Create a List of Restaurants to store the body of the response
                List<Restaurant> restaurants = response.body();
                //Set data(list of restaurants) to adapter and update the data by notifyDataSetChanged in the setData method in the RestaurantAdapter
                mAdapter.setData(restaurants);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });

    }
}
