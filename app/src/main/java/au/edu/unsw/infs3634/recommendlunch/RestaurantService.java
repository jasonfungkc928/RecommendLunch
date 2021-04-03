package au.edu.unsw.infs3634.recommendlunch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestaurantService {
    // Return a list of all restaurants
    @GET("/restaurants")
    Call<List<Restaurant>> getRestaurants();

    // TODO for Question 1a)
    // Implement code for a second API endpoint "/restaurants/{id}
    // Details about the API can be found here: https://pqd82925.pythonanywhere.com

    // Return a restaurant by their corresponding Id using @Path which is a variable substitution for the API endpoints
    // id will be swapped for the {id} in the URL endpoint
    @GET("/restaurants/{id}")
    Call<Restaurant> getRestaurantById (@Path("id") int id);


    // TODO for Question 1a)
    // Implement code for a third API endpoint "/restaurants/location/{location}
    // Details about the API can be found here: https://pqd82925.pythonanywhere.com/l

    // Return a list of restaurants according to their location using @Path
    // either upper campus or middle campus or lower campus will be swapped for the {location} in the URL endpoint
    @GET("/restaurants/location/{location}")
    Call<List<Restaurant>> getRestaurantByLocation (@Path("location") String location);


}
