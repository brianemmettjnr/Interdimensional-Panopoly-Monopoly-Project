import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import javax.swing.JTextField;


public class CommandPanel extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	private static final int FONT_SIZE = 14;
	
	private JTextField commandField = new JTextField(); 
	private LinkedList<String> commandBuffer = new LinkedList<String>();
	private String string;
	
	
	CommandPanel () {
		class AddActionListener implements ActionListener {
			   public void actionPerformed(ActionEvent event)	{
				   synchronized (commandBuffer) {
					   commandBuffer.add(commandField.getText());
					   commandField.setText("");
					   commandBuffer.notify();
				   }
		           return;
			   }
		   }
		ActionListener listener = new AddActionListener();
		commandField.addActionListener(listener);
		commandField.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
		setLayout(new BorderLayout());
		add(commandField, BorderLayout.CENTER);
		return;
	}

	
	public void inputString() {
		synchronized (commandBuffer) {
			while (commandBuffer.isEmpty()) {
				try {
					commandBuffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			string = commandBuffer.pop();
		}
		return;
	}
	
	
	public String getString() {
		return string;
	}
}
