package global.reusables;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PrivateKey;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StringEncryptionExe {

	static JPasswordField inputText;
	static JTextField encrText;

	//Pavan--Below Code is for Customized window to Get Encrypted Password
	public static void main(String[] args) {
		JFrame frame= new JFrame("Kewill Encryption Util");
		Container encryptionDialog=frame.getContentPane();
		frame.setDefaultCloseOperation(3);
		encryptionDialog.setLayout(new GridLayout(3, 2));
		JLabel label=new JLabel("Please Enter Password To Encrypt:");
		inputText=new JPasswordField(25);
		JLabel outLabel=new JLabel("Encrypted Password:");
		encrText=new JTextField(25);
		JButton button= new JButton("Convert:");
		encryptionDialog.add(label);
		encryptionDialog.add(inputText);
		encryptionDialog.add(outLabel);
		encryptionDialog.add(encrText);
		encryptionDialog.add(button);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				char[] actualPassword=StringEncryptionExe.inputText.getPassword();
				if((actualPassword==null)||actualPassword.length<=0){
					return;
				}
				String encryptedPassword=new String(actualPassword);
				try {
					encryptedPassword=EncryptionUtil.encrypt(encryptedPassword);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				StringEncryptionExe.encrText.setText(encryptedPassword);
			}
		});
		frame.setSize(800, 150);
		frame.setVisible(true);
	}

}
