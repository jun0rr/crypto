/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.crypto;

import java.util.Map;
import java.util.Optional;
import picocli.CommandLine.ITypeConverter;

/**
 *
 * @author F6036477
 */
public class SizeConverter implements ITypeConverter<Size> {
  
  private static final Map<String,Long> units = Map.of(
      "k", 1024L, 
      "m", 1024L * 1024L, 
      "g", 1024L * 1024L * 1024L
  );

  @Override
  public Size convert(String value) {
    String unit = value.substring(value.length() -1).toLowerCase();
    Optional<Long> mult = units.entrySet().stream()
        .filter(e->unit.equals(e.getKey()))
        .map(e->e.getValue())
        .findAny();
    if(mult.isPresent()) {
      return new Size(value, Long.parseLong(value.substring(0, value.length() -1)) * mult.get());
    }
    else {
      return new Size(value, Long.parseLong(value));
    }
  }
  
}
