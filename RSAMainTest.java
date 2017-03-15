/**
 * Created by jasonhertz on 3/13/17.
 * RSA test main
 */
import java.util.Scanner;

public class RSAMainTest {
    public static void main (String[] args) {

        int counter = 0;
        int[] key = RSA.getKey();
        System.out.println("keys made " + key.length);
        RSA RSAC = new RSA(key[0], key[1], key[2]);

        do {
            counter++;

            System.out.println("RSA Cipher" + counter);

            System.out.println("Choose ... " + "\nencrypt \ndecrypt \nquit");
            Scanner sca = new Scanner(System.in);
            String method = sca.nextLine();

            if (method.equals("quit")) System.exit(0);

            if (method.equals("encrypt")) {
                System.out.println("Enter string to encrypt: ");
                Scanner scan = new Scanner(System.in);
                String sCipher = scan.nextLine();
                sCipher = RSAC.encrypt(sCipher);
                System.out.println("encrypted string: " + sCipher);
            }

            else if(method.equals("decrypt")) {
                System.out.println("Enter string to de-cipher: ");
                Scanner scann = new Scanner(System.in);
                String dsCipher = scann.nextLine();
                String dsCipherI = RSAC.decrypt(dsCipher);

                System.out.println("decrypted message: " + RSA.read(dsCipherI));

            }
            System.out.println("");


        } while (counter< (int) Math.pow(10,6)) ;

    }
}

