

# 안드로이드 섯다 게임 앱
![main](https://user-images.githubusercontent.com/65906245/116114384-441e4e80-a6f4-11eb-88e5-55860ed1c218.PNG)



## 목차
#### * [프로젝트 소개](#프로젝트-소개)
##### 　　　I. [프로젝트 기능](#1-프로젝트-기능)
##### 　　　II. [프로젝트 개발환경](#2-프로젝트-개발환경)
##### 　　　III. [프로젝트 동작](#3-프로젝트-동작)
##### 　　　IV. [실행 영상](#4-실행-영상)
#### * [Front-End(Android App)](#front-endandroid-app)
##### 　　　I. [레이아웃, 클래스 구성](#1레이아웃-클래스-구성)
##### 　　　II. [Code comment](#2code-commentapp)
#### * [Back-End](#back-end)
##### 　DB
##### 　　　I. [테이블 설계](#1-테이블-설계)
##### 　JSP
##### 　　　I. [Jsp 구성](#1-jsp-구성)
##### 　　　II. [code comment](#2-code-commentweb)
##### 　JAVA socket
##### 　　　I. [JAVA socket 구성](#1-java-socket-구성)
##### 　　　II. [code comment](#2-code-commentsocekt)
<hr/>

## [프로젝트 소개](#목차)<br/>
안드로이드 공부 후 처음으로 개발한 프로젝트입니다.<br/>
안드로이드 앱의 기본적인 기능 위주로
웹서버, 게임 서버를 연동해 구현해본 온라인 섯다 게임 앱입니다.<br/>

### [1. 프로젝트 기능](#목차)<br/><br/>
&nbsp;&nbsp;동시에 5명까지 참가가 가능한 실시간 섯다게임.

	* 기본 기능
	    유저 로그인 가능
		    로그인, 회원가입	Back-end(웹)로컬DB에 정보 저장, 조회
		    계정찾기(email)	회원정보 DB조회 후 email로 정보전송
	* 게임 기능
		    입장/퇴장, 직전승리에 따른 패 순서, 방향 부여
		    화투 패 배분 (ObjectAnimator & TranslateAnimation)
		    배팅 후 승패 판정

### [2. 프로젝트 개발환경](#목차)<br/><br/>
&nbsp;&nbsp; OS: WINDOWS10<br/>
&nbsp;&nbsp; IDE: ECLIPSE 4, Android Studio 4, IntelliJ<br/>
&nbsp;&nbsp; DB: Oracle 11g<br/>

### [3. 프로젝트 동작](#목차)<br/><br/>
유저가 Front-end(앱)을 통해 사용자 인증, 조회 요청시 localDB를 조회해 처리합니다.<br/>
POST 메소드를 사용해 통신합니다.<br/>
Front-end 에서 게임시작 요청시 Back-end(소켓서버) 에서는 새로운 스레드를 생성합니다.<br/>
현재 진행중인 게임 유저 수 의 따른 고유한 순서번호를 부여합니다.<br/>
게임이 진행되면 Back-end(소켓서버) 에서 화투패를 섞고 순서대로 배분합니다.<br/>
배분과 배팅이 모두 끝나면 승리한 유저에게 게임머니(소켓서버-웹-localDB 통신)를 지급하고 첫순서를 부여합니다.<br/>
<br/>
+) Json과같은 구조 사용없이 거의 모든통신을 수동으로,각 데이터를 세미콜론 단위로 구분하는  get방식과 유사한 방식을 사용해 통신합니다.(socket & http-post method)<br/>

### [4. 실행 영상](#목차)<br/><br/>
<hr/>

## [Front-End(Android App)](#목차)<br/>

