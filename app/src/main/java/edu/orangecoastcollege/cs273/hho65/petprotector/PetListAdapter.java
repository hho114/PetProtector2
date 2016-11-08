package edu.orangecoastcollege.cs273.hho65.petprotector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy Ho on 11/5/2016.
 */


/**
 * Helper class to provide custom adapter for the <code>Pet</code> list.
 */
public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetList = new ArrayList<>();
    private int mResourceId;

    /**
     * Creates a new <code>PetListAdapter</code> given a mContext, resource id and list of pets.
     *
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param pets The list of games to display
     */
    public PetListAdapter(Context c, int rId, List<Pet> pets) {
        super(c, rId, pets);
        mContext = c;
        mResourceId = rId;
        mPetList = pets;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the Pet selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        //TODO:  Code for getting the view of a list item correctly inflated.
        Pet selectedPet = mPetList.get(pos);

        LinearLayout petListLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        TextView petListNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView petListDetailTextView = (TextView) view.findViewById(R.id.petListDetailTextView);
        ImageView petListImageView = (ImageView) view.findViewById(R.id.petListImageView);

        petListLinearLayout.setTag(selectedPet);

        petListImageView.setImageURI(selectedPet.getmImageURI());
        petListNameTextView.setText(selectedPet.getmName());
        petListDetailTextView.setText(selectedPet.getmDetails());


        return view;
    }
}