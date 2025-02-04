package com.example.xlsxreader.controller;

import com.example.xlsxreader.service.XlsxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
@Tag(name = "Excel Reader", description = "Reads numbers from XLSX file")
public class XlsxController {
    private final XlsxService service;

    public XlsxController(XlsxService service) {
        this.service = service;
    }

    @Operation(summary = "Get N-th largest number from XLSX file")
    @GetMapping("/max-number")
    public ResponseEntity<?> getNthLargest(@RequestParam String filePath, @RequestParam int n) {
        if (!Files.exists(Paths.get(filePath))){
            return ResponseEntity.badRequest().body("File not found: " + filePath);
        }
        try {
            int result = service.getNthLargest(filePath, n);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error reading file: " + e.getMessage());
        }
    }
}
