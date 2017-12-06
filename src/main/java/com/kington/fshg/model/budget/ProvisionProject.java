package com.kington.fshg.model.budget;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ProjectType;
import com.kington.fshg.model.POSupport;

/**
 *	合同外费用预提项目信息表 
 *
 */
@Entity
@Table(name = "bud_provision_project")
public class ProvisionProject extends POSupport {
	private static final long serialVersionUID = 7436251761609943874L;

	@Column(length = 100)
	private String feeName;//费用名称
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ProjectType projectType;//预提项目类型
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				ProvisionProject.class.getSimpleName()) : StringUtils.EMPTY ;
	}


	public String getFeeName() {
		return feeName;
	}


	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}


	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

}
