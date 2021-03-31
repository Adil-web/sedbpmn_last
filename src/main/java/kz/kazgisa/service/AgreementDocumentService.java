package kz.kazgisa.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import kz.kazgisa.data.dto.FileDto;
import kz.kazgisa.data.dto.TaskDto;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.enums.FileCategory;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class AgreementDocumentService implements JavaDelegate {
    private final FileService fileService;
    private final SubserviceService subserviceService;
    private final HistoryCustomService historyCustomService;
    private final RoleService roleService;
    private final AppService appService;

    BaseFont baseFont = BaseFont.createFont("/usr/share/fonts/times-new-roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    BaseFont arialFont = BaseFont.createFont("/usr/share/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    //BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    //BaseFont arialFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    Font fontHead = new Font(baseFont, 14, Font.BOLD);
    Font font10 = new Font(baseFont, 10);
    Font font14 = new Font(baseFont, 14);

    public AgreementDocumentService(FileService fileService,
                                    SubserviceService subserviceService,
                                    HistoryCustomService historyCustomService,
                                    RoleService roleService,
                                    AppService appService) throws IOException, DocumentException {
        this.fileService = fileService;
        this.subserviceService = subserviceService;
        this.historyCustomService = historyCustomService;
        this.roleService = roleService;
        this.appService = appService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long id = ((Number) delegateExecution.getVariable("appId")).longValue();
        App app = appService.getById(id);
        byte[] generatedDocument = generateAgreementDocument(id, true);
        if (generatedDocument != null) {
            FileDto fileDto = fileService.uploadFile(generatedDocument, "agreement_document.pdf", FileCategory.AGREEMENT_DOCUMENT);
            AppFile file = new AppFile();
            file.setName(fileDto.fileName);
            file.setObjectId(fileDto.uid);
            file.setContentType(fileDto.contentType);
            file.setSize(fileDto.fileSize);
            file.setApp(app);
            file.setFileCategory(FileCategory.AGREEMENT_DOCUMENT);
            appService.saveAppFile(file);
        }
    }

    public byte[] getGeneratedAgreementDocument(Long appId) {
        List<AppFile> appFiles = appService.getAppFiles(appId);
        for(AppFile appFile : appFiles) {
            if(appFile.getFileCategory() == FileCategory.AGREEMENT_DOCUMENT) {
                return fileService.downloadFile(appFile.getObjectId());
            }
        }
        return null;
    }

    public byte[] generateAgreementDocument(Long appId, Boolean finalDoc) {
        App app = appService.getById(appId);
        Document document = new Document(PageSize.A4, 0, 0, 30, 80);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        List<TaskDto> tasks = historyCustomService.getAppHistory(appId, 25L);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setFullCompression();
            if (finalDoc) {
                Image qr = generateDownloadQrCode(appId);
                FooterEvent event = new FooterEvent(qr);
                writer.setPageEvent(event);
            }

            document.open();

            PdfPTable centerTable = new PdfPTable(1);
            centerTable.setTotalWidth(300);
            centerTable.setLockedWidth(true);
            PdfPCell centerText = new PdfPCell(new Phrase("Атырау қалалық әкімдігі қаулысы жобасының\n" +
                    "КЕЛІСУ ПАРАҒЫ", fontHead));
            centerText.setBorder(Rectangle.NO_BORDER);
            centerText.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            centerText.setPaddingTop(50);
            centerTable.addCell(centerText);
            document.add(centerTable);

            UserRole[] cnMembers = getCnUserRoles();

            PdfPTable appAndOzoRukTable = new PdfPTable(1);
            PdfPCell idCell = new PdfPCell(new Phrase("\n" +
                    "Тiркеу № " + appId + "\n" +
                    "", font14));
            idCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            idCell.setBorder(Rectangle.NO_BORDER);
            appAndOzoRukTable.addCell(idCell);
            String appProjectName = app.getProjectName() != null ? app.getProjectName() : "";
            String appProjectAddress = app.getProjectAddress() != null ? app.getProjectAddress() : "";
            PdfPCell text = new PdfPCell(new Phrase("\n" +
                    "    1.Жоба атауы: " + appProjectName + "\n\n" +
                    appProjectAddress + "\n\n" +
                    "    2. Қала  әкімдігі  қаулысының  жобасын   дайындауға жауапты құрылымдық бөлімі: " +
                    cnMembers[0].getUser().getPositionKk() + " (" +
                    cnMembers[0].getUser().getFirstName() + " " + cnMembers[0].getUser().getLastName() +
                    ")\n\n" +
                    "", font14));
            text.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
            text.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            text.setBorder(Rectangle.NO_BORDER);
            appAndOzoRukTable.addCell(text);
            if (cnMembers[0] != null) {
                String ozoDateStr = "";
                Date ozoDate = getUserApprovedDate(tasks, cnMembers[0].getUser().getUsername(), cnMembers[0].getRole().getName());
                if (ozoDate != null) {
                    ozoDateStr = sdf.format(ozoDate);
                    PdfPCell ozoConfirmText = new PdfPCell(new Phrase(ozoDateStr + ": "
                            + cnMembers[0].getUser().getFirstName() + " " + cnMembers[0].getUser().getLastName() + " "
                            + (cnMembers[0].getUser().getSecondName() != null ? cnMembers[0].getUser().getSecondName() : "")
                            + " - келісілді\n", font10));
                    ozoConfirmText.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
                    ozoConfirmText.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                    ozoConfirmText.setBorder(Rectangle.NO_BORDER);
                    appAndOzoRukTable.addCell(ozoConfirmText);
                }
            }
            PdfPCell text3 = new PdfPCell(new Phrase("\n" +
                    "    3. Қала әкімдігі қаулысының жобасына келісім алу:\n\n", font14));
            text3.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
            text3.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            text3.setBorder(Rectangle.NO_BORDER);
            appAndOzoRukTable.addCell(text3);
            document.add(appAndOzoRukTable);

            PdfPTable akimatTable = new PdfPTable(3);
            akimatTable.setWidths(new float[]{1, 10, 10});
            PdfPCell numberCell = new PdfPCell(new Phrase("№\n\n", font14));
            akimatTable.addCell(numberCell);
            PdfPCell userCell = new PdfPCell(new Phrase("Басшының аты-жөні, лауазымы", font14));
            akimatTable.addCell(userCell);
            PdfPCell approveCell = new PdfPCell(new Phrase("Басшының келісім берген күні", font14));
            akimatTable.addCell(approveCell);
            for (int i = 1; i < 5; i++) {
                UserRole member = cnMembers[i];
                if (member != null) {
                    PdfPCell numberCell1 = new PdfPCell(new Phrase(i + "", font14));
                    akimatTable.addCell(numberCell1);
                    PdfPCell userCell1 = new PdfPCell(new Phrase(member.getUser().getFirstName() + " " + member.getUser().getLastName() +
                            " - " + member.getUser().getPositionKk(), font14));
                    akimatTable.addCell(userCell1);
                    String akimatDateStr = "";
                    Date akimatDate;
                    if(finalDoc && i == 1) {
                        akimatDate = new Date();
                    } else {
                        akimatDate = getUserApprovedDate(tasks, member.getUser().getUsername(), member.getRole().getName());
                    }
                    if (akimatDate != null) {
                        akimatDateStr = sdf.format(akimatDate) + ": "
                                + member.getUser().getLastName() + " " + member.getUser().getFirstName() + " "
                                + (member.getUser().getSecondName() != null ? member.getUser().getSecondName() : "")
                                + " - келісілді";
                    }
                    PdfPCell approveCell1 = new PdfPCell(new Phrase(akimatDateStr, font10));
                    akimatTable.addCell(approveCell1);
                }
            }
            document.add(akimatTable);

            PdfPTable notesTable = new PdfPTable(1);
            PdfPCell notesCell = new PdfPCell(new Phrase("\n" +
                    "    4. Жоба бойынша ескертпелер және кім енгізді:\n" +
                    "_______________________________________________________________________________" +
                    "_______________________________________________________________________________" +
                    "___________________________________________", font14));
            notesCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
            notesCell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            notesCell.setBorder(Rectangle.NO_BORDER);
            notesTable.addCell(notesCell);
            document.add(notesTable);

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateAgreementDocumentDocx(Long appId, Boolean finalDoc) {
        App app = appService.getById(appId);
        XWPFDocument  document = new XWPFDocument();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        List<TaskDto> tasks = historyCustomService.getAppHistory(appId, 25L);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setTop(BigInteger.valueOf(1100L));
            pageMar.setBottom(BigInteger.valueOf(1440L));

            XWPFParagraph paragraph1 = document.createParagraph();
            paragraph1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run1 = setRun(paragraph1);
            run1.setBold(true);
            run1.setText("Атырау қалалық әкімдігі қаулысы жобасының");
            run1.addBreak();
            run1.setText("КЕЛІСУ ПАРАҒЫ");
            run1.addBreak();

            XWPFParagraph paragraph2 = document.createParagraph();
            paragraph2.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun run2 = setRun(paragraph2);
            run2.setText("Тiркеу № " + appId);
            run2.addBreak();
            run2.addBreak();

            UserRole[] cnMembers = getCnUserRoles();
            String appProjectName = app.getProjectName() != null ? app.getProjectName() : "";
            String appProjectAddress = app.getProjectAddress() != null ? app.getProjectAddress() : "";

            XWPFParagraph paragraph3 = document.createParagraph();
            paragraph3.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run3 = setRun(paragraph3);
            run3.setText("1. Жоба атауы: " + appProjectName);
            run3.addBreak();
            run3.setText(appProjectAddress);
            run3.addBreak();
            run3.addBreak();
            run3.setText("2. Қала  әкімдігі  қаулысының  жобасын   дайындауға жауапты құрылымдық бөлімі: " +
                    cnMembers[0].getUser().getPositionKk() + " (" +
                    cnMembers[0].getUser().getFirstName() + " " + cnMembers[0].getUser().getLastName() +
                    ")");
            run3.addBreak();
            run3.addBreak();

            if (cnMembers[0] != null) {
                String ozoDateStr = "";
                Date ozoDate = getUserApprovedDate(tasks, cnMembers[0].getUser().getUsername(), cnMembers[0].getRole().getName());
                if (ozoDate != null) {
                    ozoDateStr = sdf.format(ozoDate);
                    XWPFParagraph paragraph4 = document.createParagraph();
                    paragraph4.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun run4 = setRun(paragraph4);
                    run4.setFontSize(10);
                    run4.setText(ozoDateStr + ": "
                            + cnMembers[0].getUser().getFirstName() + " " + cnMembers[0].getUser().getLastName() + " "
                            + (cnMembers[0].getUser().getSecondName() != null ? cnMembers[0].getUser().getSecondName() : "")
                            + " - келісілді");
                    run4.addBreak();
                    run4.addBreak();
                }
            }

            XWPFParagraph paragraph5 = document.createParagraph();
            paragraph5.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run5 = setRun(paragraph5);
            run5.setText("3. Қала әкімдігі қаулысының жобасына келісім алу:");
            run5.addBreak();

            XWPFTable table = document.createTable(5, 3);
            table.setWidth(65*144);
            table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(720));
            for (int col = 1 ; col < 3; col++) {
                table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(3*1440));
            }

            XWPFParagraph numberCell = table.getRow(0).getCell(0).getParagraphs().get(0);
            numberCell.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun numberCellRun = setRun(numberCell);
            numberCellRun.setText("№");
            numberCellRun.addBreak();

            XWPFParagraph memberCell = table.getRow(0).getCell(1).getParagraphs().get(0);
            memberCell.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun memberCellRun = setRun(memberCell);
            memberCellRun.setText("Басшының аты-жөні, лауазымы");
            memberCellRun.addBreak();

            XWPFParagraph dateCell= table.getRow(0).getCell(2).getParagraphs().get(0);
            dateCell.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun dateCellRun = setRun(dateCell);
            dateCellRun.setText("Басшының келісім берген күні");
            dateCellRun.addBreak();

            for (int i = 1; i < 5; i++) {
                UserRole member = cnMembers[i];
                if (member != null) {
                    XWPFParagraph number = table.getRow(i).getCell(0).getParagraphs().get(0);
                    number.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun numRun = setRun(number);
                    numRun.setText(String.valueOf(i));

                    XWPFParagraph memb = table.getRow(i).getCell(1).getParagraphs().get(0);
                    memb.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun memRun = setRun(memb);
                    memRun.setText(member.getUser().getFirstName() + " " + member.getUser().getLastName() +
                            " - " + member.getUser().getPositionKk());

                    String akimatDateStr = "";
                    Date akimatDate;
                    if(finalDoc && i == 1) {
                        akimatDate = new Date();
                    } else {
                        akimatDate = getUserApprovedDate(tasks, member.getUser().getUsername(), member.getRole().getName());
                    }
                    if (akimatDate != null) {
                        akimatDateStr = sdf.format(akimatDate) + ": "
                                + member.getUser().getLastName() + " " + member.getUser().getFirstName() + " "
                                + (member.getUser().getSecondName() != null ? member.getUser().getSecondName() : "")
                                + " - келісілді";
                    }

                    XWPFParagraph membDate = table.getRow(i).getCell(1).getParagraphs().get(0);
                    membDate.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun dateRun = setRun(membDate);
                    dateRun.setFontSize(10);
                    dateRun.setText(akimatDateStr);
                }
            }

            XWPFParagraph paragraph6 = document.createParagraph();
            paragraph6.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run6 = setRun(paragraph6);
            run6.addBreak();
            run6.setText("4. Жоба бойынша ескертпелер және кім енгізді:");
            run6.addBreak();
            run6.setText("__________________________________________________________________");
            run6.addBreak();
            run6.setText("__________________________________________________________________");
            run6.addBreak();
            run6.setText("__________________________________________________________________");

            if (finalDoc) {
                CTSectPr sectPrFooter = document.getDocument().getBody().addNewSectPr();
                XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPrFooter);
                XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
                XWPFParagraph paragraph = footer.getParagraphArray(0);
                if (paragraph == null) paragraph = footer.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
                tabStop.setVal(STTabJc.LEFT);
                tabStop.setPos(BigInteger.valueOf(6 * 1440));

                XWPFRun run = paragraph.createRun();
                java.awt.Image qr = generateDownloadQrCodeImage(appId);

                BufferedImage bImage= new BufferedImage(qr.getWidth(null), qr.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g = bImage.createGraphics();
                g.drawImage(qr, 0, 0, null);
                g.dispose();

                File tmp = new File("tmp.png");
                ImageIO.write(bImage, "png", tmp);
                FileInputStream pic = new FileInputStream(tmp);
                run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, "QRcode", Units.toEMU(70), Units.toEMU(70));
                pic.close();
            }

            document.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public XWPFRun setRun (XWPFParagraph paragraph) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(14);
        run.setFontFamily("Times New Roman");
        return run;
    }

    public UserRole[] getCnUserRoles() {
        String[] cnRoles = new String[]{"CN_OZO_HEAD", "CN_ZAMAKIM", "CN_RUK_APPARAT", "CN_JURIST", "CN_LINGVIST"};
        List<UserRole> members = subserviceService.getSubserviceMembersById(25L, null);
        UserRole[] akimatMembers = new UserRole[5];
        for (int i = 0; i < cnRoles.length; i++) {
            for (UserRole member : members) {
                if (member.getCurrent() &&
                        member.getRole() != null &&
                        member.getRole().getName().equals(cnRoles[i])
                ) {
                    akimatMembers[i] = member;
                    break;
                }
            }
        }
        return akimatMembers;
    }

    public Date getUserApprovedDate(List<TaskDto> tasks, String username, String roleName) {
        boolean firstZamakimPassed = false;
        for (int i = 0; i < tasks.size(); i++) {
            TaskDto task = tasks.get(i);
            if (task.getAssignee().equals(username) && task.getEndTime() != null) {
                if (roleName.equals("CN_ZAMAKIM") && !firstZamakimPassed) {
                    firstZamakimPassed = true;
                    continue;
                }
                return task.getEndTime();
            }
        }
        return null;
    }

    public Image generateDownloadQrCode(Long appId) throws DocumentException {
        BarcodeQRCode qrCode = new BarcodeQRCode("http://bp.eatyrau.kz/sedapi/userapp/cn/agreement/" + appId, 1, 1, new HashMap<EncodeHintType, Object>() {
        });
        Image image = qrCode.getImage();
        image.scaleAbsolute(100, 100);
        return image;
    }

    public java.awt.Image generateDownloadQrCodeImage(Long appId) throws DocumentException {
        BarcodeQRCode qrCode = new BarcodeQRCode("http://bp.eatyrau.kz/sedapi/userapp/" + appId + "/cn/agreement/docx", 1, 1, new HashMap<EncodeHintType, Object>() {
        });
        return qrCode.createAwtImage(Color.BLACK, Color.WHITE);
    }


    private class FooterEvent extends PdfPageEventHelper {
        protected PdfPTable table;

        public FooterEvent(Image qr) throws IOException, DocumentException {
            table = new PdfPTable(1);
            table.setTotalWidth(300);
            qr.scaleAbsoluteWidth(70);
            qr.scaleAbsoluteHeight(70);
            PdfPCell cell = new PdfPCell(qr);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(50);
            cell.setPaddingLeft(0);
            table.addCell(cell);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            table.writeSelectedRows(0, -1,
                    document.left() + 30,
                    document.bottom() + 60,
                    writer.getDirectContent());
        }
    }
}
