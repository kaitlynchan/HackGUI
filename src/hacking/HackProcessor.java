package hacking;


public class HackProcessor {
	
	public HackProcessor(){

	}
	
	public void process(String command, Server serv){
		if(command.split(" ")[0].equals("bypass")){
			serv.bypass(command.split(" ")[1]);
		}
		if(command.equalsIgnoreCase("commands")){
			MainControlGUI.quickWrite("sudo nmap <targetIP> sends a SYN to find the server");
			MainControlGUI.quickWrite("sudo -sS -sV <targetIP> checks what is running stealthily");
			MainControlGUI.quickWrite("nmap -p <port> --script telnet-brute --script-args userdb=<users file>,passdb=<password file>,telnet-brute.timeout=8s <targetIP>\ngets you into a computer if you have files of possible usernames and passwords");
			MainControlGUI.quickWrite("telnet -l <username> <targetIP> lets you enter a username to log in \nYou will then be prompted for a password");
			MainControlGUI.quickWrite("nmap --script ftp-vsftpd-backdoor -p <port> enters and scans a computer");
			MainControlGUI.quickWrite("sudo nmap -sV -p <port> --script ftp-vsftpd-backdoor --script-args exploit.cmd=\"<username> / <filename>\" <targetip>\nlets you access a file owned by a certain user");
		}
		if(command.length()>10){
			if (command.length()<30&&command.substring(0,10).equals("sudo nmap ")){
				serv.find(command.substring(10));
			}
			else if(command.length()>13 && command.substring(0, 13).equals("sudo -sS -sV ")){
				if (serv.getFound()){
					MainControlGUI.write("Telnet is running at port "+serv.getPort());
				}
			}
			else if(command.substring(0,10).equals("telnet -l ")&&command.split(" ").length>3&&command.split(" ")[3].equals(serv.getIp())){
				serv.login(command.split(" ")[2]);
			}
			else if(command.length()>37&&command.substring(0,37).equals("nmap --script ftp-vsftpd-backdoor -p ")&&Integer.parseInt(command.substring(37))==serv.getPort()){
				serv.backdoor();
			}
			if(command.equals("sudo nmap -sV -p "+serv.getPort()+" --script ftp-vsftpd-backdoor --script-args exploit.cmd=\"admin / MagpieFile\" "+serv.getIp())){
				serv.getFile();
			}
		}
		
		if(command.equals(serv.getPass())&&serv.getFound()){
			serv.pass(command);
		}
		
	}
	
}