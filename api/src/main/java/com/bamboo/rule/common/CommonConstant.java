package com.bamboo.rule.common;

public class CommonConstant {

  // postgres
  public final static String POSTGRESQL_JDBC_DRIVER_CLASS_NAME = "org.postgresql.Driver";
  public final static String POSTGRESQL_JDBC_URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s?user=%s&password=%s";
  public final static String PSQL_PREFIX = "psql";
  public final static String HOST_KEY = "host";
  public final static String PORT_KEY = "port";
  public final static String DATABASE_KEY = "database";
  public final static String USER_KEY = "user";
  public final static String PASSWORD_KEY = "password";

  // rule engine
  public static final String RULE_ENGINE_KEY = "rule.engine";

  private CommonConstant() {
  }

}
