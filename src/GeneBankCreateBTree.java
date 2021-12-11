import java.io.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class GeneBankCreateBTree
{


	//open file and read only atgc characters after origin before // line
	public static void main(String args[])
	{
		int debugLevel=Integer.parseInt(args[0]);//debug level
		int degree=Integer.parseInt(args[1]);//degree of tree
		if(degree<4 || degree%2==1)
		{
			System.out.println("Degree must be greater than 4 and even");
			return;
		}
		String gbk=(args[2]);//input file
		int k=Integer.parseInt(args[3]);//sequence length

		try {
			int cacheSize = Integer.parseInt(args[4]);
		}catch(Exception e){System.out.println("No chosen cache size");}

		try {
			int debugLevel2 = Integer.parseInt(args[5]);
		}catch(Exception e){System.out.println("no second debug level");}


		if(k>31)
		{
			System.out.println("size must be smaller than 31!");
			return;
		}


		String str=getFullString(gbk);				//gets full string of actgACTG
		String strArr[]=getCutStrings(str, k);		//cuts the previous string into k sized chunks
		long intArr[]=getLongInts(strArr, k);		//converts the previous string into binary, then long int based off that

		BTree bTree=new BTree(degree);
		for (int i=0; i<intArr.length; i++){
			bTree.put(intArr[i],strArr[i] );
		}


		System.out.println();
		System.out.println(bTree);
		System.out.println();
		System.out.println("size:    " + bTree.size());
		System.out.println("height:  " + bTree.height());

		File file=new File(gbk+".btree.data."+k+"."+degree);
		byte[]data=bTree.toString().getBytes(StandardCharsets.UTF_8);

		try(FileOutputStream fos=new FileOutputStream(file)) {
			fos.write(data);
			System.out.println("successfully completed");
		}catch(IOException e){
			e.printStackTrace();
		}


	}




	//no idea if this is needed
	public static long [] getLongInts(String []strArr,int k)
	{
		try{
			String []strInt=new String[strArr.length];
			long toRetLong[]=new long[strArr.length];
			for(int j=0;j<strArr.length;j++)
			{
				strInt[j]="";
				for(int i=0;i<strArr[0].length();i++)
				{							//there has to be a more efficent method but im tired and dont want to fix this.
					if(strArr[j].charAt(i)=='A' || strArr[j].charAt(i)=='a')
					{
						strInt[j]=strInt[j]+"00";
					}
					else if(strArr[j].charAt(i)=='T' || strArr[j].charAt(i)=='t')
					{
						strInt[j]=strInt[j]+"11";
					}
					else if(strArr[j].charAt(i)=='C' || strArr[j].charAt(i)=='c')
					{
						strInt[j]=strInt[j]+"01";
					}
					else
					{
						strInt[j]=strInt[j]+"10";
					}
				}
				toRetLong[j]=Long.parseLong(strInt[j],2);
				//	System.out.println("j is "+j);				//testing
				//	System.out.println(strInt[j]);
				//	System.out.println(toRetLong[j]);
			}
			return toRetLong;

		}catch(Exception e)
		{
			System.out.println("something went wrong converting the strings to longs");
		}
		return null;
	}

	public static String [] getCutStrings(String str, int k)
	{
		//reads second argument as length, saved as int k
		try{
			String [] cutStrings=new String[str.length()-k];
			File outputFile=new File("testOutput.txt");			//creates testoutput file, unused as of 12/1
			if(outputFile.createNewFile())
			{
				System.out.println("File Created: "+outputFile.getName());
			}
			else
			{
				System.out.println("File already exists.");
			}
			FileWriter myWriter=new FileWriter("testOutput.txt");
			for(int i=0;i<str.length()-k;i++)
			{
				cutStrings[i]=str.substring(i,i+k);
				myWriter.write(str.substring(i,i+k)+"\n");;
			}
			myWriter.close();
			System.out.println("finished writing to testOutput.txt");
			return cutStrings;
		} catch (IOException e){
			System.out.println("An error occured creating an output file.");
			e.printStackTrace();
		}
		String[]failure=new String[1];
		failure[0]="";
		return failure;
	}

	public static String getFullString(String gbk)
	{
		try
		{

			String str;		//final string with only actg in it
			str="";

			try (Scanner sc=new Scanner(new File(gbk))) 	//read first argument as file name
			{

				String line;		//temp string name for reading input file
				Boolean dna=false;	//if true, reading dna into str String
				while (sc.hasNextLine())
				{
					Scanner sc2=new Scanner(sc.nextLine());
					while(sc2.hasNext())
					{

						line=sc2.next();
						if(!dna)
						{
							if(line.equals("ORIGIN"))
							{
								dna=true;
//								System.out.println("found origin");
							}
						}
						else if(dna)
						{				//ends dna reading segments
							if(line.equals("//"))
							{
								dna=false;
//								System.out.println("found //");		//when it finds // it ends dna mode
								continue;
							}
							else
							{

								for(int i=0;i<line.length();i++)
								{
									//removes numbers and 'n's from it hopefully. kinda annoying but idk it works
									if(line.charAt(i)!='g' && line.charAt(i)!='a' && line.charAt(i)!='c' && line.charAt(i)!='t' && line.charAt(i)!='G' && line.charAt(i)!='A' && line.charAt(i)!='C' && line.charAt(i)!='T')
										continue;

									else 						//if a or g or c or t, adds to str
										str=str+line.charAt(i);

								}
							}
						}
					}
				}
			}catch (Exception e)
			{
				System.out.println("Failed to read file "+gbk);
			}
			return str;
		}catch(Exception e){
			System.out.println("Input in style java GeneBankCreateBTree [inputfile] [length]");
		}
		return "";
	}
}