package com.kington.fshg.model.budget;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ProjectType;
import com.kington.fshg.model.VOSupport;

public class ProvisionProjectVO extends VOSupport {
	private static final long serialVersionUID = 2676542375931317552L;

	private String feeName;
	private ProjectType projectType;
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				ProvisionProjectVO.class.getSimpleName()) : StringUtils.EMPTY ;
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
