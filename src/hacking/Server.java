package hacking;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Server extends MainControlGUI{
	
	private String username;
	private String password;
	private String ipAddress;
	private int port;
	
	private String file;
	
	private boolean access;
	private boolean found;
	
	private Scanner input;
	private HackProcessor proc;
	
	boolean hacked;
	boolean bypass;
	
	public Server(String user, String pass, String fileText, String ip, Scanner in, HackProcessor process){
		username = user;
		password = pass;
		file = fileText;
		access = false;
		found = false;
		ipAddress = ip;
		input = in;
		proc = process;
		port = (int) (Math.random() * 30);
		hacked = false;
		bypass = false;
	}
	
	public void login(String user){
		if(found){
			if(user.equals(username)){
				MainControlGUI.write("Password:");
				input1 =  (textArea.getText()).trim();
				MainControlGUI.write(input1 + " input1");
				try {
					String temp = getInput();
					MainControlGUI.write(input1 + " " + input2 + " "+ temp + "hi");
					if(temp.equals(password)){
						access = true;
						MainControlGUI.write("Access granted");
						return;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MainControlGUI.write("Incorrect password");
				return;
			}
			MainControlGUI.write("Incorrect username");
		}
	}
	public void backdoor(){
		MainControlGUI.write("Backdoor found");
		MainControlGUI.pause(600);
		MainControlGUI.write("Scanning for files...");
		MainControlGUI.pause(1200);
		MainControlGUI.write("File found titled \"MagpieFile\" under user admin");
	}
	public void find(String ip){
		if(ipAddress.equals(ip)){
			found = true;
			MainControlGUI.write("Server found");
		}
		else{
			MainControlGUI.write("Server not found, reenter command and IP");
			String command = input.nextLine();
			proc.process(command, this);
		}
	}
	public void bypass(String pass){
		if (pass.equals("nerd")){
			found = true;
			access = true;
			System.out.println(port+" "+getIp());
			bypass = true;
		}
	}
	public boolean getBypass(){
		return bypass;
	}
	public void getFile(){
		MainControlGUI.write(file);
		hacked = true;
	}
	public int getPort(){
		return port;
	}
	public String getIp(){
		return ipAddress;
	}
	public boolean getFound(){
		return found;
	}
	
	
	
}