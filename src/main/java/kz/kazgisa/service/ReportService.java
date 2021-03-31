package kz.kazgisa.service;

import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.data.dto.TaskDto;
import kz.kazgisa.data.dto.report.ReportByExecutorDto;
import kz.kazgisa.data.entity.AdmDocument;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.Correspondence;
import kz.kazgisa.data.entity.ReportType;
import kz.kazgisa.data.entity.contract.Contract;
import kz.kazgisa.data.entity.contract.ContractExecution;
import kz.kazgisa.data.entity.dict.AppComment;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.Role;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.CorrespondenceType;
import kz.kazgisa.data.enums.SearchOperation;
import kz.kazgisa.data.repositories.AppRepository;
import kz.kazgisa.data.repositories.ReportTypeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private ReportTypeRepository reportTypeRepository;
    private HistoryCustomService historyCustomService;
    private AppRepository appRepository;

    public ReportService(ReportTypeRepository reportTypeRepository,
                         HistoryCustomService historyCustomService,
                         AppRepository appRepository) {
        this.reportTypeRepository = reportTypeRepository;
        this.historyCustomService = historyCustomService;
        this.appRepository = appRepository;
    }

    public byte[] exportReportGosUslugi(List<App> data, List<SearchCriteriaDto> list) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("GosUslugi");
            String sheetName = "";
            String val = "";
            int dataRow = 4;
            if ((list.get(0).getKey()).equals("archSigned") && (list.get(1).getKey()).equals("open")) {
            sheetName = "Госуслуги (поступившие)";
            List<String> columns = Arrays.asList("№", "Рег номер заявки", "Дата поступления", "ИИН|БИН", "Заявитель", "Тип запроса", "Срок исполнения");
            createStyles(workbook, sheet, sheetName, columns);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                row.createCell(1).setCellValue(data.get(i).getId() != null ? data.get(i).getId().toString() : val);
                row.createCell(2).setCellValue(data.get(i).getCreateDate() != null ? sdf.format(data.get(i).getCreateDate()) : val);
                if (data.get(i).getBin() != null) {
                    row.createCell(3).setCellValue(data.get(i).getIin() + "|" + data.get(i).getBin());
                } else { row.createCell(3).setCellValue(data.get(i).getIin()); }
                row.createCell(4).setCellValue(data.get(i).getFirstName() + " " + data.get(i).getLastName());
                row.createCell(5).setCellValue(data.get(i).getSubservice().getShortNameRu() != null ? data.get(i).getSubservice().getShortNameRu() : val);
                row.createCell(6).setCellValue(data.get(i).getPlanEndDate() != null ? sdf.format(data.get(i).getPlanEndDate()) : val);
                }
                autoSize (sheet, columns);
            }
            if ((list.get(0).getKey()).equals("archSigned") && list.get(0).getValue().equals(true)) {
                sheetName = "Госуслуги завершенные";
                List<String> columns = Arrays.asList("№", "Рег номер заявки", "Дата поступления", "Заявитель", "Тип запроса", "Срок исполнения", "Статус", "Факт исполнения");
                createStyles(workbook, sheet, sheetName, columns);
                for (int i =0; i < data.size(); i++) {
                    Row row = sheet.createRow(dataRow++);
                    Cell nCell = row.createCell(0);
                    nCell.setCellValue(i + 1);
                    row.createCell(1).setCellValue(data.get(i).getId() != null ? data.get(i).getId().toString() : val);
                    row.createCell(2).setCellValue(data.get(i).getCreateDate() != null ? data.get(i).getCreateDate().toString() : val);
                    row.createCell(3).setCellValue(data.get(i).getFirstName() + " " + data.get(i).getLastName());
                    row.createCell(4).setCellValue(data.get(i).getSubservice().getShortNameRu() != null ? data.get(i).getSubservice().getShortNameRu() : val);
                    row.createCell(5).setCellValue(data.get(i).getPlanEndDate() != null ? data.get(i).getPlanEndDate().toString() : val);
                    row.createCell(6).setCellValue(data.get(i).getApproved() != null ? data.get(i).getApproved().toString() : val);
                    row.createCell(7).setCellValue(data.get(i).getFactEndDate()!= null ? data.get(i).getFactEndDate().toString() : val);
                }
                autoSize (sheet, columns);
            }
            if ((list.get(0).getKey()).equals("archSigned") && (list.get(1).getKey()).equals("control")) {
                sheetName = "У меня на контроле";
                List<String> columns = Arrays.asList("№", "Рег номер заявки", "Дата поступления", "Заявитель", "Тип запроса", "Срок исполнения", "Отв. исполнитель", "Текущии исполнитель", "Задача", "Статус");
                createStyles(workbook, sheet, sheetName, columns);
                for (int i =0; i < data.size(); i++) {
                    Row row = sheet.createRow(dataRow++);
                    Cell nCell = row.createCell(0);
                    nCell.setCellValue(i + 1);
                    row.createCell(1).setCellValue(data.get(i).getId() != null ? data.get(i).getId().toString() : val);
                    row.createCell(2).setCellValue(data.get(i).getCreateDate() != null ? data.get(i).getCreateDate().toString() : val);
                    row.createCell(3).setCellValue(data.get(i).getFirstName() + " " + data.get(i).getLastName());
                    row.createCell(4).setCellValue(data.get(i).getSubservice().getShortNameRu() != null ? data.get(i).getSubservice().getShortNameRu() : val);
                    row.createCell(5).setCellValue(data.get(i).getPlanEndDate() != null ? data.get(i).getPlanEndDate().toString() : val);
                    row.createCell(6).setCellValue(data.get(i).getCurrentExecutor() != null ? data.get(i).getCurrentExecutor() : val);
                    row.createCell(7).setCellValue(data.get(i).getCurrentExecutor() != null ? data.get(i).getCurrentExecutor() : val);
                    row.createCell(8).setCellValue(data.get(i).getCurrentTaskName() != null ? data.get(i).getCurrentTaskName() : val);
                    row.createCell(9).setCellValue(data.get(i).getApproved() != null ? data.get(i).getApproved().toString() : val);
                }
                autoSize (sheet, columns);
            }
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportGosUslugiTask(List<TaskDto> data, List<SearchCriteriaDto> list) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("GosUslugi");
            String sheetName = "";
            String val = "";
            int dataRow = 4;
            if (list.isEmpty() || ((list.get(0).getKey() != null) && !list.get(0).getKey().equals("assignee"))) {
                sheetName = "Госуслуги на исполнении";
                List<String> columns = Arrays.asList("№", "Рег номер заявки", "Дата поступления", "ИИН|БИН", "Заявитель", "Тип запроса", "Задача", "Отв. исполнитель", "Срок исполнения", "Статус");
                createStyles(workbook, sheet, sheetName, columns);
                for (int i =0; i < data.size(); i++) {
                    Row row = sheet.createRow(dataRow++);
                    Cell nCell = row.createCell(0);
                    nCell.setCellValue(i + 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    row.createCell(1).setCellValue(data.get(i).getContent().get("regAppId") != null ? data.get(i).getContent().get("regAppId").toString() : val);
                    row.createCell(2).setCellValue(data.get(i).getCreateTime() != null ? sdf.format(data.get(i).getCreateTime()) : val);
                    if (data.get(i).getContent().get("iin") != null && data.get(i).getContent().get("bin") != null) {
                        row.createCell(3).setCellValue(data.get(i).getContent().get("iin").toString() + "|" + data.get(i).getContent().get("bin").toString());
                    } else if (data.get(i).getContent().get("iin") != null || data.get(i).getContent().get("bin") != null) {
                        row.createCell(3).setCellValue(data.get(i).getContent().get("iin") != null ? data.get(i).getContent().get("iin").toString() : data.get(i).getContent().get("bin").toString());
                    } else {
                        row.createCell(3).setCellValue("");
                    }
                    row.createCell(4).setCellValue(data.get(i).getContent().get("firstName") + " " + data.get(i).getContent().get("lastName"));
                    row.createCell(5).setCellValue(data.get(i).getContent().get("subserviceType") != null ? data.get(i).getContent().get("subserviceType").toString() : val);
                    row.createCell(6).setCellValue(data.get(i).getName() != null ? data.get(i).getName() : val);
                    row.createCell(7).setCellValue(data.get(i).getContent().get("executorName") != null ? data.get(i).getContent().get("executorName").toString() : val);
                    row.createCell(8).setCellValue(data.get(i).getContent().get("dueDate") != null ? data.get(i).getContent().get("dueDate").toString() : val);
                    row.createCell(9).setCellValue(data.get(i).getContent().get("approved") != null ? data.get(i).getContent().get("approved").toString() : val);
                }
                autoSize (sheet, columns);
            } else  if (list.get(0).getKey().equals("assignee")) {
                sheetName = "Мои задачи - назначенные";
                List<String> columns = Arrays.asList("№", "Рег номер заявки", "Дата поступления", "Заявитель", "Тип запроса", "Задача", "Срок исполнения", "Статус");
                createStyles(workbook, sheet, sheetName, columns);
                for (int i =0; i < data.size(); i++) {
                    Row row = sheet.createRow(dataRow++);
                    Cell nCell = row.createCell(0);
                    nCell.setCellValue(i + 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    row.createCell(1).setCellValue(data.get(i).getContent().get("regAppId") != null ? data.get(i).getContent().get("regAppId").toString() : val);
                    row.createCell(2).setCellValue(data.get(i).getCreateTime() != null ? sdf.format(data.get(i).getCreateTime()) : val);
                    row.createCell(3).setCellValue(data.get(i).getContent().get("firstName") + " " + data.get(i).getContent().get("lastName"));
                    row.createCell(4).setCellValue(data.get(i).getContent().get("subserviceType") != null ? data.get(i).getContent().get("subserviceType").toString() : val);
                    row.createCell(5).setCellValue(data.get(i).getName() != null ? data.get(i).getName() : val);
                    row.createCell(6).setCellValue(data.get(i).getContent().get("dueDate") != null ? data.get(i).getContent().get("dueDate").toString() : val);
                    row.createCell(7).setCellValue(data.get(i).getContent().get("approved") != null ? data.get(i).getContent().get("approved").toString() : val);
                }
                autoSize (sheet, columns);
            }
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportGosUslugiFinishedTasks(List<TaskDto> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("GosUslugi");
            String sheetName = "Мои задачи - исполненные";
            String val = "";
            int dataRow = 4;
                List<String> columns = Arrays.asList("№", "Рег номер заявки", "Дата поступления", "Заявитель", "Тип запроса", "Задача", "Срок исполнения", "Дата получения этапа", "Дата исполнения этапа",  "Статус");
                createStyles(workbook, sheet, sheetName, columns);
                for (int i =0; i < data.size(); i++) {
                    Row row = sheet.createRow(dataRow++);
                    Cell nCell = row.createCell(0);
                    nCell.setCellValue(i + 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    row.createCell(1).setCellValue(data.get(i).getContent().get("regAppId") != null ? data.get(i).getContent().get("regAppId").toString() : val);
                    row.createCell(2).setCellValue(data.get(i).getCreateTime() != null ? sdf.format(data.get(i).getCreateTime()) : val);
                    row.createCell(3).setCellValue(data.get(i).getContent().get("firstName") + " " + data.get(i).getContent().get("lastName"));
                    row.createCell(4).setCellValue(data.get(i).getContent().get("subserviceType") != null ? data.get(i).getContent().get("subserviceType").toString() : val);
                    row.createCell(5).setCellValue(data.get(i).getName() != null ? data.get(i).getName() : val);
                    row.createCell(6).setCellValue(data.get(i).getContent().get("dueDate") != null ? data.get(i).getContent().get("dueDate").toString() : val);
                    row.createCell(7).setCellValue(data.get(i).getStartTime() != null ? sdf.format(data.get(i).getStartTime()) : val);
                    row.createCell(8).setCellValue(data.get(i).getEndTime() != null ? sdf.format(data.get(i).getEndTime()) : val);
                    row.createCell(9).setCellValue(data.get(i).getContent().get("approved") != null ? data.get(i).getContent().get("approved").toString() : val);
                }
                autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportCorrespondences(List<Correspondence> data, CorrespondenceType type) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Correspondences");
            List<String> columns = Arrays.asList("№", "Номер", "Дата регистрации", "Категория", "Отправитель");
            String sheetName = type == CorrespondenceType.IN ? "ЖУРНАЛ ВХОДЯЩИХ" : "ЖУРНАЛ ИСХОДЯЩИХ";

            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getId() != null ? data.get(i).getId().toString() : val);
                row.createCell(2).setCellValue(data.get(i).getRegDate() != null ? data.get(i).getRegDate().toString().substring(0, 16) : val);
                row.createCell(3).setCellValue(data.get(i).getCategory().getNameRu() != null ? data.get(i).getCategory().getNameRu() : val);
                row.createCell(4).setCellValue(data.get(i).getSender() != null ? data.get(i).getSender() : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportAdmDocuments(List<AdmDocument> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("AdmDocuments");
            List<String> columns = Arrays.asList("№", "Номер", "Дата регистрации", "Категория", "Отправитель");
            String sheetName = "Административные документы";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getId() != null ? data.get(i).getId().toString() : val);
                row.createCell(2).setCellValue(data.get(i).getDate() != null ? data.get(i).getDate().toString().substring(0, 16) : val);
                row.createCell(3).setCellValue(data.get(i).getCategory().getNameRu() != null ? data.get(i).getCategory().getNameRu() : val);
                row.createCell(4).setCellValue(data.get(i).getEmployee() != null ?data.get(i).getEmployee().getFirstName() + " " + data.get(i).getEmployee().getLastName() : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportContracts(List<Contract> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Contracts");
            List<String> columns = Arrays.asList("№", "Предмет договора", "Номер договора", "Дата договора", "Заказчик (ФИО / Наименование ЮЛ)", "Сумма", "Автор", "Срок исполнения");
            String sheetName = "Реестр договоров";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getId() != null ? data.get(i).getSubject().getNameRu() : val);
                row.createCell(2).setCellValue(data.get(i).getNumber() != null ? data.get(i).getNumber() : val);
                row.createCell(3).setCellValue(data.get(i).getCreateDate() != null ? data.get(i).getCreateDate().toString().substring(0, 16) : val);
                row.createCell(4).setCellValue(data.get(i).getCustomer().getLastName() + " " + data.get(i).getCustomer().getFirstName());
                row.createCell(5).setCellValue(data.get(i).getSum() != null ? data.get(i).getSum().toString() : val);
                row.createCell(6).setCellValue(data.get(i).getAuthor() != null ? (data.get(i).getAuthor().getFirstName() + " " + data.get(i).getAuthor().getLastName()) : val);
                row.createCell(7).setCellValue(data.get(i).getDueDate() != null ? data.get(i).getDueDate().toString().substring(0, 16) : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportContractExecutions(List<ContractExecution> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("ContractExecutions");
            List<String> columns = Arrays.asList("№", "Предмет договора", "Исполнено", "Сумма фактического исполнения", "Сумма","Срок фактического исполнения");
            String sheetName = "Исполнения договора";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getContract().getSubject().getNameRu() != null ? data.get(i).getContract().getSubject().getNameRu() : val);
                row.createCell(2).setCellValue(data.get(i).getExecuted() != null ? data.get(i).getExecuted().toString() : val);
                row.createCell(3).setCellValue(data.get(i).getFactSum() != null ? data.get(i).getFactSum().toString() : val);
                row.createCell(4).setCellValue(data.get(i).getSum() != null ? data.get(i).getSum().toString() : val);
                row.createCell(5).setCellValue(data.get(i).getDueDate() != null ? data.get(i).getDueDate().toString() : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportUsers(List<User> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");
            List<String> columns = Arrays.asList("№", "Логин", "Email", "ФИО", "Должность", "Организация", "Роли и права");
            String sheetName = "Справочник пользователей";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                String roles = "";
                if (data.get(i).getRoles() != null) {
                for (int j = 0; j < data.get(i).getRoles().size(); j++) {
                    roles = roles + data.get(i).getRoles().get(j).getShortNameRu() + " " + System.lineSeparator();
                }}
                row.createCell(1).setCellValue(data.get(i).getUsername() != null ? data.get(i).getUsername() : val);
                row.createCell(2).setCellValue(data.get(i).getEmail() != null ? data.get(i).getEmail() : val);
                row.createCell(3).setCellValue(data.get(i).getFirstName() + " " + data.get(i).getLastName());
                row.createCell(4).setCellValue(data.get(i).getPositionRu() != null ? data.get(i).getPositionRu() : val);
                row.createCell(5).setCellValue(data.get(i).getOrganization().getNameRu() != null ? data.get(i).getOrganization().getNameRu() : val);
                row.createCell(6).setCellValue(data.get(i).getRoles() != null ? roles : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportSubServices(List<Subservice> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Subservices");
            List<String> columns = Arrays.asList("№", "Код БП", "Краткое наименование", "Полное наименование", "Комментарий");
            String sheetName = "Справочник услуг";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getService().getCode() != null ? data.get(i).getService().getCode() : val);
                row.createCell(2).setCellValue(data.get(i).getShortNameRu() != null ? data.get(i).getShortNameRu() : val);
                row.createCell(3).setCellValue(data.get(i).getNameRu() != null ? data.get(i).getNameRu() : val);
                row.createCell(4).setCellValue(""); //data.get(i).getDescription()
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportRoles(List<Role> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Roles");
            List<String> columns = Arrays.asList("№", "Код роли", "Краткое наименование", "Полное наименование", "Комментарий");
            String sheetName = "Справочник ролей";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getName() != null ? data.get(i).getName(): val);
                row.createCell(2).setCellValue(data.get(i).getShortNameRu() != null ? data.get(i).getShortNameRu() : val);
                row.createCell(3).setCellValue(data.get(i).getNameRu() != null ? data.get(i).getNameRu() : val);
                row.createCell(4).setCellValue(data.get(i).getDescription() != null ? data.get(i).getDescription() : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportAppComments(List<AppComment> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("AppComments");
            List<String> columns = Arrays.asList("№", "Атауы", "Наименование");
            String sheetName = "Справочник замечаний";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getNameKk() != null ? data.get(i).getNameKk(): val);
                row.createCell(2).setCellValue(data.get(i).getNameRu() != null ? data.get(i).getNameRu() : val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] exportReportExecutorTasks(List<ReportByExecutorDto> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Executors");
            List<String> columns = Arrays.asList("№", "Количество", "Роль", "Пользователь", "Услуга");
            String sheetName = "Отчет по исполнителям";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i =0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                row.createCell(1).setCellValue(data.get(i).getCount() != null ? data.get(i).getCount().toString(): val);
                row.createCell(2).setCellValue(data.get(i).getUserRole().getRole().getShortNameRu() != null ? data.get(i).getUserRole().getRole().getShortNameRu() : val);
                row.createCell(3).setCellValue(data.get(i).getUserRole().getUser().getFirstName() + " " + data.get(i).getUserRole().getUser().getLastName());
                row.createCell(4).setCellValue(data.get(i).getUserRole().getSubservice().getShortNameRu() != null ? data.get(i).getUserRole().getSubservice().getShortNameRu(): val);
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createStyles (Workbook workbook, Sheet sheet, String sheetName, List<String> columns) {
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Times New Roman");
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row nameRow = sheet.createRow(1);
        Cell appName = nameRow.createCell(1);
        appName.setCellStyle(headerCellStyle);
        appName.setCellValue(sheetName);

        Row headerRow = sheet.createRow(3);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(headerCellStyle);
        }

    }

    public void autoSize (Sheet sheet, List<String> columns) {
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public byte[] streamBos (Workbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bos.close();
            workbook.close();
        }
        return bos.toByteArray();
    }

    public List<ReportType> getReportTypes() {
        return reportTypeRepository.findAll();
    }

    public Page<TaskDto> getReportByCode(String code, List<SearchCriteriaDto> additionalCriteriaList, Pageable pageable) {
        if(code.equals("R01v1")) {
            List<SearchCriteriaDto> criteriaList = new ArrayList<>();
//            criteriaList.add(new SearchCriteriaDto("finished", true, SearchOperation.EQUAL));

            if (additionalCriteriaList != null && additionalCriteriaList.size() > 0) {
                criteriaList.addAll(additionalCriteriaList);
            }

            Page<TaskDto> taskPage = historyCustomService.filterUserTasksForReport(criteriaList, pageable);

            for(TaskDto taskDto : taskPage.getContent()) {
                Map<String, Object> variables = taskDto.getContent();
                App app = appRepository.findById(Long.valueOf(variables.get("appId").toString())).orElse(null);
                if(app != null) {
                    if(app.getObjectInfo() != null) {
                        variables.put("objectName", app.getObjectInfo().getName());
                    }
                }
                taskDto.setContent(variables);
            }

            return taskPage;
        } else {
            return null;
        }
    }

    public byte[] exportReportByCode(List<TaskDto> data) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("ReportsByCode");
            List<String> columns = Arrays.asList("№", "Наименование услуги", "Номер заявки", "Дата поступления", "Дата исполнения", "Заявитель", "Заказчик", "Наименование объекта", "Адрес объекта", "Проектировщик", "Статус");
            String sheetName = "Отчет";
            createStyles(workbook, sheet, sheetName, columns);
            int dataRow = 4;
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(dataRow++);
                Cell nCell = row.createCell(0);
                nCell.setCellValue(i + 1);
                String val = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                row.createCell(1).setCellValue(data.get(i).getContent().get("subserviceType") != null ? data.get(i).getContent().get("subserviceType").toString(): val);
                row.createCell(2).setCellValue(data.get(i).getContent().get("regAppId") != null ? data.get(i).getContent().get("regAppId").toString() : val);
                row.createCell(3).setCellValue(data.get(i).getStartTime() != null ? sdf.format(data.get(i).getStartTime()) : val);
                row.createCell(4).setCellValue(data.get(i).getEndTime() != null ? sdf.format(data.get(i).getEndTime()) : val);
                String zValue = "";
                zValue = data.get(i).getContent().get("iin") != null ? data.get(i).getContent().get("iin").toString() : val;
                zValue = zValue + " " + (data.get(i).getContent().get("firstName") != null ? data.get(i).getContent().get("firstName").toString() : val);
                zValue = zValue + " " + (data.get(i).getContent().get("lastName") != null ? data.get(i).getContent().get("lastName").toString() : val);
                row.createCell(5).setCellValue(zValue != null ? zValue : val);
                String aValue = "";
                aValue = data.get(i).getContent().get("applicantIinBin") != null ? data.get(i).getContent().get("applicantIinBin").toString() : val;
                aValue = aValue + " " + (data.get(i).getContent().get("applicantName") != null ? data.get(i).getContent().get("applicantName").toString() : val);
                row.createCell(6).setCellValue(aValue != null ? aValue : val);
                row.createCell(7).setCellValue(data.get(i).getContent().get("objectName") != null ? data.get(i).getContent().get("objectName").toString() : val);
                row.createCell(8).setCellValue(data.get(i).getContent().get("objectAddress") != null ? data.get(i).getContent().get("objectAddress").toString() : val);
                row.createCell(9).setCellValue(data.get(i).getContent().get("objectDesigner") != null ? data.get(i).getContent().get("objectDesigner").toString() : val);
                if (data.get(i).getContent().containsKey("signedDocument") && data.get(i).getContent().get("signedDocument") != null && data.get(i).getContent().get("signedDocument").equals(true)) {
                    row.createCell(10).setCellValue("+");
                }
            }
            autoSize (sheet, columns);
            return streamBos(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
