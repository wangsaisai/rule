package com.bamboo.rule;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class encapsulates a set of rules and represents a rules namespace. CommonRules must have a
 * unique name within a rules namespace.
 */
public class CommonRules<R extends CommonRule> implements Iterable<R> {

  private Set<R> rules = new TreeSet<>();

  /**
   * Create a new {@link CommonRules} object.
   *
   * @param rules to register
   */
  public CommonRules(Set<R> rules) {
    this.rules = new TreeSet<>(rules);
  }

  /**
   * Create a new {@link CommonRules} object.
   *
   * @param rules to register
   */
  @SafeVarargs
  public CommonRules(R... rules) {
    Collections.addAll(this.rules, rules);
  }

  /**
   * Register a new rule.
   *
   * @param rule to register
   */
  public void register(R rule) {
    Objects.requireNonNull(rule);
    rules.add(rule);
  }

  /**
   * Unregister a rule.
   *
   * @param rule to unregister
   */
  public void unregister(R rule) {
    Objects.requireNonNull(rule);
    rules.remove(rule);
  }

  /**
   * Unregister a rule by name.
   *
   * @param ruleName the name of the rule to unregister
   */
  public void unregister(final String ruleName) {
    Objects.requireNonNull(ruleName);
    R rule = findRuleByName(ruleName);
    if (rule != null) {
      unregister(rule);
    }
  }

  /**
   * Check if the rule set is empty.
   *
   * @return true if the rule set is empty, false otherwise
   */
  public boolean isEmpty() {
    return rules.isEmpty();
  }

  /**
   * Return rules as a set.
   *
   * @return the current rules as a {@link TreeSet}
   */
  public Set<R> asSet() {
    return rules;
  }

  /**
   * Clear rules.
   */
  public void clear() {
    rules.clear();
  }

  @Override
  public Iterator<R> iterator() {
    return rules.iterator();
  }

  private R findRuleByName(String ruleName) {
    for (R rule : rules) {
        if (rule.getName().equalsIgnoreCase(ruleName)) {
            return rule;
        }
    }
    return null;
  }
}
