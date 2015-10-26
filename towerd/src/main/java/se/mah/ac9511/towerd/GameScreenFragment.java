package se.mah.ac9511.towerd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameScreenFragment extends Fragment {

    //A vector is like an ArrayList a little bit slower but Thread-safe. This means that it can handle concurrent changes.
    TextView tUser,tMoney,tState;
    Button pTower;
    User user;
   String Gold;


    private Firebase myFirebaseRef;



    public GameScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_game_screen, container, false);
        Firebase.setAndroidContext(this.getContext());
        myFirebaseRef = new Firebase("https://blistering-heat-6102.firebaseio.com/");
       myFirebaseRef.child("Game/0");


        tUser=(TextView)v.findViewById(R.id.playerTextView);
        tMoney=(TextView)v.findViewById(R.id.moneyTextView);
        tState=(TextView)v.findViewById(R.id.enemyKilledTextView);
        pTower=(Button)v.findViewById(R.id.buttonTower);
        user=new User("Player 1",0,0,User.Action.IDLE,Gold);

        tUser.setText(user.getId());
        tState.setText(user.getPriority().toString());
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("There are ","Items: " + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Gold=postSnapshot.child("Gold").getKey();
                    tMoney.setText(Gold);



                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        myFirebaseRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {





                       // Gold= myFirebaseRef.child("Game").child("0").child("Gold").toString();




            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return v;
    }


}
