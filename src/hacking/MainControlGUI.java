package hacking;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.awt.*;

public class MainControlGUI  { //main class runner of GUI

	JTextArea textArea = new JTextArea (18,90);
	String input1 = "";
	String input2 = "";
	String inputtotal = "";
	String userIn = "";


	public static void main(String[] args) {
		
		MainControlGUI gui = new MainControlGUI(); //create GUI
		gui.go();
		
	}
	
	public void go(){
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quits program once window closes
		
		//textArea styling
		textArea.setBackground(Color.darkGray);
		Font font = new Font("Courier New", Font.PLAIN, 18);
        textArea.setFont(font);
        textArea.setForeground(Color.white);
        textArea.setCaretColor(Color.white);
        
		//creating the scroll pane
	    JScrollPane jScrollPane = new JScrollPane(textArea);

	    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    jScrollPane.setViewportBorder(new LineBorder(Color.GREEN));
	    jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    jScrollPane.setBackground(Color.black);

		//adding the pusheen image
	    Image image = null;
	    URL url = null;
	    try {
	        url = new URL("https://kaitlynchan.github.io/HackGUIweb/hack%20copy.gif");
	        image = ImageIO.read(url);
	    } catch (MalformedURLException ex) {
	        System.out.println("Malformed URL");
	    } catch (IOException iox) {
	        System.out.println("Can not load file");
	    }
	    JLabel background = new JLabel(new ImageIcon(image));
	   		
		//add components to the panel
	    panel.add(jScrollPane);
		panel.setBackground(Color.black);
	    frame.getContentPane().add(BorderLayout.CENTER,panel);
	    frame.getContentPane().add(BorderLayout.SOUTH, background);
	    frame.getContentPane().setBackground(Color.BLACK);
	    
	    //found methods to replace system.out to redirect to GUI
		redirectSystemStreams();
		
		//frame set up
		frame.setSize(1100, 700); //pixels
		frame.setTitle("HackSim");
		frame.setVisible(true); //show the stuff!
		
		//begin building the hacksim

		HackProcessor proc = new HackProcessor();
		String currIp = newIP();
		String nextIp = newIP();
		Server serv = new Server("admin", "password", "The next IP is "+nextIp+"\nUser is admin\nPassword is the minimum moves to complete the Towers of Hanoi with three rings", currIp, proc);
		
		//output information to GUI
		for (int i = 0; i < 3; i++){
			write(".");
			pause(200);
		}
		
		write("Hello agent\n");
		pause(800);
		write("Welcome to Hacking Simulator\n");
		pause(1000);
		write("Your task today is to destroy the infamous AI called Magpie\n");
		pause(800);
		write("Do you accept this mission? Please answer yes or no");

		//record the current text in the area
		input1 = (textArea.getText()).trim();
		
		//process the user input
		try {
			userIn = getInput();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		if(!userIn.equals("yes")&&!userIn.equals("no")){
			proc.process(userIn, serv);
		}
		if(userIn.equalsIgnoreCase("no")){
			pause(800);
			write("We'll see you next time, agent");
			pause(400);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		else{
			if(!serv.getBypass()){
				write("Our sources have reported that a computer is running Magpie\nat the IP address "+currIp);
				System.out.println();
				pause(800);
				write("The computer only grants access to administrators, username \"admin\",\nand the password is believed to just be \"password\"");
				pause(600);
				write("Every computer on the network has a file called MagpieFile\nthat can help you get deeper into the network\n");
				pause(750);
			}		
			write("For a list of useful commands, enter the word 'commands' without quotes");
			
			//process next input
			input1 = (textArea.getText()).trim();
			while(serv.hacked == false){
				input1 = (textArea.getText()).trim();
				
				try {
					userIn = getInput();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				proc.process(userIn, serv);
				
			}
			currIp = nextIp;
			serv = new Server("admin", "7", "That's the last server in the demo!", currIp, proc);
			write("Congratulations!\nYou've hacked the first server and gotten a clue to the next one\nRepeat the process on the next server");
			
			//process more input 
			input1 = (textArea.getText()).trim();
			while(serv.hacked == false){
				input1 = (textArea.getText()).trim();
				try {
					userIn = getInput();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				proc.process(userIn, serv);
			}
			write("Good job agent, you've finished the 2-server demo\n\n");
			pause(800);
			write("We'll see you next time, agent");
		}
	}
	
	//method to set user input to a string once enter key event occurs
	public String getInput() throws InterruptedException
	{
	    final CountDownLatch latch = new CountDownLatch(1);
	    KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
	        // Anonymous class invoked from EDT
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER)
	                latch.countDown();
	            return false;
	        }
	    };
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
	    latch.await();  // current thread waits here until countDown() is called
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);
	    input2 = (textArea.getText()).trim();
	    inputtotal = (input2.substring(input1.length())).trim();
        return inputtotal;
	}
	
	public static String newIP(){
		String ip = (int) (Math.random() * 256) +"."+(int) (Math.random() * 256) +"."+(int) (Math.random() * 256) +"."+(int) (Math.random() * 256);
		return ip;
	}
	
	public static void quickWrite(String str){
		for(int i = 0; i < str.length(); i++){
			System.out.print(str.charAt(i));
			pause(20);
		}
		System.out.println();
		System.out.println();
		pause(400);
	}
	
	public static void pause(int time){
		long currenttime = System.currentTimeMillis();
		long checktime = 0;
		while ((checktime = System.currentTimeMillis()) < (currenttime + time)){
		}
	}
	
	public static void write(String str){
		for(int i = 0; i < str.length(); i++){
			System.out.print(str.charAt(i));
			pause(40);
		}
		System.out.println();
	}
	
	//part of method to redirect system streams to GUI from console
	private void updateTextArea(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      textArea.append(text);
		    }
		  });
		}
		 
	private void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(int b) throws IOException {
		      updateTextArea(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextArea(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
	}

}
