package mx.lania.kdemo.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jaguilar
 */
@RestController
public class ListController {
    
    @GetMapping("/data")
    List<String> getDataList() {
        return List.of("Hola","mundo");
    }
}
