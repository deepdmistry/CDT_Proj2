/*-------------------------------------------------------------------------------
* sim.java
* Main class which has all the predictors and also prints the results
* Author - Deep Mistry
* Student ID - 000962873
* ddmistry@ncsu.edu
-------------------------------------------------------------------------------*/

//package project2;

import java.io.*;
import java.math.*;
import java.util.Arrays;

public class sim {

table [] entry;
table [] entryb;
table [] entryg;
table [] choose;
int[] hReg;
int pCount=0;
int mispCount=0;

    sim(int m,int n,int k,int m2, String type)
    {

        if(type.equals("hybrid"))
        {


            // bimodal

        int size =    (int) Math.pow(2,m2);


        hReg = new int[n];


    entryb = new table[size];
        // initialize the table

  for(int i=0;i< size;i++)
  {
      entryb[i]= new table();
      entryb[i].count =2;
  }


    // gshare


        int sizeg =    (int) Math.pow(2,m);


        hReg = new int[n];


    entryg = new table[sizeg];
        // initialize the table

  for(int i=0;i< sizeg;i++)
  {
      entryg[i]= new table();
      entryg[i].count =2;
  }

    for(int i=0;i< n;i++)
  {
    hReg[i]=0;
  }

    // hybrid
    int chSize = (int) Math.pow(2,k);
    choose = new table[chSize];

for(int i=0;i< chSize ;i++)
  {
      choose[i]= new table();
      choose[i].count =1;
  }

        
        }
        else if (type.equals("bimodal"))
        {
        // bimodal

        int size =    (int) Math.pow(2,m);


        hReg = new int[n];


    entryb = new table[size];
        // initialize the table

  for(int i=0;i< size;i++)
  {
      entryb[i]= new table();
      entryb[i].count =2;
  }


    // gshare

    for(int i=0;i< n;i++)
  {
    hReg[i]=0;
  }

    // hybrid
    int chSize = (int) Math.pow(2,k);
    choose = new table[chSize];
    
for(int i=0;i< chSize ;i++)
  {
      choose[i]= new table();
      choose[i].count =1;
  }
    
        }

        else if (type.equals("gshare"))
        {
        // bimodal

        int size =    (int) Math.pow(2,m);


        hReg = new int[n];


    entryg = new table[size];
        // initialize the table

  for(int i=0;i< size;i++)
  {
      entryg[i]= new table();
      entryg[i].count =2;
  }


    // gshare

    for(int i=0;i< n;i++)
  {
    hReg[i]=0;
  }

    // hybrid
    int chSize = (int) Math.pow(2,k);
    choose = new table[chSize];

for(int i=0;i< chSize ;i++)
  {
      choose[i]= new table();
      choose[i].count =1;
  }

        }


    }

