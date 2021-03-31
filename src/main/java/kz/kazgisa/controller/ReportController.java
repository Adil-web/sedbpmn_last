package kz.kazgisa.controller;

import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.data.dto.TaskDto;
import kz.kazgisa.data.dto.report.ReportByExecutorDto;
import kz.kazgisa.data.entity.AdmDocument;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.Correspondence;
import kz.kazgisa.data.entity.contract.Contract;
import kz.kazgisa.data.entity.contract.ContractExecution;
import kz.kazgisa.data.entity.dict.AppComment;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.Role;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.CorrespondenceType;
import kz.kazgisa.data.repositories.dict.AppCommentRepository;
import kz.kazgisa.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final AppService appService;
    private final ReportService reportService;
    private final CorrespondenceService service;
    private final AdmDocumentService admService;
    private final ContractService contractService;
    private final ContractExecutionService contractExecutionService;
    private final TaskCustomService taskCustomService;
    private final HistoryCustomService historyCustomService;
    private final UserService userService;
    private final SubserviceService subserviceService;
    private final RoleService roleService;
    private final AppCommentRepository appCommentRepository;
    public ReportController (AppService appService, ReportService reportService,
                             CorrespondenceService service, AdmDocumentService admService,
                             ContractService contractService, ContractExecutionService
                             contractExecutionService, TaskCustomService taskCustomService,
                             HistoryCustomService historyCustomService, UserService userService,
                             SubserviceService subserviceService, RoleService roleService,
                             AppCommentRepository appCommentRepository) {
        this.appService = appService;
        this.reportService = reportService;
        this.service = service;
        this.admService = admService;
        this.contractService = contractService;
        this.contractExecutionService = contractExecutionService;
        this.taskCustomService = taskCustomService;
        this.historyCustomService = historyCustomService;
        this.userService = userService;
        this.subserviceService = subserviceService;
        this.roleService = roleService;
        this.appCommentRepository =appCommentRepository;
    }

    @PostMapping("/export-gosuslugi") // Госуслуги(поступившие), Госуслуги завершенные, Госуслуги на контроле, Госуслуги на исполнении, Мои задачи - назначенные
    public ResponseEntity<?> getExcelGos( @RequestBody List<SearchCriteriaDto> list,
                                         Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        String[] sort = pageable.getSort().toString().split(":");

        if (list.isEmpty() || (list.get(0).getKey() != null && !list.get(0).getKey().equals("archSigned")) || list.get(0).getKey().equals("assignee")) {
            try {
                Page<TaskDto> data = taskCustomService.filterUserTasks(list, allPages, sort[0]);
                List<TaskDto> dataList = data.getContent();
                byte[] file = reportService.exportReportGosUslugiTask(dataList, list);
                return headersSet(file);
            } catch (Exception e) {
                e.printStackTrace();
            }} else if (list.get(0).getKey().equals("archSigned")) {
        try {
            Page<App> data = appService.searchWithFilter(list, allPages);
            List<App> dataList = data.getContent();
            byte[] file = reportService.exportReportGosUslugi(dataList, list);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/export-finishedtasks") // Мои задачи - исполненные
    public ResponseEntity<?> getExcelFinishedTasks(@RequestBody List<SearchCriteriaDto> list, Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<TaskDto> data = historyCustomService.filterUserTasks(list, allPages);
            List<TaskDto> dataList = data.getContent();
            byte[] file = reportService.exportReportGosUslugiFinishedTasks(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-correspondences") // Журнал входящих и Журнал Исходящих
    public ResponseEntity<?> getExcelCorrespondences(@RequestParam CorrespondenceType type, Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<Correspondence> data = service.getAll(type, allPages);
            List<Correspondence> dataList = data.getContent();
            byte[] file = reportService.exportReportCorrespondences(dataList, type);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-admdocuments") // Административные документы
    public ResponseEntity<?> exportReportAdmDocuments(Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<AdmDocument> data = admService.getAll(allPages);
            List<AdmDocument> dataList = data.getContent();
            byte[] file = reportService.exportReportAdmDocuments(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/export-contracts") // Реестр договоров
    public ResponseEntity<?> exportReportContracts(Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<Contract> data = contractService.getContracts(allPages);
            List<Contract> dataList = data.getContent();
            byte[] file = reportService.exportReportContracts(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-contractsexe") // Исполнение договора
    public ResponseEntity<?> exportReportContractExecutions(Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<ContractExecution> data = contractExecutionService.getContractExecutions(allPages);
            List<ContractExecution> dataList = data.getContent();
            byte[] file = reportService.exportReportContractExecutions(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-users") // Справочник пользователей
    public ResponseEntity<?> exportReportUsers() throws Exception {
        try {
            List<User> data = userService.getUsers();
            byte[] file = reportService.exportReportUsers(data);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-subservices") // Справочник услуг
    public ResponseEntity<?> exportReportSubServices(@RequestParam(required = false) String name, Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<Subservice> data = subserviceService.getSubservices(name, allPages);
            List<Subservice> dataList = data.getContent();
            byte[] file = reportService.exportReportSubServices(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-roles") // Справочник ролей
    public ResponseEntity<?> exportReportRoles(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) Long subserviceId,
                                               Pageable pageable) throws Exception {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<Role> data = roleService.getRoles(name, subserviceId, allPages);
            List<Role> dataList = data.getContent();
            byte[] file = reportService.exportReportRoles(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-comments") // Справочник замечаний
    public ResponseEntity<?> exportReportRoles() throws Exception {
        try {
            List<AppComment> dataList = appCommentRepository.findAll();
            byte[] file = reportService.exportReportAppComments(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-executorstats") // Отчет по исполнителям
    public ResponseEntity<?> getExcelExecutors(@RequestParam (required = false) Long from,
                                               @RequestParam (required = false) Long to) throws Exception {
        try {
            if(from == null) {
                from = LocalDate.of(2019,1,1).atStartOfDay(ZoneId.systemDefault())
                        .toInstant().toEpochMilli();
            }
            if(to == null) {
                to = LocalDate.of(2025,1,1).atStartOfDay(ZoneId.systemDefault())
                        .toInstant().toEpochMilli();
            }
            List<ReportByExecutorDto> dataList = historyCustomService.getTaskStats(from, to);
            byte[] file = reportService.exportReportExecutorTasks(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> headersSet (byte[] file) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8"));
        headers.setContentLength(file.length);
        String encoded = URLEncoder.encode("Information", "utf-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".xlsx\"");
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<?> getReportTypes() {
        return ResponseEntity.ok(reportService.getReportTypes());
    }

    @PostMapping("/code/{code}")
    public ResponseEntity<?> getReportByCode(@PathVariable String code,
                                             @RequestBody List<SearchCriteriaDto> criteriaList,
                                             Pageable pageable) {
        return ResponseEntity.ok(reportService.getReportByCode(code, criteriaList, pageable));
    }

    @PostMapping("/code/{code}/report")
    public ResponseEntity<?> getExcelReportByCode(@PathVariable String code,
                                                  @RequestBody List<SearchCriteriaDto> criteriaList,
                                                  Pageable pageable) {
        Pageable allPages = PageRequest.of(0, 100000, pageable.getSort());
        try {
            Page<TaskDto> data = reportService.getReportByCode(code, criteriaList, allPages);
            List<TaskDto> dataList = data.getContent();
            byte[] file = reportService.exportReportByCode(dataList);
            return headersSet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

