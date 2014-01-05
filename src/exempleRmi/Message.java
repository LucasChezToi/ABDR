package exempleRmi;


import java.util.HashMap;
import java.util.LinkedList;

import java.io.Serializable;


/*
 * Message
 */
public class Message implements Serializable {
    private Msg type;

    private long creationClockTime;
    private long creationTimestamp;

    private HashMap<String, String> properties;
    

    private LinkedList<FT> fromTo;

    Message(Msg type) {
	this.type = type;
	properties = new HashMap<String, String>();
	creationClockTime = System.currentTimeMillis();
	creationTimestamp= System.nanoTime();
	fromTo = new LinkedList<FT>();
    }


    Message(String from, String to, Msg type) {
	this.type = type;
	properties = new HashMap<String, String>();
	creationClockTime = System.currentTimeMillis();
	creationTimestamp= System.nanoTime();
	fromTo = new LinkedList<FT>();
	appendFromTo(from, to);
    }

    private void appendFromTo(String from, String to) {
	fromTo.add(new FT(from, to));
    }


    
    void put(String name, String value) {
	properties.put(name,value);
    }

    String get(String name) {
	return properties.get(name);
    }

    Msg getType(){return type;}



    String getFrom() {
	return fromTo.getLast().from;
    }

    String getInitialSender() {
	return fromTo.getFirst().from;
    }

}

// pair
class FT implements Serializable{
    String from;
    String to;
    FT(String from, String to) {
	this.from = from;
	this.to = to;
    }
}
