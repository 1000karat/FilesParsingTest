package model;

import java.util.ArrayList;

public class JsonRoot {
    String squadName;
    String homeTown;
    int formed;
    String secretBase;
    boolean active;
    ArrayList<JsonMembers> members;

    public String getSquadName() {
        return squadName;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public int getFormed() {
        return formed;
    }

    public String getSecretBase() {
        return secretBase;
    }

    public boolean isActive() {
        return active;
    }

    public ArrayList<JsonMembers> getMembers() {
        return members;
    }
}
