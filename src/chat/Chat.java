package chat;

import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Chat {
    //public static int BIT_LENGTH = 1024;
    
    
    
    public static void main(String[] args) {
       
       // Generuje losowe liczby pierwsze
     /* Random rand = new SecureRandom();
      BigInteger p = new BigInteger(BIT_LENGTH / 2, 100, rand);
      BigInteger q = new BigInteger(BIT_LENGTH / 2, 100, rand);

        // Obliczenia składników
      BigInteger n = p.multiply(q);
      BigInteger phi = p.subtract(BigInteger.ONE)
              .multiply(q.subtract(BigInteger.ONE));

        // Generuje klucze
      BigInteger e;
      do e = new BigInteger(phi.bitLength(), rand);
      while (e.compareTo(BigInteger.ONE) <= 0
              || e.compareTo(phi) >= 0
              || !e.gcd(phi).equals(BigInteger.ONE));
      BigInteger d = e.modInverse(phi);
      System.out.println("n: "+n);
      System.out.println("d: "+d);
      System.out.println("e: "+e);
      oos = new ObjectOutputStream(client.getOutputStream());
      Message mes = new Message();
            mes.d = d;
            mes.n = n;
            mes.e = e;
        // konwersja String do BigInteger
     String txt = "Witaj przyjacielu.";
      byte[] fooBytes = txt.getBytes();
      BigInteger msg = new BigInteger(fooBytes);

         System.out.println(msg);
      // Wiadomość szyfrowanie
      BigInteger enc = msg.modPow(e, n);
         System.out.println(enc);

      // Wiadomość deszyfrowanie
      BigInteger dec = enc.modPow(d, n);
      txt = new String(dec.toByteArray());
         System.out.println("dec: "+txt);*/
    }
}
