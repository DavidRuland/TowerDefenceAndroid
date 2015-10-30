package se.mah.ac9511.towerd;

/**
 * Created by Peter on 2015-10-26.
 */

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

enum Action
{
    IDLE,
    OK,
    FAILED,
    BUILD_TOWER_1,
    BUILD_TOWER_2,
    BUILD_TOWER_3,
    BUILD_TOWER_4
}

enum ToUpdate
{
    GOLD,
    KILLS,
    LIVES_LEFT,
    SCORE,
    IS_ALIVE,
}

public class Connecter {

    //String mMainConnection;
    private Firebase mMainConnectionRef;
    private String mShortcut;
    private boolean mInLobby;
    private Firebase playerRef = new Firebase("https://vivid-heat-894.firebaseio.com/");//Lek värde
    private double mGold = 0;
    private double mKills;
    private double mScore;
    private double mLivesLeft;
    private boolean mIsLobbyHost;

    private int playerNr;
    private Action action;
    private String[] playerName;
    private boolean[] mPlayerReady;

    private String mMyPlayerName;
    //private List<ValueEventListener> mValueEeventListenerList;
    private List<ListenerMemory> mListenerMemory;

    //region Getters and Setters
    public String getmMyPlayerName() {
        return mMyPlayerName;
    }

    public void setMyPlayerName(String mMyPlayerName) {
        this.mMyPlayerName = mMyPlayerName;
    }

    public String getPlayerName(int index) {
        return playerName[index];
    }

    public boolean getPlayerReady(int index) { return mPlayerReady[index];}

    public boolean[] getAllPlayersReadiness() {return mPlayerReady;}

    public Action getAction() {
        return action;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public boolean IsLobbyHost() {
        return mIsLobbyHost;
    }

    public double getLivesLeft() {
        return mLivesLeft;
    }

    public double getScore() {
        return mScore;
    }

    public double getKills() {
        return mKills;
    }

    public double getGold() {
        return mGold;
    }

    public boolean isInLobby() {
        return mInLobby;
    }

    public void setInLobby(boolean mInLobby) {
        this.mInLobby = mInLobby;
    }

    /** Om man vill snabbtesta.
     * public void setmShortcut(String mShortcut) {
        this.mShortcut = mShortcut;
    }*/

    /** Ska sättas i konstruktorn.
    public void setmMainConnectionRef(Firebase mMainConnectionRef) {
        this.mMainConnectionRef = mMainConnectionRef;
    }*/
//endregion

    public Connecter(Firebase mainConnectionRef, String myPlayerName)
    {
        this.mMainConnectionRef = mainConnectionRef;
        mGold = 0;
        mKills = 0;
        mScore = 0;
        mLivesLeft = 0;
        action = Action.IDLE;//se till att denna är idle från början.
        playerName = new String[4];
        mPlayerReady = new boolean[4];
        mMyPlayerName = myPlayerName;
        //mValueEeventListenerList = new ArrayList<>();
        mListenerMemory = new ArrayList<>();
        playerNr = 9999;
        mInLobby = false;
    }

    //region UpdateNode
    private void UpdateNodeXY(String path, int x, int y)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        String numbers = Integer.toString(x)+","+Integer.toString(y);
        putInt.put("Position", numbers);
        place.updateChildren(putInt);
    }

    private void UpdateNodeStr(String path, String nodeName, String value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        putInt.put(nodeName, value);
        place.updateChildren(putInt);
    }

   private void UpdateNodeInt(String path, String nodeName, int value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        putInt.put(nodeName, value);
        place.updateChildren(putInt);
    }

    private void UpdateNodeDouble(String path, String nodeName, double value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putInt = new HashMap<String, Object>();
        putInt.put(nodeName, value);
        place.updateChildren(putInt);
    }

    private void UpdateNodeBool(String path, String nodeName, boolean value)
    {
        Firebase place = mMainConnectionRef.child(path);
        Map<String, Object> putBool = new HashMap<String, Object>();
        putBool.put(nodeName, value);
        place.updateChildren((putBool));
    }
    //endregion

