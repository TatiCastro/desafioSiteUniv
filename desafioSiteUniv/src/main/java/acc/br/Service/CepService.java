package acc.br.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface CepService {

    @GetMapping("/{zipCode}/json/")
    Map<String, String> getAddressByZipCode(@PathVariable("zipCode") String zipCode);
}
