package com.bamboo.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CommonFacts {

  private Map<String, Object> globalFacts = new HashMap<>();

  private Set<Object> localFacts = new HashSet<>();

  /**
   * Put a global fact This will replace any fact having the same name.
   *
   * @param name fact name
   * @param fact object to putGlobalFact
   * @return the previous value associated with <tt>name</tt>, or
   * <tt>null</tt> if there was no mapping for <tt>name</tt>.
   * (A <tt>null</tt> return can also indicate that the map previously associated <tt>null</tt> with
   * <tt>name</tt>.)
   */
  public Object putGlobalFact(String name, Object fact) {
    Objects.requireNonNull(name);
    return globalFacts.put(name, fact);
  }

  /**
   * Put a local fact
   *
   * @param fact object to putGlobalFact
   */
  public void insertLocalFact(Object fact) {
    localFacts.add(fact);
  }

  /**
   * Remove fact.
   *
   * @param name of fact to remove
   * @return the previous value associated with <tt>name</tt>, or
   * <tt>null</tt> if there was no mapping for <tt>name</tt>.
   * (A <tt>null</tt> return can also indicate that the map previously associated <tt>null</tt> with
   * <tt>name</tt>.)
   */
  public Object remove(String name) {
    Objects.requireNonNull(name);
    return globalFacts.remove(name);
  }

  /**
   * Get a fact by name.
   *
   * @param name of the fact
   * @param <T> type of the fact
   * @return the fact having the given name, or null if there is no fact with the given name
   */
  @SuppressWarnings("unchecked")
  public <T> T get(String name) {
    Objects.requireNonNull(name);
    return (T) globalFacts.get(name);
  }

  /**
   * Return global facts as a map.
   *
   * @return the current global facts as a {@link HashMap}
   */
  public Map<String, Object> getGlobalFacts() {
    return globalFacts;
  }

  /**
   * Return local facts as a set.
   *
   * @return the current local facts as a {@link HashSet}
   */
  public Set<Object> getLocalFacts() {
    return localFacts;
  }

}
