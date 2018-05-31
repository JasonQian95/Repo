package edu.toronto.csc301.warehouse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RackUtil {
	
	static final Pattern pattern = Pattern.compile("R:([1-9]\\d*)");

	
	public static byte[] rack2bytes(Rack rack) {
		return String.format("R:%d", rack.getCapacity()).getBytes();
	}
	

	public static Rack bytes2rack(byte[] bytes) {
		String s = new String(bytes);
		Matcher m = pattern.matcher(s.trim());
		if(m.matches()){
			return new Rack(Integer.parseInt(m.group(1)));
		} else {
			throw new IllegalArgumentException("Invalid string, " + s);
		}
	}

}
