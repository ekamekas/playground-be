package id.maseka.playground.service.device.controller;

import id.maseka.playground.service.device.service.DeviceService;
import id.maseka.playground.service.device.service.dto.DeviceGetResponseDTO;
import id.maseka.playground.service.device.service.dto.DeviceUpdateOrCreateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("v1/device")
public class DeviceWebController {

    private final DeviceService service;

    public DeviceWebController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<String> create(@Valid @RequestBody DeviceUpdateOrCreateRequestDTO requestDTO) {
        var result = service.create(requestDTO);

        return ResponseEntity
                .created(URI.create("v1/device/" + result))
                .body(result);
    }

    @GetMapping("{id}")
    ResponseEntity<DeviceGetResponseDTO> get(@PathVariable(name = "id") String id) {
        var result = service.get(id);

        return ResponseEntity
                .ok(result);
    }

}
