package com.bamboo.rule.drool;

import com.bamboo.rule.CommonRule;
import java.util.Objects;

public class DroolRule extends CommonRule {

  private String name;
  private int priority;
  private String ruleType;
  private String content;

  public DroolRule(String name, int priority, String ruleType, String content) {
    this.name = name;
    this.priority = priority;
    this.ruleType = ruleType;
    this.content = content;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPriority() {
    return priority;
  }

  @Override
  public String getRuleType() {
    return ruleType;
  }

  public String getContent() {
    return content;
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
    DroolRule droolRule = (DroolRule) o;
    return priority == droolRule.priority &&
        Objects.equals(name, droolRule.name) &&
        ruleType.equals(droolRule.ruleType) &&
        Objects.equals(content, droolRule.content);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), name, priority, ruleType, content);
  }
}
