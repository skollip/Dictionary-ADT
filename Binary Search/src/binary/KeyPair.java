package binary;

public class KeyPair {

	private String value;
	private int key;
	
	
	public KeyPair(int key, String value) {
		this.value = value;
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}
	
	
}
