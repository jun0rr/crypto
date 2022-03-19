/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jun0rr.crypto.test;

import com.jun0rr.crypto.Main;
import org.junit.jupiter.api.Test;

/**
 *
 * @author juno
 */
public class TestMain {
  
  @Test
  public void test_encrypt() {
    String[] args = {"-e", "-b", "-g", "-r", "-s=30k", "-p=inadonuj", "/home/juno/Documentos/CRLVDigitalDetran.pdf", "/home/juno/Documentos/CRLVDigitalDetran_encrypted.txt.gz"};
    new Main().processCmd(args);
  }
  
  @Test
  public void test_decrypt() {
    String[] args = {"-d", "-b", "-g", "-r", "-s=30k", "-p=inadonuj", "/home/juno/Documentos/CRLVDigitalDetran_encrypted.txt.gz", "/home/juno/Documentos/CRLVDigitalDetran2.pdf"};
    new Main().processCmd(args);
  }
  
}
