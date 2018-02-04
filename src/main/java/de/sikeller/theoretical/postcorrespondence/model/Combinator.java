package de.sikeller.theoretical.postcorrespondence.model;

import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(of = "result")
public class Combinator {
    private List<Integer> result = new LinkedList<>();
    private StringBuilder top = new StringBuilder();
    private StringBuilder bottom = new StringBuilder();

    public Combinator copy() {
        Combinator combinator = new Combinator();
        combinator.result = new LinkedList<>(result);
        combinator.top = new StringBuilder(top);
        combinator.bottom = new StringBuilder(bottom);
        return combinator;
    }

    public void add(Integer index, Block block) {
        result.add(index);
        top.append(block.getTop());
        bottom.append(block.getBottom());
    }

    public boolean valid(Block block) {
        String newTop = top.toString() + block.getTop();
        String newBottom = bottom.toString() + block.getBottom();
        int newBottomLength = newBottom.length();
        int newTopLength = newTop.length();
        if (newTopLength > newBottomLength) {
            return newTop.substring(0, newBottomLength).equals(newBottom);
        } else {
            return newBottom.substring(0, newTopLength).equals(newTop);
        }
    }

    public boolean finished() {
        return top.length() == bottom.length() && result.size() > 0
                && top.toString().equals(bottom.toString());
    }

    public List<Integer> getResult() {
        return new LinkedList<>(result);
    }

    @Override
    public String toString() {
        return "Combinator(" + top.toString() + "," + bottom.toString() + ")";
    }

}
