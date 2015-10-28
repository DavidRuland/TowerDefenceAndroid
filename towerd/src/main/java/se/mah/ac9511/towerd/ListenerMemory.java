package se.mah.ac9511.towerd;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

/**
 * Created by Peter on 2015-10-28.
 */
public class ListenerMemory {
    public Firebase getLocation() {
        return location;
    }

    public void setLocation(Firebase location) {
        this.location = location;
    }

    public ValueEventListener getListener() {
        return listener;
    }

    public void setListener(ValueEventListener listener) {
        this.listener = listener;
    }

    private Firebase location;

    public ListenerMemory(Firebase location, ValueEventListener listener) {
        this.location = location;
        this.listener = listener;
    }

    private ValueEventListener listener;
}
