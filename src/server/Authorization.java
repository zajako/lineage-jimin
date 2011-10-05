package server;

import java.io.IOException;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.AccountAlreadyLoginException;
import l1j.server.server.GameServerFullException;
import l1j.server.server.clientpackets.C_AuthLogin;
import l1j.server.server.clientpackets.C_NoticeClick;
import l1j.server.server.serverpackets.S_LoginResult;
import l1j.server.server.serverpackets.S_Notice;

public class Authorization {
	private static Authorization uniqueInstance = null;
	private static Logger _log = Logger.getLogger(C_AuthLogin.class.getName());
	
	public static Authorization getInstance() {
		if(uniqueInstance == null) {
			synchronized(Authorization.class) {
				if(uniqueInstance == null)
					uniqueInstance = new Authorization();
			}
		}
		
		return uniqueInstance;
	}
	
	private Authorization() {}
	
	public  void auth(LineageClient client, String accountName, String password, String ip, String host) throws IOException {
		if(checkDuplicatedIPConnection(ip)) {
			_log.info("동일한 IP의 중복 로그인을 거부했습니다. account=" + accountName + " host=" + host);
			client.sendPacket(new S_Notice("동일한 IP의 중복 로그인을 거부했습니다."));
			disconnect(client);
			return;
		}

		//Account account = loadAccountInfoFromDB(accountName);
		
		Account account = Account.load(accountName);
		if (account == null) {			
			if (Config.AUTO_CREATE_ACCOUNTS) {
				if(Account.checkLoginIP(ip)) {
					client.sendPacket(new S_Notice("동일 IP로 생성한 계정이 2개 있습니다"));
					try {
						Thread.sleep(1500);
						client.kick();
						client.close();
					} catch (Exception e1) {}
				}else{
					account = Account.create(accountName, password, ip, host);
					account = Account.load(accountName);
				}
			} else {
				_log.warning("account missing for user " + accountName);
			}
		}

		if (account == null || !account.validatePassword(accountName, password)) {
			int lfc = client.getLoginFailedCount();
			client.setLoginFailedCount(lfc + 1);
			if (lfc > 2) disconnect(client);
			else client.sendPacket(new S_LoginResult(26));
			return;
		}

		if (account.isBanned()) {
			_log.info("BAN IP의 로그인을 거부했습니다. account=" + accountName + " host=" + host);
			client.sendPacket(new S_LoginResult(S_LoginResult.REASON_BUG_WRONG));
			disconnect(client);
			return;
		}

		try {
			LoginController.getInstance().login(client, account);
			Account.updateLastActive(account); // 최종 로그인일을 갱신한다
			client.setAccount(account);
			sendNotice(client);
		} catch (GameServerFullException e) {
			disconnect(client);
			_log.info("접속 인원수를 초과하였습니다. (" + client.getHostname() + ")의 접속 시도를 강제 종료했습니다.");
			return;
		} catch (AccountAlreadyLoginException e) {
			_log.info("동일한 계정의 중복 로그인을 거부했습니다. account=" + accountName + " host=" + host);
			client.sendPacket(new S_Notice("이미 접속 중 입니다. 접속을 강제 종료합니다."));
			disconnect(client);
			return;
		}
	}

	private void sendNotice(LineageClient client) {
		String accountName = client.getAccountName();
		
		// 읽어야할 공지가 있는지 체크
		if(S_Notice.NoticeCount(accountName) > 0){
			client.sendPacket(new S_Notice(accountName, client));
		} else {
			client.setloginStatus(1);
			new C_NoticeClick(client);
		}
	}

	private void disconnect(LineageClient client) throws IOException {
		client.kick();
		client.close();
	}

	@SuppressWarnings("unused")
	private Account loadAccountInfoFromDB(String accountName) {
		/* 자동 계정 생성을 IP 기준으로 막고 있음, 자동 생성은 막음
		if (account == null) {
			if (Config.AUTO_CREATE_ACCOUNTS) {
				
				if(Account.checkLoginIP(ip)) {   
					client.sendPacket(new S_CommonNews("동일 IP로 생성한 계정이 3개 있습니다."));
					try {
						Thread.sleep(1500);
						client.kick();
						client.close();
					} catch (Exception e1) {}
					return;
				}else{
					account = Account.create(accountName, password, ip, host);
//					account = Account.load(accountName);
				}
			} else {
				_log.warning("account missing for user " + accountName);
			}
		}*/
		
		return Account.load(accountName);
	}

	private boolean checkDuplicatedIPConnection(String ip) {
		if (!Config.ALLOW_2PC) {
			return LoginController.getInstance().checkDuplicatedIP(ip);
		}
		return false;
	}

}
