import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GeneBankCreateBTree
{


//open file and read only atgc characters after origin before // line
	public static void main(String args[])
	{

		int k=Integer.parseInt(args[1]);
		String str=getFullString(args);
		String strArr[]=getCutStrings(str, k);

	}

	public static String [] getCutStrings(String str, int k)
	{
				//reads second argument as length, saved as int k
		try{
			String [] cutStrings=new String[str.length()-k];
			File outputFile=new File("testOutput");			//creates testoutput file, unused as of 12/1
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

	public static String getFullString(String args[])
	{
			try
		{

			String str;		//final string with only actg in it
			str="";

			try (Scanner sc=new Scanner(new File(args[0]))) 	//read first argument as file name
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
				System.out.println("Failed to read file "+args[0]);
			}
			return str;
		}catch(Exception e){
			System.out.println("Input in style java GeneBankCreateBTree [inputfile] [length]");
		}
		return "";
	}
}
 













