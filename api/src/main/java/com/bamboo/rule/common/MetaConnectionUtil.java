package com.bamboo.rule.common;

import com.bamboo.rule.exception.RuleException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.configuration.Configuration;

/**
 * A Util to get metastore jdbc connection (postgres)
 */
public class MetaConnectionUtil {

  private MetaConnectionUtil() {
  }

  public static Connection getConnection() throws RuleException, SQLException {
    Configuration psqlConf = RuleProperties.getSubsetConfiguration(CommonConstant.PSQL_PREFIX);

    String url = String.format(CommonConstant.POSTGRESQL_JDBC_URL_TEMPLATE,
        psqlConf.getString(CommonConstant.HOST_KEY),
        psqlConf.getString(CommonConstant.PORT_KEY),
        psqlConf.getString(CommonConstant.DATABASE_KEY),
        psqlConf.getString(CommonConstant.USER_KEY),
        psqlConf.getString(CommonConstant.PASSWORD_KEY));

    try {
      Class.forName(CommonConstant.POSTGRESQL_JDBC_DRIVER_CLASS_NAME);
    } catch (ClassNotFoundException e) {
      throw new RuleException(e);
    }
    return DriverManager.getConnection(url);
  }

}
