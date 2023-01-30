package Export;

public abstract class Node {
    protected String text;

    //getters


    public String getText() {
        return text;
    }

    //setters


    public void setText(String text) {
        this.text = text;
    }

    //builder

    public Node(){
        this.setText("");
    }

    public Node(String text){
        this.setText(text);
    }

}
