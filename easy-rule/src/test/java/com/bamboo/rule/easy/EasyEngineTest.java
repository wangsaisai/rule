package com.bamboo.rule.easy;

import com.bamboo.rule.CommonFacts;
import com.bamboo.rule.CommonRules;
import com.bamboo.rule.exception.RuleException;
import org.junit.Assert;
import org.junit.Test;

public class EasyEngineTest {

  @Test
  public void easyRuleTest() throws Exception {

    DataObjectFact dataobject = new DataObjectFact();
    dataobject.setDbName("db1");
    dataobject.setTableName("t1");
    dataobject.setColumnName("c1");
    dataobject.setRun(false);

    CommonFacts facts = new CommonFacts();
    facts.putGlobalFact("dataobject", dataobject);

    CommonRules<EasyRule> rules = new CommonRules<EasyRule>();
    EasyRule easyRule = new EasyRule("r1", 1, "type1", "",
        "dataobject.dbName==\"db1\"", "dataobject.setRun(true)");
    rules.register(easyRule);

    EasyRuleEngine engine = new EasyRuleEngine();

    engine.exec(rules, facts);

    Assert.assertTrue(dataobject.isRun());

    // test null facts
    try {
      engine.exec(rules, null);
      Assert.fail("Don't throw exception when input facts is null");
    } catch (RuleException e) {
      // expected exception
    }

    // test with local facts
    try {
      facts.insertLocalFact(new Object());
      engine.exec(rules, facts);
      Assert.fail("Don't throw exception when input facts is null");
    } catch (RuleException e) {
      // expected exception
    }
  }
}
