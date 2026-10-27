package game_server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ShareArea {
	
	public boolean[] userSequence = {true,true,true,true,true};
	public  String[] userId = {"id","id","id","id","id"};
	public  List<PrintWriter> userList = new ArrayList<PrintWriter>(); 
	public boolean gamePlaying = false;
	public List<Integer> card = new ArrayList<Integer>() {{
		add(1);
		add(2);
		add(3);
		add(4);
		add(5);
		add(6);
		add(7);
		add(8);
		add(9);
		add(10);
		add(11);
		add(12);
		add(13);
		add(14);
		add(15);
		add(16);
		add(17);
		add(18);
		add(19);
		add(20);
	}};
	public boolean[] playingUser = {false, false, false, false, false};
	
	public void setUserId(int a, String s) {
		userId[a] = s;
	}
	
	public String re (int card1, int card2) {
		
		if( ((card1 == 13)&&(card2 == 18)) || ((card1 == 18)&&(card2 == 13)) ) return "삼팔 광땡";
		else if( ((card1 == 11)&&(card2 == 18)) || ((card1 == 18)&&(card2 == 11)) ) return "일팔 광땡";
		else if( ((card1 == 10)&&(card2 == 20)) || ((card1 == 20)&&(card2 == 10)) ) return "장땡";
		else if( ((card1 == 9)&&(card2 == 19)) || ((card1 == 19)&&(card2 == 9)) ) return "구땡";
		else if( ((card1 == 8)&&(card2 == 18)) || ((card1 == 18)&&(card2 == 8)) ) return "팔땡";
		else if( ((card1 == 7)&&(card2 == 17)) || ((card1 == 17)&&(card2 == 7)) ) return "칠땡";
		else if( ((card1 == 6)&&(card2 == 16)) || ((card1 == 16)&&(card2 == 6)) ) return "육땡";
		else if( ((card1 == 5)&&(card2 == 15)) || ((card1 == 15)&&(card2 == 5)) ) return "오땡";
		else if( ((card1 == 4)&&(card2 == 14)) || ((card1 == 14)&&(card2 == 4)) ) return "사땡";
		else if( ((card1 == 3)&&(card2 == 13)) || ((card1 == 13)&&(card2 == 3)) ) return "삼땡";
		else if( ((card1 == 2)&&(card2 == 12)) || ((card1 == 12)&&(card2 == 2)) ) return "이땡";
		else if( ((card1 == 1)&&(card2 == 11)) || ((card1 == 11)&&(card2 == 1)) ) return "일땡";
		else if( ((card1 + card2) % 10) == 0) return "망통";
		else if( ((card1 + card2) % 10) == 1) return "한끗";
		else if( ((card1 + card2) % 10) == 2) return "두끗";
		else if( ((card1 + card2) % 10) == 3) return "세끗";
		else if( ((card1 + card2) % 10) == 4) return "네끗";
		else if( ((card1 + card2) % 10) == 5) return "다섯끗";
		else if( ((card1 + card2) % 10) == 6) return "여섯끗";
		else if( ((card1 + card2) % 10) == 7) return "일곱끗";
		else if( ((card1 + card2) % 10) == 8) return "여덟끗";
		else if( ((card1 + card2) % 10) == 9) return "아홉끗 갑오";
		else return null;
	}
}
