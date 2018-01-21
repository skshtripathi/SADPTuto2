 
import java.net.*;
import java.io.*;
public class MultithreadedSocketServer {
  public static void main(String[] args) throws Exception {
    try{
      ServerSocket server=new ServerSocket(8888);
      int counter=0;
      System.out.println("Server Started ....");
      while(true){
        counter++;
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(" >> " + "Client No:" + counter + " started!");
        ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
        sct.start();
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
//Calculator program
 class Calculator{
String stack[]=new String[50]; 
int top=-1;
void expStack(String exp){
    String s1="";
  top=-1;
for(int i=0;i<exp.length();i++){
   if(exp.charAt(i)>=48&&exp.charAt(i)<=57){
    s1+=exp.charAt(i);
   }
   else
   {
    stack[++top]=s1;
    stack[++top]=""+exp.charAt(i);
    s1="";
   }
}
stack[++top]=s1;

}

 int calculate(String exp){
    
    int a;
    expStack(exp);
    String temp[]=new String[50];
    int t=-1;
    for(int i=0;i<top+1;i++)
    {
   
        if(stack[i].equals("*"))
        {
            a=Integer.parseInt(temp[t--])*Integer.parseInt(stack[i+1]);
            
            temp[++t]=""+a;
            i++;

        }
        else
        if(stack[i].equals("/"))
        {
            a=Integer.parseInt(temp[t--])/Integer.parseInt(stack[i+1]);
            
            temp[++t]=""+a;
            i++;
            
        }
        else
            temp[++t]=stack[i];
        
    }
    stack=temp;
    
    int result=Integer.parseInt(stack[0]);
    for(int i=1;i<t;i++)
    {
        
        if(stack[i].equals("+"))
        {
           
            result+=Integer.parseInt(stack[i+1]);
            
                
        }
        else
        if(stack[i].equals("-"))
        {
            result-=Integer.parseInt(stack[i+1]);
            }
    }
    
    return result;


}

}

//calculator end











 
class ServerClientThread extends Thread {
  Socket serverClient;
  int clientNo;
  int squre;
  ServerClientThread(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
  }
  public void run(){
    try{
      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
      Calculator calc=new Calculator();
      String clientMessage="", serverMessage="";
      while(!clientMessage.equals("bye")){
        clientMessage=inStream.readUTF();
        System.out.println("From Client-" +clientNo+ ": Expression is :"+clientMessage);


        
      //  DataInputStream in = new DataInputStream(
        //        new BufferedInputStream(serverClient.getInputStream()));
        //DataOutputStream output= new DataOutputStream(serverClient.getOutputStream());
          //  String line = "";

                         //  try
               // {
                //    line = in.readUTF();
                   
                 //   output.writeUTF(""+);
 
              //  }
              //  catch(IOException i)
              //  {
               //     System.out.println(i);
                //}


       // squre = Integer.parseInt(clientMessage) * Integer.parseInt(clientMessage);
       serverMessage="From Server to Client-" + clientNo + " Result of " + clientMessage + " is " +calc.calculate(clientMessage);
        outStream.writeUTF(serverMessage);
        outStream.flush();
      }
      inStream.close();
      outStream.close();
      serverClient.close();
    }catch(Exception ex){
      System.out.println(ex);
    }finally{
      System.out.println("Client -" + clientNo + " exit!! ");
    }
  }
}
