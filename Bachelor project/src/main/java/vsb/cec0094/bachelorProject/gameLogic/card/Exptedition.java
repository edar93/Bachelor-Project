package vsb.cec0094.bachelorProject.gameLogic.card;

public class Exptedition extends Card{

    private int cross;
    private int anchor;
    private int hut;

    public Exptedition(CardType cardType, int coin, int influence, int cross, int anchor, int hut) {
        super(cardType, coin, influence);
        this.cross = cross;
        this.anchor = anchor;
        this.hut = hut;
    }

    public Exptedition(CardType cardType, int cross, int anchor, int hut) {
        super(cardType);
        this.cross = cross;
        this.anchor = anchor;
        this.hut = hut;
    }

    public Exptedition(CardType cardType) {
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
