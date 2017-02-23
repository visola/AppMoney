package com.appmoney.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import com.appmoney.model.MonthlyForecastEntryAmount;
import com.appmoney.model.Permission;
import com.appmoney.model.User;
import com.appmoney.repository.ForecastEntryRepository;
import com.appmoney.repository.MonthlyForecastEntryAmountRepository;

@RestController
@RequestMapping("/api/v1/forecast_entries")
public class ForecastEntryController {

  @Autowired
  ForecastEntryRepository forecastEntryRepository;

  @Autowired
  MonthlyForecastEntryAmountRepository monthlyForecastEntryAmountRepository;

  @Autowired
  ForecastDao forecastDao;

  @RequestMapping(method=RequestMethod.GET)
  public List<ForecastEntryDto> getEntries(@AuthenticationPrincipal User user) {
    Optional<Forecast> forecastForUser = forecastDao.getForUser(user.getId());
    if (forecastForUser.isPresent()) {
      return monthlyForecastEntryAmountRepository
          .findByForecastId(forecastForUser.get().getId()).stream()
          .collect(Collectors.groupingBy(MonthlyForecastEntryAmount::getForecastEntry))
          .entrySet()
          .stream()
          .map(entry -> this.fromEntry(entry.getKey(), entry.getValue()))
          .collect(Collectors.toList());
    }

    return new ArrayList<>();
  }

  @RequestMapping(method=RequestMethod.POST)
  @Transactional
  public ForecastEntryDto createEntry(@RequestBody @Valid ForecastEntryDto entryDto, @AuthenticationPrincipal User user) {
    Forecast forecast = ensureForecast(user.getId());

    ForecastEntry entry = new ForecastEntry();
    BeanUtils.copyProperties(entryDto, entry);

    entry.setForecastId(forecast.getId());

    entry.setCreated(new Date());
    entry.setCreatedBy(user.getId());

    entry.setUpdated(new Date());
    entry.setUpdatedBy(user.getId());

    forecastEntryRepository.save(entry);

    List<MonthlyForecastEntryAmount> monthlyAmounts = entryDto.getMonthlyAmounts().stream()
        .map( amountDto -> {
          MonthlyForecastEntryAmount amount = new MonthlyForecastEntryAmount();
          BeanUtils.copyProperties(amountDto, amount);

          amount.setForecastEntry(entry);
          return monthlyForecastEntryAmountRepository.save(amount);
        }).collect(Collectors.toList());

    return fromEntry(entry, monthlyAmounts);
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{id}")
  @Transactional
  public ForecastEntryDto updateEntry(@PathVariable int id,
                                           @RequestBody @Valid ForecastEntryDto entryDto,
                                           @AuthenticationPrincipal User user) {

    ForecastEntry loaded = forecastEntryRepository.findOne(id);
    Optional<Forecast> forecast = forecastDao.findById(loaded.getForecastId(), user.getId());

    if (!forecast.isPresent()) {
      throw new AccessDeniedException("You do not have permissions to update this forecast.");
    }

    ForecastEntry entry = new ForecastEntry();
    BeanUtils.copyProperties(entryDto, entry);

    entry.setId(loaded.getId());
    entry.setForecastId(forecast.get().getId());

    entry.setCreated(loaded.getCreated());
    entry.setCreatedBy(loaded.getCreatedBy());

    entry.setUpdated(new Date());
    entry.setUpdatedBy(user.getId());

    forecastEntryRepository.save(entry);

    monthlyForecastEntryAmountRepository.deleteByForecastEntryId(id);

    List<MonthlyForecastEntryAmount> monthlyAmounts = entryDto.getMonthlyAmounts().stream()
        .map( amountDto -> {
          MonthlyForecastEntryAmount amount = new MonthlyForecastEntryAmount();
          BeanUtils.copyProperties(amountDto, amount);

          amount.setForecastEntry(entry);
          return monthlyForecastEntryAmountRepository.save(amount);
        }).collect(Collectors.toList());

    return fromEntry(entry, monthlyAmounts);
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

  private ForecastEntryDto fromEntry(ForecastEntry entry, List<MonthlyForecastEntryAmount> monthlyAmounts) {
    ForecastEntryDto dto = new ForecastEntryDto();
    BeanUtils.copyProperties(entry, dto);
    dto.setMonthlyAmounts(monthlyAmounts.stream()
        .map( a -> {
          MonthlyForecastEntryAmountDto aDto = new MonthlyForecastEntryAmountDto();
          BeanUtils.copyProperties(a, aDto);
          return aDto;
        })
        .collect(Collectors.toList()));
    return dto;
  }

}
