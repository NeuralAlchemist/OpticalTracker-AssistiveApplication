package userPack;

public class User {
	private String name;
	private int age ;
	private ErrorProfile userErrorProfile ;

	
	
	public User(String name, int age, ErrorProfile userErrorProfile) {
		super();
		this.name = name;
		this.age = age;
		this.userErrorProfile = userErrorProfile;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public ErrorProfile getUserErrorProfile() {
		return userErrorProfile;
	}



	public void setUserErrorProfile(ErrorProfile userErrorProfile) {
		this.userErrorProfile = userErrorProfile;
	}



	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", userErrorProfile=" + userErrorProfile + "]";
	}
	
	
	
	

}
