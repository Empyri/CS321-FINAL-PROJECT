import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GeneBankCreateBTree
{


//open file and read only atgc characters after origin before // line
	public static void main(String args[])
	{
		String str;		//final string with only actg in it
		str="";
		try (Scanner sc=new Scanner(new File(args[0]))) 	//read first argument as file name
		{
			int k=Integer.parseInt(args[1]);			//reads second argument as length, saved as int k

			try{
				File outputFile=new File("testOutput");			//creates testoutput file, unused as of 12/1
				if(outputFile.createNewFile())
				{
					System.out.println("File Created: "+outputFile.getName());
				}
				else
				{
					System.out.println("File already exists.");
				}
			} catch (IOException e){
				System.out.println("An error occured creating an output file.");
				e.printStackTrace();
			}

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
							System.out.println("found origin");
						}
					}
					else if(dna)
					{				//ends dna reading segments
						if(line.equals("//"))
						{
							dna=false;
							System.out.println("found //");		//when it finds // it ends dna mode
							continue;
						}
						else
						{

							for(int i=0;i<line.length();i++)
							{
								//removes numbers and 'n's from it hopefully. kinda annoying but idk it works
								if(line.charAt(i)!='g' && line.charAt(i)!='a' && line.charAt(i)!='c' && line.charAt(i)!='t')
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
			System.out.println("Input in style java GeneBankCreateBTree [inputfile] [length]");
			System.out.println("Failed to read file "+args[0]);
		}
		System.out.println(str);
	}
}
















