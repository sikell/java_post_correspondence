package de.sikeller.theoretical.postcorrespondence.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CombinatorTest {

    @Test
    public void add() throws Exception {
        Combinator sut = new Combinator();
        sut.add(1, new Block("aba", "ab"));
        sut.add(2, new Block("ba", "aba"));
        assertEquals("Combinator(ababa,ababa)", sut.toString());
    }

    @Test
    public void valid() throws Exception {
        Combinator sut = new Combinator();
        assertTrue(sut.valid(new Block("aba", "ab")));
        sut.add(1, new Block("aba", "ab"));
        assertFalse(sut.valid(new Block("aba", "b")));
        assertFalse(sut.valid(new Block("a", "ba")));
        assertTrue(sut.valid(new Block("ba", "aba")));
        sut.add(2, new Block("ba", "aba"));
        assertTrue(sut.valid(new Block("ab", "aba")));
    }

    @Test
    public void finished() throws Exception {
        Combinator sut = new Combinator();
        assertFalse(sut.finished());
        sut.add(1, new Block("aba", "ab"));
        assertFalse(sut.finished());
        sut.add(2, new Block("ba", "aba"));
        assertTrue(sut.finished());
        sut.add(1, new Block("aba", "ab"));
        assertFalse(sut.finished());
    }

    @Test
    public void getResult() throws Exception {
        Combinator sut = new Combinator();
        sut.add(1, new Block("aba", "ab"));
        sut.add(2, new Block("ba", "aba"));
        List<Integer> result = sut.getResult();
        assertEquals(2, result.size());
        assertEquals(new Integer(1), result.get(0));
        assertEquals(new Integer(2), result.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResultNotFinished() throws Exception {
        Combinator sut = new Combinator();
        sut.add(1, new Block("aba", "ab"));
        sut.getResult();
    }

}