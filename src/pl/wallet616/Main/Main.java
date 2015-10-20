package pl.wallet616.Main;

public class Main {
	public static String[][] usersList = new String[256][2];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataRead.loadUsers();
		
		Log.log(usersList[0][0]);
		DataSave.changeData("jkhgfbsfsgdfgsdfgs", "userKey", "Mentorkie");
		Log.log(usersList[0][0]);
		
		DataSave.addData("521das221asd", "userKey");
	}

}
