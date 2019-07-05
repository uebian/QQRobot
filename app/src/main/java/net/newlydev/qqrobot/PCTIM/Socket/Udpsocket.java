package net.newlydev.qqrobot.PCTIM.Socket;
import java.io.*;
import java.net.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;


public class Udpsocket
{
	private DatagramSocket client_socket = null;
	
	private int server_port = 0;
	
	
	private InetAddress IPAddress =  null;

	
    public Udpsocket(QQUser user){
		this.server_port = user.TXProtocol.WServerPort;
		try
		{
			if (user.IsLoginRedirect){
				IPAddress = InetAddress.getByName(user.TXProtocol.DwRedirectIP);
			
			}else{
			    IPAddress = InetAddress.getByName(user.TXProtocol.DwServerIP);
			}
			client_socket= new DatagramSocket();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void sendMessage(byte[] send_data){
		DatagramPacket send_packet = new DatagramPacket(send_data,send_data.length, 
						   IPAddress, server_port);
		try
		{
			client_socket.send(send_packet);
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
								
	public byte[] receiveMessage(){
		byte[] arr = new byte[1024];
		DatagramPacket packet = new DatagramPacket(arr, arr.length);
		
		try
		{

			client_socket.receive(packet);
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
		
	
		byte[]  recvdata = packet.getData();//得到
		
		return Util.subByte(recvdata,0,packet.getLength());
	}
			
			


    public void shutdown(){
	    client_socket.close();
	}
}
