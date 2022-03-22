/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.crypto;

import com.jun0rr.util.StringPad;
import com.jun0rr.util.crypto.EncryptedFile.Progress;
import java.util.function.Consumer;

/**
 *
 * @author F6036477
 */
public class ProgressListener implements Consumer<Progress> {
  
  public static final int MAX_DOTS = 18;
  
  private final StringPad pad = StringPad.of("");
  
  private int curDots = 0;
  
  private boolean start = false;
  
  @Override
  public void accept(Progress p) {
    if(!start) {
      start = true;
      System.out.print("");
    }
    float perc = p.getCurrent() / Long.valueOf(p.getTotal()).floatValue();
    int cur = Math.round(MAX_DOTS * perc);
    if(cur > curDots) {
      System.out.print(pad.lpad("=", cur - curDots));
    }
    curDots = cur;
    if(p.getCurrent() >= p.getTotal()) {
      System.out.println("] OK");
    }
  }

}
