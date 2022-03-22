/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.crypto;

import com.jun0rr.util.match.Match;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class Size {
  
  private final String value;
  
  private final long size;
  
  public Size(String val, long len) {
    this.value = Match.notNull(val).getOrFail("Bad null Size value");
    this.size = Match.betweenExclusive(Long.valueOf(len), Long.valueOf(0L), Long.valueOf(Long.MAX_VALUE)).getOrFail("Bad size: " + len);
  }
  
  public long getSize() {
    return size;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 17 * hash + Objects.hashCode(this.value);
    hash = 17 * hash + (int) (this.size ^ (this.size >>> 32));
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Size other = (Size) obj;
    if (this.size != other.size) {
      return false;
    }
    return Objects.equals(this.value, other.value);
  }
  
  @Override
  public String toString() {
    return value;
  }
  
}
