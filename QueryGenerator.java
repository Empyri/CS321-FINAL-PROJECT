import java.lang.Math.*;

public class QueryGenerator
{
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