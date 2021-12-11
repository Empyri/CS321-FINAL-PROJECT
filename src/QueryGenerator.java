import java.lang.Math.*;
import java.io.*;

public class QueryGenerator
{

	public static void main(String args[])
	{
		String[] queries=generateQuery(Integer.parseInt(args[0]));


		try{
			File outputFile=new File("queries/query"+Integer.parseInt(args[0]));			//creates testoutput file, unused as of 12/1
			if(outputFile.createNewFile())
			{
				System.out.println("File Created: "+outputFile.getName());
			}
			else
			{
				System.out.println("File already exists.");
			}
			FileWriter myWriter=new FileWriter("queries/query"+Integer.parseInt(args[0]));
			for(int i=0;i<queries.length;i++)
			{
				myWriter.write(queries[i]+"\n");
			}
			myWriter.close();
			System.out.println("finished writing to queries/query"+Integer.parseInt(args[0]));
		} catch (IOException e){
			System.out.println("An error occured creating an output file.");
			e.printStackTrace();
		}


	}

	public static String[] generateQuery(int k)
	{
		String []toRetStr=new String[(int)Math.pow(4,k)];
		for (int i=0;i<(int)Math.pow(4,k);i++)
		{
			toRetStr[i]="";
			for(int j=0;j<k;j++)
			{
				if(i/(int)(Math.pow(4,j))%4==0)
					toRetStr[i]="a"+toRetStr[i];
				else if(i/(int)(Math.pow(4,j))%4==1)
					toRetStr[i]="c"+toRetStr[i];
				else if(i/(int)(Math.pow(4,j))%4==2)
					toRetStr[i]="g"+toRetStr[i];
				else if(i/(int)(Math.pow(4,j))%4==3)
					toRetStr[i]="t"+toRetStr[i];
			}
		//	System.out.println(toRetStr[i]);
		}
		return toRetStr;

	}
}