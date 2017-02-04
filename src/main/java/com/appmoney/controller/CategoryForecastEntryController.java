package com.appmoney.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.CategoryForecastEntryDao;
import com.appmoney.dao.ForecastDao;
import com.appmoney.model.CategoryForecastEntry;
import com.appmoney.model.Forecast;
import com.appmoney.model.Permission;
import com.appmoney.model.User;

@RestController
@RequestMapping("/api/v1/category_forecast_entries")
public class CategoryForecastEntryController {

  @Autowired
  CategoryForecastEntryDao categoryForecastEntryDao;
  
  @Autowired
  ForecastDao forecastDao;

  @RequestMapping(method=RequestMethod.GET)
  public List<CategoryForecastEntry> getEntries(@AuthenticationPrincipal User user) {
    Optional<Forecast> forecastForUser = forecastDao.getForUser(user.getId());
    if (forecastForUser.isPresent()) {
      return categoryForecastEntryDao.getEntries(forecastForUser.get().getId());
    }

    return new ArrayList<>();
  }

  @RequestMapping(method=RequestMethod.POST)
  @Transactional
  public CategoryForecastEntry createEntry(@RequestBody @Valid CategoryForecastEntry entry, @AuthenticationPrincipal User user) {
    Forecast forecast = ensureForecast(user.getId());

    entry.setForecastId(forecast.getId());

    entry.setCreated(new Date());
    entry.setCreatedBy(user.getId());

    entry.setUpdated(new Date());
    entry.setUpdatedBy(user.getId());

    return categoryForecastEntryDao.insert(entry);
  }

  private Forecast ensureForecast(int userId) {
    Optional<Forecast> forecastForUser = forecastDao.getForUser(userId);
    if (forecastForUser.isPresent()) {
      return forecastForUser.get();
    } else {
      Forecast toCreate = new Forecast();
      toCreate.getPermissions().add(Permission.OWNER);

      Date now = new Date();
      toCreate.setCreatedBy(userId);
      toCreate.setCreated(now);
      toCreate.setUpdatedBy(userId);
      toCreate.setUpdated(now);

      return forecastDao.insert(toCreate);
    }
  }

}
