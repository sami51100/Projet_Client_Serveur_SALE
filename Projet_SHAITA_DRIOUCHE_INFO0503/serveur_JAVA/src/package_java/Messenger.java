package package_java;

import java.io.Serializable;

public class Messenger implements Serializable {

    private final String id;
    private final char delimAV;
    private final char delimAP;
    private final String prefix;

    public Messenger(String id) {
        this.id = id;
        this.delimAV = '[';
        this.delimAP = ']';
        this.prefix = this.delimAV + " " + this.id + " " + delimAP + " : ";
    }

    public Messenger(String id, char delim) {
        this.id = id;
        this.delimAV = this.delimAP = delim;
        this.prefix = this.delimAV + " " + this.id + " " + delimAP + " : ";
    }

    public Messenger(String id, char delimAV, char delimAP) {
        this.id = id;
        this.delimAV = delimAV;
        this.delimAP = delimAP;
        this.prefix = this.delimAV + " " + this.id + " " + delimAP + " : ";
    }

    public void afficheMessage(String message) {
        System.out.println(prefix + message);
    }

    @Override
    public String toString() {

        String configuration = "\nConfiguration actuelle :\n";
        configuration += "\tIdentite         = " + this.id + "\n";
        configuration += "\tDelimiteur avant = " + this.delimAV + "\n";
        configuration += "\tDelimiteur apres = " + this.delimAP + "\n";
        configuration += "\tPrefix resultant = " + this.prefix + "\n";

        return configuration;
    }
}
