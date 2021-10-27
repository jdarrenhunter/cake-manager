package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.dto.CakeDtoMapper;
import com.waracle.cakemgr.dto.ErrorDto;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@ApiIgnore
public class UserInterfaceController {

    @Resource(name="restProxyCakeService")
    private final CakeService cakeService;
    private final CakeDtoMapper cakeDtoMapper;
    private final ObjectMapper objectMapper;

    public UserInterfaceController(CakeService cakeService, CakeDtoMapper cakeDtoMapper, ObjectMapper objectMapper) {
        this.cakeService = cakeService;
        this.cakeDtoMapper = cakeDtoMapper;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listCakes(Model model) {
        model.addAttribute("cakes", cakeService.getCakes());
        model.addAttribute("newCake", CakeDto.builder().build());
        return "cakes";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createCake(@ModelAttribute("newCake") CakeDto cake, Model model) {
        cakeService.addCake(cakeDtoMapper.toCake(cake));
        model.addAttribute("successMessage",
                String.format("Cake with Title %s successfully created!", cake.getTitle()));
        return listCakes(model);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public String handleClientError(WebClientResponseException ex, Model model) throws IOException {
        ErrorDto dto = objectMapper.readValue(ex.getResponseBodyAsByteArray(), ErrorDto.class);
        model.addAttribute("errorMessage", errorMessageFrom(dto.getMessage()));
        return listCakes(model);
    }

    private String errorMessageFrom(String message) {
        if(message.contains("Input validation error(s)")) {
            return Set.of("Title", "Desc", "Image").stream()
                    .filter(field -> message.contains(field))
                    .map(field -> String.format("Invalid %s field", field))
                    .collect(Collectors.joining("  |  "));
        } else {
            return message;
        }
    }

}