package kz.kazgisa.service;

import kz.kazgisa.data.dto.AppArchDto;
import kz.kazgisa.data.entity.contract.Contract;
import kz.kazgisa.data.repositories.contract.ContractRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MonitoringService {
    @Value("${project.dejurka.url}")
    private String dejurkaUrl;
    @Value("${project.dejurka.secret}")
    private String dejurkaSecret;

    private final ContractRepository contractRepository;

    public MonitoringService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Page<Contract> getContracts(String by,Pageable pageable) {
        if(by==null)
            by = "date";
        if(by.equals("date")) {
            return contractRepository.findByPreFactDateIsNullOrMainFactDateIsNullOrderByDateAsc(pageable)
                    .map(contract -> {
                        if(contract.getPreDate() != null && contract.getPreFactDate()==null) {
                            long diff = new Date().getTime() - contract.getPreDate().getTime();
                            if(diff>0)
                                contract.setPreLateDays(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            else
                                contract.setPreLateDays(0L);
                        } else {
                            contract.setPreLateDays(0L);
                        }
                        if(contract.getMainDate() != null && contract.getMainFactDate()==null) {
                            long diff = new Date().getTime() - contract.getMainDate().getTime();
                            if(diff>0)
                                contract.setMainLateDays(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            else
                                contract.setMainLateDays(0L);
                        } else {
                            contract.setMainLateDays(0L);
                        }
                        return contract;
                    });
        } else if(by.equals("preDate")) {
            return contractRepository
                    .findByPreFactDateIsNullOrMainFactDateIsNull(
                            PageRequest.of(pageable.getPageNumber(),pageable.getPageSize()
                                    , Sort.by(Sort.Order.desc("preFactDate").nullsFirst(),
                                              Sort.Order.asc("preDate"))))
                    .map(contract -> {
                        if(contract.getPreDate() != null && contract.getPreFactDate()==null) {
                            long diff = new Date().getTime() - contract.getPreDate().getTime();
                            if(diff>0)
                                contract.setPreLateDays(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            else
                                contract.setPreLateDays(0L);
                        } else {
                            contract.setPreLateDays(0L);
                        }
                        if(contract.getMainDate() != null && contract.getMainFactDate()==null) {
                            long diff = new Date().getTime() - contract.getMainDate().getTime();
                            if(diff>0)
                                contract.setMainLateDays(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            else
                                contract.setMainLateDays(0L);
                        } else {
                            contract.setMainLateDays(0L);
                        }
                        return contract;
                    });
        } else if(by.equals("mainDate")) {
            return contractRepository.findByPreFactDateIsNullOrMainFactDateIsNull(
                    PageRequest.of(pageable.getPageNumber(),pageable.getPageSize()
                            , Sort.by(Sort.Order.desc("mainFactDate").nullsFirst(),
                                    Sort.Order.asc("mainDate"))))
                    .map(contract -> {
                        if(contract.getPreDate() != null && contract.getPreFactDate()==null) {
                            long diff = new Date().getTime() - contract.getPreDate().getTime();
                            if(diff>0)
                                contract.setPreLateDays(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            else
                                contract.setPreLateDays(0L);
                        } else {
                            contract.setPreLateDays(0L);
                        }
                        if(contract.getMainDate() != null && contract.getMainFactDate()==null) {
                            long diff = new Date().getTime() - contract.getMainDate().getTime();
                            if(diff>0)
                                contract.setMainLateDays(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            else
                                contract.setMainLateDays(0L);
                        } else {
                            contract.setMainLateDays(0L);
                        }
                        return contract;
                    });
        }
        return null;
    }

    public Object getMonitoringZu(AppArchDto filter, Pageable pageable) {
        String where = " id > 0 ";
        if(filter != null) {
            if(filter.getId() != null) {
                where += " and id=" + filter.getId();
            }
            if(filter.getIin() != null && !filter.getIin().equals("")) {
                where += " and lower(iin) like '%" + filter.getIin().toLowerCase() + "%'";
            }
            if(filter.getBin() != null && !filter.getBin().equals("")) {
                where += " and lower(bin) like '%" + filter.getBin().toLowerCase() + "%'";
            }
            if(filter.getFirstname() != null && !filter.getFirstname().equals("")) {
                where += " and lower(firstname) like '%" + filter.getFirstname().toLowerCase() + "%'";
            }
            if(filter.getLastname() != null && !filter.getLastname().equals("")) {
                where += " and lower(lastname) like '%" + filter.getLastname().toLowerCase() + "%'";
            }
            if(filter.getOrgname() != null && !filter.getOrgname().equals("")) {
                where += " and lower(orgname) like '%" + filter.getOrgname().toLowerCase() + "%'";
            }
            if(filter.getAddress() != null && !filter.getAddress().equals("")) {
                where += " and lower(address) like '%" + filter.getAddress().toLowerCase() + "%'";
            }
            if(filter.getCadastrenumber() != null && !filter.getCadastrenumber().equals("")) {
                where += " and lower(cadastrenumber) like '%" + filter.getCadastrenumber().toLowerCase() + "%'";
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("where", where);

        String url = dejurkaUrl + "/dutymap/api/layers/app_arch/ext/objects/filter?geoserverId=1&page=" +
                pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&secret=" + dejurkaSecret;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity(url, map, Object.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public Object getMonitoringZuIntersect(Long gid, Pageable pageable) {
        String url = dejurkaUrl + "/dutymap/api/layers/app_arch/ext/intersect/objects?gid=" + gid + "&page=" +
                pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&secret=" + dejurkaSecret;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }
}
