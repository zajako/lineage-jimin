package server;

import java.io.IOException;
import java.util.logging.Logger;

import bone.server.Config;
import bone.server.server.Account;
import bone.server.server.AccountAlreadyLoginException;
import bone.server.server.GameServerFullException;
import bone.server.server.clientpackets.C_AuthLogin;
import bone.server.server.clientpackets.C_NoticeClick;
import bone.server.server.serverpackets.S_LoginResult;
import bone.server.server.serverpackets.S_Notice;

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
			_log.info("������ IP�� �ߺ� �α����� �ź��߽��ϴ�. account=" + accountName + " host=" + host);
			client.sendPacket(new S_Notice("������ IP�� �ߺ� �α����� �ź��߽��ϴ�."));
			disconnect(client);
			return;
		}

		//Account account = loadAccountInfoFromDB(accountName);
		
		Account account = Account.load(accountName);
		if (account == null) {			
			if (Config.AUTO_CREATE_ACCOUNTS) {
				if(Account.checkLoginIP(ip)) {
					client.sendPacket(new S_Notice("���� IP�� ������ ������ 2�� �ֽ��ϴ�"));
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
			_log.info("BAN IP�� �α����� �ź��߽��ϴ�. account=" + accountName + " host=" + host);
			client.sendPacket(new S_LoginResult(S_LoginResult.REASON_BUG_WRONG));
			disconnect(client);
			return;
		}

		try {
			LoginController.getInstance().login(client, account);
			Account.updateLastActive(account); // ���� �α������� �����Ѵ�
			client.setAccount(account);
			sendNotice(client);
		} catch (GameServerFullException e) {
			disconnect(client);
			_log.info("���� �ο����� �ʰ��Ͽ����ϴ�. (" + client.getHostname() + ")�� ���� �õ��� ���� �����߽��ϴ�.");
			return;
		} catch (AccountAlreadyLoginException e) {
			_log.info("������ ������ �ߺ� �α����� �ź��߽��ϴ�. account=" + accountName + " host=" + host);
			client.sendPacket(new S_Notice("�̹� ���� �� �Դϴ�. ������ ���� �����մϴ�."));
			disconnect(client);
			return;
		}
	}

	private void sendNotice(LineageClient client) {
		String accountName = client.getAccountName();
		
		// �о���� ������ �ִ��� üũ
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
		/* �ڵ� ���� ������ IP �������� ���� ����, �ڵ� ������ ����
		if (account == null) {
			if (Config.AUTO_CREATE_ACCOUNTS) {
				
				if(Account.checkLoginIP(ip)) {   
					client.sendPacket(new S_CommonNews("���� IP�� ������ ������ 3�� �ֽ��ϴ�."));
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