package com.bamboo.rule.exception;

import com.bamboo.rule.RuleEngine;

public class RuleException extends Exception {

  public RuleException() {
  }

  public RuleException(Throwable cause) {
    super(cause);
  }

  public RuleException(String message) {
    super(message);
  }

  public RuleException(String message, Throwable cause) {
    super(message, cause);
  }

  public RuleException(RuleEngine engine, Throwable cause) {
    super(String.format("Rule Engine: [%s] exception!!!", engine.getName()), cause);
  }

}
