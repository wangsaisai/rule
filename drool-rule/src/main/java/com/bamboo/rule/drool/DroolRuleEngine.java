package com.bamboo.rule.drool;

import com.bamboo.rule.CommonFacts;
import com.bamboo.rule.CommonRules;
import com.bamboo.rule.RuleEngine;
import com.bamboo.rule.RuleEngineFactory;
import com.bamboo.rule.exception.RuleException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolRuleEngine extends RuleEngine<DroolRule> {

  private static final String DROOL_RULE_ENGINE_NAME = "Drool-Rule-Engine";

  static {
    try {
      RuleEngineFactory.registerRuleEngine(new DroolRuleEngine());
    } catch (RuleException e) {
      throw new RuntimeException("Cann't register rule engine: " + DROOL_RULE_ENGINE_NAME);
    }
  }


  public DroolRuleEngine() throws RuleException {
    super(new DroolRulePostgresRepository());
  }

  @Override
  public String getName() {
    return DROOL_RULE_ENGINE_NAME;
  }

  @Override
  public boolean upsert(DroolRule rule) throws RuleException {
    return storage.upsert(rule);
  }

  // todo use drools api for validation
  @Override
  public boolean check(DroolRule rule) throws RuleException {
    return rule.getName() != null
        && rule.getContent() != null;
  }

  @Override
  public void exec(CommonRules<DroolRule> rules, CommonFacts facts) throws RuleException {
    try {
      KieServices ks = KieServices.Factory.get();
      KieRepository kr = ks.getRepository();
      KieFileSystem kfs = ks.newKieFileSystem();

      for (DroolRule rule : rules) {
        String content = rule.getContent();
        // todo  only path prefix : src/main/resources available, unknown reason
        kfs.write("src/main/resources/" + rule.getName() + ".drl", content);
      }

      KieBuilder kb = ks.newKieBuilder(kfs);

      kb.buildAll();
      if (kb.getResults().hasMessages(Message.Level.ERROR)) {
        throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
      }

      KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());

      KieSession kieSession = kContainer.newKieSession();

      facts.getGlobalFacts().forEach(kieSession::setGlobal);

      facts.getLocalFacts().forEach(kieSession::insert);

      kieSession.fireAllRules();
      kieSession.destroy();
    } catch (Exception e) {
      throw new RuleException(this, e);
    }
  }

}
