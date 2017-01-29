package vsb.cec0094.bachelorProject.gameLogic.card;

import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;

public class Expedition extends Card {

    private int cross;
    private int anchor;
    private int hut;

    public void canBeTaken(int crosses, int anchors, int huts, int jacks) throws TooExpensiveExpeditionException {
        int require = 0;
        require += compareAttribute(this.cross, crosses);
        require += compareAttribute(this.hut, huts);
        require += compareAttribute(this.anchor, anchors);
        if(require > jacks){
            throw new TooExpensiveExpeditionException("too expensive expetion");
        }
    }

    private int compareAttribute(int required, int players) {
        return required - players > -1 ? required - players : 0;
    }

    public Expedition(CardType cardType, int coin, int influence, int cross, int anchor, int hut) {
        super(cardType, coin, influence);
        this.cross = cross;
        this.anchor = anchor;
        this.hut = hut;
    }

    public Expedition(CardType cardType, int cross, int anchor, int hut) {
        super(cardType);
        this.cross = cross;
        this.anchor = anchor;
        this.hut = hut;
    }

    public Expedition(CardType cardType) {
        super(cardType);
    }

    public int getCross() {
        return cross;
    }

    public void setCross(int cross) {
        this.cross = cross;
    }

    public int getAnchor() {
        return anchor;
    }

    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }

    public int getHut() {
        return hut;
    }

    public void setHut(int hut) {
        this.hut = hut;
    }
}
