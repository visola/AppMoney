package com.appmoney.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.Forecast;
import com.appmoney.model.ForecastPermission;
import com.appmoney.model.User;
import com.appmoney.repository.ForecastPermissionRepository;

@RestController
@RequestMapping("/api/v1/forecasts")
public class ForecastController {

  @Autowired
  ForecastPermissionRepository forecastPermissionRepository;

  @RequestMapping(method=RequestMethod.GET)
  public Forecast getUserForecast(@AuthenticationPrincipal User user) {
    Optional<Forecast> maybeForecast = forecastPermissionRepository.findByUserId(user.getId()).map(ForecastPermission::getForecast);
    if (maybeForecast.isPresent()) {
      return maybeForecast.get();
    }
    return new Forecast();
  }

}
