package com.kington.fshg.service.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jtframework.websupport.mvc.persist.PersistSupport;
import com.kington.fshg.model.system.Seq;

/**
 * 序列号管理类实现类
 * 
 * @author lijialin
 * 
 */
public class SeqServiceImpl extends PersistSupport implements SeqService {

	private static final long serialVersionUID = 8127514741103920412L;

	@Override
	public String makeNo(String table, String pix, Integer len)
			throws Exception {
		if (StringUtils.isBlank(table) || StringUtils.isBlank(pix))
			throw new Exception("参数无效");
		if (len == null)
			len = new Integer(3);

		Seq po = null;
		try {
			// 查询出当前系统的编码
			String sql = "FROM Seq o WHERE o.tableName=:tableName and o.prefixion=:prefixion ";
			Query query = em.createQuery(sql);
			query.setParameter("tableName", table);
			query.setParameter("prefixion", pix);
			List<Seq> list = query.getResultList();
			po = null;
			if (list == null || list.size() == 0) {
				po = add(table, pix);
			} else {
				po = list.get(0);
				po.setSeqNum(po.getSeqNum() + 1);
				po = this.merge(po);
			}
		} catch (Exception e) {
		}
		if (po == null) {
			throw new Exception("产生序列号时出现异常");
		} else {
			return pix + StringUtils.leftPad(po.getSeqNum().toString(), len, "0");
		}
	}

	private Seq add(String table, String pix) {
		Seq po = new Seq();
		po.setTableName(table);
		po.setPrefixion(pix);
		po.setSeqNum(new Integer(1));
		po.setCreateTime(new Date());
		po.setUpdateTime(new Date());
		po.setVersion(0);
		return this.merge(po);
	}

}
