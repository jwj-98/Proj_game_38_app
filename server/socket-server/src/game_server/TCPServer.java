package game_server;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	
	public static final int PORT = 9001;
	public static ShareArea sa;
	
    public static void main(String[] args){

        ServerSocket serverSocket = null;
        sa = new ShareArea();
        
        try{
            // 1. 서버 소켓 객체 생성
            serverSocket = new ServerSocket();
            

            // 2. 소켓을 호스트의 포트와 binding
            String localHostAddress = InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind(new InetSocketAddress(localHostAddress, PORT));
            System.out.println("[server] binding! \naddress:" + localHostAddress + ", port:" + PORT);

            // 3. 클라이언트로부터 연결 요청이 올 때까지 대기
            // 연결 요청이 오기 전까지 서버는 block 상태이며,
            // TCP 연결 과정인 3-way handshake로 연결이 되면 통신을 위한 Socket 객체가 반환됨
            // TCP 연결은 java에서 처리해주며, 더 내부적으로는 OS가 처리한다.
            while(true) {
            	Socket socket = serverSocket.accept();
            	
            	
            	
            	///////////////////////////////////////////////////////////////////////////////////////////
            	InetSocketAddress remoteSocketAddress =(InetSocketAddress)socket.getRemoteSocketAddress();
                String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
                int remoteHostPort = remoteSocketAddress.getPort();
                System.out.println("[client] connected! \nconnected socket address:" + remoteHostName
                        + ", port:" + remoteHostPort);
                ///////////////////////////////////////////////////////////////////////////////////////////
                
                int userNum = 0; // 접속된 유저의 순번 
                
                for(int i=0; i<5; i++) {
                	synchronized(sa) {
	                	if(sa.userSequence[i]) { // 해당순서의 유저가 비어있다면
	                		sa.userSequence[i] = false; // 해당순서를 비활성화
	                		userNum = i+1; // 해당 순서를 저장
	                		break; //for문 나오기
	                	}
                	}
                }
                
                if(userNum != 0) { //유저 순서가 정해졌다면
                	System.out.println("          " + userNum + "번으로 접속합니다.");
                	synchronized(sa) {
                		new ServerThread(socket, userNum, sa).start();
                	}
                }
                else System.out.println("[server] 유저접속 최대인원 초과, socket을 종료합니다.");
            	

                synchronized(sa) {
                	if(sa.gamePlaying == false) {//게임 진행중이 아니라면
                        int currentUser =0 ;
                        for(int i=0; i<5; i++) {
                        	if(sa.userSequence[i] == false) {//유저가 두명이상일경우를 걸러냄
                        		currentUser++;
                        	}
                        }
                        
                        if(currentUser >= 2) {//참여인원이 2명이상일경우
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
            }
            

            // 4. 연결 요청이 오면 연결이 되었다는 메시지 출력
            
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if( serverSocket != null && !serverSocket.isClosed() ){
                    serverSocket.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        

    }//end main
    
	private static void broadcast(String data) {
        synchronized (sa) {
            for(PrintWriter writer : sa.userList) {
            	System.out.println("메인 쓰레드에서 broadcast : " + data );
                writer.println(data);
                writer.flush();
            }
        }
    }
	
}//end class