### [1.레이아웃, 클래스 구성](#목차)<br/>
>java src 
>>[CreateNick](#createnickjava)<br/>
>>[CreateNickRegister](#createnickregisterjava)<br/>
>>[FindAccount](#findaccountjava)<br/>
>>[FindAccountRegister](#findaccountregisterjava)<br/>
>>[GameTable](#gametablejava)<br/>
>>[LoginActivity](#loginactivityjava)<br/>
>>[LoginRegister](#loginregisterjava)<br/>
>>[mainPage](#mainpagejava)<br/>
>>[SignupActivity](#signupactivityjava)<br/>
>>[SignupRegister](#signupregisterjava)<br/>
>>[UserInfo](#userinfojava)<br/>
>>[UserInfoRegister](#userinfojava)<br/>

>xml layout
>>[activity_create_nick](#activity_create_nickxml)<br/>
>>[activity_find_account](#activity_find_accountxml)<br/>
>>[activity_game_table](#activity_game_tablexml)<br/>
>>[activity_login](#activity_loginxml)<br/>
>>[activity_main_page](#activity_main_pagexml)<br/>
>>[activity_signup](#activity_signupxml)<br/><br/>

### [2.Code comment(app)](#목차)<br/>

#### [CreateNick.java](#1레이아웃-클래스-구성)<br/>
	유저 첫 로그인 시 보여지는 액티비티에서 받은 닉네임을
	AsyncTack를 상속받은 CreateNickRegister 객체로 Back-end(웹)로 전송합니다.

#### [CreateNickRegister.java](#1레이아웃-클래스-구성)<br/>
	메인 스레드에서의 HTTP통신을 권장하지않으므로 AsyncTask를 상속받은 클래스로 Back-end(웹)와 통신합니다.

#### [FindAccount.java](#1레이아웃-클래스-구성)<br/>
	계정찾기 액티비티에서 입력한 유저 정보를
	AsyncTask를 상속받은 FindAccountRegister 객체로 Back-end(웹)로 전송합니다.

#### [FindAccountRegister.java](#1레이아웃-클래스-구성)<br/>
	메인 스레드에서의 HTTP통신을 권장하지않으므로 AsyncTask를 상속받은 클래스로 Back-end(웹)와 통신합니다.

#### [GameTable.java](#1레이아웃-클래스-구성)<br/>
	새로운 스레드에서 Socket객체로 Back-end(게임서버)와 통신합니다.
	내 정보와 플레이하는 다른 유저의 정보를 가져오기 위해 Back-end(웹)와 HTTP통신합니다.
	게임 시작 시 Front-end(액티비티)에 보이는 위젯들을 invisible해 게임 테이블을 clear합니다.

 	게임서버에서 받은 첫 데이터에 따라 각 동작을 나머지 데이터로 수행합니다.
 	ok	입장완료처리, 플레이하고 있는 유저수 데이터를 유저순번으로 부여합니다.
		플레이하고 있는 유저의 정보를 요청합니다.
 	join	플레이하고 있는 유저의 정보나 새로 참가한 유저의 정보를 받습니다.
		순번에 따라 시계방향으로 게임이 진행되도록 자리에 맞춰 유저 정보를 액티비티에 보여줍니다.
		DB에서 유저정보를 가져오기 위해 Back-end(웹)과 통신합니다.
 	exit	다른 유저가 접속 종료할 경우 해당 유저의 정보를 액티비티에서 invisible 합니다.
 	ready	받은 선번호가 유저 고유 순서번호와 일치하면 배팅버튼을 누를 경우 데이터를 보내고 게임이 시작됩니다.
 	throw	유저들에게 첫번째 패를 배분합니다.
		유저 고유순번에 따라 시계방향으로 다른유저도 뒷면으로 패를 배분받는 애니메이션을 액티비티에 보여줍니다.
 	play	첫번째 배팅 여부를 선택합니다. 배팅버튼을 누를 경우 데이터를 보내고 다음차례로 넘어갑니다.
	throw2	유저들에게 두번째 패를 배분합니다.
	play2	두번째 배팅 여부를 선택하면 데이터를 보냅니다.
	flip	받은 유저 고유순번과 패번호 데이터로 다른유저의 패를 뒤집는 애니메이션을 보여줍니다.
	call	다른유저가 배팅할 경우 "call" 메세지를 액티비티의 유저 메세지칸에 띄워줍니다.
	play3	다음게임을 시작하기 위해 배팅 버튼을 누를 경우 데이터를 보냅니다.
	clear	모든 유저의 패 칸을 invisible합니다.
	result	게임 결과를 보여줍니다. 끗, 땡 등 패결과를 액티비티의 유저 메세지칸에 띄워줍니다.

	액티비티에서 나가기 버튼을 누를경우 퇴장 데이터를 Back-end(게임서버)로 보내고 메인 액티비티를 호출합니다.

	패를뒤집거나 이동시키는 애니메이션은 TranslateAnimation 과 ObjectAnimator객체를 사용합니다.

	service로 배경음악설정할 경우 onRestart 단계에서 중복으로 음악이 나오게되어
	각 액티비티마다 따로 배경음악을 설정합니다.(MediaPlayer)

#### [LoginActivity.java](#1레이아웃-클래스-구성)<br/>
	최초실행 로그인 성공 이후 자동로그인을 지원하기위해 Shared Preferences를 사용합니다.

	* 버튼이벤트
		로그인		ID&PW값의 조건(길이, 영문 등) 검사 후  AsyncTask를 상속받은 LoginRegister객체로 
				Back-end(웹)로컬DB에서 값 조회 요청, 로그인 성공여부를 통신합니다. 
				로그인 성공 시 mainPage액티비티 인텐트 생성 후
				PK로 사용하는 ID값을 추가해 해당 액티비티를 호출합니다.
		가입,찾기	회원가입, 계정찾기 액티비티Intent 생성 후 해당 액티비티를 호출합니다.

#### [LoginRegister.java](#1레이아웃-클래스-구성)<br/>
	메인 스레드에서의 HTTP통신을 권장하지않으므로 AsyncTask를 상속받은 클래스로 Back-end(웹)와 통신합니다.

#### [mainPage.java](#1레이아웃-클래스-구성)<br/>
	유저 정보 조회에 pk로 사용되는 id값을 SharedPreferences에서 가져와 Back-end(웹) 에 유저정보 조회를 요청합니다.
	받은 유저 정보를 액티비티에 보여줍니다.

	버튼이벤트=로그아웃	앱 실행 시 자동로그인 해제를 위해 SharedPreferences를 null로 초기화합니다.
				로그인 액티비티Intent 생성 후 해당 액티비티를 호출합니다.

	service로 배경음악설정할 경우 onRestart 단계에서 중복으로 음악이 나오게되어
	각 액티비티마다 따로 배경음악을 설정합니다.(MediaPlayer)

#### [SignupActivity.java](#1레이아웃-클래스-구성)<br/>
	Front-end(액티비티)에서 입력한 값의 유효성 검사 후 SignupRegister객체로 Back-end(웹)로 값을 전송합니다.

	Back-end에서 중복검사 후 받은 값을 Toast출력합니다.
	가입성공일 경우 현재 액티비티를 종료시켜 LoginActivity가 호출되게합니다.

#### [SignupRegister.java](#1레이아웃-클래스-구성)<br/>
	메인 스레드에서의 HTTP통신을 권장하지않으므로 AsyncTask를 상속받은 클래스로 Back-end(웹)와 통신합니다.

#### [UserInfo.java](#1레이아웃-클래스-구성)<br/>
	Back-end(웹-로컬DB)에서 가져온 유저 정보를 저장 할 객체입니다. 유저 id, nick, gold 생정자.

#### [UserInfoRegister.java](#1레이아웃-클래스-구성)<br/>
	메인 스레드에서의 HTTP통신을 권장하지않으므로 AsyncTask를 상속받은 클래스로 Back-end(웹)와 통신합니다.
	
	pk로 사용되는 user ID 로 유저정보를 요청해 UserInfo객체로 리턴합니다.


#### [activity_create_nick.xml](#1레이아웃-클래스-구성)<br/>
![createNick](https://user-images.githubusercontent.com/65906245/116112801-de7d9280-a6f2-11eb-9726-b64a34e25a92.PNG)

회원가입 후 최초 로그인 시 게임 내에서 사용할 닉네임을 입력받는 액티비티 입니다.<br/>
입력을 받기위한 TextView 와 Button 위젯 추가.<br/>

#### [activity_find_account.xml](#1레이아웃-클래스-구성)<br/>
![findAccount](https://user-images.githubusercontent.com/65906245/116112634-b2621180-a6f2-11eb-861d-190dfff2d14f.PNG)


회원정보를 찾기위해 이름과 email을 입력받는 액티비티 입니다.<br/>
입력을 받기위한 TextView와 Button위젯 추가.<br/>

#### [activity_game_table.xml](#1레이아웃-클래스-구성)<br/>

![gameTable](https://user-images.githubusercontent.com/65906245/116113004-11c02180-a6f3-11eb-9cc4-02a40a2cdf80.PNG)

게임 진행을 위한 판(테이블)입니다.<br/>
게임 상황에 따라 이미지가 보이거나 보이지 않아야 하기때문에 FrameLayout 내부에 ImageView들을 위치시킵니다.<br/>

#### [activity_login.xml](#1레이아웃-클래스-구성)<br/>

![login](https://user-images.githubusercontent.com/65906245/116113884-deca5d80-a6f3-11eb-8a1e-ea932367778d.PNG)

로그인을 위해 ID과 비밀번호를 입력받는 액티비티 입니다.<br/>
입력 및 회원 서비스를 위해 TextView와 Button위젯 추가.<br/>


#### [activity_main_page.xml](#1레이아웃-클래스-구성)<br/>

![main](https://user-images.githubusercontent.com/65906245/116114384-441e4e80-a6f4-11eb-88e5-55860ed1c218.PNG)

로그인 성공 후 보여지는 메인 액티비티입니다.<br/>
접속한 유저의 게임머니를 표시합니다.<br/>
게임시작을 위한 Button 위젯 추가.<br/>

#### [activity_signup.xml](#1레이아웃-클래스-구성)<br/>

![signup](https://user-images.githubusercontent.com/65906245/116114722-965f6f80-a6f4-11eb-902d-992eb0d57e01.PNG)

회원가입을 위한 액티비티 입니다.<br/>
입력을위한 TextEdit, RadioGroup및 Button 위젯 추가.<br/>

<hr/>

## [Back-End](#목차)<br/>

## DB
### [1. 테이블 설계](#목차)
### ![table](https://user-images.githubusercontent.com/65906245/208213309-6b704001-a5ac-4cef-b867-cec3229714e9.PNG)
## JSP
### [1. Jsp 구성](#목차)
>java src 
>>[CreateNick](#createnick)<br/>
>>[FindAccount](#findaccount)<br/>
>>[GetUserInfo](#getuserinfo)<br/>
>>[MailAuth](#mailauth)<br/>
>>[MailSend](#mailsend)<br/>
>>[SigninDB](#signindb)<br/>
>>[SignupDB](#signupdb)<br/>

>web src
>>[web.xml](#webxml)<br/>
>>[createnick.jsp](#createnickjsp)<br/>
>>[findAccount.jsp](#findaccountjsp)<br/>
>>[getuserinfo.jsp](#getuserinfojsp)<br/>
>>[signin.jsp](#signinjsp)<br/>
>>[signup.jsp](#signupjsp)<br/>
### [2. code comment(web)](#목차)<br/>

#### [CreateNick](#1-jsp-구성)<br/>
	oracle sql 접근정보 필드 구성
	createNick(id, nick) 메서드 - sql에서 id매개변수와 일치하는 tuple의 nick매개변수 UPDATE 

#### [FindAccount](#1-jsp-구성)<br/>
	findAccount(name, email) 메서드 - sql에서 name, email과 일치하는 tuple의 ID,PW를 리턴합니다.

#### [GetUserInfo](#1-jsp-구성)<br/>
	getUserInfo(id) 메서드 - sql에서 id와 일치하는 tuple의 ID, NICK, GOLD를 리턴합니다.

#### [MailAuth](#1-jsp-구성)<br/>
	생성자 - mail사용을 위한 인증정보 구성

#### [MailSend](#1-jsp-구성)<br/>
	생성자 - smtp를 이용하여 mail매개변수로 계정찾기결과 smsg매개변수값을 보냅니다.

#### [SigninDB](#1-jsp-구성)<br/>
	signinDB(id, pw) 메서드 - sql에서 id, pw와 일치하는 tuple의 여부에 따라 값을 리턴합니다.

#### [SignupDB](#1-jsp-구성)<br/>
	signupConnDB(name, id, pw, email, gender) 메서드 - sql에서 중복되는 id가없다면 새로운 tuple을 INSERT하고 결과를 리턴합니다.

#### [web.xml](#1-jsp-구성)<br/>
	서블릿 맵핑을 위한 설정 구성

#### [createnick.jsp](#1-jsp-구성)<br/>
	Input된 값으로 CreateNick Class를 이용해 사용자 닉네임을 생성합니다.

#### [findAccount.jsp](#1-jsp-구성)<br/>
	Input된 값으로 FindAccount Class를 이용해 계정을 찾고 결과를 리턴하고 MailSend Class를 이용해 메일로 전송합니다.

#### [getuserinfo.jsp](#1-jsp-구성)<br/>
	Input된 값으로 getUserInfo Class를 이용해 유저 정보를 가져오고 값을 리턴합니다.

#### [signin.jsp](#1-jsp-구성)<br/>
	Input된 값으로 signinDB Class를 이용해 사용자 로그인 여부를 리턴합니다.

#### [signup.jsp](#1-jsp-구성)<br/>
	Input된 값으로 signupDB Class를 이용해 사용자 계정을 생성하고 성공여부를 리턴합니다.

## JAVA socket
### [1. JAVA socket 구성](#목차)
### [2. code comment(socekt)](#목차)


web서버에서 다수의 요청에 대비해 db접근 클래스들 인스턴스를 singleton 패턴을 사용해봤는데 해당 요청에서는 제대로 동작하는지 모르겠다. 이해가 어렵다.
