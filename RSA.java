import java.math.BigInteger;

/**
 * Created by jasonhertz on 3/13/17.
 * RSA encryption methods: encrypt, decrypt, get keys, read(display)
 */
public class RSA {
    private int n, d, e; //n,e public encryption key; n,d private decryption key
    //method only handles characters ABC..Z

    private String Message;
    private char[] mChars;

    public RSA(int n, int d, int e) {
        this.n = n;
        this.d = d;
        this.e = e;

    }

    public String encrypt(String message) {
        Message = "";

        mChars = new char[message.length()];

        String csum=""; //collect two letter blocks for encryption
        int ascii; //hold ascii integer value of each character in message

        for (int a = 0; a < message.length(); a++) {
            //System.out.println("counter " + a);

            mChars[a] = message.charAt(a);

            ascii = mChars[a];
            ascii -= 65; //convert to integer value, A=0...Z=25

            String temp = String.valueOf(ascii); //convert back to string in order to add to block

            if(Integer.parseInt(temp)<10) {
                String temp2 = "0";
                temp2 += temp;
                temp = temp2;
            } //pad letter value with zero if < 10

            System.out.println("ascii " + temp);

            csum += temp; //add to block

            if((a+1) % 2 ==0) {
                System.out.print("csum "+csum);
                //encrypt csum block of two using modular exponentiation with public key e
                int M = Integer.parseInt(csum);
                int C = Prime.modpow(M,e,n);
                System.out.println(" C " +C);

                //pad encrypte message C with zeros if not four digits
                if(Math.floor(Math.log10(C))<3) {
                    Message += "0";

                    if(Math.floor(Math.log10(C)) == 1) {
                        Message += "0";
                    }
                    if(Math.floor(Math.log10(C)) == 0) {
                        Message += "0" + "0";
                    }

                }

                Message += String.valueOf(C); //add encrypted block C to Message string

                csum = ""; //reset block for next iteration
            }
        }
        return Message;
    }

    public String decrypt(String message) {
        String messageRow = ""; //four char/digit block to decode

        String[] messageV = new String[message.length()/4]; //array to collect blocks
        int messageVCount = 0;

        for (int i = 0; i < message.length(); i++) {
            if (i <= (int) Math.log10(n)) {
                messageRow += message.charAt(i); //first row: add to block until column equal to "width" of n or log10(n)
                //System.out.print("added" + message.charAt(i));
            }

            if (i == (int) Math.log10(n)) { //add block to messageV array, increase counter, and reset messageRow block
                messageV[messageVCount] = messageRow;
                messageVCount++;
                messageRow = "";
                //System.out.println("");
            }

            if (i > (int) Math.log10(n)) { //after first row, add columns for the "width" of n or log10(n)
                messageRow += message.charAt(i);
                //System.out.print("added" + message.charAt(i));

                if ((i + 1) % ((int) Math.log10(n) + 1) == 0) { //rows end on elements 1 less than a multiple of log10(n)+1
                    messageV[messageVCount] = messageRow;
                    messageVCount++;
                    messageRow = ""; //add block to messageV array, increase counter, and reset messageRow block
                    //System.out.println("");
                }

            }
        }
        //decode each element, block, of messageV array
        for(int j=0; j<messageV.length; j++) {
            String messageTemp = messageV[j];
            //System.out.println("msgTemp " + messageTemp);

            if(messageTemp != null) {
                int c = Integer.parseInt(messageTemp); //cast to integer value of number characters
                System.out.println("c" + c);

                double M = Prime.modpow(c, d, n); //modular exponentiation with secret key d
                System.out.println("m" + M);

                //padding for results too narrow or with log10(M) < 3
                if(Math.floor(Math.log10(M))<3) {
                    String Mplus = "0";

                    if(Math.floor(Math.log10(M)) == 1) {
                        Mplus += "0";
                    }
                    if(Math.floor(Math.log10(M)) == 0) {
                        Mplus += "0" + "0";
                    }
                    Mplus += (int) M;
                    messageV[j] = Mplus; //either: update array with padded decrypted block
                }

                if(Math.floor(Math.log10(M))>=3) {
                    messageV[j] = String.valueOf((int) M); //or: update array with decrypted block (no padding)
                }
                //System.out.println("messagV[j] " + messageV[j]);
            }
        }

        System.out.println("messages added");
        String decryptedM = ""; //return string with decrypted message

        for(int i=0; i<messageV.length;i++){
            //System.out.println(dsCipherI[i]);
            decryptedM += messageV[i];
        } //add message to return string
        //System.out.println("decrypted message: " + decryptedM);

        return decryptedM;
    }
    public static String read(String m) {
        String read = "";
        String temp1 = "";
        for(int i = 0; i<m.length(); i++) {
            //System.out.println("m.charAt(i)" + m.charAt(i));
            temp1 += m.charAt(i); //block collection

            if((i+1)%2 == 0) {
                int tempa = Integer.parseInt(temp1); //cast to integer
                System.out.print("tempa " + tempa + " ");
                char rd = (char) (tempa + 65); //add 65 back to ascii value and cast to char
                System.out.println(rd);
                read += rd; //add to read string
                temp1 = ""; //reset block collector
                System.out.println("");
            }
        }
        return read;
    }

    public static int[] getKey() {
        int product[] = Prime.prodOfPrimesForN(2525); //method returns two large prime numbers,
                                                        // whose product is >= N
        /*
        System.out.println(product[0]);
        System.out.println(product[1]);
        System.out.println(product[2]);
        */
        int totientN = (product[0]-1)*(product[1]-1);
        //System.out.println("totient" + totientN);
        //System.out.println("");
        int ep = 2;


        for (int e = 2; ep < totientN; e++) {
            ep = Prime.prime(e);
            //System.out.println("e" + ep + " " + Prime.GCD(ep, totientN));
            if (ep>11 && Prime.GCD(ep, totientN) == 1) break;
        } //find a prime e that satisfies key conditions
        //System.out.println(ep);

        //d is inverse of e mod totient
        //d*e :== 1 (mod totient)
        BigInteger d;
        BigInteger e = new BigInteger(String.valueOf(ep));
        BigInteger totient = new BigInteger(String.valueOf(totientN));
        d = e.modInverse(totient);
        /*
        System.out.println("d = " + d.toString());
        System.out.println("d*e = " + d.multiply(e).toString());
        System.out.println("d*e mod totient = " + d.multiply(e).mod(totient));

        */
        System.out.println("Your key is n : " + product[2] + " d: " + d + " e: " + ep + " p: " + product[1] + " q: " + product[0]);

        int[] key = {product[2], d.intValue(), ep, product[1], product[0]};
        return key;
    }
}




