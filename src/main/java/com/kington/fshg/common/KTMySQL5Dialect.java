/**
 * 
 */
package com.kington.fshg.common;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class KTMySQL5Dialect extends org.hibernate.dialect.MySQL5Dialect {
	public KTMySQL5Dialect() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
		registerFunction("date_add", new SQLFunctionTemplate(Hibernate.DATE,
				"date_add(?1, INTERVAL ?2 ?3)"));
		registerFunction("convert", new SQLFunctionTemplate(org.hibernate.Hibernate.STRING, 
				"convert(?1 using ?2)") );
	}
}
