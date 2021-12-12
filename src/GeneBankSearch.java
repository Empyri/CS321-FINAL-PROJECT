
import java.io.*;


public class GeneBankSearch
{

    public static void main(String[] args)
    {
        int cache=Integer.parseInt(args[0]);
        String bTreeFile=args[1];
        String queryFile=args[2];
        int cacheSize=0;
        int debugLevel=0;

        try {
            cacheSize = Integer.parseInt(args[3]);
        }catch(Exception e){System.out.println("No chosen cache size");}
        try {
            debugLevel = Integer.parseInt(args[4]);
        }catch(Exception e){System.out.println("No chosen debug level");}

        try{
            FileInputStream fis=new FileInputStream("t.tmp");
            ObjectInputStream ois=new ObjectInputStream(fis);
            BTree bTree=(BTree) ois.readObject();

            try (BufferedReader br = new BufferedReader(new FileReader(queryFile))) {
                String line;System.out.println("help");
                int i=0;
                int ht=bTree.getHeight();
                while ((line = br.readLine()) != null) {
                    //		geneSearch(bTree,i,ht);
                }
            }catch(Exception e){System.out.println("something went wrong BufferedReader");}

        }catch(Exception e){System.out.println("something went wrong FileInputStream");}




        //    System.out.println("string"+ois.readObject());



    }









}















