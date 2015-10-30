package se.mah.ac9511.towerd;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameScreenFragment extends Fragment {

    //A vector is like an ArrayList a little bit slower but Thread-safe. This means that it can handle concurrent changes.
    TextView tUser,tMoney,tState;
    Button pTower;
    User user;
   int Gold;
    int x,y;
   Connecter c;
    SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Firebase myFirebaseRef;
    Paint paint;
    Canvas canvas;




    public GameScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_game_screen, container, false);
        InitializeFirebaseValue(v);

       return v;
        //return new CustomView(this.getActivity());
    }
    private void InitializeFirebaseValue(View v)
    {
        surfaceView=(SurfaceView)v.findViewById(R.id.surfaceView);
        holder=surfaceView.getHolder();
        Firebase.setAndroidContext(this.getContext());
        myFirebaseRef = new Firebase("https://blistering-heat-6102.firebaseio.com/");
        user=new User("Player 1",100,100,Action.IDLE,Gold,Color.BLUE);

        //c.UpdateIntValue(ToUpdate.GOLD,user.getMoney());
        c=new Connecter(myFirebaseRef,user.getId());
        myFirebaseRef.child("Game/0");
        c.UpdateNodeXY("Game/0", user.getxPos(), user.getyPos());

        tUser=(TextView)v.findViewById(R.id.playerTextView);
        tMoney=(TextView)v.findViewById(R.id.moneyTextView);
        tState=(TextView)v.findViewById(R.id.enemyKilledTextView);
        //tMoney.setText(Integer.toString(Gold));
        pTower=(Button)v.findViewById(R.id.buttonTower);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HandleTouch(event);
                return true;
            }
        });
        pTower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                user.setxPos(r.nextInt(300));
                user.setyPos(r.nextInt(600));
                c.UpdateNodeXY("Game/0", user.getxPos(), user.getyPos());
            }
        });
        tUser.setText(user.getId());
        tState.setText(user.getPriority().toString());
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("There are ","Items: " + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Gold= (String)postSnapshot.child("Game/0/Gold").getValue();
                    // snapshot.getValue();
                    Log.i("Snapshot",snapshot.child("Game/0/Gold").getValue().toString());
                    //Gold= String.valueOf(myFirebaseRef.child(postSnapshot.getKey()).child("Gold"));
                    //myFirebaseRef.child(postSnapshot.getKey()).child("Gold").setValue((int)postSnapshot.getValue()+50);
                    //tMoney.setText(Gold);
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
            public void onChildChanged(DataSnapshot arg0, String arg) {

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

    }
private void UserPos(MotionEvent event)
{

    x = (int) event.getX();
    y = (int) event.getY();
    user.setxPos(x);
    user.setyPos(y);
    c.UpdateNodeXY("Game/0", user.getxPos(), user.getyPos());
    Log.i("ACTION_DOWN", "Resultat: " + x + "," + y);
    canvas =new Canvas();
    paint = new Paint();
    paint.setColor(Color.BLACK);
    canvas.drawRect(x, y, 25, 25, paint);
   //holder.unlockCanvasAndPost(canvas);
}
    private void HandleTouch(MotionEvent event) {
        final int actionPeformed = event.getAction();

        switch (actionPeformed) {
            case MotionEvent.ACTION_DOWN: {
                UserPos(event);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                UserPos(event);
                break;
            }
        }
    }


    }




