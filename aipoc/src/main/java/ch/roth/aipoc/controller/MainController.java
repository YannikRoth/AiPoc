package ch.roth.aipoc.controller;

import ch.roth.aipoc.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "/greeting")
    public ObjectNode greeting(@RequestParam String name) {
        System.out.println("Hello, " + name + "!");

        String result = this.mainService.greeting(name);
        ObjectNode r = objectMapper.createObjectNode();
        r.put("result", result);
        return r;
    }

}