  void FileRead(int m, int n, String file,String type, int k, int m2){


    // FileRead
      try{
    FileInputStream fstream = new FileInputStream(file);

    DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String strLine;

    while ((strLine = br.readLine()) != null)   {
    //  System.out.println(pCount);
        pCount++;
      
      String address,tn;
      int sp=0;
      sp=strLine.indexOf(" ");
      address = strLine.substring(0,sp);
      tn = strLine.substring(sp+1,strLine.length());

            // Hex to binary

    String[]hex={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
  String[]binary={"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"};


  String hexValue= address;
  String binValue="";
  for(int i=0;i<hexValue.length();i++)
  {
   char temp=hexValue.charAt(i);
   String temp2=""+temp+"";
   for(int j=0;j<hex.length;j++)
   {
    if(temp2.equalsIgnoreCase(hex[j]))
    {
     binValue=binValue+binary[j];
    }
   }
  }

int index=0;
String PTIndex = binValue.substring(binValue.length()-m-2,binValue.length()-2);
if(type.equals("bimodal"))
{
index = (int) binaryToDecimal(PTIndex);
typewise(index,tn);
}
else if(type.equals("gshare"))
{
    

   // System.out.println("PTIndex "+ PTIndex);
 String nmpcbits = PTIndex.substring(0,n);
 //System.out.println("nmpcbits "+ nmpcbits);
 String sindex = "";

 String temp2 = Arrays.toString(hReg).replace(", ","");
// System.out.println("temp 2 "+ temp2);
 String temp3 = temp2.substring(1,temp2.length()-1);
// System.out.println("temp3 "+ temp3);
 int tempindex =  (int) (binaryToDecimal(temp3) ^ binaryToDecimal(nmpcbits));
// System.out.println("tempindex "+ tempindex);
 String preindex = Integer.toBinaryString(tempindex);
// System.out.println("preindex "+ preindex);
 index = (int) binaryToDecimal(preindex.concat(PTIndex.substring(n,PTIndex.length())));
// System.out.println("index "+ index);
typewise1(index, tn,n,type);
}

else if(type.equals("hybrid"))
{
    // obtain both predictions

    // bimodal
    String bindex = binValue.substring(binValue.length()-m2-2,binValue.length()-2);
    int index1 = (int) binaryToDecimal(bindex);
    int p1 = htypewise(index1,tn);
    
    

    //ghshare
    //System.out.println("PTIndex "+ PTIndex);
    String nmpcbits = PTIndex.substring(0,n);
   // System.out.println("nmpcbits "+ nmpcbits);
    String sindex = "";
    int index2=0;
    String temp2 = Arrays.toString(hReg).replace(", ","");
   // System.out.println("temp 2 "+ temp2);
    String temp3 = temp2.substring(1,temp2.length()-1);
   // System.out.println("temp3 "+ temp3);
    int tempindex =  (int) (binaryToDecimal(temp3) ^ binaryToDecimal(nmpcbits));
   // System.out.println("tempindex "+ tempindex);
    String preindex = Integer.toBinaryString(tempindex);
   // System.out.println("preindex "+ preindex);
    index2 = (int) binaryToDecimal(preindex.concat(PTIndex.substring(n,PTIndex.length())));
   // System.out.println("index "+ index2);
    int p2 =  htypewise1(index2, tn);
    

// chooos logic ... index ...

String tcindex = binValue.substring(binValue.length()-k-2,binValue.length()-2);
int cindex = (int) binaryToDecimal(tcindex);

if(choose[cindex].count <2)
{
    // choose bimodal
    typewise(index1, tn);
   // System.out.println("CP  "+cindex + " "+ choose[cindex].count);
    
}
else
{
    // choose gshare
    typewise1(index2,tn,n,type);
    //System.out.println("CP  "+cindex + " "+ choose[cindex].count);
}

if(tn.equals("t"))
{
    for(int i=n-1;i>0;i--)
    {

        hReg[i]=hReg[i-1];
    }
    hReg[0]=1;

}
else if(tn.equals("n"))
{
    for(int i=n-1;i>0;i--)
    {

        hReg[i]=hReg[i-1];
    }
    hReg[0]=0;

}




if(p1 == 1)
{
    if(p2 == 1){}
    else if(p2 == 0)
    {
        // bimodal correct gshare incorrect
        if(!(choose[cindex].count == 0))
        choose[cindex].count--;
        //System.out.println("CU  "+cindex + " "+ choose[cindex].count);
    }
}
else if (p1 == 0)
{
    if(p2 == 0){}
    else if(p2 == 1)
    {
        // gshare correct bimodal incorrect
        if(!(choose[cindex].count == 3))
        choose[cindex].count++;
      //          System.out.println("CU "+ cindex + " "+ choose[cindex].count);
    }
}


}

    }
in.close();
    }catch (Exception e){//Catch exception if any
        e.printStackTrace();
        System.err.println("Error: " + e.getMessage());
    }

  }

  
 void typewise(int index, String tn) {


if(entryb[index].count < 2)
{
    //System.out.println("BP "+ index + " "+ entryb[index].count);
    //System.out.println("Branch not taken");
    if(tn.equals("t"))
        mispCount++;
}
else
{
   //System.out.println("BP "+ index + " "+ entryb[index].count);
    //System.out.println("Branch taken");
    if(tn.equals("n"))
        mispCount++;
}

//update count

if(tn.equals("t"))
{
    if(!(entryb[index].count == 3))
    entryb[index].count++;
    //System.out.println("BU "+ index + " "+ entryb[index].count);

}
else if(tn.equals("n"))
{
    if(!(entryb[index].count == 0))
    entryb[index].count--;
    //System.out.println("BU "+ index + " "+ entryb[index].count);
}


    }

 void typewise1(int index, String tn, int n,String type) {


if(entryg[index].count < 2)
{
    //System.out.println("GP "+ index + " "+ entryg[index].count);
    //System.out.println("Branch not taken");
    if(tn.equals("t"))
        mispCount++;
}
else
{
    //System.out.println("GP "+ index + " "+ entryg[index].count);
    //System.out.println("Branch taken");
    if(tn.equals("n"))
        mispCount++;
}

//update count

if(tn.equals("t"))
{
    if(!(entryg[index].count == 3))
    entryg[index].count++;
    //System.out.println("GU "+ index + " "+ entryg[index].count);
   
    if(!(type.equals("hybrid"))){
    for(int i=n-1;i>0;i--)
    {

        hReg[i]=hReg[i-1];
    }
    hReg[0]=1;}

}
else if(tn.equals("n"))
{
    if(!(entryg[index].count == 0))
    entryg[index].count--;
    //System.out.println("GU "+ index + " "+ entryg[index].count);
    if(!(type.equals("hybrid"))){
    for(int i=n-1;i>0;i--)
    {

        hReg[i]=hReg[i-1];
    }
    hReg[0]=0;}
}


    }

 // used 2 update count in hybrid ...
 int htypewise(int index, String tn) {


if(entryb[index].count < 2)
{
    //System.out.println("BP "+ index + " "+ entryb[index].count);
    //System.out.println("Branch not taken");
    if(tn.equals("t"))
      return 0;
    else
        return 1;
}
else
{
    //System.out.println("BP "+ index + " "+ entryb[index].count);
    //System.out.println("Branch taken");
   if(tn.equals("n"))
     return 0;
   else
       return 1;
}

    }

// used 2 update count in hybrid ...
 int htypewise1(int index, String tn) {


if(entryg[index].count < 2)
{
    //System.out.println("GP "+ index + " "+ entryg[index].count);
    //System.out.println("Branch not taken");
    if(tn.equals("t"))
      return 0;
    else
        return 1;
}
else
{
    //System.out.println("GP "+ index + " "+ entryg[index].count);
    //System.out.println("Branch taken");
   if(tn.equals("n"))
     return 0;
   else
       return 1;
}

    }


   // convert binary to decimal
public static long binaryToDecimal(String binary) throws NumberFormatException {
  // Initialize result to 0
  long res = 0;

  // Do not continue on an empty string
  if (binary.isEmpty()) {
    throw new NumberFormatException("Empty string is not a binary number");
  }

  // Consider each digit in the string
  for (int i = 0; i < binary.length(); i++) {
    // Get the nth char from the right (first = 0)
    char n = binary.charAt(binary.length() - (i+1));

    // Check if it's a valid bit
    if ((n != '0') && (n != '1')) {
      // And if not, die horribly
      throw new NumberFormatException("Not a binary number");
    } else if (n == '1') {
      // Only add the value if it's a 1
      res += Math.round(Math.pow(2.0, i));
    }
  }

  return res;
}

void printResults(String type, int size, int chSize, int sizeb)
{
    System.out.println("number of predictions: "+ pCount);
    System.out.println("number of mispredictions: "+ mispCount);
    float temp = (float)mispCount/(float)pCount;
    temp=temp*100;
    
    double rate = Math.round(temp*100.0)/100.0;
    double rem1 = rate*100;
    double rem = rem1 % 10;
    if(!(rem == 0))
    System.out.println("misprediction rate: "+ rate+"%");
    else
    System.out.println("misprediction rate: "+ rate+"0%");
    if(type.equals("hybrid"))
    {
     System.out.println(" FINAL CHOOSER CONTENTS");
     for (int i =0; i< chSize;i++)
     {
         System.out.println(i+ "        "+choose[i].count);
     }
     
     System.out.println("FINAL GSHARE CONTENTS");
     for (int i =0; i< size;i++)
     {
         System.out.println(i+ "        "+entryg[i].count);
     }
     System.out.println("FINAL BIMODAL CONTENTS");
     for (int i =0; i< sizeb;i++)
     {
         System.out.println(i+ "        "+entryb[i].count);
     }

    }
    else if (type.equals("bimodal")){
    System.out.println("FINAL BIMODAL CONTENTS");
     for(int i=0;i< size;i++)
  {
      System.out.println(i+ "        "+entryb[i].count);
  }
    
    }
    
    else if (type.equals("gshare")){
    System.out.println("FINAL GSHARE CONTENTS");
     for(int i=0;i< size;i++)
  {
      System.out.println(i+ "        "+entryg[i].count);
  }
    
    }

}



public static void main(String args[])
  {
    int m = 0,n = 0,m2 = 0,k=0;
    String file ="";

       /*  m=10;
         n=7;
         k=5;
         m2=5;
*/
       
        String type = args[0];
       if(type.equals("bimodal"))
       {
        m = Integer.parseInt(args[1]);
         file = args[2];
	        System.out.println("COMMAND");
        System.out.println("sim "+ type +" "+m+" "+file+".txt");        
       }

       else if(type.equals("gshare"))
       {
           m = Integer.parseInt(args[1]);
           n = Integer.parseInt(args[2]);
          file = args[3];
	        System.out.println("COMMAND");
        System.out.println("sim "+ type +" "+m+" "+n+" "+file+".txt");
          
       }
       else if(type.equals("hybrid"))
       {
           k = Integer.parseInt(args[1]);
           m = Integer.parseInt(args[2]);
           n = Integer.parseInt(args[3]);
           m2 = Integer.parseInt(args[4]);
            file = args[5];
        System.out.println("COMMAND");
        System.out.println("sim "+ type +" "+k+" "+m+" "+n+" "+m2+" "+file+".txt");
          
       }

     //   String type = "hybrid";
     //    file = "jpeg_trace";
        int size =    (int) Math.pow(2,m);
        int chSize =  (int) Math.pow(2,k);
        sim ob1 = new sim(m,n,k,m2,type);
        int sizeb = (int) Math.pow(2,m2);
        ob1.FileRead(m,n,file,type,k,m2);
        System.out.println("OUTPUT");
        ob1.printResults(type, size,chSize,sizeb);

  }

  
}
