
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
        File file=null;

        try {
            cacheSize = Integer.parseInt(args[3]);
        }catch(Exception e){System.out.println("No chosen cache size");}
        try {
            debugLevel = Integer.parseInt(args[4]);
        }catch(Exception e){System.out.println("No chosen debug level");}

        try{
            FileInputStream fis=new FileInputStream(bTreeFile);
            ObjectInputStream ois=new ObjectInputStream(fis);
            BTree bTree=(BTree) ois.readObject();

            try (BufferedReader br = new BufferedReader(new FileReader(queryFile))) {
             //   System.out.println(bTree);
                String line;
                if(debugLevel==1)
                {
                    int count=0;
                    System.out.println(bTreeFile.substring(0,12)+"_"+queryFile.substring(8,14)+"_result");
                    file=new File(bTreeFile.substring(0,12)+"_"+queryFile.substring(8,14)+"_result");
                    if(file.createNewFile()){
                        System.out.println("File Created: "+file.getName());
                    }else{
                        System.out.println("File: "+file.getName()+" already exists");
                    }
                            FileWriter myWriter=new FileWriter(file);
                            myWriter.write("");
                    while((line=br.readLine()) !=null)
                    {
                        Long i=getLong(line);
                            System.out.println("searching for long "+i+" line "+line);
                        String str=bTree.get(i);
                        count++;

                        if(str!=null)
                        {
                            myWriter.append(str+"\n");
                             System.out.println(str);
                        }
                    }
                    myWriter.flush();
                    myWriter.close();
                }
                else{
                    while((line=br.readLine()) !=null)
                    {
                        Long i=getLong(line);
                    //    System.out.println("long "+i+" line "+line);
                        String str=bTree.get(i);

                        if(str!=null)
                        {
                 	   //     System.out.println(str);
                        }
                    }
                }
            }catch(Exception e){System.out.println("something went wrong BufferedReader");}

        }catch(Exception e){System.out.println("something went wrong FileInputStream");}




        //    System.out.println("string"+ois.readObject());



    }

    private static Long getLong(String line)
    {
        try{
            String strInt="";
            long toRetLong=0L;
                for(int i=0;i<line.length();i++)
                {                           //there has to be a more efficent method but im tired and dont want to fix this.
                    if(line.charAt(i)=='A' || line.charAt(i)=='a')
                    {
                        strInt=strInt+"00";
                    }
                    else if(line.charAt(i)=='T' || line.charAt(i)=='t')
                    {
                        strInt=strInt+"11";
                    }
                    else if(line.charAt(i)=='C' || line.charAt(i)=='c')
                    {
                        strInt=strInt+"01";
                    }
                    else if(line.charAt(i)=='G' || line.charAt(i)=='g')
                    {
                        strInt=strInt+"10";
                    }

                }
                toRetLong=Long.parseLong(strInt,2);
            return toRetLong;

        }catch(Exception e)
        {
            System.out.println("something went wrong converting the strings to longs");
        }
        return null;
    }

}















