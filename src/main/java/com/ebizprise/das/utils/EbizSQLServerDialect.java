package com.ebizprise.das.utils;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * 
 * 注册nvarchar类型映射
 */

public class EbizSQLServerDialect extends SQLServerDialect {

	public EbizSQLServerDialect() {

		super(); // 调用父类的构造方法（super()一定要放在方法的首个语句）

		registerColumnType(Types.CHAR, "nchar(1)");
		registerColumnType(Types.LONGVARCHAR, "nvarchar(max)");
		registerColumnType(Types.VARCHAR, 4000, "nvarchar($l)");
		registerColumnType(Types.VARCHAR, "nvarchar(max)");
		registerColumnType(Types.CLOB, "nvarchar(max)");

		registerColumnType(Types.DECIMAL, "decimal");

		registerColumnType(Types.NCHAR, "nchar(1)");
		registerColumnType(Types.LONGNVARCHAR, "nvarchar(max)");
		registerColumnType(Types.NVARCHAR, 4000, "nvarchar($l)");
		registerColumnType(Types.NVARCHAR, "nvarchar(max)");
		registerColumnType(Types.NCLOB, "nvarchar(max)");

		registerHibernateType(Types.NCHAR,
				StandardBasicTypes.CHARACTER.getName());
		registerHibernateType(Types.LONGNVARCHAR,
				StandardBasicTypes.TEXT.getName());
		registerHibernateType(Types.NVARCHAR,
				StandardBasicTypes.STRING.getName());
		registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());

	}

}
