package agile.app.morsecodeapp.morsetotext;

import java.util.List;

/**
 * Created by marcelo on 15/04/16.
 */
public class Decoder {

    private static final int numNodes = 63;
    private static String[] morseValues = {null,"e","t","i","a","n","m","s","u","r","w","d","k","g","o","h","v",
            "f",null,"l",null,"p","j","b","x","c","y","z","q",null,null,"5","4",null,"3",null,null,null,"2",null,null,"+",
            null,null,null,null,"1","6","=","/",null,null,null,null,null,"7",null,null,null,"8",null,"9","0"};

    private Node[] nodes;

    public Decoder() {
        this.nodes = new Node[numNodes];
        fillNodes();
    }

    public void fillNodes(){
        for (int i = 0; i < morseValues.length; i++){
            nodes[i] = new Node(morseValues[i]);
        }
        for (int j = 0; j < 31; j++){
            nodes[j].setDotDash(nodes[j*2 + 1],nodes[j*2 + 2]);
        }
    }

    public String decodeMorse(List<String> morse){
        Node currentNode = nodes[0];
        String result = "";
        int count = 0;

        for (String character:morse){
            if (count < 5){
                if (character == "."){
                    currentNode = currentNode.getDot();
                    count ++;
                }

                else if (character == "-"){
                    currentNode = currentNode.getDash();
                    count ++;
                }

                else if(character == " "){
                    if (currentNode.getCharacter() != null){
                        result += currentNode.getCharacter();
                        currentNode = nodes[0];
                        count = 0;
                    }

                    else{
                        //Throw an exception.
                    }
                }
            }
            else {
                //Throw an exception.
            }

        }
        return result;
    }


    //Morse Tree


}
