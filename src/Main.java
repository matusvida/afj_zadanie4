import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matus on 16.05.2016.
 */
public class Main {
    public static void main(String[] args) {

        char ntermnl[],termnl[];
        int ntlen,tlen;
        String [][]grmr;
        int count = 0;
        String fst[];
        String flw[];
        String ntPravidlaPocet[] = null;

        File file = new File("input1.txt");
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

        grmr = new String[automat.getNeterminaly().size()][5];
        for(int i=0; i<pocetPravidiel.length; i++){
            for(int k=0;k<pocetPravidiel[i];k++) {
                String[] parts = automat.getPravidla().get(count).split(">");
                grmr[i][k] = parts[1];
                count++;
            }
        }

        for(int t=0;t<automat.getNeterminaly().size();t++){
            List<String> list = new ArrayList<String>(Arrays.asList(grmr[t]));
            list.removeAll(Collections.singleton(null));
            grmr[t] = list.toArray(new String[list.size()]);
        }

        fst=new String[ntlen];
        for(int i=0;i<ntlen;i++) {
            fst[i] = first(i, grmr, automat);
        }
        System.out.println("First Set");
        for(int i=0;i<ntlen;i++) {
            System.out.println(removeDuplicates(fst[i]));
        }

    }

    public static String first(int i, String grmr[][], Automat automat)
    {
        int j,k,l=0,found=0;
        String temp="",str="";
        int ntlen = automat.getNeterminaly().size();
        for(j=0;j<grmr[i].length;j++) //number of productions
        {
           for (k = 0; k < grmr[i][j].length(); k++, found = 0) //when nonterminal has epsilon production
           {
               for (l = 0; l < ntlen; l++) //finding nonterminal
               {
                   if (grmr[i][j].charAt(k) == automat.getNeterminaly().get(l).charAt(0)) //for nonterminal in first set
                   {
                       str = first(l, grmr, automat);
                       if (!(str.length() == 1 && str.charAt(0) == '0')) //when epsilon production is the only nonterminal production
                           temp = temp + str;
                       found = 1;
                       break;
                   }
               }
               if (found == 1) {
                   if (str.contains("0")) //here epsilon will lead to next nonterminal’s first set
                       continue;
               } else //if first set includes terminal
                   temp = temp + grmr[i][j].charAt(k);
               break;
           }
        }

        return temp;
    }

    static String follow(int i, String[][] grmr, Automat automat, String fst[])
    {
        int ntlen = automat.getNeterminaly().size();
        char pro[],chr[];
        String [] flw = new String[20];
        String temp="";
        int j,k,l,m,n,found=0;
        if(i==0)
            temp="nema zmysel";
        for(j=0;j<ntlen;j++)
        {
            for(k=0;k<grmr[j].length;k++) //entering grammar matrix
            {
                pro=new char[grmr[j][k].length()];
                pro=grmr[j][k].toCharArray();
                for(l=0;l<pro.length;l++) //entering each production
                {
                    if(pro[l]==automat.getNeterminaly().get(i).charAt(0)) //finding the nonterminal whose follow set is to be found
                    {
                        if(l==pro.length-1) //if it is the last terminal/non-terminal then follow of current non-terminal
                        {
                            if(j<i)
                                temp=temp+flw[j];
                        }
                        else
                        {
                            for(m=0;m<ntlen;m++)
                            {
                                if(pro[l+1]==automat.getNeterminaly().get(m).charAt(0)) //first of next non-terminal otherwise (else later�)
                                {
                                    chr=new char[fst[m].length()];
                                    chr=fst[m].toCharArray();
                                    for(n=0;n<chr.length;n++)
                                    {
                                        if(chr[n]=='9') //if first includes epsilon
                                        {
                                            if(l+1==pro.length-1)
                                                temp=temp+follow(j, grmr, automat, fst); //when non-terminal is second last
                                            else
                                                temp=temp+follow(m, grmr, automat, fst);
                                        }
                                        else
                                            temp=temp+chr[n]; //include whole first set except epsilon
                                    }
                                    found=1;
                                }
                            }
                            if(found!=1)
                                temp=temp+pro[l+1]; //follow set will include terminal(else is here)
                        }
                    }
                }
            }
        }
        return temp;
    }


    public static String removeDuplicates(String str){
        int i;
        char ch;
        boolean seen[] = new boolean[256];
        StringBuilder sb = new StringBuilder(seen.length);
        for(i=0;i<str.length();i++)
        {
            ch=str.charAt(i);
            if (!seen[ch])
            {
                seen[ch] = true;
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
