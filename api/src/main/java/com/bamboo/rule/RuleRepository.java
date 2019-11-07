package com.bamboo.rule;

import com.bamboo.rule.exception.RuleException;

public interface RuleRepository<R extends CommonRule> {

  /**
   * insert or update rule
   */
  boolean upsert(R rule) throws RuleException;

  CommonRules<R> getRuleByType(String ruleType) throws RuleException;

}
