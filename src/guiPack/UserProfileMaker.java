package guiPack;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import userPack.User;

public class UserProfileMaker extends JFrame implements ActionListener
{
	private JPanel startPanel ;
	private JPanel newUserPanel ;
	private JPanel existingUserPanel ;
	private JPanel newUserErrorProfilerPanel ;

	private JLabel startPanelHeaderLabel;
	private JLabel newUserPanelHeaderLabel;
	private JLabel existingUserPanelHeaderLabel;
	private JLabel nameLabel;
	private JLabel ageLabel ;

	private JButton useExistingUserProfileButton ;
	private JButton createNewUserProfileButton ;
	private JButton addNewUserDetailsButton ;
	private JButton exitWindowButton1, exitWindowButton2, exitWindowButton3 ;
	private JButton goBackButton ;

	private JTextField nameTextField ;
	private JTextField ageTextField ;



	private Dimension startPanelDimension = new Dimension(300, 200) ;
	private Dimension newUserPanelDimension = new Dimension(255, 200) ;
	private Dimension existingUserPanelDimension = new Dimension(300,200) ;
	
	private User userData ;
	private boolean isUserDataReady ;


	public UserProfileMaker() 
	{
		this.startPanel = new JPanel() ;
		this.newUserPanel = new JPanel() ;
		this.existingUserPanel = new JPanel() ;


		this.startPanelHeaderLabel = new JLabel() ;
		this.startPanelHeaderLabel.setText("<html><center>Start from Here.<BR>"
				+ " Select existing User Profile. <BR>"
				+ "Or create and use a new User Profile.<BR>"
				+ "Or Press Exit to close this window.</center></html>") ;

		this.newUserPanelHeaderLabel = new JLabel() ;
		this.newUserPanelHeaderLabel.setText("<html>Add new User Details.<P></html>");
		
		this.existingUserPanelHeaderLabel = new JLabel() ;
		this.existingUserPanelHeaderLabel.setText("Select from List of Users.");

		this.nameLabel = new JLabel("Enter User Name: ");
		this.ageLabel  = new JLabel("Enter User Age:  ") ;

		this.useExistingUserProfileButton = new JButton("Use Existing User Profile") ;
		this.createNewUserProfileButton = new JButton("Create new Existing User Profile") ;
		this.addNewUserDetailsButton = new JButton("Add New User Details.") ;
		this.goBackButton = new JButton("Go Back.") ;
		this.exitWindowButton1 = new JButton("Exit.");
		this.exitWindowButton2 = new JButton("Exit.");
		this.exitWindowButton3 = new JButton("Exit.");

		this.useExistingUserProfileButton.addActionListener(this);
		this.createNewUserProfileButton.addActionListener(this);
		this.addNewUserDetailsButton.addActionListener(this);
		this.goBackButton.addActionListener(this);
		this.exitWindowButton1.addActionListener(this);
		this.exitWindowButton2.addActionListener(this);
		this.exitWindowButton3.addActionListener(this);


		this.nameTextField = new JTextField("") ;
		this.nameTextField.setColumns(10);
		this.ageTextField = new JTextField("") ;
		this.ageTextField.setColumns(10);

		this.startPanel.add(this.startPanelHeaderLabel) ;
		this.startPanel.add(this.useExistingUserProfileButton);
		this.startPanel.add(this.createNewUserProfileButton) ;
		this.startPanel.add(this.exitWindowButton1) ;

		this.newUserPanel.add(this.newUserPanelHeaderLabel);
		this.newUserPanel.add(this.nameLabel) ;
		this.newUserPanel.add(this.nameTextField) ;
		this.newUserPanel.add(this.ageLabel) ;
		this.newUserPanel.add(this.ageTextField) ;
		this.newUserPanel.add(this.addNewUserDetailsButton) ;
		this.newUserPanel.add(this.goBackButton) ;
		this.newUserPanel.add(this.exitWindowButton2) ;

		
		this.existingUserPanel.add(this.existingUserPanelHeaderLabel) ;
		this.existingUserPanel.add(exitWindowButton3);




		this.setContentPane(this.startPanel);
		setDimension(startPanelDimension) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("User Profile Maker & Selector GUI");
		
		this.isUserDataReady = false ;

		this.setVisible(true);

	}

	private void setDimension(Dimension d)
	{
		this.setSize(d);
		this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-d.getWidth())/2, 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-d.getHeight())/2);
	}

	public void startUserProfileMakerGUI()
	{



	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == exitWindowButton1 || e.getSource() == exitWindowButton2 || e.getSource() == exitWindowButton3)
		{
			this.setVisible(false);
			this.dispose();
		}
		else if ((JButton)e.getSource() == createNewUserProfileButton)
		{
			this.remove(this.startPanel);
			this.setContentPane(this.newUserPanel);
			setDimension(newUserPanelDimension);
			this.setTitle("New User Details");
			switchToCreateNewUserProfile();
		}
		else if (e.getSource() == goBackButton)
		{
			this.remove(this.newUserPanel);
			this.setContentPane(this.startPanel);
			setDimension(startPanelDimension);
			this.setTitle("User Profile Maker & Selector GUI");
			startUserProfileMakerGUI();
		}
		else if (e.getSource() == addNewUserDetailsButton)
		{
			boolean dataOk = true ;
			String name = "";
			int age = 0;
			if (name.trim().length() == 0)
			{
				System.out.println("Name issue");
				dataOk = false ;
			}
			try 
			{
				age = Integer.parseInt(this.ageTextField.getText());
			} catch (NumberFormatException e1) {
				dataOk = false ;
			}
			if(age <= 0 || age >=123)
			{
				dataOk = false;
			}
			if (dataOk)
			{
				System.out.println(name+" "+age);
				this.userData = new User(name, age, null) ;
				this.remove(newUserPanel);
				
			}
		}
		else if(e.getSource() == useExistingUserProfileButton)
		{
			this.remove(startPanel);
			this.setContentPane(existingUserPanel);
		}
		this.validate();
		this.repaint();
	}



	private void switchToCreateNewUserProfile() {

	}



	public User getUserData() {
		return userData;
	}

	public boolean isUserDataReady() {
		return isUserDataReady;
	}


}
