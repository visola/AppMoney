package com.appmoney.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.ForecastDao;
import com.appmoney.model.Forecast;
import com.appmoney.model.User;

@RestController
@RequestMapping("/api/v1/forecasts")
public class ForecastController {

  @Autowired
  ForecastDao forecastDao;

  @RequestMapping(method=RequestMethod.GET)
  public Forecast getUserForecast(@AuthenticationPrincipal User user) {
    Optional<Forecast> maybeForecast = forecastDao.getForUser(user.getId());
    if (maybeForecast.isPresent()) {
      return maybeForecast.get();
    }
    return new Forecast();
  }

}
