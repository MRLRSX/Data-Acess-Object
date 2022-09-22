package db;

@SuppressWarnings("serial")
public class DBIntegrityException extends RuntimeException {
    
	public DBIntegrityException(String MSG) {
		super(MSG);
	}
}
