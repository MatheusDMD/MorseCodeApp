package agile.app.morsecodeapp;

/**
 * Created by Eric on 29/04/16.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import agile.app.morsecodeapp.morsetotext.Decoder;
import dalvik.annotation.TestTarget;

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
        String i = decoder.decodeMorse(morseList);
        assertEquals("i", i);
    }

    @Test
    public void checkLetterS() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String s = decoder.decodeMorse(morseList);
        assertEquals("s", s);
    }

    @Test
    public void checkLetterH() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String h = decoder.decodeMorse(morseList);
        assertEquals("h", h);
    } 

    @Test
    public void checkNumber5() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        string five = Decoder.decodeMorse(morseList);
        assertEquals("5", five);
    }

    @Test
    public void checkNumber4() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        string four = Decoder.decodeMorse(morseList);
        assertEquals("4", four);
    }

    @Test
    public void checkLetterV() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        String v = decoder.decodeMorse(morseList);
        assertEquals("v", v);
    }

    @Test
    public void checkNumber3() {
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String three = decoder.decodeMorse(morseList);
        assertEquals("3", three);
    }

    @Test
    public void checkLetterU() {
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        String u = decoder.decodeMorse(morseList);
        assertEquals("u", u);
    }

    @Test
    public void checkLetterF() {
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        String f = decoder.decodeMorse(morseList);
        assertEquals("f", f);
    }

    @Test
    public void checkNumber2() {
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String two = decoder.decodeMorse(morseList);
        assertEquals("2", two);
    }

    @Test
    public void checkLetterA() {
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        String a = decoder.decodeMorse(morseList);
        assertEquals("a", a);
    }

    @Test
    public void checkLetterR() {
        morseList.add(".");
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        String r = decoder.decodeMorse(morseList);
        assertEquals("r", r);
    }

    @Test
    public void checkLetterB() {
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String b = decoder.decodeMorse(morseList);
        assertEquals("b", b);
    }
    
    @Test
    public void checkLetterC() {
        morseList.add("-");
        morseList.add(".");
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        String c = decoder.decodeMorse(morseList);
        assertEquals("c", c);
    }

    @Test
    public void checkLetterD() {
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String d = decoder.decodeMorse(morseList);
        assertEquals("d", d);
    }

    @Test
    public void checkLetterG() {
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        String g = decoder.decodeMorse(morseList);
        assertEquals("g", g);
    }

    @Test
    public void checkLetterJ() {
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String j = decoder.decodeMorse(morseList);
        assertEquals("j", j);
    }

    @Test
    public void checkLetterK() {
        morseList.add("-");
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        String k = decoder.decodeMorse(morseList);
        assertEquals("k", k);
    }

    @Test
    public void checkLetterL() {
        morseList.add(".");
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String l = decoder.decodeMorse(morseList);
        assertEquals("l", l);
    }

    @Test
    public void checkLetterM() {
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String m = decoder.decodeMorse(morseList);
        assertEquals("m", m);
    }

    @Test
    public void checkLetterN() {
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        String n = decoder.decodeMorse(morseList);
        assertEquals("n", n);
    }

    @Test
    public void checkLetterO() {
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String o = decoder.decodeMorse(morseList);
        assertEquals("o", o);
    }

    @Test
    public void checkLetterP() {
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        String p = decoder.decodeMorse(morseList);
        assertEquals("p", p);
    }

    @Test
    public void checkLetterQ() {
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        String q = decoder.decodeMorse(morseList);
        assertEquals("q", q);
    }


    @Test
    public void checkLetterT() {
        morseList.add("-");
        morseList.add(" ");
        String t = decoder.decodeMorse(morseList);
        assertEquals("t", b);
    }

    @Test
    public void checkLetterW() {
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String w = decoder.decodeMorse(morseList);
        assertEquals("w", w);
    }


    @Test
    public void checkLetterX() {
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add("-");
        morseList.add(" ");
        String x = decoder.decodeMorse(morseList);
        assertEquals("x", x);
    }

    @Test
    public void checkLetterY() {
        morseList.add("-");
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        String y = decoder.decodeMorse(morseList);
        assertEquals("y", y);
    }

    @Test
    public void checkLetterZ() {
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        String z = decoder.decodeMorse(morseList);
        assertEquals("z", z);
    }

    @Test
    public void checkNumber1() {
        morseList.add(".");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        string one = Decoder.decodeMorse(morseList);
        assertEquals("1", one);
    }


    @Test
    public void checkNumber6() {
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        string six = Decoder.decodeMorse(morseList);
        assertEquals("6", six);
    }    

    @Test
    public void checkNumber7() {
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        string seven = Decoder.decodeMorse(morseList);
        assertEquals("7", seven);
    }

    @Test
    public void checkNumber8() {
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add(".");
        morseList.add(" ");
        string eight = Decoder.decodeMorse(morseList);
        assertEquals("8", eight);
    }

    @Test
    public void checkNumber9() {
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(".");
        morseList.add(" ");
        string nine = Decoder.decodeMorse(morseList);
        assertEquals("9", nine);
    }

    @Test
    public void checkNumber0() {
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add("-");
        morseList.add(" ");
        string zero = Decoder.decodeMorse(morseList);
        assertEquals("0", zero);
    }

    
    
}
