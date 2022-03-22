/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jun0rr.crypto;

import com.jun0rr.util.StringPad;
import com.jun0rr.util.Unchecked;
import com.jun0rr.util.crypto.EncryptedFile;
import com.jun0rr.util.crypto.EncryptedFile.Progress;
import java.nio.file.Path;
import java.util.function.Consumer;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 *
 * @author juno
 */
@CommandLine.Command(name = "crypto", 
    mixinStandardHelpOptions = true, 
    version = "crypto-0.8", 
    description = "Crypto - Encrypt/Decrypt files")
public class Main implements Runnable {
  
  public static class Mode {
    @Option(names = {"-e", "--encrypt"}, required = true, description = "Encrypt Mode")
    public boolean encrypt;

    @Option(names = {"-d", "--decrypt"}, required = true, description = "Decrypt Mode")
    public boolean decrypt;
  }
  
  @ArgGroup(exclusive = true, multiplicity = "1")
  public Mode mode;
  
  @Option(names = {"-b", "--base64"}, defaultValue = "true", negatable = true, description = "Disable Base64 Codec")
  public boolean base64;
  
  @Option(names = {"-g", "--no-gzip"}, defaultValue = "true", negatable = true, description = "Disable Gzip Codec")
  public boolean gzip;
  
  @Option(names = {"-r", "--progress"}, description = "Show Progress on STDOUT")
  public boolean progress;
  
  @Option(names = {"-s", "--split"}, arity = "0..1", description = "Split file <size[K|M|G]>", converter = SizeConverter.class)
  public Size split;
  
  @Option(names = {"-p", "--password"}, arity = "1", description = "Password", required = true)
  public String passwd;
  
  @Parameters(index = "0", description = "Source file")
  public Path src;
  
  @Parameters(index = "1", description = "Destination file")
  public Path dst;
  
  @Override
  public void run() {
    if(passwd == null) {
      throw new IllegalArgumentException("Password not informed (--password)");
    }
    EncryptedFile f = EncryptedFile.of(passwd, src, dst)
        .disableBase64Codec()
        .disableGzipCodec();
    if(base64) f = f.enableBase64Codec();
    if(gzip) f = f.enableGzipCodec();
    if(split != null) f = f.split(split.getSize());
    System.out.printf("[Crypto-0.1]>> %s file...%n", mode.encrypt ? "Encrypting" : "Decrypting");
    System.out.println("  - Input File.....: " + src);
    System.out.println("  - Output File....: " + dst);
    System.out.println("  - Base64 Codec...: " + base64);
    System.out.println("  - Gzip Codec.....: " + gzip);
    System.out.println("  - Split File.....: " + (split != null ? split : false));
    Consumer<Progress> c = p->{};
    if(progress) {
      c = new ProgressListener();
      System.out.print("  - Progress.......: [");
    }
    EncryptedFile ef = f;
    Consumer<Progress> cs = c;
    if(mode.encrypt) {
      Unchecked.call(()->ef.encrypt(cs));
    }
    else {
      Unchecked.call(()->ef.decrypt(cs));
    }
  }
  
  public int processCmd(String[] args) {
    return new CommandLine(this).execute(args);
  }

  public static void main(String[] args) {
    System.exit(new CommandLine(new Main()).execute(args));
  }

}
