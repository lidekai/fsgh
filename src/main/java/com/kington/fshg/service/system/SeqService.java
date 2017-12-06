package com.kington.fshg.service.system;

/**
 * 序列号管理类
 * @author lijialin
 *
 */
public interface SeqService {
	
	/**
	 * 根据条件产生最新的序列号
	 * @param table 表名
	 * @param pix  前缀
	 * @param len 流水号长度，为空时默认为4位
	 * @return
	 */
	public String makeNo(String table,String pix,Integer len) throws Exception;

}
