package com.appmoney.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.ForecastDao;
import com.appmoney.model.Forecast;
import com.appmoney.model.ForecastEntry;
import com.appmoney.model.Permission;
import com.appmoney.model.User;
import com.appmoney.repository.ForecastEntryRepository;

@RestController
@RequestMapping("/api/v1/forecast_entries")
public class ForecastEntryController {

  @Autowired
  ForecastEntryRepository forecastEntryRepository;

  @Autowired
  ForecastDao forecastDao;

  @RequestMapping(method=RequestMethod.GET)
  public List<ForecastEntry> getEntries(@AuthenticationPrincipal User user) {
    Optional<Forecast> forecastForUser = forecastDao.getForUser(user.getId());
    if (forecastForUser.isPresent()) {
      return forecastEntryRepository.findByForecastId(forecastForUser.get().getId());
    }

    return new ArrayList<>();
  }

  @RequestMapping(method=RequestMethod.POST)
  @Transactional
  public ForecastEntry createEntry(@RequestBody @Valid ForecastEntry entry, @AuthenticationPrincipal User user) {
    Forecast forecast = ensureForecast(user.getId());

    entry.setForecastId(forecast.getId());

    entry.setCreated(new Date());
    entry.setCreatedBy(user.getId());

    entry.setUpdated(new Date());
    entry.setUpdatedBy(user.getId());

    return forecastEntryRepository.save(entry);
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{id}")
  @Transactional
  public ForecastEntry updateEntry(@PathVariable int id,
                                           @RequestBody @Valid ForecastEntry entry,
                                           @AuthenticationPrincipal User user) {

    ForecastEntry loaded = forecastEntryRepository.findOne(id);
    Optional<Forecast> forecast = forecastDao.findById(loaded.getForecastId(), user.getId());

    if (!forecast.isPresent()) {
      throw new AccessDeniedException("You do not have permissions to update this forecast.");
    }

    entry.setId(loaded.getId());
    entry.setForecastId(forecast.get().getId());

    entry.setCreated(loaded.getCreated());
    entry.setCreatedBy(loaded.getCreatedBy());

    entry.setUpdated(new Date());
    entry.setUpdatedBy(user.getId());

    return forecastEntryRepository.save(entry);
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
