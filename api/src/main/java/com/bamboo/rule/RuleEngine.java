package com.bamboo.rule;

import com.bamboo.rule.exception.RuleException;

public abstract class RuleEngine<R extends CommonRule> {

  protected RuleRepository<R> storage;

  public RuleEngine(RuleRepository<R> storage) {
    this.storage = storage;
  }

  public abstract String getName();

  /**
   * insert or update rule
   */
  public boolean upsert(R rule) throws RuleException {
    return storage.upsert(rule);
  }

  public abstract boolean check(R rule) throws RuleException;

  public CommonRules<R> getRulesByType(String ruleType) throws RuleException {
    return storage.getRuleByType(ruleType);
  }

  public abstract void exec(CommonRules<R> rules, CommonFacts facts) throws RuleException;

  public void exec(String ruleType, CommonFacts facts) throws RuleException {
    exec(getRulesByType(ruleType), facts);
  }

}
