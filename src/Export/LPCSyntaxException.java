package Export;

public class LPCSyntaxException extends Exception{

    public LPCSyntaxException(String erreur_sur_les_balises) {
        super(erreur_sur_les_balises);
    }
}
