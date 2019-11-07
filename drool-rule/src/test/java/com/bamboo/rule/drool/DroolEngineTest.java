package com.bamboo.rule.drool;

import com.bamboo.rule.CommonFacts;
import com.bamboo.rule.CommonRules;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DroolEngineTest {

  CommonRules<DroolRule> rules;
  DataObjectFact fact;
  SubFact subFact;

  @Before
  public void setup() {
    // setup data object
    fact = new DataObjectFact();
    fact.setDbName("db1");
    fact.setTableName("t1");
    fact.setColumnName("c1");

    // setup subclass data object
    subFact = new SubFact();
    subFact.setDbName("db1");
    subFact.setTableName("t1");
    subFact.setColumnName("c1");
    subFact.setData("xxx");

    String content = "package rules\n" +
        "import com.bamboo.rule.drool.DataObjectFact\n" +
        "global java.util.List list\n" +
        "rule \"r1\"\n" +
        "  no-loop true\n" +
        "  lock-on-active true\n" +
        "  salience 1\n" +
        "  when\n" +
        "    $o : DataObjectFact(dbName == \"db1\")\n" +
        "  then\n" +
        "    $o.setRun(true);\n" +
        "    list.add($o);\n" +
        "    update($o);\n" +
        "end\n";

    rules = new CommonRules<>();
    DroolRule droolRule = new DroolRule("r1", 1, "type1", content);
    rules.register(droolRule);
  }

  @Test
  public void factTest() throws Exception {
    fact.setRun(false);
    List<Object> list = new ArrayList<>();

    CommonFacts facts = new CommonFacts();
    facts.insertLocalFact(fact);
    facts.putGlobalFact("list", list);

    DroolRuleEngine engine = new DroolRuleEngine();
    engine.exec(rules, facts);

    Assert.assertTrue(fact.isRun());
    Assert.assertEquals(list.size(), 1);
  }

  @Test
  public void subClassFactTest() throws Exception {
    List<Object> list = new ArrayList<>();
    fact.setRun(false);
    subFact.setRun(false);

    CommonFacts facts = new CommonFacts();
    facts.putGlobalFact("list", list);
    facts.insertLocalFact(fact);
    facts.insertLocalFact(subFact);

    DroolRuleEngine engine = new DroolRuleEngine();
    engine.exec(rules, facts);

    Assert.assertTrue(fact.isRun());
    Assert.assertTrue(subFact.isRun());
    Assert.assertEquals(list.size(), 2);
  }

}
