package au.edu.unsw.infs3634.recommendlunch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

// TODO for Question 1b)
// Fix all the bugs, provide comments that explain the bugs and how your code fixes them.
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    //Bug: The constructor is expecting a List instead of an ArrayList
    //TODO: replace ArrayList with List
    private List<Restaurant> mRestaurants;
    private RecyclerViewClickListener mListener;

    public RestaurantAdapter(List<Restaurant> restaurants, RecyclerViewClickListener listener) {
        mRestaurants = restaurants;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int id);
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Bug: This is the RestaurantAdapter but the layout for the DetailActivity is loaded
        //TODO: replace activity_detail with list_row
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new RestaurantViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        //Bug: mRestaurants.get(0) means that it will always get the first element of the list which we want to get the element based on the position
        //TODO: replace 0 with position
        Restaurant restaurant = mRestaurants.get(position);
        holder.name.setText(restaurant.getName());
        holder.location.setText(restaurant.getLocation());
        //Bug: v.getTag is expecting an integer instead of a String
        //TODO: replace restaurant.getName() to restaurant.getId()
        holder.itemView.setTag(restaurant.getId());
        Glide.with(holder.itemView)
                //Bug: loaded the wrong method to get the image
                //TODO: replace restaurant.getName() to restaurant.getImage()
                .load(restaurant.getImage())
                .fitCenter()
                .into(holder.image);
    }

    @Override
    //Bug: Returning 0 means there will be 0 item on the list so we have to set it to the restaurant list size
    //TODO: replace 0 with mRestaurants.size()
    public int getItemCount() {
        return mRestaurants.size();
    }

    //Bug: To use recyclerViewClickListener, we have to implements View.OnClickListener
    //TODO: implements View.OnClickListener
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        public TextView name, location;
        private RecyclerViewClickListener listener;

        public RestaurantViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            //Bug: listener should not be put in itemView.setOnClickListener
            //TODO: replace listener to this
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.ivCover);
            //Bug: wrong view has been assigned for name
            //TODO: replace tvLocation with tvName
            name = itemView.findViewById(R.id.tvName);
            location = itemView.findViewById(R.id.tvLocation);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (int) v.getTag());
        }
    }

    public void setData(List<Restaurant> data) {
        mRestaurants.clear();
        //Bug: We want to add all the restaurant data instead of null
        //TODO: replace null with data
        mRestaurants.addAll(data);
        //Bug: notify() is not a correct method to notify observers that the underlying data has been changed
        //TODO: replace notify() with notifyDataSetChanged()
        notifyDataSetChanged();
    }
}
