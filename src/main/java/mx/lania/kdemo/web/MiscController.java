package mx.lania.kdemo.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jaguilar
 */
@RestController
public class MiscController {

    @Autowired
    ApplicationEventPublisher pubiicadorEventos;
    
    private String VERSION = "2.0";

    @GetMapping("/data")
    List<String> getDataList() {
        return List.of("Hola", "mundo");
    }

    @GetMapping("/trabajo")
    List<Integer> realizarTrabajoComplejo(@RequestParam("unidades") Integer numUnidades) {
        AvailabilityChangeEvent.publish(pubiicadorEventos, this, ReadinessState.REFUSING_TRAFFIC);
        Random rnd = new Random();
        List<Integer> resultados = new ArrayList<>();
        for (int ii = 0; ii<numUnidades; ii++) {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            resultados.add(rnd.nextInt());
        }
        AvailabilityChangeEvent.publish(pubiicadorEventos, this, ReadinessState.ACCEPTING_TRAFFIC);
        return resultados;
    }
    
    @PostMapping("/romper")
    String romperAplicacion() {
        AvailabilityChangeEvent.publish(pubiicadorEventos, this, LivenessState.BROKEN);        
        return "Oops";
    }
    
    @Autowired
    PrimeGenerator generador;
    
    // Candidatos
    // 290212049128220693
    // 5826543228209422189
    // 79151450320229707039
    @GetMapping("/busqueda") 
    List<String> getPrimos() {
        List<String> primos = new ArrayList<>(11);
        for(int ii = 18; ii<22; ii++) {
            Optional<BigInteger> posible = generador.encontrarPrimo(ii);
            if (posible.isPresent()) {
                primos.add(posible.get().toString());
            }
        }
        return primos;
    }
    
    @GetMapping("/trabajoIntensivo")
    String factorizarPrimo() {
        BigInteger candidato = new BigInteger("5826543228209422189");
        generador.esPrimo(candidato);
        return "LISTO";
    }
    
    @GetMapping("/version")
    String getVersion() {
        return VERSION;
    }
}
