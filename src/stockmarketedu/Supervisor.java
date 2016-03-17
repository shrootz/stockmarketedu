package stockmarketedu;

import java.util.UUID;

public class Supervisor{
	private final String accessCode;
	private Class myClass;
	public Supervisor(){
		accessCode = UUID.randomUUID().toString();
		myClass = new Class(accessCode);
	}
	
}
