import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Matus on 22.03.2016.
 */
public class Automat {

    private List<String> neterminaly = new ArrayList<String>();
    private List<String> terminaly = new ArrayList<String>();
    private List<String> pravidla = new ArrayList<String>();
    private String initialState;
    private List<String> abeceda = new ArrayList<String>();


    public Automat(List<String> automat){
        initAutomat(automat);
    }

    public void initAutomat(List<String> automat){
        int pocetNeterminalov = Integer.parseInt(automat.get(0));
        int pocetTerminalov = Integer.parseInt(automat.get(1));
        int pocetPravidiel = Integer.parseInt(automat.get(2));
        int transitionIndex = 0;
        int finalStateIndex = 0;

        for(int i=0; i < pocetNeterminalov; i++){
            neterminaly.add(i, automat.get(3 + i));
        }

        for(int j=0; j < pocetTerminalov; j++){
            terminaly.add(j, automat.get(3 + pocetNeterminalov + j));
        }

        for(int t = (3 + pocetNeterminalov + pocetTerminalov); t < automat.size(); t++){

            pravidla.add(transitionIndex++, automat.get(t));
        }
    }

    public List<String> getNeterminaly() {
        return neterminaly;
    }

    public List<String> getTerminaly() {
        return terminaly;
    }

    public List<String> getPravidla() {
        return pravidla;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getAbeceda() {
        return abeceda;
    }
}
