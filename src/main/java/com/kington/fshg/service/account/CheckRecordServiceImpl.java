package com.kington.fshg.service.account;

import javax.persistence.Query;

import com.kington.fshg.model.account.CheckRecord;
import com.kington.fshg.model.account.CheckRecordVO;
import com.kington.fshg.service.BaseServiceImpl;

public class CheckRecordServiceImpl extends
		BaseServiceImpl<CheckRecord, CheckRecordVO> implements
		CheckRecordService {
	private static final long serialVersionUID = 2225344256978107705L;

	@Override
	protected String getQueryStr(CheckRecordVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setQueryParm(Query query, CheckRecordVO vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void switchVO2PO(CheckRecordVO vo, CheckRecord po)
			throws Exception {
		if(vo.getReceiptBill() != null)
			po.setReceiptBill(vo.getReceiptBill());
		if(vo.getReceiveBill() != null)
			po.setReceiveBill(vo.getReceiveBill());
		
		if(vo.getReceivePrice() != null)
			po.setReceivePrice(vo.getReceivePrice());
		if(vo.getChargePrice() != null)
			po.setChargePrice(vo.getChargePrice());
		if(vo.getReturnPrice() != null)
			po.setReturnPrice(vo.getReturnPrice());
		if(vo.getHoldPrice() != null)
			po.setHoldPrice(vo.getHoldPrice());
		if(vo.getOtherPrice() != null)
			po.setOtherPrice(vo.getOtherPrice());
		if(vo.getActualPrice() != null)
			po.setActualPrice(vo.getActualPrice());
		
		
		if(vo.getReceivePriceOld() != null)
			po.setReceivePriceOld(vo.getReceivePriceOld());
		if(vo.getChargePriceOld() != null)
			po.setChargePriceOld(vo.getChargePriceOld());
		if(vo.getReturnPrice() != null)
			po.setReturnPriceOld(vo.getReturnPriceOld());
		if(vo.getHoldPriceOld() != null)
			po.setHoldPriceOld(vo.getHoldPriceOld());
		if(vo.getOtherPriceOld() != null)
			po.setOtherPrice(vo.getOtherPriceOld());
		if(vo.getActualPriceOld() != null)
			po.setActualPriceOld(vo.getActualPriceOld());
		
		if(vo.getReceiptCount() != null)
			po.setReceiptCount(vo.getReceiptCount());
		
		if(vo.getState() != null)
			po.setState(vo.getState());
	}


}
