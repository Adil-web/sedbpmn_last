package kz.kazgisa.service;

import kz.kazgisa.controller.UserAppController;
import kz.kazgisa.data.dto.MenuCountDto;
import kz.kazgisa.data.dto.report.ReportSubserviceDto;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.CorrespondenceType;
import kz.kazgisa.data.repositories.AppRepository;
import kz.kazgisa.data.repositories.CorrespondenceRepository;
import kz.kazgisa.data.repositories.dict.ServiceRepository;
import kz.kazgisa.data.repositories.dict.SubserviceRepository;
import kz.kazgisa.data.repositories.user.UserMenuRepository;
import kz.kazgisa.utils.CustomDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final AppRepository appRepository;
    private final TaskCustomService taskCustomService;
    private final HistoryCustomService historyCustomService;
    private final UserService userService;
    private final ServiceRepository serviceRepository;
    private final SubserviceRepository subserviceRepository;
    private final CorrespondenceRepository correspondenceRepository;
    private final UserMenuRepository userMenuRepository;
    Logger log = LoggerFactory.getLogger(AnalyticsService.class);


    public AnalyticsService(AppRepository appRepository, TaskCustomService taskCustomService, HistoryCustomService historyCustomService, UserService userService, ServiceRepository serviceRepository, SubserviceRepository subserviceRepository, CorrespondenceRepository correspondenceRepository, UserMenuRepository userMenuRepository) {
        this.appRepository = appRepository;
        this.taskCustomService = taskCustomService;
        this.historyCustomService = historyCustomService;
        this.userService = userService;
        this.serviceRepository = serviceRepository;
        this.subserviceRepository = subserviceRepository;
        this.correspondenceRepository = correspondenceRepository;
        this.userMenuRepository = userMenuRepository;
    }


    public MenuCountDto getMenuAnalytics(String username) {
        List<Subservice> services = userService.getCurrentUserSubservices(username);
        MenuCountDto dto = new MenuCountDto();
        log.info(username);
        dto.incoming = appRepository.countBySubserviceInAndArchSignedFalseAndOpenTrue(services);
        dto.outcoming = appRepository.countBySubserviceInAndFactEndDateIsNotNull(services);
        dto.control = appRepository.countBySubserviceInAndControlAndArchSignedFalse(services, username);
        dto.allTasks = taskCustomService.getUserTasksCount(services, null, null, null);
        dto.currentTasks = taskCustomService.getUserTasksCount(services, username, null, null);
        dto.finishedTasks = historyCustomService.getUserTasksCount(services, username, true);
        dto.incomingCorrespondences = correspondenceRepository.countByType(CorrespondenceType.IN);
        dto.outcomingCorrespondences = correspondenceRepository.countByType(CorrespondenceType.OUT);
        dto.unassignedTasks = taskCustomService.getUserUnassignedTasks(services);
        User user = userService.getUserByUserName(username);
        if (user != null)
            dto.access = userMenuRepository.findByUserId(user.getId()).stream().map(m -> m.getMenu().getCode()).collect(Collectors.toList());
        else
            dto.access = new ArrayList<>();

        return dto;
    }

    public List<ReportSubserviceDto> getStatAnalytics(Long subserviceId, Date inStartDate, Date inEndDate) {
        if (inStartDate == null)
            inStartDate = new Date(0);
        if (inEndDate == null)
            inEndDate = new Date();
        Date startDate = CustomDateUtils.getDayStart(inStartDate);
        Date endDate = CustomDateUtils.getDayEnd(inEndDate);

        List<ReportSubserviceDto> list = new ArrayList<>();
        ReportSubserviceDto root = new ReportSubserviceDto();
        List<Long> subservices = new ArrayList<>();
        if (subserviceId == null) {
            subservices.addAll(subserviceRepository.findAll().stream().map(Subservice::getId).collect(Collectors.toList()));
        } else {
            subservices.add(subserviceId);
        }
        root.setApplied(appRepository.countBySubserviceIdInAndSignedDateBetweenAndOpenTrue(subservices, startDate, endDate));
        root.setApproved(appRepository.countBySubserviceIdInAndSignedDateBetweenAndApprovedTrueAndFactEndDateIsNotNull(subservices, startDate, endDate));
        root.setRejected(appRepository.countBySubserviceIdInAndSignedDateBetweenAndApprovedIsNullAndFactEndDateIsNotNull(subservices, startDate, endDate));
        root.setRegistered(appRepository.countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndFactEndDateIsNull(subservices, startDate, endDate));
        root.setId(0L);
        root.setNameRu("Все");
        root.setHighlight(true);
        list.add(root);
        serviceRepository.findAll().forEach(service -> {
            ReportSubserviceDto r = new ReportSubserviceDto();
            r.setApplied(appRepository.countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndSubserviceService(subservices, startDate, endDate, service));
            r.setApproved(appRepository.countBySubserviceIdInAndSignedDateBetweenAndApprovedTrueAndFactEndDateIsNotNullAndSubserviceService(subservices, startDate, endDate, service));
            r.setRejected(appRepository.countBySubserviceIdInAndSignedDateBetweenAndApprovedIsNullAndFactEndDateIsNotNullAndSubserviceService(subservices, startDate, endDate, service));
            r.setRegistered(appRepository.countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndFactEndDateIsNullAndSubserviceService(subservices, startDate, endDate, service));
            r.setId(service.getId());
            r.setNameRu(service.getNameRu());
            r.setHighlight(true);
            list.add(r);
            List<Subservice> subserviceList = subserviceRepository.findByServiceIdOrderByPriority(service.getId());
            if (subserviceList.size() > 1 && service.getCode() != null) {
                subserviceList.forEach(subservice -> {
                    ReportSubserviceDto s = new ReportSubserviceDto();
                    s.setApplied(appRepository.countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndSubservice(subservices, startDate, endDate, subservice));
                    s.setApproved(appRepository.countBySubserviceIdInAndSignedDateBetweenAndApprovedTrueAndFactEndDateIsNotNullAndSubservice(subservices, startDate, endDate, subservice));
                    s.setRejected(appRepository.countBySubserviceIdInAndSignedDateBetweenAndApprovedIsNullAndFactEndDateIsNotNullAndSubservice(subservices, startDate, endDate, subservice));
                    s.setRegistered(appRepository.countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndFactEndDateIsNullAndSubservice(subservices, startDate, endDate, subservice));
                    s.setId(subservice.getId());
                    s.setNameRu(subservice.getNameRu());
                    s.setHighlight(false);
                    list.add(s);
                });
            }
        });
        return list;

    }
}
