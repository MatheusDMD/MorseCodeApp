package agile.app.morsecodeapp;

/**
 * Created by Eric on 29/04/16.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import agile.app.morsecodeapp.morsetotext.Decoder;

import static org.junit.Assert.assertEquals;

public class MorseTeste {
    Decoder decoder = new Decoder();
    List<String> morseList;
    @Before
    public void clearMorseList() {
        morseList.clear();
    }
    @Test
    public void checkLetterE() {
        morseList.add(".");
        morseList.add(" ");
        String e = decoder.decodeMorse(morseList);
        assertEquals("e", e);
    }
    @Test
    public void checkLetterI() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String e = decoder.decodeMorse(morseList);
        assertEquals("e", e);
    }
        Decoder decoderI = new Decoder();
        string i = Decoder.decodeMorse([".","."]);
        assertEquals("i", result);

        Decoder decoderS = new Decoder();
        string s = Decoder.decodeMorse([".",".","."]);
        assertEquals("s", result);

        Decoder decoderH = new Decoder();
        string h = Decoder.decodeMorse([".",".",".","."]);
        assertEquals("h", result);

        Decoder decoder5 = new Decoder();
        string 5 = Decoder.decodeMorse(".",".",".",".",".");
        assertEquals("5", result);

        Decoder decoder4 = new Decoder();
        string 4 = Decoder.decodeMorse(".",".",".",".","-");
        assertEquals("4", result);

        Decoder decoderV = new Decoder();
        string v = Decoder.decodeMorse(".",".",".","-");
        assertEquals("v", result);

        Decoder decoder3 = new Decoder();
        string 3 = Decoder.decodeMorse(".",".",".","-","-");
        assertEquals("3", result);

        Decoder decoderU = new Decoder();
        string u = Decoder.decodeMorse([".",".","-"]);
        assertEquals("u", result);

        Decoder decoderF = new Decoder();
        string f = Decoder.decodeMorse([".",".","-","."]);
        assertEquals("f", result);

        Decoder decoder2 = new Decoder();
        String dois = Decoder.decodeMorse([".",".","-","-","-"]);
        assertEquals("2", result);

        Decoder decoderA = new Decoder();
        string a = Decoder.decodeMorse([".","-"]);
        assertEquals("a", result);

        Decoder decoderR = new Decoder();
        string r = Decoder.decodeMorse([".","-","."]);
        assertEquals("r", result);

        Decoder decoderB = new Decoder();
        string b = Decoder.decodeMorse(["-",".",]);
        assertEquals("b", result);

        Decoder decoderC = new Decoder();
        string c = Decoder.decoderMorse(["-",".","-","."]);
        assertEquals("c", result);

        Decoder decoderD = new Decoder();
        string d = Decoder.decodeMorse(["-",".","."]);
        assertEquals("d", result);

        Decoder decoderE = new Decoder();
        string e = Decoder.decodeMorse(["."]);
        assertEquals("e", result);

        Decoder decoderG = new Decoder();
        string g = Decoder.decodeMorse(["-","-","."]);
        assertEquals("g", result);

        Decoder decoderJ = new Decoder();
        string j = Decoder.decodeMorse([".","-","-","-"]);
        assertEquals("j", result);

        Decoder decoderK = new Decoder();
        string k = Decoder.decodeMorse(["-",".","-"]);
        assertEquals("k", result);

        Decoder decoderL = new Decoder();
        string l = Decoder.decodeMorse([".","-",".","."]);
        assertEquals("l", result);

        Decoder decoderM = new Decoder();
        string m = Decoder.decodeMorse(["-","-"]);
        assertEquals("m", result);

        Decoder decoderN = new Decoder();
        string n = Decoder.decodeMorse(["-","."]);
        assertEquals("n", result);

        Decoder decoderO = new Decoder();
        string o = Decoder.decodeMorse(["-","-","-"]);
        assertEquals("o", result);

        Decoder decoderP = new Decoder();
        string p = Decoder.decodeMorse([".","-","-","."]);
        assertEquals("p", result);

        Decoder decoderQ = new Decoder();
        string q = Decoder.decodeMorse(["-","-",".","-"]);
        assertEquals("q", result);

        Decoder decoderT = new Decoder();
        string t = Decoder.decodeMorse(["-"]);
        assertEquals("t", result);

        Decoder decoderW = new Decoder();
        string w = Decoder.decodeMorse([".","-","-"]);
        assertEquals("w", result);

        Decoder decoderX = new Decoder();
        string x = Decoder.decodeMorse(["-",".",".","-"]);
        assertEquals("x", result);

        Decoder decoderY = new Decoder();
        string y = Decoder.decodeMorse(["-",".","-","-"]);
        assertEquals("y", result);

        Decoder decoderZ = new Decoder();
        string z = Decoder.decodeMorse(["-","-",".","."]);
        assertEquals("z", result);

        Decoder decoder1 = new Decoder();
        string 1 = Decoder.decodeMorse([".","-","-","-","-"]);
        assertEquals("1", result);

        Decoder decoder6 = new Decoder();
        string 6 = Decoder.decodeMorse(["-",".",".",".","."]);
        assertEquals("6", result);

        Decoder decoder7 = new Decoder();
        stirng 7 = Decoder.decodeMorse(["-","-",".",".","."]);
        assertEquals("7", result);

        Decoder decoder8 = new Decoder();
        string 8 = Decoder.decodeMorse(["-","-","-",".","."]);
        assertEquals("8", result);

        Decoder decoder9 = new Decoder();
        string 9 = Decoder.decodeMorse(["-","-","-","-","."]);
        assertEquals("9", result);

        Decoder decoder0 = new Decoder();
        string 10 = Decoder.decodeMorse(["-","-","-","-","-"]);
        assertEquals("10", result);
    }
}
