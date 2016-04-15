package agile.app.morsecodeapp.morsetotext;

/**
 * Created by marcelo on 15/04/16.
 */
public class Node {

    private String character;
    private Node dot;
    private Node dash;

    public Node(String character){

        this.character = character;
        this.dot = null;
        this.dash = null;
    }

    public Node getDot() {
        return dot;
    }

    public void setDotDash(Node dot,Node dash) {
        this.dot = dot;
        this.dash = dash;
    }

    public Node getDash() {
        return dash;
    }

    public String getCharacter() {
        return character;
    }
}
