import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matus on 16.05.2016.
 */
public class Main {
    public static void main(String[] args) {

        char ntermnl[],termnl[];
        int ntlen,tlen;
        String grmr[][] = new String[5][5];
        String fst[];
        String flw[];
        String ntPravidlaPocet[] = null;

        File file = new File("input.txt");
        String path = file.getAbsolutePath();
        Loader loader = new Loader(path);
        List<String> input = new ArrayList<String>();
        try{
            input = loader.load();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Automat automat = new Automat(input);
        System.out.println("nieco");

        ntlen = automat.getNeterminaly().size();
        tlen = automat.getTerminaly().size();
        ntPravidlaPocet = new String[automat.getNeterminaly().size()];

        int pocetPravidiel[] = new int[automat.getPravidla().size()];
        for(int i=0;i<automat.getNeterminaly().size();i++) {
            for (int j = 0; j < automat.getPravidla().size(); j++) {
                if(automat.getNeterminaly().get(i).charAt(0) == automat.getPravidla().get(j).charAt(0)) {
                    pocetPravidiel[i] += 1;

                }
            }
        }

        for(int i=0; i<pocetPravidiel.length; i++){
            for(int k=0;k<pocetPravidiel[i];k++) {
                String[] parts = automat.getPravidla().get(i+k).split("-");
                grmr[i][k] = parts[1];
            }
        }

        fst=new String[ntlen];
        for(int i=0;i<ntlen;i++)
            fst[i]=first(i, grmr);
        System.out.println("First Set");



    }

    public static String first(int i, String grmr[][])
    {
        int j,k,l=0,found=0;
        String temp="",str="";
        for(j=0;j<grmr[i].length;j++) //number of productions
        {
            for(k=0;k<grmr[i][j].length();k++,found=0) //when nonterminal has epsilon production
            {
//                for(l=0;l<ntlen;l++) //finding nonterminal
//                {
//                    if(grmr[i][j].charAt(k)==ntermnl[l]) //for nonterminal in first set
//                    {
//                        str=first(l);
//                        if(!(str.length()==1 && str.charAt(0)=='9')) //when epsilon production is the only nonterminal production
//                            temp=temp+str;
//                        found=1;
//                        break;
//                    }
//                }
                if(found==1)
                {
                    if(str.contains("9")) //here epsilon will lead to next nonterminal’s first set
                        continue;
                }
                else //if first set includes terminal
                    temp=temp+grmr[i][j].charAt(k);
                break;
            }
        }
        return temp;
    }
}
