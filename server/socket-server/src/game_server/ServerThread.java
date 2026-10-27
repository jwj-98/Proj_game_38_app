package game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class ServerThread extends Thread{
	private int userNum;
	private Socket socket = null;
	public static ShareArea sa;
	String request;
	boolean whileTrig = true;
	
	public ServerThread(Socket socket, int userNum, ShareArea sa) {
		this.socket = socket;
		this.userNum = userNum;
		this.sa = sa;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter (new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        	
        	
        	pw.println("ok" + ";" + userNum); //클라이언트로 접속완료+순번 전송
        	pw.flush();
        	
			while(whileTrig) {

				request = br.readLine(); //request할때까지 binding
				System.out.println(userNum + "번 클라이언트로부터 요청 : " + request);
				if(request != null) {
				String[] array = request.split(";");

				
				
				
				
					if(array[0].equals("join")) {
						
						//if(Integer.parseInt(array[1]) == userNum) {//join요청이 해당(내)스레드라면
							synchronized(sa) { //공유객체에 내id를 저장하고
								sa.setUserId( (userNum-1), array[2] );
							}
							//참여중인 유저 정보를 모두 보냄
							for(int i=0; i<5; i++) {
								synchronized(sa) {
									if(sa.userSequence[i] == false) { //만약 i+1 번째 유저가 참여중이라면 0.2초 단위로 참여중인 유저 정보를 보냄
										pw.println("join" + ";" + (int)(i+1) + ";" + sa.userId[i] );
										pw.flush();
										Thread.sleep(200);
									}
								}
							}
							//참여중인 유저들 에게 내정보를 보냄
							broadcast("join" + ";" + (int)(userNum) + ";" + array[2]);
							//내정보를 broadcasting 후 broadcasting할 PW를 공유객체에 저장
							synchronized (sa) {
					            sa.userList.add(pw);
					        	}
							
						//}
						
						System.out.println("요청 유형 : join	상태 : 완료");
						
					}
					
					
					
					
					
					if(array[0].equals("exit")) {
						
						
							synchronized(sa) {
								
								sa.userSequence[(userNum-1)] = true;
								sa.playingUser[(userNum-1)] = false;
					            sa.userList.remove(pw);
					            
					        }
							broadcast("exit" + ";" + (int)userNum + ";" + array[2]);
							System.out.println(userNum + "번 쓰레드 종료");
							sa.gamePlaying = false;
							for(int i=0; i<5; i++) {
                        		if(sa.playingUser[i] == true) {//참여중인유저중 가장앞선 순번 걸러냄
                        			broadcast("ready" + ";" + (int)(i+1)); //가장앞선 순번에게 게임시작 트리거 부여
                        		}
                        		break;
                            }
							
							whileTrig = false;
						
					}
					
					if(array[0].equals("call")) {
						broadcast("call" + ";" + array[1]);
						
						if(Integer.parseInt(array[2]) == 1) {
							pw.println("play");
                        	sa.gamePlaying = true; //게임 진행상태로 변경
							synchronized(sa) {
								Collections.shuffle(sa.card);//카드섞기
							}
							for(int i=0; i<5; i++) {
								if(sa.playingUser[i] == true) {
									
									broadcast("throw" + ";" + (int)(i+1) + ";" + sa.card.get(i) );
									Thread.sleep(300);
									
								}
							}
							
						}
						
						if(Integer.parseInt(array[2]) == 2) {
							pw.println("play2");
							for(int i=0; i<5; i++) {
								if(sa.playingUser[i] == true) {
									broadcast("throw2" + ";" + (int)(i+1) + ";" + sa.card.get( (19-i)) );
									Thread.sleep(300);
								}
							}
						}
						
						if(Integer.parseInt(array[2]) == 3) {
							pw.println("play3");
							for(int i=0; i<5; i++) {
								if(sa.playingUser[i] == true) {
									
									broadcast("flip" + ";" + (int)(i+1) + ";" + sa.card.get(i) + ";" + sa.card.get( (19-i)) );
									Thread.sleep(600);
									
								}
							}
							for(int i=0; i<5; i++) {
								if(sa.playingUser[i] == true) {
									broadcast("result" + ";" + (int)(i+1) + ";" + sa.re(sa.card.get(i), sa.card.get(19-i)) );
								}
							}
							

							
						}
						
						if(Integer.parseInt(array[2]) == 4) {
							broadcast("clear");
							sa.gamePlaying = false;
							for(int i=0; i<5; i++) {
								sa.playingUser[i] = false;
							}
							for(int i=0; i<5; i++) {
								if(sa.userSequence[i] == false) {
									sa.playingUser[i] = true;
								}
							}
							for(int i=0; i<5; i++) {
                        		if(sa.userSequence[i] == false) {//참여중인유저중 가장앞선 순번 걸러냄
                        			broadcast("ready" + ";" + (int)(i+1)); //가장앞선 순번에게 게임시작 트리거 부여
                        		}
                        		break;
                            }
						}
						
						
					}
					
				}
					
			}
		}catch(IOException e) {} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void broadcast(String data) {
        synchronized (sa) {
            for(PrintWriter writer : sa.userList) {
            	System.out.println(userNum + "번쓰레드에서 broadcast : " + data );
                writer.println(data);
                writer.flush();
            }
        }
    }
	
}
