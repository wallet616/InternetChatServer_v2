package pl.wallet616.Main;

public class Main {
	public static final int slots = 32;
	public static String[][] usersList = new String[slots][2];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DataSave.addData("521das221asd", "Kaktus");
		
		DataSave.addData("wwwwwaf2df", "Ziemniak");
		
		DataSave.addData("22d21saf2df", "Ziemniak");
		
		DataSave.addData("ddddddd2df", "Ogorek");
		
		DataSave.addData("120ad", "Ziemniak");
		
		
		DataSave.changeData("22d21saf2df", "userName", "Proszek do pieczenia");
	}

}
