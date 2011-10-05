/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package bone.server.server.clientpackets;

import server.LineageClient;
import bone.server.server.model.L1Teleport;
import bone.server.server.model.Instance.L1PcInstance;
import bone.server.server.serverpackets.S_Disconnect;
import bone.server.server.serverpackets.S_HPUpdate;
import bone.server.server.serverpackets.S_MPUpdate;
import bone.server.server.serverpackets.S_OwnCharStatus;
import bone.server.server.serverpackets.S_ReturnedStat;
import bone.server.server.serverpackets.S_OwnCharAttrDef;
import bone.server.server.serverpackets.S_SPMR;
import bone.server.server.utils.CalcStat;

public class C_ReturnStaus extends ClientBasePacket {
	public C_ReturnStaus(byte[] decrypt, LineageClient client) {
		super(decrypt);
        int type = readC();
		L1PcInstance pc = client.getActiveChar();
        if(type == 1) {
    		short init_hp = 0, init_mp = 0;
    		
            int str = readC();
            int intel = readC();
            int wis = readC();
            int dex = readC();
            int con = readC();
            int cha = readC();
    		int total = 0;
    		total = str+dex+con+wis+cha+intel;
    		
    		if(!pc.getAbility().isNormalAbility(pc.getClassId(), pc.getLevel(), pc.getHighLevel(), total)) {
    			pc.sendPackets(new S_Disconnect());
    			return;
    		}
                       
            pc.getAbility().reset();

            pc.getAbility().setBaseStr((byte)str);
            pc.getAbility().setBaseInt((byte)intel);
            pc.getAbility().setBaseWis((byte)wis);
            pc.getAbility().setBaseDex((byte)dex);
            pc.getAbility().setBaseCon((byte)con);
            pc.getAbility().setBaseCha((byte)cha);
            
			pc.setLevel(1);
			
    		if (pc.isCrown()) { // CROWN
    			init_hp = 14;
    			switch (pc.getAbility().getBaseWis()) {
    			case 11:
    				init_mp = 2;
    				break;
    			case 12:
    			case 13:
    			case 14:
    			case 15:
    				init_mp = 3;
    				break;
    			case 16:
    			case 17:
    			case 18:
    				init_mp = 4;
    				break;
    			default:
    				init_mp = 2;
    				break;
    			}
    		} else if (pc.isKnight()) { // KNIGHT
    			init_hp = 16;
    			switch (pc.getAbility().getBaseWis()) {
    			case 9:
    			case 10:
    			case 11:
    				init_mp = 1;
    				break;
    			case 12:
    			case 13:
    				init_mp = 2;
    				break;
    			default:
    				init_mp = 1;
    				break;
    			}
    		} else if (pc.isElf()) { // ELF
    			init_hp = 15;
    			switch (pc.getAbility().getBaseWis()) {
    			case 12:
    			case 13:
    			case 14:
    			case 15:
    				init_mp = 4;
    				break;
    			case 16:
    			case 17:
    			case 18:
    				init_mp = 6;
    				break;
    			default:
    				init_mp = 4;
    				break;
    			}
    		} else if (pc.isWizard()) { // WIZ
    			init_hp = 12;
    			switch (pc.getAbility().getBaseWis()) {
    			case 12:
    			case 13:
    			case 14:
    			case 15:
    				init_mp = 6;
    				break;
    			case 16:
    			case 17:
    			case 18:
    				init_mp = 8;
    				break;
    			default:
    				init_mp = 6;
    				break;
    			}
    		} else if (pc.isDarkelf()) { // DE
    			init_hp = 12;
    			switch (pc.getAbility().getBaseWis()) {
    			case 10:
    			case 11:
    				init_mp = 3;
    				break;
    			case 12:
    			case 13:
    			case 14:
    			case 15:
    				init_mp = 4;
    				break;
    			case 16:
    			case 17:
    			case 18:
    				init_mp = 6;
    				break;
    			default:
    				init_mp = 3;
    				break;
    			}
    		} else if (pc.isDragonknight()) { // 용기사
    			init_hp = 16;
    			init_mp = 2;
    		} else if (pc.isIllusionist()) { // 환술사
    			init_hp = 14;
    			switch (pc.getAbility().getBaseWis()) {
    			case 10:
    			case 11:
    			case 12:
    			case 13:
    			case 14:
    			case 15:
    				init_mp = 5;
    				break;
    			case 16:
    			case 17:
    			case 18:
    				init_mp = 6;
    				break;
    			default:
    				init_mp = 5;
    				break;
    			}
    		}
            pc.addBaseMaxHp((short) (init_hp - pc.getBaseMaxHp()));
            pc.addBaseMaxMp((short) (init_mp - pc.getBaseMaxMp()));
            pc.getAC().setAc(10);
    		pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
    		pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP));
        } else if(type == 2) {
            int levelup = readC();
            switch(levelup){
            case 0: statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 1: pc.getAbility().addStr((byte) 1); statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 2: pc.getAbility().addInt((byte) 1); statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 3: pc.getAbility().addWis((byte) 1); statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 4: pc.getAbility().addDex((byte) 1); statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 5: pc.getAbility().addCon((byte) 1); statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 6: pc.getAbility().addCha((byte) 1); statup(pc); pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP)); break;
            case 7: 
            	if(pc.getLevel()+10 < pc.getHighLevel()){
                    for(int m = 0; m < 10; m++)
                    	statup(pc);
            		pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LEVELUP));
                }
            	break;
            case 8:
                int statusup = readC();
                switch(statusup){
                case 1: pc.getAbility().addStr((byte) 1); break;
                case 2: pc.getAbility().addInt((byte) 1); break;
                case 3: pc.getAbility().addWis((byte) 1); break;
                case 4: pc.getAbility().addDex((byte) 1); break;
                case 5: pc.getAbility().addCon((byte) 1); break;
                case 6: pc.getAbility().addCha((byte) 1); break;
                }
                if(pc.getAbility().getElixirCount() > 0) {
            		pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.END));
                } else {
                	try {
                		if(pc.getLevel() >= 51)	pc.getAbility().setBonusAbility(pc.getLevel() - 50);
                		else					pc.getAbility().setBonusAbility(0);
                		
                		pc.setExp(pc.getReturnStat());
                		pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.END));
                		pc.sendPackets(new S_OwnCharStatus(pc));
                		pc.sendPackets(new S_OwnCharAttrDef(pc));
                		pc.sendPackets(new S_SPMR(pc));
	                    pc.setCurrentHp(pc.getMaxHp());
	                    pc.setCurrentMp(pc.getMaxHp());
	                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
	                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
	                    L1Teleport.teleport(pc, 32612, 32734, (short)4, 5, true);
	                    pc.setReturnStat(0);
	                    pc.save();
	                    pc.CheckStatus();
                	} catch (Exception exception) {}
                }
                break;
            }
        } else if(type == 3) { // 스텟 초기화시 엘릭서 처리
        	try{
            int str = readC();
            int intel = readC();
            int wis = readC();
            int dex = readC();
            int con = readC();
            int cha = readC();
            
            pc.getAbility().addStr((byte) (str - pc.getAbility().getStr()));
            pc.getAbility().addInt((byte) (intel - pc.getAbility().getInt()));
            pc.getAbility().addWis((byte) (wis - pc.getAbility().getWis()));
            pc.getAbility().addDex((byte) (dex - pc.getAbility().getDex()));
            pc.getAbility().addCon((byte) (con - pc.getAbility().getCon()));
            pc.getAbility().addCha((byte) (cha - pc.getAbility().getCha()));
    		
            if(pc.getLevel() >= 51)	pc.getAbility().setBonusAbility(pc.getLevel() - 50);
    		else					pc.getAbility().setBonusAbility(0);
    		
    		pc.setExp(pc.getReturnStat());
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharAttrDef(pc));
            pc.setCurrentHp(pc.getMaxHp());
            pc.setCurrentMp(pc.getMaxHp());
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
			pc.sendPackets(new S_ReturnedStat(pc, 4));
			L1Teleport.teleport(pc, 32612, 32734, (short)4, 5, true);			
			pc.setReturnStat(0);
			pc.save();
			pc.CheckStatus();
        	} catch (Exception exception) {}
        }
    }
	
	public void statup(L1PcInstance pc){
        int Stathp = 0;
        int Statmp = 0;
        pc.setLevel(pc.getLevel() + 1);
        Stathp = CalcStat.calcStatHp(pc.getType(), pc.getBaseMaxHp(), pc.getAbility().getCon());
        Statmp = CalcStat.calcStatMp(pc.getType(), pc.getBaseMaxMp(), pc.getAbility().getWis());
        pc.resetBaseAc();
        pc.getAC().setAc(pc.getBaseAc());
        pc.addBaseMaxHp((short) Stathp);
        pc.addBaseMaxMp((short) Statmp);
	}
}