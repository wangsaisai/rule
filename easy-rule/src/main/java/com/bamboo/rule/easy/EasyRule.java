package com.bamboo.rule.easy;

import com.bamboo.rule.CommonRule;
import java.util.Objects;

public class EasyRule extends CommonRule {

  private String name;
  private int priority;
  private String ruleType;
  private String description;
  private String condition;
  private String actions;

  public EasyRule(String name, int priority, String ruleType, String description,
      String condition, String actions) {
    this.name = name;
    this.priority = priority;
    this.ruleType = ruleType;
    this.description = description;
    this.condition = condition;
    this.actions = actions;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPriority() {
    return priority;
  }

  public String getDescription() {
    return description;
  }

  public String getCondition() {
    return condition;
  }

  public String getActions() {
    return actions;
  }

  @Override
  public String getRuleType() {
    return ruleType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    EasyRule easyRule = (EasyRule) o;
    return priority == easyRule.priority &&
        Objects.equals(name, easyRule.name) &&
        ruleType.equals(easyRule.ruleType) &&
        Objects.equals(description, easyRule.description) &&
        Objects.equals(condition, easyRule.condition) &&
        Objects.equals(actions, easyRule.actions);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(super.hashCode(), name, priority, ruleType, description, condition, actions);
  }

  @Override
  public String toString() {
    return "EasyRule{" +
        "name='" + name + '\'' +
        ", priority=" + priority +
        ", ruleType=" + ruleType +
        ", description='" + description + '\'' +
        ", condition='" + condition + '\'' +
        ", actions='" + actions + '\'' +
        '}';
  }
}
