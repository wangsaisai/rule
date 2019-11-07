package com.bamboo.rule.easy;

import com.bamboo.rule.CommonFacts;
import com.bamboo.rule.CommonRules;
import com.bamboo.rule.RuleEngine;
import com.bamboo.rule.RuleEngineFactory;
import com.bamboo.rule.exception.RuleException;
import java.util.Map;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;

public class EasyRuleEngine extends RuleEngine<EasyRule> {

  private static final String EASY_RULE_ENGINE_NAME = "Easy-Rule-Engine";
  static RulesEngine easyRuleEngine = new DefaultRulesEngine();

  static {
    try {
      RuleEngineFactory.registerRuleEngine(new EasyRuleEngine());
    } catch (RuleException e) {
      throw new RuntimeException("Cann't register rule engine: " + EASY_RULE_ENGINE_NAME);
    }
  }

  public EasyRuleEngine() throws RuleException {
    super(new EasyRulePostgresRepository());
  }

  public static Rules transform(CommonRules<EasyRule> easyRules) {
    Rules rules = new Rules();
    for (EasyRule easyRule : easyRules) {
      Rule rule = new MVELRule()
          .name(easyRule.getName())
          .description(easyRule.getDescription())
          .priority(easyRule.getPriority())
          .when(easyRule.getCondition())
          .then(easyRule.getActions());
      rules.register(rule);
    }
    return rules;
  }

  public static Facts transform(Map<String, Object> globalFacts) {
    Facts facts = new Facts();

    for (Map.Entry<String, Object> entry : globalFacts.entrySet()) {
      facts.put(entry.getKey(), entry.getValue());
    }
    return facts;
  }

  @Override
  public String getName() {
    return EASY_RULE_ENGINE_NAME;
  }

  @Override
  public boolean upsert(EasyRule rule) throws RuleException {
    return storage.upsert(rule);
  }

  @Override
  public boolean check(EasyRule rule) throws RuleException {
    return rule.getName() != null
        && rule.getCondition() != null
        && rule.getActions() != null;
  }

  @Override
  public void exec(CommonRules<EasyRule> rules, CommonFacts facts) throws RuleException {
    if (facts == null) {
      throw new RuleException("facts cannot be null");
    }

    if (!facts.getLocalFacts().isEmpty()) {
      throw new RuleException(EASY_RULE_ENGINE_NAME + " doesn't support local facts");
    }

    try {
      easyRuleEngine.fire(transform(rules), transform(facts.getGlobalFacts()));
    } catch (Exception e) {
      throw new RuleException(this, e);
    }
  }
}
