package se.mah.ac9511.towerd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameScreenFragment extends Fragment {

    //A vector is like an ArrayList a little bit slower but Thread-safe. This means that it can handle concurrent changes.
    TextView tUser,tMoney,tEnemyKilled;
    Button pTower;


    public GameScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_game_screen, container, false);
        tUser=(TextView)v.findViewById(R.id.playerTextView);
        tMoney=(TextView)v.findViewById(R.id.moneyTextView);
        tEnemyKilled=(TextView)v.findViewById(R.id.enemyKilledTextView);
        pTower=(Button)v.findViewById(R.id.buttonTower);
        return v;
    }


}
