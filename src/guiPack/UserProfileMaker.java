package guiPack;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataPack.EyeCoordinate;
import userPack.ErrorProfile;
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
	private JLabel selectedExistingUserNameLabel ;
	private JLabel selectedExistingUserAgeLabel ;

	private JButton useExistingUserProfileButton ;
	private JButton createNewUserProfileButton ;
	private JButton addNewUserDetailsButton ;
	private JButton exitWindowButton1, exitWindowButton2, exitWindowButton3 ;
	private JButton goBackButton1,goBackButton2 ;
	private JButton selectCurrentUserButton ;

	private JTextField newUserNameTextField ;
	private JTextField newUserAgeTextField ;
	private JTextField existingUserNameTextField ;
	private JTextField existingUserAgeTextField ;
	
	
	private JComboBox<String> existingUserListComboBox ;



	private Dimension startPanelDimension = new Dimension(300, 200) ;
	private Dimension newUserPanelDimension = new Dimension(255, 200) ;
	private Dimension existingUserPanelDimension = new Dimension(300,200) ;
	
	private User userData ;
	private boolean isUserDataReady ;
	private boolean isUserDataNew ;
	private ErrorProfile userErrorProfile ;

	
	private ArrayList<User> userList ;

	public UserProfileMaker(ArrayList<User> userList) 
	{
		this.userList = userList ;
		this.userData = userList.get(0) ;
		this.isUserDataReady = false ;
		this.isUserDataNew = false ;
		this.userErrorProfile = null ;
		
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
		
		this.selectedExistingUserNameLabel = new JLabel("Selected User's Name: ");
		this.selectedExistingUserAgeLabel = new JLabel("Selected User's Age: ");

		this.useExistingUserProfileButton = new JButton("Use Existing User Profile") ;
		this.createNewUserProfileButton = new JButton("Create new Existing User Profile") ;
		this.addNewUserDetailsButton = new JButton("Add New User Details.") ;
		this.goBackButton1 = new JButton("Go Back.") ;
		this.exitWindowButton1 = new JButton("Exit.");
		this.exitWindowButton2 = new JButton("Exit.");
		this.exitWindowButton3 = new JButton("Exit.");
		this.selectCurrentUserButton = new JButton("Select User.") ;
		this.goBackButton2 = new JButton("Go Back.") ;

		this.useExistingUserProfileButton.addActionListener(this);
		this.createNewUserProfileButton.addActionListener(this);
		this.addNewUserDetailsButton.addActionListener(this);
		this.goBackButton1.addActionListener(this);
		this.exitWindowButton1.addActionListener(this);
		this.exitWindowButton2.addActionListener(this);
		this.exitWindowButton3.addActionListener(this);
		this.selectCurrentUserButton.addActionListener(this);
		this.goBackButton2.addActionListener(this);


		this.newUserNameTextField = new JTextField("") ;
		this.newUserNameTextField.setColumns(10);
		this.newUserAgeTextField = new JTextField("") ;
		this.newUserAgeTextField.setColumns(10);
		this.existingUserNameTextField = new JTextField("") ;
		this.existingUserNameTextField.setColumns(10);
		this.existingUserAgeTextField = new JTextField("") ;
		this.existingUserAgeTextField.setColumns(10);
		this.existingUserNameTextField.setEditable(false);
		this.existingUserAgeTextField.setEditable(false);
		this.existingUserNameTextField.setText(userList.get(0).getName());
		this.existingUserAgeTextField.setText(""+userList.get(0).getAge());
		
		
		
		this.existingUserListComboBox = new JComboBox<String>() ;
		for(User u: this.userList)
		{
			this.existingUserListComboBox.addItem(u.getName());
		}
		this.existingUserListComboBox.addActionListener(this);

		this.startPanel.add(this.startPanelHeaderLabel) ;
		this.startPanel.add(this.useExistingUserProfileButton);
		this.startPanel.add(this.createNewUserProfileButton) ;
		this.startPanel.add(this.exitWindowButton1) ;

		this.newUserPanel.add(this.newUserPanelHeaderLabel);
		this.newUserPanel.add(this.nameLabel) ;
		this.newUserPanel.add(this.newUserNameTextField) ;
		this.newUserPanel.add(this.ageLabel) ;
		this.newUserPanel.add(this.newUserAgeTextField) ;
		this.newUserPanel.add(this.addNewUserDetailsButton) ;
		this.newUserPanel.add(this.goBackButton1) ;
		this.newUserPanel.add(this.exitWindowButton2) ;

		
		this.existingUserPanel.add(this.existingUserPanelHeaderLabel) ;
		this.existingUserPanel.add(this.existingUserListComboBox) ;
		this.existingUserPanel.add(this.selectedExistingUserNameLabel) ;
		this.existingUserPanel.add(this.existingUserNameTextField) ;
		this.existingUserPanel.add(this.selectedExistingUserAgeLabel) ;
		this.existingUserPanel.add(this.existingUserAgeTextField) ;
		this.existingUserPanel.add(this.selectCurrentUserButton) ;
		this.existingUserPanel.add(this.goBackButton2) ;
		this.existingUserPanel.add(exitWindowButton3);




		this.setContentPane(this.startPanel);
		setDimension(startPanelDimension) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("User Profile Maker & Selector GUI");
		


		this.setVisible(true);

	}

	private void setDimension(Dimension d)
	{
		this.setSize(d);
		this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-d.getWidth())/2, 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-d.getHeight())/2);
	}



	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == exitWindowButton1 || e.getSource() == exitWindowButton2 || e.getSource() == exitWindowButton3)
		{
			this.setVisible(false);
			this.dispose();
		}
		else if (e.getSource() == createNewUserProfileButton)
		{
			this.remove(this.startPanel);
			this.setContentPane(this.newUserPanel);
			setDimension(newUserPanelDimension);
			this.setTitle("New User Details");
		}
		else if (e.getSource() == goBackButton1 || e.getSource() == goBackButton2)
		{
			this.remove(this.newUserPanel);
			this.remove(this.existingUserPanel);
			this.setContentPane(this.startPanel);
			setDimension(startPanelDimension);
			this.setTitle("User Profile Maker & Selector GUI");
		}
		else if (e.getSource() == addNewUserDetailsButton)
		{
			boolean dataOk = true ;
			String name = "";
			int age = 0;
			name = this.newUserNameTextField.getText() ;
			if (name.trim().length() == 0)
			{
				dataOk = false ;
			}
			try 
			{
				age = Integer.parseInt(this.newUserAgeTextField.getText());
			} catch (NumberFormatException e1) {
				dataOk = false ;
			}
			if(age <= 0 || age >=123)
			{
				dataOk = false;
			}
			if (dataOk)
			{
				userErrorProfile = new ErrorProfile(0, new EyeCoordinate(0.0, 0.0),new EyeCoordinate(0, 0)) ;
				

				this.userData = new User(name, age, userErrorProfile) ;
				this.isUserDataNew = true ;
				System.out.println("From Within is New User: "+isUserDataNew+"\n"+userData);

				
				this.remove(newUserPanel);
				//TODO Add Intermediary screen, then new profile panel and fill userErrorProfile.

				this.isUserDataReady = true ;
				this.setVisible(false);
				this.dispose();
				
			}
		}
		else if(e.getSource() == useExistingUserProfileButton)
		{
			this.remove(startPanel);
			this.setContentPane(existingUserPanel);
			this.setDimension(existingUserPanelDimension);
			this.setTitle("Select Exisiting User Profile.");
		}
		else if (e.getSource() == existingUserListComboBox)
		{

			this.existingUserNameTextField.setText(userList.get(existingUserListComboBox.getSelectedIndex()).getName());
			this.existingUserAgeTextField.setText(""+userList.get(existingUserListComboBox.getSelectedIndex()).getAge());
			this.userData = userList.get(existingUserListComboBox.getSelectedIndex()) ;
		}
		else if (e.getSource() == selectCurrentUserButton)
		{
			this.isUserDataReady = true ;
			this.isUserDataNew = false ;
			this.setVisible(false);
			this.dispose();

//			System.out.println(this.isUserDataNew+" "+this.isUserDataReady+" "+this.userData);
		}
		this.validate();
		this.repaint();
	}






	public boolean isUserDataNew() {
		return isUserDataNew;
	}


	public User getUserData() {
		return userData;
	}

	public boolean isUserDataReady() {
		return isUserDataReady;
	}


}
