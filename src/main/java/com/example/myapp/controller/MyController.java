package com.example.myapp.controller;

import com.example.myapp.dto.DTODataRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class MyController {

    @GetMapping(path = "/greeting")
    @Operation(summary = "Get Greeting", description = "Returns a greeting message.")
    public ResponseEntity<String> getGreeting() {
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }

    @PostMapping(path = "/submit")
    @Operation(summary = "Submit Data", description = "Receives data and returns a confirmation message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data successfully received."),
            @ApiResponse(responseCode = "400", description = "Bad request: Data is null or empty.")
    })
    public ResponseEntity<String> operation(
            @RequestBody(required = false) @Schema(description = "The data to be submitted", implementation = DTODataRequest.class) DTODataRequest data) {
        if (data.getData() != null && !data.getData().isEmpty()) {
            return new ResponseEntity<>("Data received: " + data.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad request: Data is null or empty.", HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    @Operation(summary = "Get User By Id", description = "Return a user id to get.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Data successfully received."),
        @ApiResponse(responseCode = "400", description = "Bad request: Invalid ID format, id is lower or equals to 0 or Invalid ID format, id is not a number or Bad info"),
    })
    public ResponseEntity<String> getUserById(
            @PathVariable(name = "id") @Parameter(description = "Id to search, this id only accept numbers") String id) {
        try {
            long userId = Long.parseLong(id);
            if (userId > 0) {
                return new ResponseEntity<>("User ID: " + userId, HttpStatus.OK);
            }
            return new ResponseEntity<>("Bad request: Invalid ID format, id is lower or equals to 0",
                    HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>("Bad request: Invalid ID format, id is not a number", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Bad request: Bad info", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/search")
    @Operation(summary = "Search", description = "Searches for items based on a query parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results for:"),
    })
    public ResponseEntity<String> search(
            @RequestParam(name = "query", defaultValue = "", required = false) @Parameter(description = "Search query") String query) {
        return new ResponseEntity<>("Search results for: " + query, HttpStatus.OK);
    }

}
