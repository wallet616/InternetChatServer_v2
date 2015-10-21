package pl.wallet616.Main;

public class Background extends Main{
	public static boolean timerActive = true;
	public static Thread background = new Thread() {
		public void run() {
			while (timerActive) {
				try {
					long timeMili = System.currentTimeMillis();
					for (int i = 0; i < slots; i++) {
						if (usersList[i][2] != null && (timeMili - Long.valueOf(usersList[i][2]).longValue()) >= 20000) {
							DataRead.unloadUser(usersList[i][0]);
						}
				    }
					
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Log.error("Unable to keep the backgound process alive.");
				}
			}
		}
	};
	
}
