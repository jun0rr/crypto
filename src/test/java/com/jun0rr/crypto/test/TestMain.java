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
  
  //public static final String SRC = "/home/juno/Documentos/CRLVDigitalDetran.pdf";
  
  public static final String SRC = "C:/Java/certificado_internet_das_coisas.pdf";
  
  //public static final String DST = "/home/juno/Documentos/CRLVDigitalDetran_encrypted.txt.gz";
  
  public static final String DST = "C:/Java/certificado_internet_das_coisas.txt.gz";
  
  //public static final String DST2 = "/home/juno/Documentos/CRLVDigitalDetran2.pdf";
  
  public static final String DST2 = "C:/Java/certificado_internet_das_coisas2.pdf";
  
  @Test
  public void test_0_encrypt() {
    String[] args = {"-e", "-r", "-s=405k", "-p=inadonuj", SRC, DST};
    new Main().processCmd(args);
  }
  
  @Test
  public void test_1_decrypt() {
    String[] args = {"-d", "-r", "-s=405k", "-p=inadonuj", DST, DST2};
    new Main().processCmd(args);
  }
  
}
