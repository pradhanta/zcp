package com.zcp.socket;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the data that is being transmitted to GCP via TCP. It contains the required properties
 * as attributes and rest as name value pair.
 */
public class Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> properties;

    public Packet() {
        properties = new HashMap<>();
    }

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }

    public String getProperty(String name) {
        return properties.get(name);
    }

    public Set<String> getProperties() {
        return properties.keySet();
    }

    public String toJSON() {
        return new GsonBuilder().create().toJson((Object)properties);
    }


    public void setHeader(String header) {
        properties.put("HEADER", header);
    }

    public void setMessage(String message)  {
        properties.put("MESSAGE", message);
    }
}
