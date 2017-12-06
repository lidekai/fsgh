package com.kington.fshg.model.sale;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class SaleOrderU8DetailVO extends VOSupport {

	private static final long serialVersionUID = -442174385471238060L;
	
	private String cInvCode;//存货编码(对应U8存货编码 cInvCode)
	private String hh;//货号
	private String chmc;//存货名称
	private Long productId;//存货Id
	private String xjpfl;//新旧品分类(对应U8表体表体自定义项29 cDefine29 )
	private Float khsph;//客户商品号(对应U8表体自定义项26 cDefine26 )
	private String dfm;//对方名（对应U8表体自定义项33  cDefine33 ）
	private String ggxh;//规格型号
	private String zjl;//主计量
	private String cSAComUnitCode;//辅计量单位编码
	private Double js;//件数
	private Double sl;//数量（对应U8数量   iQuantity ）
	private Double zhl;//转换率
	private Double bj;//报价 （对应U8报价 iQuotedPrice ）
	private Double zqwshj;//折前无税合计（对应U8表体自定义项27 cDefine27 ）
	private Double hsdj;//含税单价（对应U8表体 原币含税单价 iTaxUnitPrice ）
	private Double tax;//税率（对应U8表体 税率   iTaxRate  ） 
	private Double wsdj;//无税单价（对应U8表体 原币无税单价    iUnitPrice   ） 
	private Double jshj;//价税合计（对应U8表体 原币价税合计    iSum   ） 
	private Double wsje;//无税金额（对应U8表体 原币无税金额   iMoney   ） 
	private Double zke;//折扣额（对应U8表体 原币折扣额    iDisCount   ） 
	private Double kl;//扣率（对应U8表体 扣率   KL  ） 
	private Double kl2;//扣率2（对应U8表体 二次扣率  KL2  ） 
	private String chyt;//存货用途（对应U8存货自由项2  cFree2 ）
	private Date scrq;//生产日期（对应U8表体自定义项36   cDefine36 ）
	private String bz;//备注（对应U8表体自定义项28   cDefine28 ）
	private SaleOrderU8VO vo;//所属表头

	public String getCInvCode() {
		return cInvCode;
	}

	public void setCInvCode(String cInvCode) {
		this.cInvCode = cInvCode;
	}

	public String getXjpfl() {
		return xjpfl;
	}

	public void setXjpfl(String xjpfl) {
		this.xjpfl = xjpfl;
	}

	public String getDfm() {
		return dfm;
	}

	public void setDfm(String dfm) {
		this.dfm = dfm;
	}

	public Double getJs() {
		return js;
	}

	public void setJs(Double js) {
		this.js = js;
	}

	public Double getSl() {
		return sl;
	}

	public void setSl(Double sl) {
		this.sl = sl;
	}

	public Double getBj() {
		return bj;
	}

	public void setBj(Double bj) {
		this.bj = bj;
	}

	public Double getZqwshj() {
		return zqwshj;
	}

	public void setZqwshj(Double zqwshj) {
		this.zqwshj = zqwshj;
	}

	public Double getHsdj() {
		return hsdj;
	}

	public void setHsdj(Double hsdj) {
		this.hsdj = hsdj;
	}

	public Double getWsdj() {
		return wsdj;
	}

	public void setWsdj(Double wsdj) {
		this.wsdj = wsdj;
	}

	public Double getJshj() {
		return jshj;
	}

	public void setJshj(Double jshj) {
		this.jshj = jshj;
	}

	public Double getWsje() {
		return wsje;
	}

	public void setWsje(Double wsje) {
		this.wsje = wsje;
	}

	public Double getZke() {
		return zke;
	}

	public void setZke(Double zke) {
		this.zke = zke;
	}

	public Double getKl() {
		return kl;
	}

	public void setKl(Double kl) {
		this.kl = kl;
	}

	public Double getKl2() {
		return kl2;
	}

	public void setKl2(Double kl2) {
		this.kl2 = kl2;
	}

	public String getChyt() {
		return chyt;
	}

	public void setChyt(String chyt) {
		this.chyt = chyt;
	}

	public Date getScrq() {
		return scrq;
	}

	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getChmc() {
		return chmc;
	}

	public void setChmc(String chmc) {
		this.chmc = chmc;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getZjl() {
		return zjl;
	}

	public void setZjl(String zjl) {
		this.zjl = zjl;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public SaleOrderU8VO getVo() {
		return vo;
	}

	public void setVo(SaleOrderU8VO vo) {
		this.vo = vo;
	}

	public Double getZhl() {
		return zhl;
	}

	public void setZhl(Double zhl) {
		this.zhl = zhl;
	}

	public String getcSAComUnitCode() {
		return cSAComUnitCode;
	}

	public void setcSAComUnitCode(String cSAComUnitCode) {
		this.cSAComUnitCode = cSAComUnitCode;
	}

	public Float getKhsph() {
		return khsph;
	}

	public void setKhsph(Float khsph) {
		this.khsph = khsph;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				SaleOrderU8DetailVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
