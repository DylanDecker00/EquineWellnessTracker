package equinetracker.dto;  // or whatever package you decided on

import equinetracker.entity.Horse;
import equinetracker.entity.Owner;

public class OwnerandHorse {

    private Owner owner;
    private Horse horse;

    // Getters and setters for both fields

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }
}
