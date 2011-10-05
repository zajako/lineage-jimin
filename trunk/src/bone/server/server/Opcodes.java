/**
 * 
 * 					Eva Team - http://eva.pe.kr 
 * 				    
 * 					Member	- 샤샤 (Shax2)				 	 
 * 							- 코드 (Shyeon0111)
 * 							- 뮨법사 (Cr7016)
 * 							- 똥개 (Zinasura)
 * 							- 마스터 (Babuboss)
 * 
 */

package bone.server.server;

public class Opcodes {

	public Opcodes() {}


	
	
	
	
	
	
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
		/**	* * * * * * * * * * * * * * * * Client Packet * * * * * * * * * * * * * * * * * * * * * * **/
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
	

	
		public static final int C_OPCODE_PETMENU 										= 0x00; // 펫 메뉴
		public static final int C_OPCODE_SOLDIERBUY										= 0x01; // 용병 리스트 구입
		public static final int C_OPCODE_NOTICECLICK 									= 0x02; // 공지사항 확인 눌럿을때
		public static final int C_OPCODE_WAREHOUSEPASSWORD 								= 0x03; // 창고비번
//0x04	
		public static final int C_OPCODE_WAR 											= 0x05; // 전쟁
		public static final int C_OPCODE_BOOKMARKDELETE									= 0x06; // [/기억 후 기억목록클릭 delete]
		public static final int C_OPCODE_FIGHT 											= 0x07; // [/결투]
		public static final int C_OPCODE_BOARDDELETE 									= 0x08; // 게시글 삭제
		public static final int C_OPCODE_BOOKMARK 										= 0x09; // [/기억 OO]
//0x0A	
		public static final int C_OPCODE_TRADE 											= 0x0B; // [/교환]
		public static final int C_OPCODE_BANCLAN 										= 0x0C; // 혈맹 추방 명령어
		public static final int C_OPCODE_NPCTALK 										= 0x0D; // Npc와 대화부분
//0x0E
		public static final int C_OPCODE_MOVECHAR 										= 0x0F; // 이동요청 부분
		public static final int C_OPCODE_PARTY 											= 0x10; // [/파티]
		public static final int C_OPCODE_CHANGEHEADING 									= 0x11; // 방향 전환 부분
//0x12	
		public static final int C_OPCODE_CLIENTVERSION 									= 0x13; //v 클라에서 서버 버전 요청 하는 부분	
//0x14	
		public static final int C_OPCODE_CHATGLOBAL 									= 0x15; // 전체채팅	
		public static final int C_OPCODE_BOARDWRITE 									= 0x16; // 게시판 쓰기
		public static final int C_OPCODE_PLEDGE 										= 0x17; // [/혈맹]
		public static final int C_OPCODE_EXCLUDE 										= 0x18; // [/차단]
		public static final int C_OPCODE_CALL 											= 0x19; // CALL버튼 .감시
		public static final int C_OPCODE_BASERESET 										= 0x1A; // 스텟 초기화
		public static final int C_OPCODE_SKILLBUY 										= 0x1B; // 스킬 구입
//0x1C
//0x1D
		public static final int C_OPCODE_DROPITEM 										= 0x1E; // 아이템 떨구기
		public static final int C_OPCODE_BUDDYLIST 										= 0x1F; // 친구리스트
//0x20	
		public static final int C_OPCODE_LEAVECLANE 									= 0x21; // 혈맹 탈퇴
		public static final int C_OPCODE_TITLE 											= 0x22; // 호칭 명령어
		public static final int C_OPCODE_TRADEADDOK 									= 0x23; // 교환 OK
		public static final int C_OPCODE_RETURNTOLOGIN 									= 0x24; // 다시 로긴창으로 넘어갈때 
		public static final int C_OPCODE_WHO 											= 0x25; // [/누구]
		public static final int C_OPCODE_PROPOSE 										= 0x26; // [/청혼]
		public static final int C_OPCODE_WARTIMELIST 									= 0x27; // 공성 시간 변경 리스트
		public static final int C_OPCODE_SELECTLIST 									= 0x28; // 펫리스트에서 펫찾기
		public static final int C_OPCODE_BOARD 											= 0x29; // 게시판 읽기
		public static final int C_OPCODE_USEPETITEM 									= 0x2A; // 펫 인벤토리 아이템 사용
		public static final int C_OPCODE_CHARACTERCONFIG 								= 0x2B; // 캐릭인벤슬롯정보
		public static final int C_OPCODE_SHOP_N_WAREHOUSE 								= 0x2C; // 상점 결과 처리
		public static final int C_OPCODE_AMOUNT 										= 0x2D; // 수량성 아이템 제작 갯수
		public static final int C_OPCODE_HORUNOK										= 0x2E; // 호런 마법배우기
		public static final int C_OPCODE_ENTERPORTAL 									= 0x2F; // 오른쪽 버튼으로 포탈 진입
		public static final int C_OPCODE_SOLDIERGIVE									= 0x30; // 용병 선택
		public static final int C_OPCODE_TRADEADDCANCEL									= 0x31; // 교환 취소
		public static final int C_OPCODE_CLAN 											= 0x32; // 가시범위의 혈맹 마크 요청[폴더내 emblem삭제]
//0x33
		public static final int C_OPCODE_SHIP											= 0x34; // 배타서 내릴때 나옴	
		public static final int C_OPCODE_TAXRATE 										= 0x35; // 세금 조정
//0x36
		public static final int C_OPCODE_DRAWAL											= 0x3A; // 공금 출금[자금을 인출한다]
		public static final int C_OPCODE_USESKILL 										= 0x3B; // 스킬 사용 부분
//0x3C
		public static final int C_OPCODE_DOOR 											= 0x3D; // 문짝 클릭 부분
//0x3E
//0x3F
		public static final int C_OPCODE_JOINCLAN 										= 0x40; // [/가입]
//0x41
		public static final int C_OPCODE_SECURITYSTATUS 								= 0x42; // 성내 치안 관리[성내 치안을 관리한다]
		public static final int C_OPCODE_QUITGAME 										= 0x43; // v로그인창에서 겜 종료할때	
		public static final int C_OPCODE_FIX_WEAPON_LIST 								= 0x44; // 무기수리
		public static final int C_OPCODE_RANK 											= 0x45; // [/동맹]
		public static final int C_OPCODE_USEITEM 										= 0x46; // 아이템 사용 부분
//0x47
		public static final int C_OPCODE_TRADEADDITEM 									= 0x48; // 교환창에 아이템 추가
		public static final int C_OPCODE_RESTART_AFTER_DIE 								= 0x49; // 겜중에 죽어서 리셋 눌럿을때.
		public static final int C_OPCODE_PICKUPITEM 									= 0x4A; // 아이템 줍기
		public static final int C_OPCODE_ATTR 											= 0x4B; // [ Y , N ] 선택 부분
		public static final int C_OPCODE_WARTIMESET										= 0x4C; // 공성시간 지정
		public static final int C_OPCODE_SELECT_CHARACTER 								= 0x4D; // 리스창에서 케릭 선택
//0x4E
		public static final int C_OPCODE_ARROWATTACK 									= 0x4F; // 활공격 부분
		public static final int C_OPCODE_SECURITYSTATUSSET 								= 0x50; // 성내 치안 관리 설정하기
		public static final int C_OPCODE_EXTCOMMAND 									= 0x51; // <알트+1 ~ 5 까지 액션 >
//0x52
		public static final int C_OPCODE_ADDBUDDY 										= 0x53; // 친구추가
		public static final int C_OPCODE_SOLDIERGIVEOK									= 0x54; // 용병 주기승인
		public static final int C_OPCODE_RESTART 										= 0x55; // 겜중에 리스창으로 빠짐	
		public static final int C_OPCODE_ATTACK											= 0x56; // 일반공격 부분	
		public static final int C_OPCODE_SHOP 											= 0x57; // [/상점 -> OK]
//0x58
		public static final int C_OPCODE_HORUN 											= 0x59; // 호런 클릭
		public static final int C_OPCODE_CHECKPK 										= 0x5A; // [/checkpk]
//0x5B
//0x5C
		public static final int C_OPCODE_BOARDBACK 										= 0x5D; // 게시판 next
		public static final int C_OPCODE_SELECTTARGET 									= 0x5E; // 펫 공격 목표 지정
		public static final int C_OPCODE_CREATE_CHARACTER 								= 0x5F; // 케릭 생성	
//0x60
		public static final int C_OPCODE_EMBLEM											= 0x61; // 문장데이타를 서버에 요청함
		public static final int C_OPCODE_EXIT_GHOST 									= 0x62; // 무한대전 관람모드 탈출
//0x63
		public static final int C_OPCODE_CHATWHISPER 									= 0x64; // 귓속 채팅
		public static final int C_OPCODE_KEEPALIVE 										= 0x65; // 1분마다 한번씩 옴
		public static final int C_OPCODE_REPORT											= 0x66; // 불량 유저 신고(/신고)
		public static final int C_OPCODE_PRIVATESHOPLIST 								= 0x67; // 개인상점 buy, sell
		public static final int C_OPCODE_BANPARTY 										= 0x68; // 파티 추방
//0x69
		public static final int C_OPCODE_TELEPORT										= 0x6A; // 텔레포트 사용	
		public static final int C_OPCODE_CREATECLAN 									= 0x6B; // 혈맹 창설
//0x6C
		public static final int C_OPCODE_CHAT 											= 0x6D; // 일반 채팅	
		public static final int C_OPCODE_FISHCLICK 										= 0x6E; // 낚시 입질 클릭
		public static final int C_OPCODE_LOGINTOSERVEROK 								= 0x6F; // [환경설정->전챗켬,끔]	
//0x70
//0x71
		public static final int C_OPCODE_DELETECHAR 									= 0x72; // 케릭터 삭제
		public static final int C_OPCODE_LEAVEPARTY 									= 0x73; // 파티 탈퇴
		public static final int C_OPCODE_GIVEITEM 										= 0x74; // 강제로 아이템 주기
//0x75
		public static final int C_OPCODE_DEPOSIT 										= 0x76; // 성 공금 입금
		public static final int C_OPCODE_BOARDREAD 										= 0x77; // 게시판 읽기
		public static final int C_OPCODE_DELBUDDY 										= 0x78; // 친구삭제
		public static final int C_OPCODE_LOGINPACKET 									= 0x79; //v 계정정보가 담긴 패킷.
		public static final int C_OPCODE_MAIL 											= 0x7A; // 편지함 클릭후 혈맹편지 왔다갔다
		public static final int C_OPCODE_DELETEINVENTORYITEM 							= 0x7B; // 휴지통에 아이템 삭제
//0x7C
		public static final int C_OPCODE_SKILLBUYOK 									= 0x7D; // 스킬 구입 OK	
//0x7E
		public static final int C_OPCODE_NPCACTION 										= 0x7F; // Npc 대화 액션 부분
//0x80
//0x81
		public static final int C_OPCODE_CREATEPARTY 									= 0x82; // 파티 초대
		public static final int C_OPCODE_CHATPARTY 										= 0x83; // 채팅 파티 리스트


	