    private void AttachListeners(Firebase node)
    {
        mListenerMemory.add(new ListenerMemory(node.child("Gold"), node.child("Gold").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mGold = (double) snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        })));

        mListenerMemory.add(new ListenerMemory(node.child("Kills"), node.child("Kills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mKills = (double) snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        })));

        mListenerMemory.add(new ListenerMemory(node.child("LivesLeft"), node.child("LivesLeft").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mLivesLeft = (double) snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        })));

        mListenerMemory.add(new ListenerMemory(node.child("Score"), node.child("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mScore = (double) snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        })));

        mListenerMemory.add(new ListenerMemory(node.child("action"), node.child("action").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                action = (Action) snapshot.getValue();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        })));


    }

    public void UpdatePosition(int x,int y)
    {
        UpdateNodeXY(mShortcut, x, y);
    }

    public void UpdateAction(Action a)
    {
        UpdateNodeInt(mShortcut, "Action", a.ordinal());
    }

    public void UpdateIntValue(ToUpdate task,int value)
    {
        switch (task)
        {
            case GOLD:
                UpdateNodeStr(mShortcut,"Gold",Integer.toString(value));
                break;
            case KILLS:
                UpdateNodeStr(mShortcut,"Kills",Integer.toString(value));
                break;
            case LIVES_LEFT:
                UpdateNodeStr(mShortcut,"LivesLeft",Integer.toString(value));
                break;
            case SCORE:
                UpdateNodeStr(mShortcut,"Score",Integer.toString(value));
                break;
            case IS_ALIVE:
                UpdateNodeStr(mShortcut,"IsAlive",Integer.toString(value));
                break;
            default:
                System.out.println("Error Has occurred in Connector; UpdateIntValue");
                break;
        }
    }

    public void EnterLobby()
    {
        AttachLobbyListeners();
        if(mInLobby = ThereIsRoom(playerName))
        {
            AttachListeners(mMainConnectionRef.child(mShortcut));
        }
        else
        {
            System.out.println("No room in lobby");
            ExitLobby();
        }
    }

    private boolean ThereIsRoom(String[] players)
    {
        for (int i = 0; i < players.length; i++) {
            if(players[i].equals("Empty"))
            {
                playerNr = i;
                UpdateNodeStr( "Lobby/"+i,"name",mMyPlayerName);
                mShortcut = "Game/"+Integer.toString(playerNr);
                if(i==0)
                {
                    mIsLobbyHost = true;
                }
                return true;
            }
        }
        return false;
    }

    public void ExitLobby()
    {
        RemoveLobbyListeners();
        if(mInLobby)
        {
            UpdateNodeStr( "Lobby/"+playerNr,"name","Empty");
            if(mPlayerReady[playerNr])
            {
                ReadyFlipFlop(mPlayerReady,playerNr);
            }
        }
        mIsLobbyHost = false;
    }

    private void RemoveLobbyListeners()
    {
        for (ListenerMemory listener: mListenerMemory
             ) {
            listener.getLocation().removeEventListener(listener.getListener());
        }
    }

    private void AttachLobbyListeners()
    {
        for (int i = 0; i < 4; i++) {
            final int j = i;
            Firebase ref = mMainConnectionRef.child("lobby").child(Integer.toString(i)).child("Name");
            mListenerMemory.add(new ListenerMemory(ref, ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            playerName[j] = snapshot.getValue().toString();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed" + firebaseError.getMessage());
                        }
                    })));
            ref = mMainConnectionRef.child("lobby").child(Integer.toString(i)).child("Ready");
            mListenerMemory.add(new ListenerMemory(ref,ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    mPlayerReady[j] = (Boolean) snapshot.getValue();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed" + firebaseError.getMessage());
                }
            })));
        }
    }

    public void ReadyFlipFlop(boolean[] playerReady, int playernumber)
    {
        if(playerReady[playernumber])
        {
            playerReady[playernumber] = false;
        }
        else
        {
            playerReady[playernumber] = true;
        }
        UpdateNodeBool("lobby/" + playernumber, "Ready", playerReady[playernumber]);
    }

    public boolean CommitOtionsChanges(int difficulty,int numberOfLives)
    {
        if(mIsLobbyHost) {
            Firebase optionsRef = mMainConnectionRef.child("Options");
            Map<String, Object> opt = new HashMap<>();
            opt.put("Difficulty", Integer.toString(difficulty));
            opt.put("Lives", Integer.toString(numberOfLives));
            optionsRef.setValue(opt);
            return true;
        }
        else{
            System.out.println("You are not currently lobby host. Only the host can change otions");
            return false;
        }

    }

    public void SetAllToDefaultValuesOnFirebase(Firebase MainRef)
    {
        Firebase lobbyRef = MainRef.child("Lobby");
        Map<String, Object> p = new HashMap<>();
        p.put("Name","Empty");
        p.put("Ready", false);

        Map<String, Map<String, Object>> players = new HashMap<>();
        players.put("0", p);
        players.put("1", p);
        players.put("2", p);
        players.put("3", p);

        lobbyRef.setValue(players);

        Firebase gameRef = MainRef.child("Game");
        Map<String, Object> val = new HashMap<>();
        val.put("Position", "0,0");
        val.put("Kills", "0");
        val.put("LivesLeft", "100");
        val.put("Score", "0");
        val.put("Gold", "0");
        val.put("IsAlive", true);
        val.put("Action", "0");

        Map<String, Map<String, Object>> player = new HashMap<>();
        player.put("0", val);
        player.put("1", val);
        player.put("2", val);
        player.put("3", val);

        gameRef.setValue(player);

        Firebase gameOptionsRef = MainRef.child("Options");
        Map<String, Object> opt = new HashMap<>();
        opt.put("difficulty", "0");
        opt.put("Lives", "100");

        gameOptionsRef.setValue(opt);
    }

    /** lobby
     *          player1
     *              Name
     *              Ready
     *          player2
     *              Name
     *              Ready
     *          player3
     *              Name
     *              Ready
     *          player4
     *              Name
     *              Ready
     *      Options
     *          numberOfLives
     *          difficulty
     *          handicap?
     *
     *  Game
     *      player1  <--Actual name on firebase :0
     *          Position
     *              X
     *              Y
     *          Kills
     *          LivesLeft
     *          Gold
     *          Score
     *          IsAlive
     *          Action
     *             enum value range {
     *             IDLE
     *             OK
     *             FAILED
     *             BUILD_TOWER_1
     *             BUILD_TOWER_2
     *             BUILD_TOWER_3
     *             BUILD_TOWER_4} <-- Actually just numbers from 0-6
     *     player2  <--Actual name on firebase :1
     *          ...
     *
     *
     *
     *
     *
     *          Options          Testa
     *          lobby host.      Testa
     *          populate fields. Testa
     *          */

}

////////////////////////Junkyard/////////////////////////////7

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

    /*private String PlayerNumToStr(int value)
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
    }*/

    /*private String PlayerReadyToStr(int value)
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
    }*/

 /* mMainConnectionRef.child("lobby").child("0").child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
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
        });*/
