import java.net.*;
import java.io.*;

class Server
{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server(){

    try {

        server = new ServerSocket(7777);
        System.out.println("Server is starting at port 7777");
        System.out.println("waiting....");
        socket =  server.accept();

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        startReading();
        startWriting();


    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
    }

    public void startReading(){
        //thread for reading data
        Runnable r1 =() ->{

            System.out.println("reader started...");
            try {
                while(!socket.isClosed()){
                    String msg = br.readLine();
                    if (msg.equals("exit"))
                    {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: "+ msg);
                }
                
            } catch (Exception e) {
                System.out.println("Connection closed..");
            }
        };
        new Thread(r1).start();;

    }

    public void startWriting(){
        //thread for writing data and sending it to client
        Runnable r2 =() -> {
            System.out.println("writer started...");
            try {
                while(!socket.isClosed()){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Connection closed");
            }
        };
        new Thread(r2).start();

    }




    public static void main(String[] args) {
        System.out.println("Server started!");
        new Server();
    }
}