		/** Client 안쓰는 것들 */
			
		public static final int C_OPCODE_HOTEL_ENTER 									= 0x1001; // 엔피씨로 여관 진입시 	
		
				
		
		
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
		/**	* * * * * * * * * * * * * * * * Server Packet * * * * * * * * * * * * * * * * * * * * * * **/
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
	

		
		public static final int S_OPCODE_EMBLEM											= 0x00; // 클라에 혈문장 요청
//0x01 팅
		public static final int S_OPCODE_PARALYSIS 										= 0x02; // 행동 제한 (커스패럴 상태)
		public static final int S_OPCODE_ADDITEM 										= 0x03; // 아이템 생성[아이템 떨궜다가먹기]	
		public static final int S_OPCODE_INVLIST 										= 0x04; // 인벤토리의 아이템리스트
//0x05
		public static final int S_OPCODE_INPUTAMOUNT 									= 0x06; // 수량성 아이템 제작 갯수
		public static final int S_OPCODE_TRUETARGET 									= 0x07; // 트루타겟
//0x08 팅
		public static final int S_OPCODE_MPUPDATE 										= 0x09; // MP 업데이트
//0x0A
//0x0B 팅
		public static final int S_OPCODE_INVIS 											= 0x0C; // 투명 처리 부분
		public static final int S_OPCODE_DELETEINVENTORYITEM 							= 0x0D; // 인벤토리 아이템 삭제
		public static final int S_OPCODE_OWNCHARATTRDEF									= 0x0E; // AC 및 속성방어 갱신
		public static final int S_OPCODE_SERVERMSG 										= 0x0F; // 서버 메세지[방어구중복으로체크]
//0x10 팅
//0x11
		public static final int S_OPCODE_YES_NO											= 0x12; // [ Y , N ] 메세지
		public static final int S_OPCODE_IDENTIFYDESC 									= 0x13; // 확인주문서
		public static final int S_OPCODE_MSG 											= 0x14; // 시스템 메세지 (전챗)
		public static final int S_OPCODE_DOACTIONGFX 									= 0x15; // 액션 부분(맞는모습등)
//0x16 팅
		public static final int S_OPCODE_HORUN		 									= 0x17; // 호런
//0x18 팅
		public static final int S_OPCODE_SOLDIERBUYLIST									= 0x19; // 성 용병 구입 리스트창
		public static final int S_OPCODE_DEPOSIT 										= 0x1A; // 공금 입금
		public static final int S_OPCODE_NOTICE											= 0x1B; // 공지
		public static final int S_OPCODE_SOLDIERGIVE									= 0x1C; // 선택한 용병 주기
		public static final int S_OPCODE_SHOWSHOPBUYLIST 								= 0x1D; // 상점 구입 부분
		public static final int S_OPCODE_BOARD 											= 0x1E; // 게시판
		public static final int S_OPCODE_TRADESTATUS 									= 0x1F; // 거래 취소, 완료
		public static final int S_OPCODE_REMOVE_OBJECT 									= 0x20; // 오브젝트 삭제 (토글etc)
		public static final int S_OPCODE_SHOWHTML 										= 0x21; // Npc클릭 Html열람
		public static final int S_OPCODE_CHANGEHEADING 									= 0x22; // 방향 전환 부분
		public static final int S_OPCODE_SKILLICONSHIELD 								= 0x23; // 쉴드
//0x24 팅
		public static final int S_OPCODE_WAR 											= 0x25; // 전쟁
		public static final int S_OPCODE_LOGINRESULT 									= 0x26; // 로그인 처리의대한 답변
		public static final int S_OPCODE_LETTER											= 0x27; // 편지 읽기
		public static final int S_OPCODE_HOUSEMAP 										= 0x28; // 아지트 맵
		public static final int S_OPCODE_ITEMCOLOR 										= 0x29; // 봉인 주문서
		public static final int S_OPCODE_SHORTOFMATERIAL								= 0x2A; // 재료부족으로 마법 미습득
		public static final int S_OPCODE_WHISPERCHAT 									= 0x2B; // 귓속말
		public static final int S_OPCODE_BLUEMESSAGE									= 0x2C; // 피케이 횟수 메시지[REDMESSAGE]		
//0x2D
		public static final int S_OPCODE_STRUP 											= 0x2E; // 마법 힘업
		public static final int S_OPCODE_SKILLBRAVE 									= 0x2F; // 용기
//0x30 팅
		public static final int S_OPCODE_WEATHER 										= 0x31; // 날씨 조작하기
		public static final int S_OPCODE_RESURRECTION									= 0x32; // 부활 처리 부분
		public static final int S_OPCODE_DELSKILL 										= 0x33; // 스킬 삭제 (정령력 제거)
		public static final int S_OPCODE_SHOWOBJ 										= 0x34; // 오브젝트 그리기
		public static final int S_OPCODE_DISCONNECT										= 0x35; // 해당 케릭 강제 종료
		public static final int S_OPCODE_SKILLSOUNDGFX 									= 0x36; // 이팩트 부분 (헤이스트등)
//0x37 팅
		public static final int S_OPCODE_SOUND 											= 0x38; // 사운드 이팩트 부분
		public static final int S_OPCODE_SKILLHASTE 									= 0x39; // 헤이스트
		public static final int S_OPCODE_ABILITY 										= 0x3A; // 이반, 소반  인프라 사용
//0x3B 텔?
		public static final int S_OPCODE_SERVERVERSION 									= 0x3C; //v 서버 버전
		public static final int S_OPCODE_NEWCHARPACK 									= 0x3D; // 케릭 새로 만든거 보내기
		public static final int S_OPCODE_NPCSHOUT 										= 0x3E; // 샤우팅 글
		public static final int S_OPCODE_TRADEADDITEM 									= 0x3F; // 거래창 아이템 추가 부분
		public static final int S_OPCODE_GAMETIME 										= 0x40; // 게임 시간
		public static final int S_OPCODE_CHANGENAME 									= 0x41; // 오브젝트 네임변경시
		public static final int S_OPCODE_USEMAP											= 0x42; // 지도사용	
//0x43 팅
		public static final int S_OPCODE_SELECTLIST 									= 0x44; // 무기수리
		public static final int S_OPCODE_ADDSKILL 										= 0x45; // 스킬 추가[버프패킷박스 다음]
		public static final int S_OPCODE_CURSEBLIND 									= 0x46; // 눈멀기 효과
		public static final int S_OPCODE_PRIVATESHOPLIST								= 0x47; // 개인상점 물품 열람
		public static final int S_OPCODE_SKILLBUY 										= 0x48; // 스킬 구입 창
		public static final int S_OPCODE_PACKETBOX 										= 0x49; //v 통합 패킷 관리 담당
		public static final int S_OPCODE_DRAWAL											= 0x4A; // 공금 출금
		public static final int S_OPCODE_SOLDIERGIVELIST								= 0x4B; // 용병 주는 선택 리스트
		public static final int S_OPCODE_EXP 											= 0x4C; // 경험치 갱신
		public static final int S_OPCODE_CHARAMOUNT 									= 0x4D; //v 해당 계정의 케릭 갯수
		public static final int S_OPCODE_LIQUOR											= 0x4E; // 술
		public static final int S_OPCODE_DRAGONPERL										= 0x4E; // 드래곤진주
		public static final int S_OPCODE_SHOWSHOPSELLLIST 								= 0x4F; // 상점에 판매 부분
		public static final int S_OPCODE_PETGUI 										= 0x50; //v 스텟 초기화 길이
		public static final int S_OPCODE_RETURNEDSTAT 									= 0x50; //v 스텟 초기화 길이
		public static final int S_OPCODE_CHARLIST 										= 0x51; //v 케릭터리스트의 케릭정보
		public static final int S_OPCODE_HPMETER 										= 0x52; // 미니 HP표현 부분
		public static final int S_OPCODE_TAXRATE 										= 0x53; // 세율 조정
		public static final int S_OPCODE_SPMR 											= 0x54; // sp와 mr변경/
		public static final int S_OPCODE_BOARDREAD 										= 0x55; // 게시판 읽기
		public static final int S_OPCODE_POISON											= 0x56; // 독과 굳은 상태 표현
		public static final int S_OPCODE_LIGHT 											= 0x57; // 밝기
//0x58 멈춤
//0x59 리스창으로
		public static final int S_OPCODE_OWNCHARSTATUS2 								= 0x5A; // 스테이터스 갱신(디크리즈,민투)
		public static final int S_OPCODE_MAPID 											= 0x5B; // 맵 아이디
		public static final int S_OPCODE_MOVEOBJECT 									= 0x5C; // 이동 오브젝트
//0x5D 팅
		public static final int S_OPCODE_ATTACKPACKET 									= 0x5E; // 공격 표현 부분
//0x5F 팅
		public static final int S_OPCODE_HOUSELIST 										= 0x60; // 아지트 리스트
		public static final int S_OPCODE_ITEMNAME 										= 0x61; // 아이템 착용 (E표시)
		public static final int S_OPCODE_TRADE 											= 0x62; // 거래창 부분
		public static final int S_OPCODE_PINKNAME										= 0x63; // 보라돌이
		public static final int S_OPCODE_DETELECHAROK 									= 0x64; // 케릭 삭제
		public static final int S_OPCODE_NEWCHARWRONG 									= 0x65; // 캐릭터 생성시 처리부분
		public static final int S_OPCODE_DEXUP 											= 0x66; // 덱업
//0x67
//0x68
		public static final int S_OPCODE_CASTLEMASTER 									= 0x69; // 성소유목록 세팅
		public static final int S_OPCODE_RANGESKILLS 									= 0x6A; // 파톰 어퀘등의 스킬
//0x6B
		public static final int S_OPCODE_BOOKMARKS 										= 0x6C; // 기억 리스트
		public static final int S_OPCODE_LAWFUL											= 0x6D; // 라우풀
		public static final int S_OPCODE_EFFECTLOCATION									= 0x6E; // 트랩 (좌표위 이펙트)
		public static final int S_OPCODE_BLESSOFEVA 									= 0x6F; // 에바 아이콘
//0x70
		public static final int S_OPCODE_CHARTITLE 										= 0x71; // 호칭 변경
		public static final int S_OPCODE_REFRESH_CLAN 									= 0x71; // 혈원수 변동이 있을때 오는 패킷
		public static final int S_OPCODE_OWNCHARSTATUS 									= 0x72; // 케릭 정보 갱신
		public static final int S_OPCODE_ITEMSTATUS 									= 0x73; // 인벤 아이템 갱신
		public static final int S_OPCODE_ITEMAMOUNT 									= 0x73; // 인벤내 아이템 수량 정보 바꾸기(흑단쓰면 ITEMSTATUS와 같이옴)
		public static final int S_OPCODE_WARTIME										= 0x74; // 공성 시간 변경
		public static final int S_OPCODE_UNKNOWN1 										= 0x75; // 접속담당
		public static final int S_OPCODE_POLY 											= 0x76; // 변신
//0x77
		public static final int S_OPCODE_NORMALCHAT 									= 0x78; // 일반 채팅
		public static final int S_OPCODE_CHARVISUALUPDATE 								= 0x79; // 무기 착,탈 부분
		public static final int S_OPCODE_ATTRIBUTE 										= 0x7A; // 위치값을 이동가능&불가능 조작 부분
		public static final int S_OPCODE_SHOWRETRIEVELIST 								= 0x7B; // 창고 리스트
//0x7C
		public static final int S_OPCODE_HPUPDATE 										= 0x7D; // HP 업데이트
		public static final int S_OPCODE_SELECTTARGET 									= 0x7E; // 펫 공격 목표지정
//0x7F

		
		
		/** Server Packet 안쓰는 것들 **/
		
		public static final int S_OPCODE_ALLIANCECHAT 									= 0x1000; // 동맹채팅
		public static final int S_OPCODE_TELEPORT										= 0x1001; // 텔레포트
		public static final int S_OPCODE_HOTELENTER										= 0x1002; // 엔피씨로 여관 진입시>>
		
		
		/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
			
}