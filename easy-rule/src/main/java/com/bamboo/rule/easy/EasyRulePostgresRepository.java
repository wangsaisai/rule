package com.bamboo.rule.easy;

import com.bamboo.rule.CommonRules;
import com.bamboo.rule.RuleRepository;
import com.bamboo.rule.exception.RuleException;
import com.bamboo.rule.common.MetaConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EasyRulePostgresRepository implements RuleRepository<EasyRule> {

  private final String INSERT_SQL =
      "INSERT INTO easy_rule(name, priority, type, description, condition, actions) VALUES (?, ?, ?, ?, ?, ?) "
          + "ON CONFLICT(name) DO UPDATE "
          + "SET priority = EXCLUDED.priority, type = EXCLUDED.type, description = EXCLUDED.description, condition = EXCLUDED.condition, actions = EXCLUDED.actions";

  private final String SELECT_BY_TYPE_SQL = "SELECT name, priority, description, condition, actions from easy_rule where type = ?";

  private Connection connection;
  private PreparedStatement insertStmt;
  private PreparedStatement selectStmt;


  public EasyRulePostgresRepository() throws RuleException {
    try {
      this.connection = MetaConnectionUtil.getConnection();
      this.insertStmt = connection.prepareStatement(INSERT_SQL);
      this.selectStmt = connection.prepareStatement(SELECT_BY_TYPE_SQL);
    } catch (Exception e) {
      throw new RuleException(e);
    }
  }

  @Override
  public boolean upsert(EasyRule rule) throws RuleException {
    try {
      insertStmt.setString(1, rule.getName());
      insertStmt.setInt(2, rule.getPriority());
      insertStmt.setString(3, rule.getRuleType());
      insertStmt.setString(4, rule.getDescription());
      insertStmt.setString(5, rule.getCondition());
      insertStmt.setString(6, rule.getActions());
      insertStmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuleException(e);
    }
  }

  @Override
  public CommonRules<EasyRule> getRuleByType(String ruleType)
      throws RuleException {
    try {
      selectStmt.setString(1, ruleType);
      ResultSet rs = selectStmt.executeQuery();

      CommonRules<EasyRule> rules = new CommonRules<>();
      while (rs.next()) {
        String name = rs.getString("name");
        int priority = rs.getInt("priority");
        String description = rs.getString("description");
        String condition = rs.getString("condition");
        String actions = rs.getString("actions");

        rules.register(new EasyRule(name, priority, ruleType, description, condition, actions));
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
