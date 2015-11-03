package se.mah.ac9511.towerd;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameScreenFragment extends Fragment {
    TextView tUser,tMoney,tState;
   int Gold;
    ImageView image1,image2,image3;
    int x,y;
   Connecter c;
    SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Firebase myFirebaseRef;
    Paint paint;
    Canvas canvas;
    User user;





    public GameScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_game_screen, container, false);
        user=getArguments().getParcelable("user");
        surfarceH(v);
        TextViewInfo(v);

       return v;

    }
    private void surfarceH(View v) {
        surfaceView = (SurfaceView) v.findViewById(R.id.surfaceView);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HandleTouch(event);
                return true;
            }
        });

    }
    private void TextViewInfo(View v)
    {
        image1=(ImageView)v. findViewById(R.id.imageView);
        image2=(ImageView)v. findViewById(R.id.imageView2);
        image3=(ImageView)v. findViewById(R.id.imageView3);
        tUser = (TextView) v.findViewById(R.id.textView);
        tMoney = (TextView) v.findViewById(R.id.textView3);
        tState = (TextView) v.findViewById(R.id.textView2);
        tUser.setText(user.getName());
        tMoney.setText(String.valueOf(user.getMoney()));
        tState.setText(user.getPriority().toString());
    }
private void UserPos(MotionEvent event)
{

    x = (int) event.getX();
    y = (int) event.getY();
    user.setxPos(x);
    user.setyPos(y);
    c.UpdatePosition(user.getxPos(), user.getyPos());
    Log.i("ACTION_DOWN", "Resultat: " + x + "," + y);
    canvas =new Canvas();
    paint = new Paint();
    paint.setColor(Color.BLACK);
    canvas.drawRect(x, y, 25, 25, paint);
  // holder.unlockCanvasAndPost(canvas);
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




