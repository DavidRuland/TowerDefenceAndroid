package se.mah.ac9511.towerd;

/**
 * Created by Peter on 2015-10-26.
 */

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

enum Action
{
    IDLE,
    OK,
    FAILD,
    BUILDTOWER1,
    BUILDTOWER2,
    BUILDTOWER3,
    BUILDTOWER4
}

public class Connecter {

    //String mMainConnection;
    private Firebase mMainConnectionRef;
    private Firebase playerRef = new Firebase("https://vivid-heat-894.firebaseio.com/player1");//Lek värde
    private double mGold = 0;
    private double mKills;
    private double mScore;
    private double mLivesLeft;
    private boolean mIsLobbyHost;
    private int playerNr;
    private Action action;
    private String[] playerName;
    private Boolean[] playerReady;
    private String mMyPlayerName;



    public Connecter(Firebase mainConnectionRef)
    {
        this.mMainConnectionRef = mainConnectionRef;
        mGold = 0;
        mKills = 0;
        mScore = 0;
        mLivesLeft = 0;
        action = Action.IDLE;//se till att denna är idle från början.
        playerName = new String[4];
        playerReady = new Boolean[4];
    }

    //region UpdateNode
    private void UpdateNodeXY(String path, int X, int Y)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        int[] arr = new int[2];arr[0] = X; arr[1] = Y;
        putInt.put("Position", arr);
        place.updateChildren(putInt);
    }

    void UpdateNodeStr(String path, String nodeName, String value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        putInt.put(nodeName, value);
        place.updateChildren(putInt);
    }

    void UpdateNodeInt(String path, String nodeName, int value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        putInt.put(nodeName, value);
        place.updateChildren(putInt);
    }

    void UpdateNodeInt(String path, String nodeName, double value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        putInt.put(nodeName, value);
        place.updateChildren(putInt);
    }

    void UpdateNodeBool(String path, String nodeName, boolean value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putBool = new HashMap<String, Object>();
        putBool.put(nodeName, value);
        place.updateChildren((putBool));
    }
    //endregion

    void AttachListeners(Firebase node)
    {
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
        public void onDataChange(DataSnapshot snapshot)
            {
                mGold = (double)snapshot.getValue();
            }


        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed" + firebaseError.getMessage());
        }
        });

        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                mKills = (double)snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });

        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                mLivesLeft = (double)snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });

        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                mScore = (double)snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });

        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                action = (Action) snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });


    }

    /*Action StringToEnum(String value)
    {
        Action ac = Action.nullinull;
        switch (value)
        {
            case "buildTower":
                ac = Action.buildTower;
                break;
            case "statusOk":
                ac = Action.statusOk;
                break;
            case "statusFailed":
                ac = Action.statusFailed;
                break;
            case "statusIdle":
                ac = Action.statusIdle;
            default:
                break;
        }
        return ac;
    }*/

  /* private String EnumToString(Action value)
    {
        String s = "nullinull";
        switch (value)
        {
            case buildTower:
                s = "buildTower";
                break;
            case statusOk:
                s ="statusOk" ;
                break;
            case statusFailed:
                s ="statusFailed" ;
                break;
            case statusIdle:
                s = "statusIdle";
            default:
                break;
        }
        return s;
    }*/

    private String PlayerNumToStr(int value)
    {
        String s = "nullinull";
        switch (value)
        {
            case 0:
                s = "player1";
                break;
            case 1:
                s ="player2" ;
                break;
            case 2:
                s ="player3" ;
                break;
            case 3:
                s = "player4";
            default:
                break;
        }
        return s;
    }

    private String PlayerReadyToStr(int value)
    {
        String s = "nullinull";
        switch (value)
        {
            case 0:
                s = "player1";
                break;
            case 1:
                s ="player2" ;
                break;
            case 2:
                s ="player3" ;
                break;
            case 3:
                s = "player4";
            default:
                break;
        }
        return s;
    }

    private void EnterLobby(Firebase node)
    {
        AttachLobbyListeners(node);
        if(!IfTheresRoom(playerName,node))
        {
            System.out.println("No room in lobby");
            ExitLobby();
        }
    }

    private boolean IfTheresRoom(String[] players, Firebase node)
    {
        for (int i = 0; i < players.length; i++) {
            if(players[i].equals("nullinull"))
            {
                playerNr = i;
                UpdateNodeStr(PlayerNumToStr(i)+"/","name",mMyPlayerName);
                return true;
            }
        }
        return false;
    }

    private void ExitLobby()
    {

    }

    private void AttachLobbyListeners(Firebase node)
    {
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerName[0] = snapshot.getValue().toString();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerReady[0] = (Boolean) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerName[1] = snapshot.getValue().toString();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerReady[1] = (Boolean) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerName[2] = snapshot.getValue().toString();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerReady[2] = (Boolean) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerName[3] = snapshot.getValue().toString();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        node.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                playerReady[3] = (Boolean) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });



    }

    private void MakeReady(boolean[] playerReady, Firebase node)
    {
        UpdateNodeBool("lobby/" + playerNr,"ready", (boolean)playerReady[playerNr]);
        //other stuff
    }

    private void SetGameOtions()
    {

    }

    void IsReadyToPlay()
    {

    }

    void populateFilds()
    {

    }

    /** lobby
     *          player1
     *              name
     *              ready
     *          player2
     *              name
     *              ready
     *          player3
     *              name
     *              ready
     *          player4
     *              name
     *              ready
     *      Options
     *          numberOfLives
     *          difficulty
     *          handicap?
     *
     *  Game
     *      player1
     *          position
     *              X
     *              Y
     *          kills
     *          livesLeft
     *          gold
     *          score
     *          isAlive
     *          Action
     *              buildTower
     *              statusOk
     *              statusFailed
     *              statusIdle
     *     player2
     *
     * Is more logical if gameServer populates the fields.
     *          */

}


