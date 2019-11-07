package com.bamboo.rule;

public abstract class CommonRule implements Comparable<CommonRule> {

  /**
   * Getter for rule name.
   *
   * @return the rule name
   */
  public abstract String getName();

  /**
   * Getter for rule priority.
   *
   * @return rule priority
   */
  public abstract int getPriority();

  public abstract String getRuleType();

  @Override
  public int compareTo(CommonRule rule) {
    if (getPriority() < rule.getPriority()) {
      return -1;
    } else if (getPriority() > rule.getPriority()) {
      return 1;
    } else {
      return getName().compareTo(rule.getName());
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CommonRule rule = (CommonRule) o;

    if (!getName().equals(rule.getName())) {
      return false;
    }
    if (!getRuleType().equals(rule.getRuleType())) {
      return false;
    }
    if (getPriority() != rule.getPriority()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getRuleType().hashCode();
    result = 31 * result + getPriority();
    return result;
  }

  @Override
  public String toString() {
    return getName();
  }

}
