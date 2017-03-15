//prime methods for RSA
//written by Jason Hertz

public class Prime {

    public static boolean isPrime(int number) {

        boolean pN=false;
        //A number is prime if it has no other factors than 1 and itself.
        //It is not a multiple of any numbers except for 1 and itself.

        //Iterate through all integers more than 2 and less than number

        if(number<=1) {
            System.out.println("number must be greater than one");
            System.exit(0);
        }

        if(number <4 && number >1) return true;

        for (int i=2; i<=Math.sqrt(number); i++){

            if (number%i==0) {
                pN=false;
                return false;
            } else {
                pN=true;
            }
        }

        return pN;
    }

    public static boolean isMersennePrime(int number) {
        int count = 0;
        int p;
        int iMPN;
        //the 49th Mersenne prime has something like 22 million decimal digits
        //Euler discovered the 8th Mersenne in 1772

        for(int i = 0; i<100000; i++) {
            p = prime(i);
            iMPN = (int) Math.pow(2,p)-1;
            if(iMPN == number) return true;
            if(iMPN > number) return false;
        }
        return false;
    }


    public static int prime(int n) {


        if(n<1) return -1;
        if(n==1) return 2;

        //nth prime equals n-1 prime plus two until isPrime(n-1prime + 2*n)
        int basePrime=3;
        int nPrime=3;
        int count=0;
        int pCount = 1;
        while (pCount<n) {
            nPrime = basePrime + 2*count++;
            if(isPrime(nPrime)) pCount++;
        }

        return nPrime;
    }

    public static int[] primeArray(int howMany) {

        int primes[] = new int[howMany];

        if(howMany<4) {

            if(howMany<1) {
                return null;
            }
            if(howMany==1) {
                primes[0] = 2;
                return primes;
            }
            if(howMany==2) {
                primes[0] =2;
                primes[1] =3;
                return primes;
            }
            if(howMany==3) {
                primes[0] =2;
                primes[1] =3;
                primes[2] =5;
                return primes;
            }
        }

        primes[0] =2;
        primes[1] =3;
        primes[2] =5;

        //each call for prime runs search loop from base prime, which runs primality test per iteration
        //exponential growth of complexity as howMany increases
        for (int k=3; k<howMany; k++) {
            primes[k] = prime(k+1);
        }

        return primes;
    }

    public static int[] primeFactors(int number) {

        int[] pCase = new int[1];
        if(number<1) return null;
        if(isPrime(number)) {
            pCase[0] = number;
            return pCase;
        }

        int count = 1;
        int pCount = 0;
        int toFactor = number;

        do {
            //if nth prime not a factor increase by one
            if(toFactor % prime(count) != 0) count++;
            //if n+1th prime a factor, divide off toFactor amount
            if(toFactor % prime(count) == 0) {
                toFactor = toFactor/prime(count);
                pCount++;
            }
            //add to count of iterations

            //end when toFactor is less or equal to one
        } while (toFactor>1);

        //create array after figuring out, above, how many factors there are
        int[] primeFactors = new int[pCount];

        count = 1;
        pCount = 0;
        toFactor = number;
        //repeat loop to fill array with prime factors
        do {

            if(toFactor % prime(count) != 0) count++;
            if(toFactor % prime(count) == 0) {
                toFactor = toFactor/prime(count);
                primeFactors[pCount] = prime(count);
            } else {
                continue;
            }
            pCount++;

        } while (toFactor>1);

        return primeFactors;
    }

    public static int[] prodOfPrimesForN(int n) {
        int primea = 2;
        int i = 0;
        while (primea<= (int) Math.sqrt(n)) {
            i++;
            primea = Prime.prime(i);
        }
        primea = Prime.prime(i+1);

        int primeb=2;
        int j = 0;
        while (primea*primeb<= n) {
            j++;
            primeb = Prime.prime(j);
        }

        int primes[] = new int[3];
        primes[0] = primea;
        primes[1] = primeb;
        primes[2] = primea*primeb;
        return primes;
    }

    public static int GCD(int x, int y) {

        //euclidean algorithm
        int gcd = 1;
        int a = x, b = y;
        int r;

        while (b != 0) {
            r = a%b;
            a = b;
            b = r;
        }

        return a;
    }

    public static int modpow(int a,int b, int c) {
        //modular exponentiation
        /*
        System.out.println("a"+a);

        System.out.println("b"+b);
        System.out.println("c"+c);
        */

        int power = b;
        int value = a;
        int mod = c;
        int e = 1;

        for (int i = 0; i < power; i++) {
            e = ((e * value) % mod);
        }

        return e;

    }

}
