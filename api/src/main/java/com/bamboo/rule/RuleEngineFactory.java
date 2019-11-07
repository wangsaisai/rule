package com.bamboo.rule;

import com.bamboo.rule.common.CommonConstant;
import com.bamboo.rule.common.RuleProperties;
import com.bamboo.rule.exception.RuleException;
import java.util.Hashtable;
import org.apache.commons.configuration.Configuration;

public class RuleEngineFactory {

  private final static Hashtable<String, String> ruleEngineClassNames = new Hashtable<>();
  private final static Hashtable<String, RuleEngine> registeredRuleEngines = new Hashtable<>();

  static {
    ruleEngineClassNames.put("Easy-Rule-Engine", "com.bamboo.rule.easy.EasyRuleEngine");
    ruleEngineClassNames.put("Drool-Rule-Engine", "com.bamboo.rule.drool.DroolRuleEngine");
  }

  public static void registerRuleEngine(RuleEngine engine) throws RuleException {
    if (engine != null) {
      registeredRuleEngines.putIfAbsent(engine.getName(), engine);
    } else {
      throw new RuleException("register rule engine cannot be null");
    }
  }

  public static RuleEngine get(String engineName) throws RuleException {
    RuleEngine engine = registeredRuleEngines.get(engineName);
    if (engine != null) {
      return engine;
    }

    try {
      if (ruleEngineClassNames.contains(engineName)) {
        Class.forName(ruleEngineClassNames.get(engineName));
        return registeredRuleEngines.get(engineName);
      } else {
        throw new RuleException(String.format("Rule Engine: %s not find", engineName));
      }
    } catch (Exception e) {
      throw new RuleException(e);
    }
  }

  public static RuleEngine get() throws RuleException {
    Configuration ruleConf = RuleProperties.get();

    String engineName = ruleConf.getString(CommonConstant.RULE_ENGINE_KEY);
    return get(engineName);
  }

}
