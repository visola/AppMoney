package com.appmoney.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.Forecast;
import com.appmoney.model.ForecastEntry;
import com.appmoney.model.ForecastPermission;
import com.appmoney.model.MonthlyForecastEntryAmount;
import com.appmoney.model.Permission;
import com.appmoney.model.User;
import com.appmoney.repository.ForecastEntryRepository;
import com.appmoney.repository.ForecastPermissionRepository;
import com.appmoney.repository.ForecastRepository;
import com.appmoney.repository.MonthlyForecastEntryAmountRepository;

@RestController
@RequestMapping("/api/v1/forecast_entries")
public class ForecastEntryController {

  @Autowired
  ForecastEntryRepository forecastEntryRepository;

  @Autowired
  MonthlyForecastEntryAmountRepository monthlyForecastEntryAmountRepository;

  @Autowired
  ForecastRepository forecastRepository;

  @Autowired
  ForecastPermissionRepository forecastPermissionRepository;

  @RequestMapping(method=RequestMethod.GET)
  public List<ForecastEntryDto> getEntries(@AuthenticationPrincipal User user) {
    Optional<Forecast> forecastForUser = forecastPermissionRepository.findByUserId(user.getId()).map(ForecastPermission::getForecast);
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
    Forecast forecast = ensureForecast(user);

    ForecastEntry entry = new ForecastEntry();
    BeanUtils.copyProperties(entryDto, entry);

    entry.setForecast(forecast);

    Calendar now = Calendar.getInstance();
    entry.setCreated(now);
    entry.setCreatedBy(user);

    entry.setUpdated(now);
    entry.setUpdatedBy(user);

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
    Optional<ForecastPermission> forecastPermission = forecastPermissionRepository.findByForecastIdAndUserId(loaded.getForecast().getId(), user.getId());

    if (!forecastPermission.isPresent()) {
      throw new AccessDeniedException("You do not have permissions to update this forecast.");
    }

    ForecastEntry entry = new ForecastEntry();
    BeanUtils.copyProperties(entryDto, entry);

    entry.setId(loaded.getId());
    entry.setForecast(forecastPermission.get().getForecast());

    entry.setCreated(loaded.getCreated());
    entry.setCreatedBy(loaded.getCreatedBy());

    entry.setUpdated(Calendar.getInstance());
    entry.setUpdatedBy(user);

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

  private Forecast ensureForecast(User user) {
    Optional<Forecast> forecastForUser = forecastPermissionRepository.findByUserId(user.getId()).map(ForecastPermission::getForecast);
    if (forecastForUser.isPresent()) {
      return forecastForUser.get();
    } else {
      Forecast toCreate = forecastRepository.save(new Forecast());
      forecastPermissionRepository.save(new ForecastPermission(user, toCreate, Permission.OWNER));

      Calendar now = Calendar.getInstance();
      toCreate.setCreatedBy(user);
      toCreate.setCreated(now);
      toCreate.setUpdatedBy(user);
      toCreate.setUpdated(now);

      return forecastRepository.save(toCreate);
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
