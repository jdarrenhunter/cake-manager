package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.dto.CakeDtoMapper;
import com.waracle.cakemgr.dto.ErrorDto;
import com.waracle.cakemgr.exception.DuplicateCakeException;
import com.waracle.cakemgr.service.CakeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    private final CakeService cakeService;
    private final CakeDtoMapper cakeDtoMapper;

    public CakeController(CakeService cakeService, CakeDtoMapper cakeDtoMapper) {
        this.cakeService = cakeService;
        this.cakeDtoMapper = cakeDtoMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method=RequestMethod.GET)
    @ApiOperation(value = "View all cakes", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all cakes"),
            @ApiResponse(code = 500, message = "Server failed to process the request")
    }
    )
    public List<CakeDto> getCakes() {
        List<Cake> cakes = cakeService.getCakes();
        return cakes.stream()
                .map(cakeDtoMapper::fromCake)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    @ApiOperation(value = "Add a new cake", response = CakeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added a new cake"),
            @ApiResponse(code = 400, message = "Cake field(s) invalid"),
            @ApiResponse(code = 409, message = "Cake not added because it already exists"),
            @ApiResponse(code = 500, message = "Server failed to process the request")
    }
    )
    public CakeDto addCake(@Valid @RequestBody CakeDto cakeDto) {
        Optional<Cake> cake = cakeService.addCake(cakeDtoMapper.toCake(cakeDto));

        if (cake.isPresent()) {
            return cakeDtoMapper.fromCake(cake.get());
        } else {
            throw new DuplicateCakeException(String.format("%s not added because it already exists", cakeDto.getTitle()));
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateCakeException.class)
    public ErrorDto handleDuplicateException(DuplicateCakeException ex) {
        return ErrorDto.builder()
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ErrorDto.builder().message(String.format("Input validation error(s) [%s]", errors)).build();
    }

}