package com.bamboo.rule.drool;

import com.bamboo.rule.CommonRules;
import com.bamboo.rule.RuleRepository;
import com.bamboo.rule.common.MetaConnectionUtil;
import com.bamboo.rule.exception.RuleException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DroolRulePostgresRepository implements RuleRepository<DroolRule> {

  private final String INSERT_SQL =
      "INSERT INTO drool_rule(name, priority, type, content) VALUES (?, ?, ?, ?) "
          + "ON CONFLICT(name) DO UPDATE "
          + "SET type = EXCLUDED.type, priority = EXCLUDED.priority, content = EXCLUDED.content";

  private final String SELECT_BY_TYPE_SQL = "SELECT name, priority, content from easy_rule where type = ?";

  private Connection connection;
  private PreparedStatement insertStmt;
  private PreparedStatement selectStmt;


  public DroolRulePostgresRepository() throws RuleException {
    try {
      this.connection = MetaConnectionUtil.getConnection();
      this.insertStmt = connection.prepareStatement(INSERT_SQL);
      this.selectStmt = connection.prepareStatement(SELECT_BY_TYPE_SQL);
    } catch (Exception e) {
      throw new RuleException(e);
    }
  }

  @Override
  public boolean upsert(DroolRule rule) throws RuleException {
    try {
      insertStmt.setString(1, rule.getName());
      insertStmt.setInt(2, rule.getPriority());
      insertStmt.setString(3, rule.getRuleType());
      insertStmt.setString(4, rule.getContent());
      insertStmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuleException(e);
    }
  }

  @Override
  public CommonRules<DroolRule> getRuleByType(String ruleType)
      throws RuleException {
    try {
      selectStmt.setString(1, ruleType);
      ResultSet rs = selectStmt.executeQuery();

      CommonRules<DroolRule> rules = new CommonRules<>();
      while (rs.next()) {
        String name = rs.getString("name");
        int priority = rs.getInt("priority");
        String content = rs.getString("content");

        rules.register(new DroolRule(name, priority, ruleType, content));
      }

      return rules;
    } catch (SQLException e) {
      throw new RuleException(e);
    }
  }

  public void close() {
    try {
      if (insertStmt != null) {
        insertStmt.close();
      }

      if (selectStmt != null) {
        selectStmt.close();
      }

      if (connection != null) {
        connection.close();
      }

    } catch (SQLException e) {
      // ignore
    }
  }
}
