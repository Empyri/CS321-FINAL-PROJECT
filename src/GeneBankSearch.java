
import java.io.*;


public class GeneBankSearch
{
    /**
     *A search Class that takes a file created by GeneBankCreateBTree and outputs the item searched for.
     * There are 2 arguments input: an integer that will add a size of cache for the search, and then a value of 0 or 1
     * that will output a dumpfile from the search to be read.
     * @param args CacheEnable - [0,1] -cache or not, File of BTree, queryFile, size of cache, and debug level- [0.1]
     * @Author Micahel Aberda
     * @Author Jordan Whyte
     * @Semester Fall 2021
     */
    public static void main(String[] args)
    {
        final long startTime = System.currentTimeMillis();


        int cacheEnable=Integer.parseInt(args[0]);
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
            //    System.out.println(bTree);
                String line;
                if(debugLevel==1)
                {
                    System.out.println(bTreeFile.substring(0,12)+"_"+queryFile.substring(8,14)+"_result");
                    file=new File(bTreeFile.substring(0,12)+"_"+queryFile.substring(8,14)+"_result");
                    if(file.createNewFile()){
                        System.out.println("File Created: "+file.getName());
                    }else{
                        System.out.println("File: "+file.getName()+" already exists");
                    }
                    FileWriter myWriter=new FileWriter(file);
                    myWriter.write("");

                    Cache cache = new Cache(cacheSize);

                    while((line=br.readLine()) !=null)
                    {
                        Long i=getLong(line);
                        String str = "";
                        //           System.out.println("searching for long "+i+" line "+line);
                        //search cache for i
                        //if i not in cache, add to cache and run get(i)
                        //otherwise return i.data like normal

                        if(cacheEnable == 1)
                        {
                            if(cache.find(i) == null)
                            {
                                str=bTree.get(i);
                            }
                            else
                            {
                                str = (String)cache.find(i).getElement();
                            }

                        }
                        else
                        {
                            str=bTree.get(i);
                        }

                        if(str!=null)
                        {
                            myWriter.append(str+"\n");
                            //          System.out.println(str);
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

                        String str = "";
                        //           System.out.println("searching for long "+i+" line "+line);
                        //search cache for i
                        //if i not in cache, add to cache and run get(i)
                        //otherwise return i.data like normal
                        Cache cache = new Cache(cacheSize);

                        if(cacheEnable == 1)
                        {
                            if(cache.find(i) == null)
                            {
                                str=bTree.get(i);
                            }
                            else
                            {
                                str = (String)cache.find(i).getElement();
                            }


                        }
                        else
                        {
                            str=bTree.get(i);
                        }

                        if(str!=null)
                        {
                            //     System.out.println(str);
                        }
                    }
                }
            }catch(Exception e){System.out.println("something went wrong BufferedReader");}

        }catch(Exception e){System.out.println("something went wrong FileInputStream");}


        //print the time to complete
        final long duration = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed: "+(duration));
    }

    /**
     * Converts a String into a long value for searching, as the long of the value is the key in the pairing.
     * @param line the String to be searched
     * @return the long value that is the key for that string.
     */
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