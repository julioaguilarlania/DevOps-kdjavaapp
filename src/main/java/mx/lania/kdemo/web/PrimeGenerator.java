package mx.lania.kdemo.web;

import java.math.BigInteger;
import org.springframework.stereotype.Component;
import static java.math.BigInteger.TWO;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author Jaguilar
 */
@Component
public class PrimeGenerator {

    public boolean esPrimo(BigInteger candidato) {
        if (!candidato.isProbablePrime(5)) {
            return false;
        }

        /* Innecesario para este caso
        if (!TWO.equals(candidato) && BigInteger.ZERO.equals(candidato.mod(TWO))) {
            return false;
        }
        */

        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(candidato) < 1; i = i.add(TWO)) {
            if (BigInteger.ZERO.equals(candidato.mod(i))) //check if 'i' is divisor of 'number'
            {
                return false;
            }
        }
        return true;
    }
    
    private static final int[] LAST_DIGITS = new int[]{1,3,7,9};
    
    public Optional<BigInteger> encontrarPrimo(int nDigitos) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(nDigitos);
        sb.append(rnd.nextInt(9)+1);
        for (int ii = 2; ii < nDigitos; ii++) {
            sb.append(rnd.nextInt(10));
        }
        sb.append(LAST_DIGITS[rnd.nextInt(4)]);
        BigInteger candidato = new BigInteger(sb.toString());
        if (esPrimo(candidato)) {
            return Optional.of(candidato);
        }
        return Optional.empty();
    }
}
