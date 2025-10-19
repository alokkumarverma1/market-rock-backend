package com.example.dimondinvest.service;

import com.example.dimondinvest.dto.*;
import com.example.dimondinvest.entity.*;
import com.example.dimondinvest.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotesService {

    @Autowired
    private Strategyrepo strategyrepo;
    @Autowired
    private Registerrepo registerrepo;
    @Autowired
    private StrategyLinerepo strategyLinerepo ;

    @Autowired
    private Traderepo traderepo;
    @Autowired
    private Trademonthrepo trademonthrepo;
    @Autowired
    private Tradeyearrepo tradeyearrepo;
    @Autowired
    private Tradedayrepo tradedayrepo;

    // add strategy

    public ResponseEntity<?> addstrategy(AddStrategydto addStrategydto, Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
       List<Strategy> strategyList = new ArrayList<>();
        Strategy strategy = new Strategy();
        strategy.setSname(addStrategydto.getSname());

       List<StrategyLInes> strategyLInes = addStrategydto.getMystrategy().stream().map(d->{
           StrategyLInes strategyLInes1 = new StrategyLInes();
           strategyLInes1.setDetails(d);
           strategyLInes1.setStrategy(strategy);
         return strategyLInes1;
       }).collect(Collectors.toList());

        strategy.setStrategyLInes(strategyLInes);
        strategy.setRegister(register);
        strategyrepo.save(strategy);
        strategyList.add(strategy);
        register.setStrategies(strategyList);
        registerrepo.save(register);
        return ResponseEntity.ok("strategy add sucess");
    }


    // show our strategy
    public ResponseEntity<?> mystrategy(Authentication authentication) {
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<Strategy> strategies = strategyrepo.findAllByRegisterId(register.getId());
        if (strategies == null || strategies.isEmpty()) {
            return ResponseEntity.badRequest().body("Don't have any strategy");
        }
        List<AddStrategydto> addStrategydtoList = strategies.stream().map(strategy -> {
            AddStrategydto dto = new AddStrategydto();
            dto.setId(strategy.getId());
            dto.setSname(strategy.getSname());

            // StrategyLInes -> List<String> convert karo
            List<String> lines = strategy.getStrategyLInes()
                    .stream()
                    .map(StrategyLInes::getDetails)
                    .collect(Collectors.toList());
            dto.setMystrategy(lines);
            return dto;
        }).toList();
        return ResponseEntity.ok(addStrategydtoList);
    }



    // delete strategy
    public boolean deletestrategys(AddStrategydto addStrategydto,Authentication authentication) {
        Optional<Strategy> optionalStrategy = strategyrepo.findById(addStrategydto.getId());
        if (optionalStrategy.isPresent()) {
            strategyrepo.delete(optionalStrategy.get());
            return true; // deleted successfully
        } else {
            return false; // strategy not found
        }
    }


    // loadstrategy
    public ResponseEntity<?> loadstrategys(Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<Strategy> strategies = strategyrepo.findAllByRegisterId(register.getId());
        if(strategies == null){
            return ResponseEntity.badRequest().body("Empty Strategy");
        }
        List<AddStrategydto> addStrategydtoList = strategies.stream().map(d->{
            AddStrategydto addStrategydto = new AddStrategydto();
            addStrategydto.setId(d.getId());
            addStrategydto.setSname(d.getSname());
            return addStrategydto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(addStrategydtoList);
    }

    // add trade
    public ResponseEntity<?> addtrades(Tradedto tradedto, Authentication authentication) {
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        Strategy selectedStrategy = strategyrepo.findAllByRegisterId(register.getId())
                .stream()
                .filter(d -> d.getSname().equals(tradedto.getSname()))
                .findFirst()
                .orElse(null);

        // --- TradeYear ---
        TradeYear tradeYear = tradeyearrepo.findByRegisterAndYear(register, tradedto.getYear())
                .orElseGet(() -> {
                    TradeYear ty = new TradeYear();
                    ty.setYear(tradedto.getYear());
                    ty.setRegister(register);
                    return tradeyearrepo.save(ty);
                });

        // --- Trademonth ---
        Trademonth trademonth = trademonthrepo.findByRegisterAndMonth(register, tradedto.getMonth())
                .orElseGet(() -> {
                    Trademonth tm = new Trademonth();
                    tm.setMonth(tradedto.getMonth());
                    tm.setRegister(register);
                    tm.setTradeYear(tradeYear);
                    return trademonthrepo.save(tm);
                });

        TradeDay tradeDay = tradedayrepo.findByRegisterAndDayname(register, tradedto.getDay())
                .orElseGet(() -> {
                    TradeDay tradeDay1 = new TradeDay();
                    tradeDay1.setDayname(tradedto.getDay());
                    tradeDay1.setTrademonth(trademonth);
                    tradeDay1.setRegister(register);
                    return tradedayrepo.save(tradeDay1);

                });

               // --- Trade ---
        Trade trade = new Trade();
        trade.setTradestatus(tradedto.getTradestatus());
        trade.setStrategy(selectedStrategy);
        trade.setRegister(register);
        trade.setTradeday(tradeDay);
        trade.setTrademonth(trademonth);
        trade.setDay(tradedto.getDay());

        traderepo.save(trade);
        registerrepo.save(register);

        return ResponseEntity.ok("Trade added successfully!");
    }


    // my trade anlaysis
    public ResponseEntity<?> tradehomedata(Authentication authentication) {
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<Trade> trades = traderepo.findAllByRegisterId(register.getId());

        int currentYear = LocalDate.now().getYear();
        String currentMonthName = LocalDate.now().getMonth().name();
        int yearTradeCount = 0;
        List<TradeYear> tradeYears = tradeyearrepo.findAllByRegisterId(register.getId());

        for (TradeYear tradeYear : tradeYears) {
            if (tradeYear.getYear() != null && tradeYear.getYear().equals(String.valueOf(currentYear))) {
                List<Trademonth> tradeMonths = trademonthrepo.findAllByTradeYear_Id(tradeYear.getId());

                for (Trademonth month : tradeMonths) {
                    if (month.getTrades() != null) {
                        yearTradeCount += month.getTrades().size();
                    }
                }
            }
        }
        int monthTradeCount = 0;
        List<Trademonth> allMonths = trademonthrepo.findAllByRegisterId(register.getId());
        for (Trademonth month : allMonths) {
            if (month.getTrades() != null &&
                    month.getMonth() != null &&
                    month.getMonth().equalsIgnoreCase(currentMonthName)) {
                monthTradeCount += month.getTrades().size();
            }
        }
        NoteHeaddto noteHeaddto = new NoteHeaddto();
        noteHeaddto.setAll(trades.size());
        noteHeaddto.setYear(yearTradeCount);
        noteHeaddto.setMonth(monthTradeCount);
        return ResponseEntity.ok(noteHeaddto);
    }

    // all year trades
    public List<YearTradedot> allyeartrades(Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<TradeYear> tradeYears = tradeyearrepo.findAllByRegisterId(register.getId());
        List<YearTradedot> yearTradedots = tradeYears.stream().map( d->{
            YearTradedot yearTradedot = new YearTradedot();
            yearTradedot.setYear(d.getYear());

            List<Trademonth> trademonths = trademonthrepo.findAllByTradeYear_Id(d.getId());
            List<MonthTradedto> monthTradedtoList = trademonths.stream().map(a->{
                MonthTradedto monthTradedto = new MonthTradedto();
                monthTradedto.setMonth(a.getMonth());

                List<TradeDay> tradeDays = tradedayrepo.findAllByTrademonth_Id(a.getId());
                List<DayTradeDto> dayTradeDtos = tradeDays.stream().map(k->{
                    DayTradeDto dayTradeDto = new DayTradeDto();
                    dayTradeDto.setDayname(k.getDayname());
                    List<Trade> trades =traderepo.findAllByTradeday_Id(k.getId());
                    List<Tradedto> tradedtos = trades.stream().map(t->{
                        Tradedto tradedto = new Tradedto();
                        tradedto.setDate(t.getDate());
                        tradedto.setTradestatus(t.getTradestatus());
                        tradedto.setDay(t.getDay());
                        return tradedto;
                    }).toList();
                    dayTradeDto.setTradedtoList(tradedtos);
                    return dayTradeDto;
                }).toList();

             List<Trade> trades =traderepo.findAllByTrademonth_Id(a.getId());
            List<Tradedto> tradedtos = trades.stream().map(t->{
                Tradedto tradedto = new Tradedto();
                tradedto.setDate(t.getDate());
                tradedto.setTradestatus(t.getTradestatus());
                return tradedto;
            }).toList();
                monthTradedto.setDayTradeDtos(dayTradeDtos);
                monthTradedto.setTradedtoList(tradedtos);
               return monthTradedto;
            }).toList();
            yearTradedot.setMonthTradedtoList(monthTradedtoList);
           return yearTradedot;
        }).toList();

        return yearTradedots;
    }

    // my year trade
    public YearTradedot myyeartrade(Authentication authentication) {
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<TradeYear> tradeYears = tradeyearrepo.findAllByRegisterId(register.getId());
        TradeYear latestYear = tradeYears.stream()
                .max(Comparator.comparingInt(y -> Integer.parseInt(y.getYear())))
                .orElse(null);
        if (latestYear == null) {
            return null; // no data
        }
        // Create DTO for latest year
        YearTradedot yearTradedot = new YearTradedot();
        yearTradedot.setYear(latestYear.getYear());
        List<Trademonth> trademonths = trademonthrepo.findAllByTradeYear_Id(latestYear.getId());
        List<MonthTradedto> monthTradedtos = trademonths.stream().map(month -> {
            MonthTradedto monthTradedto = new MonthTradedto();
            monthTradedto.setMonth(month.getMonth());

            List<TradeDay> tradeDays = tradedayrepo.findAllByTrademonth_Id(month.getId());
            List<DayTradeDto> dayTradeDtos = tradeDays.stream().map(k->{
                DayTradeDto dayTradeDto = new DayTradeDto();
                dayTradeDto.setDayname(k.getDayname());
                List<Trade> trades =traderepo.findAllByTradeday_Id(k.getId());
                List<Tradedto> tradedtos = trades.stream().map(t->{
                    Tradedto tradedto = new Tradedto();
                    tradedto.setDate(t.getDate());
                    tradedto.setTradestatus(t.getTradestatus());
                    tradedto.setDay(t.getDay());
                    return tradedto;
                }).toList();
                dayTradeDto.setTradedtoList(tradedtos);
                return dayTradeDto;
            }).toList();

            List<Trade> trades = traderepo.findAllByTrademonth_Id(month.getId());
            List<Tradedto> tradedtos = trades.stream().map(t -> {
                Tradedto tradedto = new Tradedto();
                tradedto.setTradestatus(t.getTradestatus());
                tradedto.setDate(t.getDate());
                return tradedto;
            }).toList();
            monthTradedto.setDayTradeDtos(dayTradeDtos);
            monthTradedto.setTradedtoList(tradedtos);
            return monthTradedto;
        }).toList();
        yearTradedot.setMonthTradedtoList(monthTradedtos);
        return yearTradedot;
    }

    // my month trade
    public ResponseEntity<?> mymonthtrade(Authentication authentication) {
        LocalDate today = LocalDate.now();
        String currentMonthName = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<Trademonth> trademonth = trademonthrepo.findAllByRegisterId(register.getId());
        Trademonth trademonth1 = trademonth.stream().filter(d -> d.getMonth().equals(currentMonthName)).findFirst().orElse(null);
        if(trademonth1 == null){
            return ResponseEntity.badRequest().body("no trade this month");
        }
        MonthTradedto monthTradedto = new MonthTradedto();

        List<TradeDay> tradeDays = trademonth1.getTradeDays();
        List<DayTradeDto> dayTradeDtos = tradeDays.stream().map(k->{
            DayTradeDto dayTradeDto = new DayTradeDto();
            dayTradeDto.setDayname(k.getDayname());
            List<Trade> trades =traderepo.findAllByTradeday_Id(k.getId());
            List<Tradedto> tradedtos = trades.stream().map(t->{
                Tradedto tradedto = new Tradedto();
                tradedto.setDate(t.getDate());
                tradedto.setTradestatus(t.getTradestatus());
                tradedto.setDay(t.getDay());
                return tradedto;
            }).toList();
            dayTradeDto.setTradedtoList(tradedtos);
            return dayTradeDto;
        }).toList();
        List<Tradedto> tradedtos = trademonth1.getTrades().stream().map(d->{
            Tradedto tradedto = new Tradedto();
            tradedto.setTradestatus(d.getTradestatus());
            tradedto.setDate(d.getDate());
          return tradedto;
        }).toList();
        int year = today.getYear();
        monthTradedto.setDayTradeDtos(dayTradeDtos);
        monthTradedto.setYear(year);
        monthTradedto.setMonth(trademonth1.getMonth());
        monthTradedto.setTradedtoList(tradedtos);
        return ResponseEntity.ok(monthTradedto);
    }


}
