import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.net.*;
public class test {
    public static void main(String[] args) throws IOException {
                String sentence;
                String modifiedSentence;
                BufferedReader inFromUser =
                        new BufferedReader(
                                new InputStreamReader(System.in));
                Socket ClientSocket = new Socket("10.241.196.76",6789);
                DataOutputStream outToServer =
                        new DataOutputStream(
                                ClientSocket.getOutputStream());
                BufferedReader inFromServer =
                        new BufferedReader(new InputStreamReader(
                                ClientSocket.getInputStream()));
                sentence =inFromUser.readLine();
                outToServer.writeBytes(sentence + '\n');
                modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER:"+
                        modifiedSentence);
                ClientSocket.close();
    }

}
