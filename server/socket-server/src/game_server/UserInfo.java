package game_server;

public class UserInfo {

	int sequence;
	String nick;
	String id;
	String gold;
	String bat;
	
	public UserInfo(int sequence,String nick,String id,String gold,String bat) {
		this.sequence = sequence;
		this.nick = nick;
		this.id = id;
		this.gold = gold;
		this.bat = bat;
	}
}
