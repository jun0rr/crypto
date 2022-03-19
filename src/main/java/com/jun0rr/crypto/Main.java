/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jun0rr.crypto;

import com.jun0rr.util.Unchecked;
import com.jun0rr.util.crypto.EncryptedFile;
import com.jun0rr.util.crypto.EncryptedFile.Progress;
import java.nio.file.Path;
import java.util.function.Consumer;
import picocli.CommandLine;
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
  
  @Option(names = {"-e", "--encrypt"}, description = "Encrypt Mode")
  public boolean encrypt;
  
  @Option(names = {"-d", "--decrypt"}, description = "Decrypt Mode")
  public boolean decrypt;
  
  @Option(names = {"-b", "--base64"}, description = "Enable Base64 Codec")
  public boolean base64;
  
  @Option(names = {"-g", "--gzip"}, description = "Enable Gzip Codec")
  public boolean gzip;
  
  @Option(names = {"-r", "--progress"}, description = "Show Progress on STDOUT")
  public boolean progress;
  
  @Option(names = {"-s", "--split"}, arity = "0..1", description = "Split file <size[K|M|G]>", converter = SizeConverter.class)
  public Long split;
  
  @Option(names = {"-p", "--password"}, arity = "1", description = "Password")
  public String passwd;
  
  @Parameters(index = "0")
  public Path src;
  
  @Parameters(index = "1")
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
    if(split > 0) f = f.split(split);
    Consumer<Progress> c = p->{};
    if(progress) {
      c = p->System.out.println(p.getProgressBar());
    }
    EncryptedFile ef = f;
    Consumer<Progress> cs = c;
    if(encrypt) {
      System.out.println("[Crypto-0.1]>> Encripting file: " + src);
      Unchecked.call(()->ef.encrypt(cs));
    }
    else if(decrypt) {
      System.out.println("[Crypto-0.1]>> Decripting file: " + src);
      Unchecked.call(()->ef.decrypt(cs));
    }
    else {
      throw new IllegalArgumentException("Mode not selected (--encrypt/--decrypt)");
    }
  }
  
  public int processCmd(String[] args) {
    return new CommandLine(this).execute(args);
  }

  public static void main(String[] args) {
    System.exit(new CommandLine(new Main()).execute(args));
  }

}
