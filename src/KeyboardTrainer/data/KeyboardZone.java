package KeyboardTrainer.data;


public enum KeyboardZone {
	ZONE_1(1),
	ZONE_2(2),
	ZONE_3(3),
	ZONE_4(4);
	
	private final int number;
	
	KeyboardZone(int number) {
		this.number = number;
	}
	
	public static KeyboardZone byNumber(int number) {
		return KeyboardZone.valueOf("ZONE_" + number);
	}
	
	public int getNumber() {
		return number;
	}
}
