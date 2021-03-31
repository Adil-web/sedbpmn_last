package kz.kazgisa.service;

import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import kz.kazgisa.data.dto.FileDto;
import kz.kazgisa.data.entity.*;
import kz.kazgisa.data.entity.base.FileEntity;
import kz.kazgisa.data.enums.FileCategory;
import kz.kazgisa.data.repositories.*;
import kz.kazgisa.utils.CommonUtils;
import kz.kazgisa.utils.ImageTagProcessor;
import kz.kazgisa.utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PdfGenerateService {

       //BaseFont baseFont = BaseFont.createFont("/usr/share/fonts/truetype/ubuntu-font-family/Ubuntu-R.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    BaseFont baseFont = BaseFont.createFont("/usr/share/fonts/times-new-roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    BaseFont arialFont = BaseFont.createFont("/usr/share/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    //BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    //BaseFont arialFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    Font font = new Font(baseFont, 10);
    Font italic = new Font(baseFont, 12, Font.ITALIC);
    Font italicBold12 = new Font(baseFont, 12, Font.BOLDITALIC);

    Font fontBold = new Font(baseFont, 10, Font.BOLD);
    Font fontUnder = new Font(baseFont, 10, Font.UNDERLINE);
    Font fontHead = new Font(baseFont, 14, Font.BOLD);
    Font fontTitle = new Font(baseFont, 12, Font.BOLD);
    Font font8 = new Font(baseFont, 8);
    Font font10 = new Font(baseFont, 10);
    Font font12 = new Font(baseFont, 12);
    Font font14 = new Font(baseFont, 14);

    Font arialBold = new Font(arialFont, 12, Font.BOLD);
    Font arial = new Font(arialFont, 12);

    //    BaseFont baseFont = BaseFont.createFont("resources/fonts/Times_New_Roman_Normal.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//  \\  Font baseFont;// = FontFactory.getFont("resources/fonts/Times_New_Roman_Normal.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//    public static final String FONTDIR = "resources/fonts";

    @Autowired
    AppRepository appRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    FileService fileService;

    @Autowired
    AppFileRepository appFileRepository;

    @Autowired
    HeaderFileRepository headerFileRepository;

    @Autowired
    UserService userService;

    @Value("${project.path}")
    private String projectPath;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrgTemplateRepository orgTemplateRepository;

    @Autowired
    private AppOrganizationRepository appOrganizationRepository;

    public PdfGenerateService() throws IOException, DocumentException {
//        FontFactory.registerDirectory(FONTDIR);
//        Set<String> fonts = new TreeSet<String>(FontFactory.getRegisteredFonts());
//        for (String fontname : fonts) {
//            System.out.println("FONT: " + fontname);
//            if(fontname.equals("times-roman")) {
//                System.out.println("set times --- " + fontname);
//
//                baseFont = FontFactory.getFont(fontname, BaseFont.CP1257, BaseFont.EMBEDDED);
//            }
//        }
    }

    public Document generatePdfAppRejected(PdfWriter writer, Document document, App app) throws IOException, DocumentException {
        document.open();
        font.setColor(BaseColor.BLACK);
        Paragraph title = new Paragraph("Приложение 5 \n" +
                "к стандарту государственной \n услуги \n" +
                "\"Предоставление исходных \n материалов при разраотке \n проектов строительства и \n реконструкции (перепланировки \n и переоборудования )\" форма", font);
        title.setIndentationLeft(320);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        String fullname = app.getOrgName() == null ? app.getFirstName() + " " + app.getLastName() + " " + app.getSecondName() : app.getOrgName();
        Paragraph head = new Paragraph("  " + fullname + " \n    (Фамилия, имя, отчество (при его наличии) \n" +
                "(далее – ФИО), либо наименование \n" +
                "организации услугополучателя)\n " + app.getAddress() + "\n  (адрес услугополучателя) ", font);
        head.setIndentationLeft(200);
        head.setAlignment(Element.ALIGN_CENTER);
        document.add(head);

        Paragraph receipt = new Paragraph("         Расписка  \n" +
                "                        об отказе в приеме документов", fontBold);

        receipt.setAlignment(Element.ALIGN_CENTER);
        document.add(receipt);

        Paragraph text = new Paragraph("                        Руководствуясь пунктом 2 статьи 20 Закона Республики Казахстан от 15 апреля 2013 \n" +
                "года \"О государственных услугах\", отдел №__ филиалаНекоммерческого акционерного \n" +
                "общества \"Государственная корпорация \"Правительство для граждан\"(указать адрес) \n" +
                "отказывает в приеме документов на оказание государственной услуги (указать наименование \n" +
                "государственной услуги в соответствии со стандартом государственной услуги) ввиду \n" +
                "представления Вами неполного пакета документов согласно перечню, предусмотренному \n" +
                "стандартом государственной услуги, а именно: \n \n                      Наименование отсутствующих документов:" +
                "\n \n 1) ________________________________________; \n" +
                "\n" +
                " 2) ________________________________________; \n" +
                "\n" +
                " 3) …. \n" +
                "\n" +
                " Настоящая расписка составлена в 2 экземплярах, по одному для каждой стороны. \n \n ФИО                                                            (подпись) \n" +
                "(работника Государственной корпорации) \n\n Исполнитель: ФИО_____________ \n" +
                "\n" +
                " Телефон __________ \n" +
                "\n" +
                " Получил: ФИО                        / подпись услугополучателя ", font);
        text.setIndentationLeft(100);
        document.add(text);

        Date date = app.getCurrentStatus().getDate();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        int year = localDate.getYear();
        Paragraph rejectedDate = new Paragraph("\n " + day + "." + month + "." + year + " год", font);
        rejectedDate.setIndentationLeft(100);
        document.add(rejectedDate);
        document.close();
        return document;
    }

    public Document generatePdfApp(PdfWriter writer, Document document, App app) throws IOException, DocumentException {

        document.open();
        font.setColor(BaseColor.BLACK);
        Paragraph title = new Paragraph("Приложение 2 \n" +
                "к стандарту государственной услуги \n" +
                "\"Предоставление исходных \n материалов при разраотке \n проектов строительства и \n реконструкции (перепланировки \n и переоборудования )\" форма", font);
        title.setIndentationLeft(320);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph line = new Paragraph("\n              _ ____________ ____________ ______ \n" +
                "_______ ____________ ____________ \n" +
                "_______ ____________ ____________ ", font);
        line.setIndentationLeft(285);
        document.add(line);

        Paragraph header = new Paragraph("\n Заявление \n о предоставлении исходных материалов / архитектурно - планировочного \n задания и технических условий", fontBold);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        String fullname = app.getOrgName() == null ? app.getFirstName() + " " + app.getLastName() + " " + app.getSecondName() : app.getOrgName();
        Paragraph name = new Paragraph("Наименование заявителя: " + fullname, font);
        name.setIndentationLeft(100);
        document.add(name);

        Paragraph address = new Paragraph("\n Адрес: " + app.getAddress(), font);
        address.setIndentationLeft(100);
        document.add(address);

        Paragraph telefon = new Paragraph("\n Телефон: " + app.getPhone(), font);
        telefon.setIndentationLeft(100);
        document.add(telefon);

        Paragraph customer = new Paragraph("\n Заказчик: " + fullname, font);
        customer.setIndentationLeft(100);
        document.add(customer);

        String objectName = app.getObjectInfo().getName() == null ? "   ___" : app.getObjectInfo().getName();
        Paragraph DesignatedObjectName = new Paragraph("\n Наименование проектируемого объекта: " + objectName, font);
        DesignatedObjectName.setIndentationLeft(100);
        document.add(DesignatedObjectName);

        String objectAdd = app.getObjectInfo().getAddress();
        Paragraph objectAddress = new Paragraph("\n Адрес проектируемого объекта: " + objectAdd, font);
        objectAddress.setIndentationLeft(100);
        document.add(objectAddress);

        Paragraph docs = new Paragraph("\n Прощу Вас выдать", font);
        docs.setIndentationLeft(100);
        document.add(docs);

        Paragraph package1 = new Paragraph("\n \n \n \n Пакет 1 (архитектурно-планировочное задание и технические условия)", font);
        package1.setIndentationLeft(100);
        document.add(package1);

        Paragraph package2 = new Paragraph("\n \n \n  Пакет 2 (архитектурно-планировочное задание, вертикальные планировочные \n " +
                "отметки , выкоприровку из проекта детальной планировки, типовые поперечные профили \n дорог и улиц, " +
                "технические условия , схемы трасс наружных инженерных сетей. )", font);
        package2.setIndentationLeft(100);
        document.add(package2);

        Paragraph matching = new Paragraph("\n           Согласен на использование сведений, составляющих охраняемую законом тайну, \n" +
                "содержащихся в информационных системах ", font);
        matching.setIndentationLeft(100);
        document.add(matching);

        Date date = app.getRegDate();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        int year = localDate.getYear();
        Paragraph currentDate = new Paragraph("\n " + day + "." + month + "." + year + " год", font);
        currentDate.setIndentationLeft(100);
        document.add(currentDate);

        Paragraph passed = new Paragraph("Сдал: ____ ______________ ____ \n              подпись", font);
        passed.setIndentationLeft(100);
        document.add(passed);


        document.newPage();
        Paragraph title2 = new Paragraph("Приложение 3  \n" +
                "к стандарту государственной \n услуги \n" +
                "\"Предоставление исходных \n материалов при разраотке \n проектов строительства и \n реконструкции (перепланировки \n и переоборудования )\" форма", font);
        title2.setIndentationLeft(320);
        title2.setAlignment(Element.ALIGN_CENTER);
        document.add(title2);

        Paragraph header2 = new Paragraph("\n Опросный лист \n для технических условий на подключение к источникам \n инженерного и коммунального обеспечения \n \n", fontBold);
        header2.setAlignment(Element.ALIGN_CENTER);
        document.add(header2);

        PdfPTable questionnaireTable = PdfUtils.getQuestionnaireTable(font, app, fontBold, fullname);
        questionnaireTable.setWidths(new float[]{9, 1});
        document.add(questionnaireTable);
/*
        PdfPTable powerSupplyTable = PdfUtils.getPowerSupplyTable(font, app, fontBold);
        powerSupplyTable.setWidths(new float[]{20, 60, 20});
        document.add(powerSupplyTable);

        PdfPTable waterSystemTable = PdfUtils.getWaterSystemTable(font, app, fontBold);
        waterSystemTable.setWidths(new float[]{20, 15, 10, 10, 5, 15, 7, 10});
        document.add(waterSystemTable);

        PdfPTable canalizationTable = PdfUtils.getCanalizationTable(font, app);
        canalizationTable.setWidths(new float[]{13, 15, 15, 10, 10, 15, 5});
        document.add(canalizationTable);

        PdfPTable heatSupplyTable = PdfUtils.getHeatSupplyTable(font, app);
        heatSupplyTable.setWidths(new float[]{33, 33, 30});
        document.add(heatSupplyTable);

        PdfPTable stormSewerageTable = PdfUtils.getStormSewerageTable(font, app);
        stormSewerageTable.setWidths(new float[]{33, 33, 30});
        document.add(stormSewerageTable);

        PdfPTable telephonizationTable = PdfUtils.getTelephonizationTable(font, app);
        telephonizationTable.setWidths(new float[]{33, 33, 30});
        document.add(telephonizationTable);

        PdfPTable gasSupplyTable = PdfUtils.getGasSupplyTable(font, app);
        gasSupplyTable.setWidths(new float[]{33, 36, 27});
        document.add(gasSupplyTable);*/

        Paragraph interview = new Paragraph("           Примечание * ________________________________________________________________________________\n" +
                "________________________________________________________________________________\n" +
                "________________________________________________________________________________ \n \n " +
                "           * В случае подачи опросного листа субпотребителем, в примечании указывается\n" +
                "согласие потребителя на подключение к его сетям субпотребителя. При этом в согласии\n" +
                "потребителя указываются его данные (физические лица – скрепляют подписью, юридические\n" +
                "лица – подписью и печатью).\n \n Заказчик: \n " + fullname + "\n \n " +
                "                                                                                                                   " + day + "." + month + "." + year + " год", font);
        interview.setIndentationLeft(60);
        document.add(interview);
        document.close();
        return document;
    }

//    public PdfPTable getHeader() {
//        PdfPTable table = new PdfPTable(3);
//        table.setWidthPercentage(100);
//
//        PdfPCell cell = new PdfPCell();
//        cell.setPadding(3);
//        cell.setBorder(0);
//
//        cell.setPhrase(new Phrase("Қазақстан Республикасы \nЖауапкершілігі \nШектеулісеріктестік", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("image", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Республика Казахстан \nТов. с ограниченной \nответственностью", font));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        table.addCell(cell);
//
//        return table;
//    }
//
//    public PdfPTable getRequisits() {
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//
//        PdfPCell cell = new PdfPCell();
//        cell.setPadding(3);
//        cell.setBorder(0);
//
//        cell.setPhrase(new Phrase("Қазақстан Республикасы \nЖауапкершілігі \nШектеулісеріктестік", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Республика Казахстан \nТов. с ограниченной \nответственностью", font));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        table.addCell(cell);
//
//        return table;
//    }

    public byte[] generateByteArrayFile(App app, Long orgId, String message, Boolean qrCode, String type, String mapImage, int fontSize, Principal principal) {
        try {
            Document document = new Document(PageSize.A4, 0, 0, 30, 70);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            if (mapImage != null) {
                app.setMapImage(mapImage);
            }
            generatePdfCommunal(app, writer, document, orgId, message, qrCode, type, mapImage, fontSize);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document generatePdfCommunal(App app, PdfWriter writer, Document document, Long orgId, String message, Boolean qrCode, String type, String imageMap, int fontSize) throws IOException, DocumentException {
        AppOrganization appOrganization = appOrganizationRepository.findFirstByAppIdAndOrganizationId(app.getId(), orgId);
        Image qr = getQRCode(null, appOrganization.getId(), type);
        FooterEvent event = new FooterEvent(qr, font, type);
        writer.setPageEvent(event);
        document.open();

        message = message.replace("<br>", "<br/>");
//        message = message.replace("<span", "<div");
//        message = message.replace("</span>", "</div>");
        ByteArrayOutputStream file = new ByteArrayOutputStream();
        try {

            String headerImage = "";
            if (orgId != 1) {
                headerImage = "<img src='" + projectPath + "/src/main/resources/images/org" + orgId + ".jpg' /><br/><br/>";
            } else if (orgId == 1) {
                headerImage = "<img src='" + projectPath + "/src/main/resources/images/archHeader.jpg' /><br/><br/>";
            }
            String mapImage = "";
            if (imageMap != null) {
                mapImage = "<img src='data:image/jpeg;base64," + imageMap + "' />";
            }

            String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                    "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                    "<body><div style='font-family: Times New Roman; padding: 0 50px;'>" + message + "</div>" + mapImage + "</body></html>";
            document.open();
            InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
            fontProvider.setUseUnicode(true);
            fontProvider.register("fonts/times-new-roman.ttf");

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (orgId != 1 && imageMap != null && !imageMap.equals("null")) {
            byte[] mapImageByteArray = Base64.getDecoder().decode(imageMap);
            Image mapImage = Image.getInstance(mapImageByteArray);
            float width = mapImage.getWidth();
            float height = mapImage.getHeight();
            float ratio = width / height;
            mapImage.scaleAbsoluteWidth(550f);
            mapImage.scaleAbsoluteHeight(500f / ratio);
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            PdfPCell image = new PdfPCell(mapImage);
            image.setPaddingLeft(25);
            image.setPaddingRight(25);
            image.setPaddingTop(25);
            image.setBorder(Rectangle.NO_BORDER);
            table.addCell(image);
            document.add(table);
        }

//        if (orgId != 1 && imageMap != null && !imageMap.equals("null")) {
//            byte[] mapImageByteArray = Base64.getDecoder().decode(imageMap);
//            Image mapImage = Image.getInstance(mapImageByteArray);
//            float width = mapImage.getWidth();
//            float height = mapImage.getHeight();
//            float ratio = width / height;
//            mapImage.scaleAbsoluteWidth(550f);
//            mapImage.scaleAbsoluteHeight(500f / ratio);
//            PdfPTable table = new PdfPTable(1);
//            table.setWidthPercentage(100);
//            PdfPCell image = new PdfPCell(mapImage);
//            image.setPaddingLeft(25);
//            image.setPaddingRight(25);
//            image.setPaddingTop(25);
//            image.setBorder(Rectangle.NO_BORDER);
//            table.addCell(image);
//            document.add(table);
//        }
//
        if (qrCode == true && type == "communal") {
            document.newPage();
            Paragraph titleQrCode = new Paragraph("Qr Code");
            titleQrCode.setIndentationLeft(60);
            document.add(titleQrCode);
            //document.add(getSignQRCode(appService.getCommunalCompressedSignedXml(app.getId(), orgId), 0));
            document.add(getSignQRCode(getCommunalCompressedSignedXml(app.getId(), orgId)));
        } else if (qrCode == true && type == "apz") {
            document.newPage();
            Paragraph titleQrCode = new Paragraph("QR-код содержит данные ЭЦП должностного лица ГУ \"Отдел архитектуры и градостроительства\"", font);
            titleQrCode.setIndentationLeft(100);
            document.add(titleQrCode);
            document.add(getSignQRCode(getArchCompressedSignedXml(app.getId())));
        }

        document.close();
        file.close();
        return document;
    }

    public String getArchCompressedSignedXml(Long id) {
        try {
            Optional<App> opApp = appRepository.findById(id);
            if (!opApp.isPresent()) {
                return null;
            }
            App app = opApp.get();
            if (app != null) {
                if (!app.getArchSigned() || app.getArchSignedXml() == null) {
                    return null;
                }
                String xml = app.getArchSignedXml();
                String compressed = CommonUtils.compressString(xml);
                return compressed;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getCommunalCompressedSignedXml(Long appId, Long orgId) {
        try {
            AppOrganization appOrganization = appOrganizationRepository.findFirstByAppIdAndOrganizationId(appId, orgId);
            if (appOrganization == null) {
                return null;
            }
            if (!appOrganization.getSigned() || appOrganization.getSignedXml() == null) {
                return null;
            }
            String xml = appOrganization.getSignedXml();
            String compressed = CommonUtils.compressString(xml);

            return compressed;
        } catch (NullPointerException e) {
            return null;
        }
    }

    //  http://localhost:3000/eqyzmet/api/pdf/generate/apz/24
    /*public byte[] generatePdfApz(Apz apz, Boolean qrCode) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 0, 0, 30, 80);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setFullCompression();
            Image qr = getQRCode(apz.getId(), null, "apz");
            FooterEvent event = new FooterEvent(qr, font, "apz");
            writer.setPageEvent(event);

            document.open();
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();


            Map<String, String> areaCharInfoMap = (Map<String, String>) gson.fromJson(apz.getAreaCharInfo(), map.getClass());
            Map<String, String> objectCharInfoMap = (Map<String, String>) gson.fromJson(apz.getObjectCharInfo(), map.getClass());
            Map<String, String> gradReqInfoMap = (Map<String, String>) gson.fromJson(apz.getGradReqInfo(), map.getClass());
            Map<String, String> archReqInfoMap = (Map<String, String>) gson.fromJson(apz.getArchReqInfo(), map.getClass());
            Map<String, String> exteriorReqInfoMap = (Map<String, String>) gson.fromJson(apz.getExteriorReqInfo(), map.getClass());
            Map<String, String> engNetworkReqInfoMap = (Map<String, String>) gson.fromJson(apz.getEngNetworkReqInfo(), map.getClass());
            Map<String, String> devCommitmentInfoMap = (Map<String, String>) gson.fromJson(apz.getDevCommitmentInfo(), map.getClass());


            PdfUtils.getMainPage(document, apz, apz.getApp(), fontTitle, fontHead, font12);
            PdfPTable areaCharInfoTable = PdfUtils.getAreaCharInfoTable(areaCharInfoMap, font, fontTitle, fontHead);
            PdfPTable objectCharInfoTable = PdfUtils.getObjectCharInfoTable(objectCharInfoMap, font, fontTitle, fontHead);
            PdfPTable gradReqInfoTable = PdfUtils.getGradReqInfoTable(gradReqInfoMap, font, fontTitle, fontHead);
            PdfPTable archReqInfoTable = PdfUtils.getArchReqInfoTable(archReqInfoMap, font, fontTitle, fontHead);
            PdfPTable exteriorReqInfoTable = PdfUtils.getExteriorReqInfoTable(exteriorReqInfoMap, font, fontTitle, fontHead);
            PdfPTable engNetworkReqInfoTable = PdfUtils.getEngNetworkReqInfoTable(engNetworkReqInfoMap, font, fontTitle, fontHead);
            PdfPTable devCommitmentInfo = PdfUtils.getDevCommitmentInfoTable(devCommitmentInfoMap, font, fontTitle, fontHead);
            PdfPTable notesInfo = PdfUtils.getNotesInfoTable(fontTitle, font12);

            document.add(areaCharInfoTable);
            document.add(objectCharInfoTable);
            document.add(gradReqInfoTable);
            document.add(archReqInfoTable);
            document.add(exteriorReqInfoTable);
            document.add(engNetworkReqInfoTable);
            document.add(devCommitmentInfo);
            document.newPage();
            document.add(notesInfo);
            if (qrCode == true) {
                if (apz.getApp() != null) {
                    document.newPage();
                    Paragraph titleQrCode = new Paragraph("QR-код содержит данные ЭЦП должностного лица ГУ \"Отдел архитектуры и градостроительства\"", font);
                    titleQrCode.setIndentationLeft(100);
                    document.add(titleQrCode);
                    document.add(getSignQRCode(appService.getArchCompressedSignedXml(apz.getApp().getId()), 0));
                }
            }


            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public Image getQRCodeApplication(Long appId) throws DocumentException {
        BarcodeQRCode qrCode;
        App app = appRepository.findById(appId).get();
        qrCode = new BarcodeQRCode("http://bp.eatyrau.kz/sedapi/userapp/" + app.getId() + "/application/pdf", 1, 1, new HashMap<EncodeHintType, Object>() {
        });

        Image image = qrCode.getImage();
        image.scaleAbsolute(50, 50);
//                startX += qrCodeSize;
//                image.setAbsolutePosition(startX, offsetBottom);
        return image;
    }

    public Image getQRCode(Long apzId, Long appOrgId, String type) throws DocumentException {
        BarcodeQRCode qrCode;
        if (type == "apz") {
            qrCode = new BarcodeQRCode("http://eatyrau.kz/eqyzmet/api/pdf/download/apz/" + apzId, 1, 1, new HashMap<EncodeHintType, Object>() {
            });
        } else {
            qrCode = new BarcodeQRCode("http://eatyrau.kz/eqyzmet/api/pdf/generate/tech/" + appOrgId, 1, 1, new HashMap<EncodeHintType, Object>() {
            });
        }

        Image image = qrCode.getImage();
        image.scaleAbsolute(50, 50);
//                startX += qrCodeSize;
//                image.setAbsolutePosition(startX, offsetBottom);
        return image;
    }

    public Image getQRCodeEskiz(Long appId) throws DocumentException {
        BarcodeQRCode qrCode;
        qrCode = new BarcodeQRCode("http://eatyrau.kz/eqyzmet/api/pdf/eskiz/" + appId, 1, 1, new HashMap<EncodeHintType, Object>() {
        });

        Image image = qrCode.getImage();
        image.scaleAbsolute(50, 50);
//                startX += qrCodeSize;
//                image.setAbsolutePosition(startX, offsetBottom);
        return image;
    }

    public Image getQRCodeUzp(long appId) throws DocumentException {
        BarcodeQRCode qrCode;
        qrCode = new BarcodeQRCode("http://bp.eatyrau.kz/sedapi/userapp/" + appId + "/download/uzp", 1, 1, new HashMap<EncodeHintType, Object>() {
        });
        Image image = qrCode.getImage();
        image.scaleAbsolute(50, 50);
        return image;
    }


//    private String addNoise(String text) {
//        int noise = (BLOCK_SIZE - text.length() % BLOCK_SIZE) % BLOCK_SIZE;
//        if (noise > NOISE_PREFIX.length()) {
//            text += NOISE_PREFIX + RandomUtils.generateDataStream(noise - NOISE_PREFIX.length());
//        }
//        return text;
//    }

    public PdfPTable getSignQRCode(String sign) {
        try {
            java.util.List<String> blockList = kz.kazgisa.utils.StringUtils.splitIntoBlocks(sign, 400);
            PdfPTable table = new PdfPTable(5);
            Date currentDate = new Date();
            int index = 0;
            Integer qrSize = (5 - (blockList.size() % 5));

            for (String block : blockList) {
                index++;
                PdfPCell cell = new PdfPCell();
                cell.setBorder(0);
                String xml = "<?xml version=\"1.0\" encoding=\"windows-1251\"?><BarCode Date=\"" + currentDate + "\" Total=\"" + blockList.size() + "\" Number=\"" + index + "\"><Content>" + block + "</Content></BarCode>";
                BarcodeQRCode qrCode = new BarcodeQRCode(xml, 1, 1, new HashMap<EncodeHintType, Object>() {
                });
                Image image = qrCode.getImage();
                image.scaleAbsolute(100, 100);
                cell.addElement(image);
                table.addCell(cell);
            }
            for (Integer i = 0; i < qrSize; i++) {
                PdfPCell cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
            return table;
        } catch (BadElementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PdfPTable getSignQRCode(String sign, int limit) {
        try {
            List<String> blockList = kz.kazgisa.utils.StringUtils.splitIntoBlocks(sign, 400);
            PdfPTable table = new PdfPTable(5);
            Date currentDate = new Date();
            int index = 0;
            Integer qrSize = (5 - (blockList.size() % 5));
            for (String block : blockList) {
                index++;
                PdfPCell cell = new PdfPCell();
                cell.setBorder(0);
                String xml = "<?xml version=\"1.0\" encoding=\"windows-1251\"?><BarCode Date=\"" + currentDate + "\" Total=\"" + blockList.size() + "\" Number=\"" + index + "\"><Content>" + block + "</Content></BarCode>";
                BarcodeQRCode qrCode = new BarcodeQRCode(xml, 1, 1, new HashMap<EncodeHintType, Object>() {
                });
                Image image = qrCode.getImage();
                image.scaleAbsolute(100, 100);
                cell.addElement(image);
                table.addCell(cell);
                if(limit != 0 && index == limit) {
                    break;
                }
            }
            if(limit == 0) { // todo
                for (Integer i = 0; i < qrSize; i++) {
                    PdfPCell cell = new PdfPCell();
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }
            }
            return table;
        } catch (BadElementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] generateTopogrPdf(Long id, String imageMap, String pdfText, Boolean portrait) {
        App app = appRepository.findById(id).get();
        if (app == null) {
            return null;
        }
        Rectangle orientation = PageSize.A4.rotate();
        if(portrait) {
            orientation = PageSize.A4;
        }
        Document document = new Document(orientation, 30, 30, 30, 30);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            //writer.setPageEvent(new EskizFooterEvent());
            document.open();

//            Paragraph header = new Paragraph("Выдача копии топографического плана", font14);
//            header.setAlignment(Element.ALIGN_CENTER);
//            document.add(header);

            pdfText = pdfText.replace("<br>", "<br/>");
//            pdfText = pdfText.replace("<span", "<div");
//            pdfText = pdfText.replace("</span>", "</div>");
            try {
                String headerImage = "<img src='" + projectPath + "/src/main/resources/images/archHeader.jpg' /><br/><br/>";
                String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                        "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                        "<body><div style='font-family: Times New Roman; padding: 0 50px;'>" +
                        headerImage + pdfText + "</div></body></html>";
                document.open();
                InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
                fontProvider.setUseUnicode(true);
                fontProvider.register("fonts/times-new-roman.ttf");

                XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            document.add(new Paragraph(pdfText, font12));

            document.add(Chunk.NEXTPAGE);

            if (imageMap != null && !imageMap.equals("")) {
                byte[] mapImageByteArray = Base64.getDecoder().decode(imageMap);
                Image mapImage = Image.getInstance(mapImageByteArray);
                float width = mapImage.getWidth();
                float height = mapImage.getHeight();
                float ratio = width / height;
                if (ratio > 1.41) {
                    mapImage.scaleAbsoluteWidth(780f);
                    mapImage.scaleAbsoluteHeight(780f / ratio);
                } else {
                    mapImage.scaleAbsoluteWidth(450f * ratio);
                    mapImage.scaleAbsoluteHeight(450f);
                }

                // add here qr code
//                document.add(getSignQRCode(appService.getAppXml(id)));

//                mapImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//                float x = (PageSize.A4.getWidth() - mapImage.getScaledWidth()) / 2;
//                float y = (PageSize.A4.getHeight() - mapImage.getScaledHeight()) / 2;
//                mapImage.setAbsolutePosition(x, y);
//                document.add(mapImage);

//                System.out.println("a4 widthh: " + PageSize.A4.getWidth());
//                System.out.println("a4 height: " + PageSize.A4.getHeight());
//                System.out.println("mi widthh: " + mapImage.getScaledWidth());
//                System.out.println("mi height: " + mapImage.getScaledHeight());
//                System.out.println("mi widthh 1: " + mapImage.getWidth());
//                System.out.println("mi height 1: " + mapImage.getHeight());
//
//                float x = (PageSize.A4.getWidth() - mapImage.getScaledWidth()) / 2;
//                float y = PageSize.A4.getHeight() - mapImage.getScaledHeight() - 100;
//                System.out.println("x: " + x);
//                System.out.println("y: " + y);
//
//                mapImage.setAbsolutePosition(5, y);
                document.add(mapImage);
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public byte[] generateAuctionPdf(Long id, AuctionPdfDto auctionPdfDto) {
//        App app = appService.getById(id);
//        if (app == null) {
//            return null;
//        }
//        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            writer.setPageEvent(new EskizFooterEvent());
//            document.open();
//
//            Paragraph header = new Paragraph("Приобретение прав на земельные участки, которые находятся в государственной собственности, на торгах (конкурсах, аукционах)", font14);
//            header.setAlignment(Element.ALIGN_CENTER);
//            document.add(header);
//
//            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//
//            document.add(new Paragraph("Дата договора: " + sdf.format(auctionPdfDto.getAgreementDate()), font14));
//            document.add(new Paragraph("Срок действия (лет): " + auctionPdfDto.getAgreementYears(), font14));
//            document.add(new Paragraph("Срок договора до: " + sdf.format(auctionPdfDto.getAgreementEndDate()), font14));
//
//            Paragraph text = new Paragraph(auctionPdfDto.getPdfText(), font14);
//            document.add(text);
//
//            document.close();
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /*public byte[] generateAuctionPdf(long appId, AuctionPdfDto auctionPdfDto, Boolean preview) {
        App app = appService.getById(appId);
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            Paragraph paragraph = new Paragraph("Договор", fontTitle);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraph1 = new Paragraph("О временном возмездном землепользовании", fontTitle);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            Phrase phrase = new Phrase("г.Атырау", italicBold12);
//            AuctionInfo auctionInfo = app.getAuctionInfo();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date agreementDate = new Date();
            if (preview) {
                agreementDate = auctionPdfDto.getAgreementDate();
            } else {
                agreementDate = app.getAuctionInfo().getAgreementDate();
            }
            Phrase phrase1 = new Phrase("" + sdf.format(agreementDate) + "г. \n", italicBold12);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);
            PdfPCell cell2 = new PdfPCell(phrase1);
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell2.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell2);
            Phrase phrase2 = new Phrase("\n           Мы, нижеподписавшиеся, ", font12);
            Phrase phrase3 = new Phrase("И о. руководителя отдела земельных отношений г.Атырау Н.Халмурзин \n", italicBold12);
            Paragraph paragraph2 = new Paragraph("именуемый в дальнейшем Арендодатель, с одной стороны, и \n", font12);
            paragraph2.setAlignment(Element.ALIGN_JUSTIFIED);
            String firstName = app.getFirstName();
            String text = "";
            if (firstName.length() > 0) {
                text = app.getLastName() + " " + firstName + " " + app.getSecondName();
            } else {
                text = app.getOrgName();
            }
            Paragraph paragraph3 = new Paragraph("" + text, italicBold12);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraph4 = new Paragraph("именуемый в дальнейшем Арендатор, с другой стороны заключили настоящий договор о нижеследующем: \n", font12);
            Paragraph paragraph5 = new Paragraph("1. Предмет договора", fontTitle);
            paragraph5.setAlignment(Element.ALIGN_CENTER);
            Phrase phrase4 = new Phrase("           1.Арендодатель передает (предоставляет) Арендатору принадлежащий ему на праве ", font12);
            Chunk chunk4 = new Chunk("государственной собственности ", italicBold12);
            chunk4.setUnderline(1.5f, -2);
            Phrase phrase6 = new Phrase("земельный участок (часть земельного участка) на основании ", font12);
            String reasonRu = "";
            if (preview) {
                reasonRu = auctionPdfDto.getReasonRu();
            } else {
                reasonRu = app.getAuctionInfo().getReasonRu();
            }
            Chunk chunk5 = new Chunk(reasonRu + ".", fontTitle);
            chunk5.setUnderline(1.5f, -2);
            Paragraph paragraph6 = new Paragraph("           2.Место расположение земельного участка и его данные:\n", font12);
            Phrase phrase8 = new Phrase("адрес: ", font12);
            Chunk chunk6 = new Chunk(app.getAuctionInfo().getAddress() + ", ", italicBold12);
            chunk6.setUnderline(1.5f, -2);
            Phrase phrase10 = new Phrase("площадь ", font12);
            Phrase phrase11 = new Phrase(app.getAuctionInfo().getArea() + " га ", fontTitle);
            Phrase phrase12 = new Phrase("сельскохозяйственных угодий - га, (пашни - га, в т.ч. орошаемой- га " +
                    "многолетних насаждений -га, сенокосов- га, пастбищ - га, залежь - га), прочих земель __________________________" +
                    "_____________________________________________________________\n", font12);
            Phrase phrase13 = new Phrase("целевое назначение: ", font12);
            Chunk chunk2 = new Chunk("для строительства и обслуживания зданий сооружений \n ", italicBold12);
            chunk2.setUnderline(1.5f, -2);
            Paragraph paragraph7 = new Paragraph("\n2. Плата за землю \n \n", fontTitle);
            paragraph7.setAlignment(Element.ALIGN_CENTER);
            Phrase phrase14 = new Phrase("           1. Ежегодная арендная плата составляет ", font12);
            Double rentPayment = 0d;
            if (preview) {
                rentPayment = auctionPdfDto.getRentPayment();
            } else {
                rentPayment = app.getAuctionInfo().getRentPayment();
            }
            Chunk chunk7 = new Chunk("" + rentPayment, fontTitle);
            chunk7.setUnderline(1.5f, -2);
            Phrase phrase16 = new Phrase(" тенге и подлежит уплате Арендатором равными " +
                    "долями по «25 » II, V, VIII, XI текущего года путем перечисления на счет ", font12);
            Chunk chunk = new Chunk("№ КZ 24070105КSN0000000 ", fontTitle);
            chunk.setUnderline(1.5f, -2);
            Chunk chunk1 = new Chunk("в Комитет Казначейства", font12);
            chunk1.setUnderline(1, -2);
            Phrase phrase17 = new Phrase(" или эта сумма компенсируется в натуральной форме в виде _________________________________" +
                    "_______________________________________________________", font12);
            Paragraph paragraph8 = new Paragraph("           2. Размер арендной платы по соглашению сторон ежегодно уточняется на основании " +
                    "данных государственной статистики об общем уровне инфляции. При сдаче государством или государственным землепользователем " +
                    "земельного участка в аренду размер арендной платы определяется в соответствии с установленным порядком.", font12);
            paragraph8.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(paragraph);
            document.add(paragraph1);
            document.add(table);
            document.add(phrase2);
            document.add(phrase3);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(phrase4);
            document.add(chunk4);
            document.add(phrase6);
            document.add(chunk5);
            document.add(paragraph6);
            document.add(phrase8);
            document.add(chunk6);
            document.add(phrase10);
            document.add(phrase11);
            document.add(phrase12);
            document.add(phrase13);
            document.add(chunk2);
            document.add(paragraph7);
            document.add(phrase14);
            document.add(chunk7);
            document.add(phrase16);
            document.add(chunk);
            document.add(chunk1);
            document.add(phrase17);
            document.add(paragraph8);
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
*/
    public byte[] generateEskizPdf(Long id, String message, String xml, Long templateId) {
        App app = appRepository.findById(id).get();
        if (app == null) {
            return null;
        }
        Document document = new Document(PageSize.A4, 30, 30, 30, 60);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Image qr = getQRCodeEskiz(id);
            writer.setPageEvent(new FooterEvent(qr, null, "eskiz"));
            document.open();
            message = message.replace("<br>", "<br/>");
//            message = message.replace("<span", "<div");
//            message = message.replace("</span>", "</div>");
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            try {
                String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                        "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
                        "<style type=\"text/css\">  table { border-collapse: collapse; width: 100%; } th { text-align:left; } " +
                        "table, th, td { border: 1px solid black; p {margin: 0; line-height: 21px;}    </style>" +
                        "</head>" +
                        "<body><div style='font-family: Times New Roman; padding: 0px 19px 19px 52.5px;'>" +
                         message + "</div></body></html>";
                document.open();
                InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
                fontProvider.setUseUnicode(true);
                fontProvider.register("fonts/times-new-roman.ttf");
                final CssFilesImpl cssFiles = new CssFilesImpl();
                cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
                final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
                final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
                hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
                final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
                final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
                final XMLWorker worker = new XMLWorker(pipeline, true);
                final Charset charset = Charset.forName("UTF-8");
                final XMLParser xmlParser = new XMLParser(true, worker, charset);
                xmlParser.parse(is, charset);

                //  XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (xml != null) {
                document.setMargins(0, 0, 30, 30);
                document.newPage();
//            Paragraph titleQrCode = new Paragraph("Qr Code");
//            titleQrCode.setIndentationLeft(60);
//            document.add(titleQrCode);

                PdfPTable centerTable = new PdfPTable(1);
                PdfPCell centerText = new PdfPCell(new Phrase("Электронно-цифровая подпись ответственного лица организации услугодателя",font14));
                centerText.setBorder(Rectangle.NO_BORDER);
                centerText.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                centerTable.addCell(centerText);

                document.add(centerTable);
                document.add(getSignQRCode(CommonUtils.compressString(xml), 0));
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String templateUzp(Long id) {
        App app = appRepository.findById(id).get();
        if (app == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        String bin = "";
        String iin = "";
        String name = "";
        String address = "";
        String phone = "";
        if (app.getBin() != null) {
            name = app.getOrgName().replace("\"", "").replace("\\", "");;
            bin = "БИН " + (app.getBin() != null ? app.getBin() : "");
            iin = "ИИН " + app.getIin() + ", " + app.getLastName() + " " +  app.getFirstName() + " " + app.getSecondName();
            address = app.getAddress() != null ? app.getAddress() : "";
            phone = app.getPhone() != null ? app.getPhone() : "";
        } else {
            name = app.getLastName() + " " + app.getFirstName() + " " + app.getSecondName();
            iin = "ИИН " + (app.getIin() != null ? app.getIin() : "");
            address = app.getAddress() != null ? app.getAddress() : "";
            phone = app.getPhone() != null ? app.getPhone() : "";
        }

        String createDate = sdf.format((app.getCreateDate() != null ? app.getCreateDate() : ""));
        String objectAddress = app.getObjectInfo().getAddress() != null ? app.getObjectInfo().getAddress() : "";
        String objectPurposeAndArea = (app.getObjectInfo().getPurpose() != null ? app.getObjectInfo().getPurpose() : "") + ", " + (app.getObjectInfo().getArea() != null ? app.getObjectInfo().getArea().toString() : "");

        String landFIO = (app.getLandInfo().getFirstName() != null ? app.getLandInfo().getFirstName() : "") + " " +
                 (app.getLandInfo().getLastName() != null ? app.getLandInfo().getLastName() : "") + " " +
                 (app.getLandInfo().getSecondName() != null ? app.getLandInfo().getSecondName() : "") + " " +
                 (app.getLandInfo().getOrgName() != null ? app.getLandInfo().getOrgName() : "");
        String landResolution = "№ " + (app.getLandInfo().getProtocolNumber() != null ? app.getLandInfo().getProtocolNumber() : "") + " от " +
                sdf.format((app.getLandInfo().getProtocolDate() != null ? app.getLandInfo().getProtocolDate() : ""));
        String message = "" +
                "<table style=\"width: 700; border-color: white; height: 122px; margin-left: auto; margin-right: auto;\">" +
                "<tbody><tr style=\"height: 7px;\"><td style=\"width: 249px; height: 7px; border-color: white;\">&nbsp;</td><td style=\"width: 249px; height: 7px; border-color: white;\">от {fio}</td></tr>" +
                (app.getBin() != null ? "<tr style=\"height: 7px;\"><td style=\"width: 249px; height: 7px; border-color: white;\">&nbsp;</td><td style=\"width: 249px; height: 7px; border-color: white;\">{bin}</td></tr>" : "") +
                "<tr style=\"height: 7px;\"><td style=\"width: 249px; height: 7px; border-color: white;\">&nbsp;</td><td style=\"width: 249px; height: 7px; border-color: white;\">{iin}</td></tr>" +
                "<tr style=\"height: 7px;\"><td style=\"width: 249px; height: 7px; border-color: white;\">&nbsp;</td><td style=\"width: 249px; height: 7px; border-color: white;\">Тел: {phone}</td></tr>" +
                "<tr style=\"height: 7px;\"><td style=\"width: 249px; height: 7px; border-color: white;\">&nbsp;</td><td style=\"width: 249px; height: 7px; border-color: white;\">Адрес {address}</td></tr>" +
                "</tbody>" +
                "</table>" +
                "<p>&shy;</p>" +
                "<p>&shy;</p>" +
                "<p>&shy;</p>" +
                "<p style=\"text-align: center;\">Заявление на утверждение землеустроительного проекта по формированию земельных участков</p>" +
                "<table width=\"779\"><tbody>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>1.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Разработчик землеустроительного проекта<br/>фамилия, имя, отчество (при его наличии) или полное наименование юридического лица</p></td><td style=\"width: 354px; vertical-align: top;\"><p><em>{landFIO}</em></p></td></tr>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>2.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Фамилия, имя, отчество (при его наличии) физического лица или (наименование юридического лица ходатайствующего о предоставлении права на земельный участок</p></td><td style=\"width: 354px; vertical-align: top;\"><p><em></em></p></td></tr>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>3.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Наименование землеустроительного проекта</p></td><td style=\"width: 354px; vertical-align: top;\"><p><em></em></p></td></tr>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>4.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Адрес (место нахождения) земельного участка</p></td><td style=\"width: 354px; vertical-align: top;\"><p><em>{objectAddress}</em></p></td></tr>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>5.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Запрашиваемое целевое назначение земельного участка и площадь, гектар</p></td><td style=\"width: 354px; vertical-align: top;\"><p><em>{objectPurposeAndArea}</em></p></td></tr>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>6.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Количество экземпляров землеустроительного проекта, номер и дата протокола земельной комиссии</p></td><td style=\"width: 354px; vertical-align: top;\"><p>Электронная копия землеустроительного проекта</p></td></tr>" +
                "<tr><td style=\"width: 47px; vertical-align: top;\"><p>7.</p></td><td style=\"width: 378px; vertical-align: top;\"><p>Решение нижестоящего местного исполнительного органа номер и дата</p></td><td style=\"width: 354px; vertical-align: top;\"><p>{landResolution}</p></td></tr>" +
                "</tbody></table>" +
                "<p>&shy;</p>" +
                "<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Землеустроительный проект изготовлен: при предоставлении государством права частной собственности на земельный участок или права землепользования, в случае изменений идентификационных характеристик земельного участка (нужное подчеркнуть).</p>" +
                "<p>&shy;</p>" +
                "<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Согласен(на) на использование сведений, составляющих охраняемую законом тайну, содержащихся в информационных системах.</p>" +
                "<p>&shy;</p>" +
                "<p style=\"text-align: center;\">Дата&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {createDate} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Заявитель ___________________________________</p>" +
                "<p style=\"text-align: center;\">________________________________________________________________________________</p>" +
                "<p style=\"text-align: center;\">(фамилия, имя, отчество (при его наличии) физического лица либо уполномоченного представителя юридического лица, подпись)</p>";
                String html = message.replace("{fio}", name).replace("{bin}", bin).replace("{iin}", iin).replace("{address}", address).replace("{phone}", phone);
                String htmlAll = html.replace("{objectAddress}", objectAddress).replace("{objectPurposeAndArea}", objectPurposeAndArea).replace("{landFIO}", landFIO).replace("{landResolution}", landResolution).replace("{createDate}", createDate);
        return htmlAll;
    }

    public byte[] generateUzpPdf(Long id, String message, String xml, Long templateId) {
        App app = appRepository.findById(id).get();
        if (app == null) {
            return null;
        }
        Document document = new Document(PageSize.A4, 30, 30, 30, 60);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Image qr = getQRCodeUzp(id);
            writer.setPageEvent(new FooterEvent(qr, null, "uzp"));
            document.open();
            message = message.replace("<br>", "<br/>");
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            try {
                String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                        "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
                        "<style type=\"text/css\">  table { border-collapse: collapse; width: 100%; } th { text-align:left; } " +
                        "table, th, td { border: 1px solid black; p {margin: 0; line-height: 21px;}    </style>" +
                        "</head>" +
                        "<body><div style='font-family: Times New Roman; padding: 0px 19px 19px 52.5px;'>" +
                        message + "</div></body></html>";
                document.open();
                InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
                fontProvider.setUseUnicode(true);
                fontProvider.register("fonts/times-new-roman.ttf");
                final CssFilesImpl cssFiles = new CssFilesImpl();
                cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
                final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
                final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
                hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
                final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
                final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
                final XMLWorker worker = new XMLWorker(pipeline, true);
                final Charset charset = Charset.forName("UTF-8");
                final XMLParser xmlParser = new XMLParser(true, worker, charset);
                xmlParser.parse(is, charset);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (xml != null) {
                document.setMargins(0, 0, 30, 30);
                PdfPTable centerTable = new PdfPTable(1);
                PdfPCell centerText = new PdfPCell(new Phrase("Электронно-цифровая подпись ответственного лица организации услугодателя",font14));
                centerText.setBorder(Rectangle.NO_BORDER);
                centerText.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                centerTable.addCell(centerText);

                document.add(centerTable);
                document.add(getSignQRCode(CommonUtils.compressString(xml), 0));
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generatePdf(String message) {
        Document document = new Document(PageSize.A4, 30, 30, 30, 60);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            message = message.replace("<br>", "<br/>");
//            message = message.replace("<span", "<div");
//            message = message.replace("</span>", "</div>");
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            try {


                String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                        "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
                        "<style type=\"text/css\">  table { border-collapse: collapse; width: 100%; } th { text-align:left; } " +
                        "table, th, td { border: 1px solid black; } p {margin: 0;padding:0; line-height: 21px;} </style>" +
                        "</head>" +
                        "<body><div style='font-family: Times New Roman; padding: 0px 19px 19px 52.5px;'>" +
                         message + "</div></body></html>";
                document.open();
                InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
                fontProvider.setUseUnicode(true);
                fontProvider.register("fonts/times-new-roman.ttf");
                final CssFilesImpl cssFiles = new CssFilesImpl();
                cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
                final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
                final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
                hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
                final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
                final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
                final XMLWorker worker = new XMLWorker(pipeline, true);
                final Charset charset = Charset.forName("UTF-8");
                final XMLParser xmlParser = new XMLParser(true, worker, charset);
                xmlParser.parse(is, charset);
                //XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public byte[] generateComissionPdf(Long meetingId) {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setPageEvent(new EskizFooterEvent());
            document.open();

//            Paragraph header1 = new Paragraph("Форма \"Свидетельство\"", font14);
//            header1.setAlignment(Element.ALIGN_CENTER);
//            document.add(header1);


            PdfPTable table = new PdfPTable(2);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            table.setWidths(new int[]{3, 4});
            PdfPCell headerLeftCell = new PdfPCell();
            headerLeftCell.setBorder(0);
            table.addCell(headerLeftCell);
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBorder(0);
            headerLeftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.addElement(new Paragraph("Комиссия төрағасы", fontTitle));
            headerCell.addElement(new Paragraph("Қала әкімінің орынбасары", fontTitle));
            headerCell.addElement(new Paragraph("Н.Б. Таубаев", fontTitle));
            headerCell.addElement(new Paragraph("__________________________", fontTitle));

            Paragraph header2 = new Paragraph("ХАТТАМА №____" + "      " + "«___»" + "_____________" + "2018 жыл", font14);


            table.addCell(headerCell);
            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));


            header2.setAlignment(Element.ALIGN_CENTER);
            document.add(header2);
            Paragraph p = new Paragraph("Қалалық әкімияттың 2015 жылғы 18 желтоқсандағы №2308,             2016 жылғы ", fontTitle);
            p.setIndentationLeft(30);
            document.add(p);


            Paragraph par = new Paragraph(" 5 мамырдағы №620, 2016 жылғы 23 маусымдағы № 900, 2016 жылғы 19 тамыздағы № 1049, \n" +
                    "2016 жылғы 27 қазандағы № 1343, 2017 жылғы 25 қаңтардағы № 16, 2017 жылғы 17 \n" +
                    "мамырдағы № 971, 2017 жылғы 12 шілдедегі № 1394, 2017 жылғы 15 тамыздағы № 1618, 2017  \n" +
                    "жылғы 2 қарашадағы № 2205, 2017 жылғы 28 желтоқсандағы № 2727,2018 жылғы 17  \n" +
                    "қаңтардағы № 18, 2018 жылғы 22 ақпандағы № 221, 2018 жылғы 26 сәуірдеғі № 551, 2018\n" +
                    "жылғы 20 шілдедеғі № 1263 қаулылары негізінде құрылған, Атырау қаласындағы \n" +
                    "қалақурылысы және жер пайдалану жөніндегі жер учаскесін сураған қосымшага сәйкес жеке \n" +
                    "турғын үй, өзіндік қосалқы шаруашылығы, бағбандық және саяжай қүрылысын арналған \n" +
                    "жер учаскелері мәселелері бойынша қалалық жүмысшы комиссиясы хаттамасы \n "
                    , fontTitle);
            Phrase pg = new Phrase("/қосымша ______________________ бетте/", italic);

            par.add(pg);
            document.add(par);
            Paragraph text16 = new Paragraph("                                       ", fontTitle);
            document.add(text16);

            Paragraph text12 = new Paragraph("1. 2018 жылдың «___» ______ бастап 2018 жьілдың «_» _____________ дейін өзіндік \n " +
                    "қосалқы шаруашылыгы, багбандъщ, саяжай және жеке тұргын үй жеке түргын үй \n " +
                    "қүрылысьін салу үшін жер учаскесін сүраган өтініштерді қосымшага сәйкес ________ дана \n " +
                    "рүқсат беру; \n", italic);
            text12.setIndentationLeft(30);
            document.add(text12);
            Paragraph para = new Paragraph("                                                      ");
            document.add(para);
            Paragraph text13 = new Paragraph("Комиссия мүшелері:", fontTitle);
            text13.setIndentationLeft(30);
            document.add(text13);
            Paragraph text14 = new Paragraph("                                       ", fontTitle);
            document.add(text14);

            List<MeetingUser> meetingUsers = meetingUserRepository.findByMeetingId(meetingId);
            List<MeetingUser> meetUsers = new ArrayList<>();
            PdfPTable tb = new PdfPTable(2);


            for (MeetingUser s : meetingUsers) {
                if (s.getStatus().getId() == 1) {
                    meetUsers.add(s);
                    break;
                }
            }

            for (MeetingUser s : meetingUsers) {
                if (s.getStatus().getId() == 2) {
                    meetUsers.add(s);
                    break;
                }
            }
            for (MeetingUser s : meetingUsers) {
                if (s.getStatus().getId() == 4) {
                    meetUsers.add(s);
                    break;
                }
            }

            for (MeetingUser s : meetingUsers) {
                if (s.getStatus().getId() == 3) {
                    meetUsers.add(s);
                }
            }

            for (MeetingUser meetUser2 : meetUsers) {
                PdfPCell p1 = new PdfPCell();
                PdfPCell p2 = new PdfPCell();
                UserDto userDto = userService.getUserDtoById(meetUser2.getUserId());
                String position = userDto.getPositionRu();
                String status = meetUser2.getStatus().getNameRu();
                boolean hasCommissionMember = true;
                if (status.equals("Член комиссии")) {
                    hasCommissionMember = false;
                }

                String fio = userDto.getLastName() + " " + userDto.getFirstName();
                if (userDto.getSecondName() != null) {
                    fio += " \n" + userDto.getSecondName();
                }
                p1.setBorder(0);
                p2.setBorder(0);
                p1.setPaddingBottom(15);
                p2.setPaddingBottom(15);
                Paragraph FIO = new Paragraph(fio, font12);
                String positionStatus = "";
                if (position != null) {
                    positionStatus += position;
                }
                if (status != null && hasCommissionMember == true) {
                    if (!positionStatus.equals("")) {
                        positionStatus += ", " + " \n";
                    }
                    positionStatus += status;
                }
                Paragraph statusPos = new Paragraph(positionStatus, font12);
                FIO.setLeading(12, 0);
                statusPos.setLeading(12, 0);
                p1.addElement(FIO);
                p2.addElement(statusPos);
                tb.addCell(p1);
                tb.addCell(p2);
            }
            document.add(tb);
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateOzoChangeReasonPdf(Long appId, String message, Principal principal) {
        App app = appService.getById(appId);
        User user = userService.getCurrentUser(principal);
        Document document = new Document(PageSize.A4, 30, 30, 30, 50);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setPageEvent(new EskizFooterEvent());
            document.open();
//            if (app != null && app.getSubservice() != null && app.getSubservice().getService() != null
//                    && (app.getSubservice().getService().getId() == 6L
//                    || app.getSubservice().getId() == 20L
//                    || app.getSubservice().getId() == 29L
//                    || app.getSubservice().getId() == 31L
//                    || app.getSubservice().getId() == 32L
//                    || app.getSubservice().getService().getId() == 1L
//            )) {
//                InputStream is = null;
//                try {
//                    is = new ClassPathResource("images/archHeader.jpg").getInputStream();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                if (is != null) {
//                    byte[] img = CommonUtils.inputStreamToByteArray(is);
//
//                    Image image = Image.getInstance(img);
//                    float width = image.getWidth();
//                    float height = image.getHeight();
//                    float ratio = width / height;
//
//                    image.scaleAbsoluteWidth(550f);
//                    image.scaleAbsoluteHeight(500f / ratio);
//
//                    document.add(image);
//                    document.add(Chunk.NEWLINE);
//                    document.add(Chunk.NEWLINE);
//                }
//            } else if (app != null && app.getSubservice() != null &&
//                    (app.getSubservice().getId() == 23L || app.getSubservice().getId() == 10L)) {
//                InputStream is = null;
//                try {
//                    is = new ClassPathResource("images/ozoHeader.jpg").getInputStream();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                if (is != null) {
//                    byte[] img = CommonUtils.inputStreamToByteArray(is);
//
//                    Image image = Image.getInstance(img);
//                    float width = image.getWidth();
//                    float height = image.getHeight();
//                    float ratio = width / height;
//
//                    image.scaleAbsoluteWidth(550f);
//                    image.scaleAbsoluteHeight(500f / ratio);
//
//                    document.add(image);
//                    document.add(Chunk.NEWLINE);
//                    document.add(Chunk.NEWLINE);
//                }
//            }
//            Paragraph paragraph = new Paragraph(text, font12);

            message = message.replace("<br>", "<br/>");
//            message = message.replace("<span", "<div");
//            message = message.replace("</span>", "</div>");
            try {
                String headerImage = "";
                if (app != null && app.getSubservice() != null && app.getSubservice().getService() != null
                        && (app.getSubservice().getService().getId() == 6L
                        || app.getSubservice().getId() == 20L
                        || (app.getSubservice().getService().getId() == 13L && user != null && user.getOrganization() != null && user.getOrganization().getId() == 1L)
                        || app.getSubservice().getService().getId() == 1L
                )) {
                    headerImage = "<img src='" + projectPath + "/src/main/resources/images/archHeader.jpg' /><br/><br/>";
                } else if (app != null && app.getSubservice() != null &&
                        (app.getSubservice().getId() == 23L
                                || app.getSubservice().getId() == 10L
                                || app.getSubservice().getService().getId() == 13L)) {
                    headerImage = "<img src='" + projectPath + "/src/main/resources/images/ozoHeader.jpg' /><br/><br/>";
                }

                String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                        "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                        "<body><div style='font-family: Times New Roman; padding: 0 50px;'>" +
                        headerImage + message + "</div></body></html>";
                document.open();
                InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
                fontProvider.setUseUnicode(true);
                fontProvider.register("fonts/times-new-roman.ttf");

                XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generatePermissionRecPdf(Long appId, String text, String position, String fullName) throws IOException, DocumentException {
        App app = appService.getById(appId);

        Long regNum = app.getId();

        if (app == null) {
            return null;
        }
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setPageEvent(new EskizFooterEvent());
            document.open();
            InputStream is = null;
            try {
                is = new ClassPathResource("images/archHeader-old.jpg").getInputStream();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (is != null) {
                byte[] img = CommonUtils.inputStreamToByteArray(is);

                Image image = Image.getInstance(img);
                float width = image.getWidth();
                float height = image.getHeight();
                float ratio = width / height;

                image.scaleAbsoluteWidth(550f);
                image.scaleAbsoluteHeight(500f / ratio);

                document.add(image);
                PdfPTable tab2 = new PdfPTable(1);
                tab2.setHorizontalAlignment(100);
                PdfPCell headerCellk = new PdfPCell();
                headerCellk.setBorder(0);
                headerCellk.setHorizontalAlignment(50);
                headerCellk.addElement(new Paragraph("№" + regNum, fontTitle));
                headerCellk.addElement(new Paragraph("" + app.getArchSignedDate(), fontTitle));
                tab2.addCell(headerCellk);
                document.add(tab2);

                Paragraph header6 = new Paragraph("ШЕШIМ", fontTitle);
                header6.setAlignment(Element.ALIGN_CENTER);
                document.add(header6);


                Paragraph emp = new Paragraph("    ");
                document.add(emp);

                Paragraph pr2 = new Paragraph("Пәтерді қайта жоспарлауға рұқсат \n" +
                        "беру туралы\n", fontTitle);
                pr2.setIndentationLeft(50);
                pr2.setIndentationRight(40);
                document.add(pr2);
                Paragraph emp1 = new Paragraph("    ");
                document.add(emp1);
                if (text != null) {
                    String[] docs = text.split("\n");

                    for (Integer i = 0; i < docs.length; i++) {
                        Paragraph paragraph = new Paragraph(docs[i], font12);
                        paragraph.setIndentationLeft(50);
                        paragraph.setIndentationRight(40);
                        document.add(paragraph);
                    }
                }

                PdfPTable taba = new PdfPTable(2);
                Paragraph pp = new Paragraph(position, fontTitle);

                Paragraph pn = new Paragraph(fullName, fontTitle);


                PdfPCell p1 = new PdfPCell();
                PdfPCell p2 = new PdfPCell();


                p1.setBorder(0);
                p2.setBorder(0);
                p1.setPaddingBottom(15);
                p2.setPaddingBottom(15);
                pp.setLeading(12, 0);
                pn.setLeading(12, 0);


                p1.addElement(pp);
                p2.addElement(pn);
                taba.addCell(p1);
                taba.addCell(p2);
                document.add(taba);


            }


            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private class FooterEvent extends PdfPageEventHelper {
        protected PdfPTable table;

        public FooterEvent(Image qr, Font font, String type) throws IOException, DocumentException {
            table = new PdfPTable(2);
            table.setTotalWidth(450);
            table.setWidths(new int[]{1, 4});
            qr.scaleAbsoluteWidth(50);
            qr.scaleAbsoluteHeight(50);
            PdfPCell cell = new PdfPCell(qr);
            cell.setBorder(Rectangle.NO_BORDER);
            if (type.equals("apz")) {
                cell.setPaddingTop(50);
                cell.setPaddingBottom(100);
            } else if (type.equals("eskiz")) {
                cell.setPaddingTop(0);
            } else if (type.equals("uzp")) {
                cell.setPaddingTop(6);
            } else {
                cell.setPaddingTop(25);
            }
            cell.setPaddingBottom(50);
            cell.setPaddingLeft(20);
            table.addCell(cell);

            PdfPCell textCell = new PdfPCell();
            textCell.addElement(new Phrase("Региональная геоинформационная система eatyrau.kz", font10));
            textCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(textCell);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            table.writeSelectedRows(0, -1,
                    document.left() + 30,
                    document.bottom() + 30,
                    writer.getDirectContent());
        }
    }

    private class EskizFooterEvent extends PdfPageEventHelper {
        protected PdfPTable table;

        public EskizFooterEvent() throws IOException, DocumentException {
            table = new PdfPTable(1);
            table.setTotalWidth(510);
            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);
            cell.addElement(new Paragraph("Осы құжат \"Электрондық құжат және электрондық цифрлық қолтаңба туралы\" 2003 жылғы 7 қаңтардағы № 370-II ҚРЗ 1 бабына сәйкес қағаз жеткiзгiштегi құжатпен бiрдей.", font8));
            cell.addElement(new Paragraph("Данный документ согласно пункту 1 статьи 7 ЗРК от 7 января 2003 года \"Об электронном документе и электронной цифровой подписи\" равнозначен документу на бумажном носителе.", font8));
            table.addCell(cell);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            table.writeSelectedRows(0, -1,
                    document.left() + 15,
                    60,
                    writer.getDirectContent());
        }

    }

    public byte[] generateTestPdf(String html) {
        Document document = new Document();
        html = html.replace("<br>", "<br/>");
//        html = html.replace("<span", "<div");
//        html = html.replace("</span>", "</div>");
        try {
            String k = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                    "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                    "<body><div style='font-family: Times New Roman;'>" +
                    "<img src='" + projectPath + "/src/main/resources/images/org2.jpg' /><br/><br/>" +
                    html + "</div></body></html>";
            ByteArrayOutputStream file = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(k.getBytes("UTF-8"));

            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
            fontProvider.setUseUnicode(true);
            fontProvider.register("fonts/times-new-roman.ttf");

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
            document.close();
            file.close();
            return file.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] addTextStamp(byte[] file, Long fileId, String numeration, Date numerationDate, Long subserviceId, Long appId) {
        try {
            PdfReader reader = new PdfReader(file);
            String url="http://bp.eatyrau.kz/sedapi/userapp/" + appId + "/stamp/" + fileId + "/download";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
            int pages = reader.getNumberOfPages();
            if(subserviceId == 10L || subserviceId == 23L) {
                if(subserviceId == 23L) {
                    pages = 1;
                }
                for (int i = 0; i < pages; i++) {
                    PdfContentByte canvas = stamper.getOverContent(i + 1);
                    Font stampFont10 = new Font(arialFont, 10);
                    Font stampFont16 = new Font(arialFont, 16);
                    stampFont10.setColor(9, 76, 152);
                    stampFont16.setColor(9, 76, 152);
                    Phrase phrase1 = new Phrase("Атырау қаласының жер қатынастары бөлімінің басшысы", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 62, 52, 0);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String kelisilgen = "БЕКІТІЛГЕН";
                    if (numeration != null && !numeration.equals("")) {
                        kelisilgen += " № " + numeration + ",  " + sdf.format(numerationDate);
                    }
                    Phrase phrase2 = new Phrase(kelisilgen, stampFont16);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 62, 36, 0);
                    Phrase linkPhrase = new Phrase(url , stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linkPhrase, 62, 18, 0);
                }
            } else if(subserviceId == 21L) {
                for (int i = 0; i < pages; i++) {
                    PdfContentByte canvas = stamper.getOverContent(i + 1);
                    Font stampFont10 = new Font(arialFont, 10);
                    Font stampFont16 = new Font(arialFont, 16);
                    stampFont10.setColor(9, 76, 152);
                    stampFont16.setColor(9, 76, 152);
                    Phrase phrase1 = new Phrase("Атырау қалалық сәулет және қалақұрылысы бөлімінің басшысы", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 62, 52, 0);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String kelisilgen = "КЕЛІСІЛГЕН";
                    String date = "";
                    if (numerationDate != null) {
                        date += ", " + sdf.format(numerationDate);
                    }
                    if (numeration != null && !numeration.equals("")) {
                        kelisilgen += " № " + numeration + date;
                    }
                    Phrase phrase2 = new Phrase(kelisilgen, stampFont16);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 62, 36, 0);
                    Phrase linkPhrase = new Phrase(url, stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linkPhrase, 62, 18, 0);
                }
            } else if(subserviceId == 12L) {
                for (int i = 0; i < pages; i++) {
                    PdfContentByte canvas = stamper.getOverContent(i + 1);
                    Font stampFont10 = new Font(arialFont, 10);
                    Font stampFont16 = new Font(arialFont, 16);
                    stampFont10.setColor(9, 76, 152);
                    stampFont16.setColor(9, 76, 152);
                    Phrase phrase1 = new Phrase("«Атырау қалалық сәулет және қалақұрылысы бөлімі»", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 62, 70, 0);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Phrase phrase2 = new Phrase("«ЕСЕПКЕ АЛЫНДЫ»", stampFont16);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 108, 54, 0);
                    String date = "";
                    if (numerationDate != null) {
                        date += sdf.format(numerationDate);
                    }
                    Phrase phrase3 = new Phrase(date, stampFont16);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 142, 36, 0);
                    Phrase linkPhrase = new Phrase(url, stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linkPhrase, 62, 18, 0);
                }
            } else {
                for (int i = 0; i < pages; i++) {
                    PdfContentByte canvas = stamper.getOverContent(i + 1);
                    Font stampFont10 = new Font(arialFont, 10);
                    Font stampFont16 = new Font(arialFont, 16);
                    stampFont10.setColor(9, 76, 152);
                    stampFont16.setColor(9, 76, 152);
                    Phrase phrase1 = new Phrase("Атырау қалалық сәулет және қалақұрылысы бөлімі", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 62, 78, 0);
                    Phrase phrase2 = new Phrase("МЕМЛЕКЕТТIК МЕКЕМЕСI", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 100, 66, 0);
                    Phrase phrase3 = new Phrase("Атырау қаласының қалақұрылысы кадастырына", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 62, 54, 0);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String kelisilgen = "«ЕНГІЗІЛДI»";
                    if (numeration != null && !numeration.equals("")) {
                        kelisilgen += " № " + numeration + ",  " + sdf.format(numerationDate);
                    }
                    Phrase phrase4 = new Phrase(kelisilgen, stampFont16);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase4, 62, 36, 0);
                    Phrase linkPhrase = new Phrase(url, stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linkPhrase, 62, 18, 0);
                }
            }
            stamper.close();
            reader.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] addEskizApprovingRejectingStamp(byte[] file, String numeration, Date numerationDate) {
        try {
            PdfReader reader = new PdfReader(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
            int pages = reader.getNumberOfPages();
            PdfContentByte canvas = stamper.getOverContent(1);
            Font stampFont = new Font(baseFont, 12);
            stampFont.setColor(BaseColor.BLACK);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String numerationString = "";
            if(numeration != null && !numeration.equals("")) {
                numerationString = "исх № " + numeration + ", ";
            }
            Phrase phrase1 = new Phrase(numerationString + sdf.format(numerationDate) + " г.", stampFont);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 54, 650, 0);
            stamper.close();
            reader.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] addUtvZuTextStamp(byte[] file, String guid, String numeration, Date numerationDate, Long subserviceId) {
        try {
            PdfReader reader = new PdfReader(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
            int pages = reader.getNumberOfPages();
            if(subserviceId == 21L) {
                for (int i = 0; i < pages; i++) {
                    PdfContentByte canvas = stamper.getOverContent(i + 1);
                    Font stampFont10 = new Font(arialFont, 10);
                    Font stampFont16 = new Font(arialFont, 16);
                    stampFont10.setColor(9, 76, 152);
                    stampFont16.setColor(9, 76, 152);
                    Phrase phrase1 = new Phrase("Атырау қалалық сәулет және қалақұрылысы бөлімінің басшысы", stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 62, 52, 0);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String kelisilgen = "КЕЛІСІЛГЕН";
                    if (numeration != null && !numeration.equals("")) {
                        kelisilgen += " № " + numeration + ",  " + sdf.format(numerationDate);
                    }
                    Phrase phrase2 = new Phrase(kelisilgen, stampFont16);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 62, 36, 0);
                    Phrase linkPhrase = new Phrase("http://eatyrau.kz/eqyzmet/api/file/e/" + guid, stampFont10);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linkPhrase, 62, 18, 0);
                }
            }
            stamper.close();
            reader.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**public void saveIjsAgreement(Long appId, String mapImage, Principal principal) {
        byte[] file = generateIjsAgreement(appId, mapImage, principal);
        FileEntity fileEntity = fileService.saveFile(file, "agreement.pdf", "application/pdf");
        App app = appService.getById(appId);
        app.getOzoInfo().setAgreementObjectId(fileEntity.getObjectId());
        appService.update(app, principal, "saveIjsAgreement");
    }

    public byte[] generateIjsAgreement(Long appId, String mapImage, Principal principal) {
        App app = appService.getById(appId);
        User user = userService.getCurrentUser(principal);
        List<AppOrganization> appOrganizations = appOrganizationRepository.findByAppId(appId);
        try {
            Document document = new Document(PageSize.A4, 30, 30, 30, 30);
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                document.open();

                PdfPTable headTable = new PdfPTable(2);
                headTable.setTotalWidth(530);
                headTable.setWidths(new int[]{2, 1});
                PdfPCell cell1 = new PdfPCell();
                cell1.setBorder(Rectangle.NO_BORDER);
                headTable.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Phrase("«БЕКІТЕМІН»" +
                        "\nАтырау қаласы әкімінің" +
                        "\nорынбасары" +
                        "\nШ.Кейкин", arialBold));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setBorder(Rectangle.NO_BORDER);
                headTable.addCell(cell2);
                document.add(headTable);
                document.add(new Phrase("\n"));

                PdfPTable titleTable = new PdfPTable(1);
                PdfPCell titleCell = new PdfPCell(new Phrase("Жеке тұрғын үй құрылысын жобалау және салу үшін" +
                        "\nжер учаскесін таңдау және келісім беру" +
                        "\nАКТІСІ № " + app.getOzoInfo().getActNumber(), arialBold));
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell.setBorder(Rectangle.NO_BORDER);
                titleTable.addCell(titleCell);
                document.add(titleTable);
                document.add(new Phrase("\n"));

                PdfPTable applicantTable = new PdfPTable(1);
                String applicantInfo = "Тапсырыс беруші: " + app.getFirstName() + " " + app.getLastName() +
                        "\nМекен-жайы: " + app.getAddress();
                PdfPCell applicantCell = new PdfPCell(new Phrase(applicantInfo, arial));
                applicantCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                applicantCell.setBorder(Rectangle.NO_BORDER);
                applicantTable.addCell(applicantCell);
                document.add(applicantTable);
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));

                PdfPTable dateTable = new PdfPTable(2);
                PdfPCell cityCell = new PdfPCell(new Phrase("Атырау қаласы", arialBold));
                cityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cityCell.setBorder(Rectangle.NO_BORDER);
                dateTable.addCell(cityCell);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                if(app.getOzoInfo().getActDate() != null) {
                    PdfPCell dateCell = new PdfPCell(new Phrase(sdf.format(app.getOzoInfo().getActDate()) + " жыл", arialBold));
                    dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    dateCell.setBorder(Rectangle.NO_BORDER);
                    dateTable.addCell(dateCell);
                }
                document.add(dateTable);
                document.add(new Phrase("\n"));

                String address = "";
                if(app.getOzoInfo().getAreaAddress() != null) {
                    address = app.getOzoInfo().getAreaAddress();
                }
                String areaSize = "";
                if(app.getOzoInfo().getAreaSize() != null) {
                    areaSize = String.valueOf(app.getOzoInfo().getAreaSize());
                }
                String areaParams = "";
                if(app.getOzoInfo().getAreaParams() != null) {
                    areaParams = app.getOzoInfo().getAreaParams();
                }
                String scale = "";
                if(app.getOzoInfo().getScale() != null) {
                    scale = app.getOzoInfo().getScale();
                }
                String genConditions = "";
                if(app.getOzoInfo().getGeneralConditions() != null) {
                    genConditions = app.getOzoInfo().getGeneralConditions();
                }
                document.add(new Paragraph("1. Жер учаскесінің орналасуы: " + address, arial));
                document.add(new Paragraph("2. Жер учаскесінің көлемі: " + areaSize, arial));
//                document.add(new Paragraph("3. Жер учаскесінің сипаты: " + areaParams, arial));
                document.add(new Paragraph("3. Топографиялық түсірілімі: " + scale, arial));
                document.add(new Paragraph("4. Негізгі шарттары: " + genConditions, arial));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));

                document.add(new Paragraph("        КЕЛІСІЛДІ:", arialBold));
                document.add(new Paragraph("\n"));

                PdfPTable signersTable = new PdfPTable(2);

                signersTable.addCell(createCell("1. Қалалық жер қатынастары бөлімі: "));
                signersTable.addCell(createCell("Келісілген", Element.ALIGN_CENTER));
                signersTable.addCell(createCell("\n"));
                signersTable.addCell(createCell("\n"));

                document.add(new Paragraph("", arial));
                int orgIndex = 2;
                for(AppOrganization appOrganization: appOrganizations) {
                    Organization org = appOrganization.getOrganization();
                    if(org != null) {
                        Boolean signed = appOrganization.getSigned();
                        signersTable.addCell(createCell(orgIndex + ". " + org.getName() + ": "));
                        String signedText = "";
                        if(signed) {
                            signedText += "Келісілген";
                        }
                        signersTable.addCell(createCell(signedText, Element.ALIGN_CENTER));
                        signersTable.addCell(createCell("\n"));
                        signersTable.addCell(createCell("\n"));
                    }
                    orgIndex++;
                }
                signersTable.addCell(createCell(orgIndex + ". Тапсырыс беруші: "));
                signersTable.addCell(createCell(""));
                document.add(signersTable);

                if (mapImage != null && !mapImage.equals("")) {
                    document.newPage();
                    byte[] mapImageByteArray = Base64.getDecoder().decode(mapImage);
                    Image img = Image.getInstance(mapImageByteArray);
                    float width = img.getWidth();
                    float height = img.getHeight();
                    float ratio = width / height;
                    img.scaleAbsoluteWidth(550f);
                    img.scaleAbsoluteHeight(550f / ratio);

                    PdfPTable mapTable = new PdfPTable(2);
                    PdfPCell mapTitleCell = createCell("Жеке тұрғын үй құрылысын жобалау және салу үшін жер учаскесін" +
                            "\nтаңдау және тексеру актісіне қосымша" +
                            "\n\n\n\n", Element.ALIGN_CENTER, arialBold);
                    mapTitleCell.setColspan(2);
                    mapTable.addCell(mapTitleCell);

                    PdfPCell mapImageCell = new PdfPCell(img);
                    mapImageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    mapImageCell.setBorder(Rectangle.NO_BORDER);
                    mapImageCell.setColspan(2);
                    mapTable.addCell(mapImageCell);

                    mapTable.addCell(createCell("Орындаушы", Element.ALIGN_CENTER, arialBold));
                    mapTable.addCell(createCell(user.getFirstName().substring(0, 1) + "." + user.getLastName(), Element.ALIGN_CENTER, arialBold));

                    document.add(mapTable);
                }

                document.close();
                return outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] generateLandSelectionAgreement(Long appId, String mapImage, LandSelectionData landSelectionData, Principal principal, boolean finalAct) {
        App app = appService.getById(appId);
        User user = userService.getCurrentUser(principal);
//        List<AppOrganization> appOrganizations = appOrganizationRepository.findByAppId(appId);
        try {
            Document document = new Document(PageSize.A4, 30, 30, 30, 30);
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                document.open();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                // document
                PdfPTable headTable = new PdfPTable(2);
                headTable.setTotalWidth(530);
                headTable.setWidths(new int[]{2, 1});
                PdfPCell cell1 = new PdfPCell();
                cell1.setBorder(Rectangle.NO_BORDER);
                headTable.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Phrase("«БЕКІТЕМІН»" +
                        "\nҚалалық сәулет және қала құрылысы" +
                        "\nбөлімі басшысы" +
                        "\nЕ.Урдабаев" +
                        "\n" + sdf.format(new Date()) + " ж.", font)); // paste
                cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell2.setBorder(Rectangle.NO_BORDER);
                headTable.addCell(cell2);
                document.add(headTable);
                document.add(new Phrase("\n"));

                PdfPTable titleTable = new PdfPTable(1);
                PdfPCell titleCell = new PdfPCell(new Phrase("Жер учаскесін алдын ала таңдау актісі" +
                        "\n№ " + app.getOzoInfo().getActNumber(), fontBold)); // paste
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell.setBorder(Rectangle.NO_BORDER);
                titleTable.addCell(titleCell);
                document.add(titleTable);
                document.add(new Phrase("\n"));

//                document.add(Chunk.TABBING);
                String actDate = "";
                if(app.getOzoInfo().getActDate() != null) {
                    actDate = sdf.format(app.getOzoInfo().getActDate()) + " жыл";
                }
                document.add(new Phrase("    " + actDate + "\n", font)); // paste
//                document.add(Chunk.TABBING);
                document.add(new Phrase("    Ақтөбе қаласы \n\n", fontBold));
//                document.add(Chunk.TABBING);
                document.add(new Phrase("    " + landSelectionData.getFullName() + " ", fontBold));
                document.add(new Phrase("өтініші бойынша ", font));
                document.add(new Phrase(landSelectionData.getObjectName() + " үшін ", fontBold));
                document.add(new Phrase(landSelectionData.getAddress() + " ", fontBold));
                document.add(new Phrase("мекенжайындағы жер учаскесі сұралып отыр. \n\n", font));
//                document.add(Chunk.TABBING);
                document.add(new Phrase("    «Азаматтарға арналған үкімет» мемлекеттік корпорациясы» коммерциялық емес акционерлік қоғамының Ақтөбе облысы бойынша филиалы басшысына: ", fontBold));

//                document.add(Chunk.TABBING);
                document.add(new Phrase("    Жер учаскесінің нысаналы мақсаты: ", font));
                document.add(new Phrase(landSelectionData.getPurpose() + " үшін \n", fontBold));
//                document.add(Chunk.TABBING);
                document.add(new Phrase("    Жер учаскесінің алаңы (гектар): ", font));
                document.add(new Phrase(" " + landSelectionData.getArea() + " га. \n", fontBold));
                document.add(new Phrase("    Көлік қою орны үшін жер учаскесінің алаңы (гектар): ", font));
                document.add(new Phrase(" " + landSelectionData.getParkingArea() + " га. \n", fontBold));
//                document.add(Chunk.TABBING);
                Gson gson = new Gson();
                try {
                    List<Map<String, String>> areas = gson.fromJson(landSelectionData.getLandAreaJson(), List.class);
                    for(Map<String, String> area: areas) {
                        document.add(new Phrase("    " + area.get("key") + ": ", font));
                        document.add(new Phrase(" " + area.get("value") + "\n", fontBold));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                document.add(new Phrase("    Бөлінетіндігі: ", font));
                document.add(new Phrase(" " + landSelectionData.getAssigned() + "\n", fontBold));

                document.add(new Phrase("    Мерзімі (жыл): \n", font));
                try {
                    List<Map<String, String>> areas = gson.fromJson(landSelectionData.getLandYearsJson(), List.class);
                    for(Map<String, String> area: areas) {
                        document.add(Chunk.TABBING);
                        document.add(new Phrase(area.get("key") + ": ", font));
                        document.add(new Phrase(" " + area.get("value") + "\n", fontBold));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                document.add(new Phrase("    Учаске және құрылыс объектісі туралы қосымша мәліметтер: ", font));
                document.add(new Phrase(" " + landSelectionData.getObjectAdditionalInfo() + "\n", fontBold));

                document.add(new Phrase("    Жер учаскесін пайдаланудағы шектеулер мен ауыртпалықтар (сервитуттар): ", font));
                document.add(new Phrase(" " + landSelectionData.getRestrictions() + "\n\n", fontBold));

                document.add(new Phrase("    Жер учаскесін зерттеу материалдарын қарап, жер учаскесін беру бойынша келісімдеуші мемлекеттік органдар (ұйымдар, кәсіпорындар) келесі мүмкіндіктерді белгіледі:(тұтынушылардың құқықтарын қорғау және төтенше жағдайлар басқармалары міндетте түрде қортынды береді): ", font));

                document.add(Chunk.TABBING);
                PdfPTable orgsTable = new PdfPTable(3);

                List<PdfPTable> qrTables = new ArrayList<>();

                List<BigInteger> orgIds = ozoService.getSendingOrgIds(appId);
                int index = 0;
                for(BigInteger biOrgId: orgIds) {
                    Long orgId = biOrgId.longValue();
                    AppOrganization appOrganization = appService.getAppOrgByAppIdAndOrgId(appId, orgId);
                    AppOrgStatus appOrgStatus = appOrganization.getCurrentStatus();
                    String communalName = "";
                    if(appOrganization.getOrganization().getCommunal() != null) {
                        communalName = appOrganization.getOrganization().getCommunal().getNameKk();
                    }
                    PdfPCell orgCell1 = new PdfPCell(new Phrase(communalName, font));
                    orgCell1.setBorder(0);
                    orgsTable.addCell(orgCell1);
                    String status = "______________________";
                    if(appOrgStatus.getStatus() == Status.APPROVED) {
                        status = "келісімделді";
                    } else if(appOrgStatus.getStatus() == Status.REJECTED) {
                        status = "келісімделген жоқ";
                    }
                    PdfPCell orgCell2 = new PdfPCell(new Phrase(status, font));
                    orgCell2.setBorder(0);
                    orgsTable.addCell(orgCell2);
                    PdfPCell orgCell3 = new PdfPCell(new Phrase(appOrganization.getOrganization().getNameKk(), fontBold));
                    orgCell3.setBorder(0);
                    orgsTable.addCell(orgCell3);

                    if(index < 1) {
                        if (appOrganization.getSignedXml() != null) {
                            PdfPTable qrTable = getSignQRCode(appOrganization.getSignedXml(), 5);
                            qrTables.add(qrTable);
                        }
                    }
                    index++;
                }
                document.add(orgsTable);

                document.add(new Phrase("    басқалар: ", font));
                document.add(new Phrase("_________________________________________________________________________________\n\n", font));

                document.add(new Phrase("    Жер учаскесін зерттеу материалдары және келісімдеуші мемлекеттік органдардың (ұйымдардың, кәсіпорынның) қорытындысы (жалғануы қажет): \n", fontBold));

                document.add(new Phrase("    1. ", font));
                document.add(new Phrase(landSelectionData.getFullName() + " ", fontBold));
                document.add(new Phrase("өтініші бойынша\n", font));

                document.add(new Phrase("    " + landSelectionData.getObjectName() + " үшін \n", fontBold));
                document.add(new Phrase("    " + landSelectionData.getAddress() + " \n", fontBold));
                document.add(new Phrase("    мекенжайындағы сұралып отырған жер учаскесін беру мүмкіндігі келісімделуші мемлекеттік органдардан қортынды негізінде айқындалады.\n", fontBold));

                document.add(new Phrase("    2. Сұралып отырған жер учаскесі қала құрылысы нормалары мен талаптарына " + landSelectionData.getMatchesNorm() + " \n", font));
                document.add(new Phrase("    3. Жер учаскесін пайдалануда келесілерді орындау (сақтау) қажет: \n", font));
                document.add(new Phrase("        " + landSelectionData.getLandNeeds() + "\n", font));

                document.add(new Phrase("    Таңдау актісіне төмендегілер қоса беріледі: \n", fontBold));
                document.add(new Phrase("        1. Келісімдеуші органдардың қорытындылары;\n", font));
                document.add(new Phrase("        2. Инженерлік коммуникацияларға қосудың техникалық талаптары;\n", font));
                document.add(new Phrase("        3. Объектіні орналастыру схемасы және жер учаскесі шекарасының жобасы.\n", font));
                document.add(new Phrase("    Объект құрылысы үшін жер учаскесін таңдау актісі _____ бетте 2 данада жасалды. \n\n", fontBold));

                document.add(new Phrase("    Орындаушы: ", font));
                document.add(new Phrase(landSelectionData.getExecutor() + "\n", fontBold));
                document.add(new Phrase("    Байланыс деректері: ", font));
                document.add(new Phrase(landSelectionData.getInquiryPhone() + "\n", font));
                document.add(new Phrase("    Ескертпе: ", fontBold));
                document.add(new Phrase("Сұралып отырған жер учаскесінің конфигурациясы мен көлемі өзгерген жағдайда, өзгертудің түсіндірілген негіздемесі көрсетіледі. («Азаматтарға арналған үкімет» мемлекеттік корпорациясы коммерциялық емес акционерлік қоғамының Ақтөбе облысы бойынша филиалы- «Жер кадастыры ғылыми-өндірістік орталығы» департаментіне үшін)\n", font));

                document.add(new Phrase("    Өтініш берушімен келісімделмеген жер учаскесін таңдау актісінің әрекет ету мерзімі он жұмыс күнін құрайды. \n", font));
                document.add(new Phrase("    Жер учаскесін таңдау актісі құрылыс жұмыстарын жүргізу, аумақтарды пайдалану және онда шаруашылық қызметін жүргізу құқығын бермейді. \n", font));

                document.add(new Phrase("    Осы актіге оң шешім болған жағдайда қалалық сәулет және және қалақұрылысы бөлімі 25 жұмыс күн ХҚКО арқылы өтініш білдірушіге келісу үшін жер учаскесін таңдау туралы соңғы актіні және «Азаматтарға арналған үкімет» мемлекеттік корпорациясы коммерциялық емес акционерлік қоғамының Ақтөбе облысы бойынша филиалы- «Жер кадастыры ғылыми-өндірістік орталығы» департаментіне төмендегіні анықтап беру үшін дайындаған жер-кадастырлық жұмыстардың төлем шотын (сметасын) жолдайды. \n\n", fontBold));

                String agreementDate = "";
                if(landSelectionData.getAgreementDate() != null) {
                    agreementDate = sdf.format(landSelectionData.getAgreementDate()) + " жыл.";
                }
                document.add(new Phrase(agreementDate, font));

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));

                for(PdfPTable qrTable: qrTables) {
                    document.add(qrTable);
                }

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));

                if (mapImage != null && !mapImage.equals("") && !mapImage.equals("null")) {
                    byte[] mapImageByteArray = Base64.getDecoder().decode(mapImage);
                    Image image = Image.getInstance(mapImageByteArray);
                    float width = image.getWidth();
                    float height = image.getHeight();
                    float ratio = width / height;
                    image.scaleAbsoluteWidth(500f);
                    image.scaleAbsoluteHeight(500f / ratio);
                    PdfPTable table = new PdfPTable(1);
                    table.setWidthPercentage(100);
                    PdfPCell imageCell = new PdfPCell(image);
                    imageCell.setPaddingLeft(25);
                    imageCell.setPaddingRight(25);
                    imageCell.setPaddingTop(25);
                    imageCell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(imageCell);
                    document.add(Chunk.NEXTPAGE);
                    document.add(table);
                }

                try {
                    List<Map<String, String>> coordinates = gson.fromJson(landSelectionData.getCoordinates(), List.class);

                    document.add(new Phrase("\n\n"));
                    PdfPTable coorTable = new PdfPTable(2);
                    coorTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                    coorTable.setWidthPercentage(60);
                    coorTable.addCell(new PdfPCell(new Phrase("x", fontBold)));
                    coorTable.addCell(new PdfPCell(new Phrase("y", fontBold)));
                    for(Map<String, String> coordinate: coordinates) {
                        coorTable.addCell(new PdfPCell(new Phrase(coordinate.get("x"), font)));
                        coorTable.addCell(new PdfPCell(new Phrase(coordinate.get("y"), font)));
                    }
                    document.add(coorTable);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                document.close();
                return outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    public PdfPCell createCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createCell(String text, int alignment) {
        return createCell(text, alignment, arial);
    }

    private PdfPCell createCell(String text) {
        return createCell(text, Element.ALIGN_LEFT);
    }

    public byte[] generateApplicationPdf(Long id, String message, String xml) {
        App app = appRepository.findById(id).get();
        if (app == null) {
            return null;
        }
        Document document = new Document(PageSize.A4, 30, 30, 30, 60);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Image qr = getQRCodeApplication(id);
            writer.setPageEvent(new FooterEvent(qr, null, "eskiz"));
            document.open();
            message = message.replace("<br>", "<br/>");
//            message = message.replace("<span", "<div");
//            message = message.replace("</span>", "</div>");
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            try {
                String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                        "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
                        "<style type=\"text/css\">  table { border-collapse: collapse; width: 100%; } th { text-align:left; } " +
                        "table, th, td { border: 1px solid black; p {margin: 0; line-height: 21px;}    </style>" +
                        "</head>" +
                        "<body><div style='font-family: Times New Roman; padding: 0px 19px 30px 52.5px;'>" +
                        message + "</div></body></html>";
                document.open();
                InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

                XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
                fontProvider.setUseUnicode(true);
                fontProvider.register("fonts/times-new-roman.ttf");
                final CssFilesImpl cssFiles = new CssFilesImpl();
                cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
                final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
                final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
                hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
                final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
                final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
                final XMLWorker worker = new XMLWorker(pipeline, true);
                final Charset charset = Charset.forName("UTF-8");
                final XMLParser xmlParser = new XMLParser(true, worker, charset);
                xmlParser.parse(is, charset);

                //  XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (xml != null) {
                document.setMargins(0, 0, 30, 30);
                document.newPage();
//            Paragraph titleQrCode = new Paragraph("Qr Code");
//            titleQrCode.setIndentationLeft(60);
//            document.add(titleQrCode);

                PdfPTable centerTable = new PdfPTable(1);
                PdfPCell centerText = new PdfPCell(new Phrase("Электронно-цифровая подпись заявителя",font14));
                centerText.setBorder(Rectangle.NO_BORDER);
                centerText.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                centerTable.addCell(centerText);

                document.add(centerTable);
                document.add(getSignQRCode(CommonUtils.compressString(xml), 0));
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateApplicationPdf(Long id) {
        App app = appRepository.findById(id).get();
        String html = getHtmlOfApplicationPdf(app);
        byte[] file = generateApplicationPdf(id, html, app.getSignedXml());
        return file;
    }

    public String getHtmlOfApplicationPdf(App app) {
        String html = "";
        /*String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
            "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
            "<style type=\"text/css\">  table { border-collapse: collapse; width: 100%; } th { text-align:left; } " +
            "table, th, td { border: 1px solid black; p {margin: 0; line-height: 21px;}    </style>" +
            "</head>" +
            "<body><div style='font-family: Times New Roman; padding: 0px 19px 19px 52.5px;'>";*/
        try {
            if (app.getSubservice().getId() == 25L || app.getSubservice().getId() == 75L) {
                html+="<table style=\"border-color: white;\">\n" +
                        "<tr>\n" +
                        "<td style=\"border: solid 0 white; width: 28%\">&nbsp;</td>\n" +
                        "<td style=\"border: solid 0 white; width: 28%\">&nbsp;</td>\n" +
                        "<td style=\"border: solid 0 white; width: 44%\">\n" +
                        "<p align=\"center\">Приложение 1</p>\n" +
                        "<p align=\"center\">к стандарту государственной услуги &laquo;Выдача решения на изменение целевого назначения земельного участка&raquo;</p>\n" +
                        "<p align=\"center\">Форма</p>\n" +
                        "<p align=\"justify\">&nbsp;</p>\n" +
                        "<p align=\"center\" style=\"line-height: 100%;\">акиму {region}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">(области, города, района, поселка, села, сельского округа</p>\n" +
                        "<p align=\"center\" style=\"line-height: 100%;\">{akim}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">фамилия, имя, отчество акима (при его наличии))</p>\n" +
                        "<p align=\"center\" style=\"line-height: 100%;\">от {fullNameOrOrgName}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">фамилия, имя, отчество (при его наличии) физического</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">лица либо полное наименование юридического лица</p>\n" +
                        "<p align=\"center\" style=\"line-height: 100%;\">{iinOrbin}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">индивидуальный либо бизнес-идентификационный номер</p>\n" +
                        "<p align=\"center\" style=\"line-height: 100%;\">{phone}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">лица, контактный телефон(при наличии), адрес</p>\n" +
                        "<p align=\"center\" style=\"line-height: 100%;\">{address}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">местонахождения (для юридических лиц) либо адрес</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 30%;\">проживания (для физических лиц)</p>\n" +
                        "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "<p align=\"center\"><strong>Заявление </strong></p>\n" +
                        "<p align=\"center\"><strong>на изменение целевого назначения земельного участка</strong></p>\n" +
                        "<p align=\"justify\" style=\"margin: 50%;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Прошу Вас изменить целевое назначение земельного участка принадлежащего мне на праве частной собственности (землепользования) расположенного по адресу {objectInfoAddress} с кадастровым номером {cadastreNumber} с целевого назначения {objectInfoPurpose} на целевое назначение {purposeRequested} в связи с {changeReason}, площадью {objectInfoArea} гектар.</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 10%; margin: 0px;\">(указать причину необходимости изменения целевого назначения земельного участка)</p>\n" +
                        "<p align=\"justify\" style=\"margin: 50%;\">Правоустанавливающий документ № {legalDocNumber} от {legalDocDate} года,</p>\n" +
                        "<p align=\"justify\" style=\"margin: 50%;\">Идентификационный документ № {identDocNumber} от {identDocDate} года.</p>\n" +
                        "<p align=\"justify\" style=\"margin: 50%;\">Согласен(на) на использование сведений, составляющих охраняемую законом тайну, содержащихся в информационных системах.</p>\n" +
                        "<p align=\"justify\" style=\"margin: 50%;\">Заявитель {fullNameOrOrgName}</p>\n" +
                        "<p align=\"center\" style=\"text-align: center; font-size: x-small; line-height: 10%; margin: 0px;\">(фамилия, имя, отчество (при его наличии) физического лица либо уполномоченного представителя юридического лица, подпись)</p>\n" +
                        "<p align=\"justify\">&nbsp;&nbsp;{currentDate} года.</p>";
                html = html.replace("{region}", regionRepository.findFirstById(app.getRegionId()).getDeclensionRu());
                html = html.replace("{akim}", regionRepository.findFirstById(app.getRegionId()).getDeclensionRuAkim());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            html = html.replace("{id}", app.getId().toString());
            html = html.replace("{subserviceName}", app.getSubservice().getNameRu());
            html = html.replace("{address}", app.getAddress());
            html = html.replace("{phone}", app.getPhone());
            html = html.replace("{iin}", app.getIin());
            String fullName = app.getLastName() + " " + app.getFirstName() + " " + app.getSecondName();
            html = html.replace("{fullName}", fullName);
            if (app.getBin() != null) {
                html = html.replace("{iinOrbin}", app.getBin());
                html = html.replace("{fullNameOrOrgName}", app.getOrgName());
            } else {
                html = html.replace("{iinOrbin}", app.getIin());
                html = html.replace("{fullNameOrOrgName}", fullName);
            }
            if (app.getObjectInfo() != null) {
                html = html.replace("{objectInfoAddress}", app.getObjectInfo().getAddress() == null ? "" : app.getObjectInfo().getAddress());
                html = html.replace("{objectInfoName}", app.getObjectInfo().getName() == null ? "" : app.getObjectInfo().getName());
                html = html.replace("{objectInfoPurpose}", app.getObjectInfo().getPurpose() == null ? "" : app.getObjectInfo().getPurpose());
                html = html.replace("{objectInfoArea}", app.getObjectInfo().getArea() == null ? "" : app.getObjectInfo().getArea().toString());
                html = html.replace("{cadastreNumber}", app.getObjectInfo().getCadastreNumber() == null ? "" : app.getObjectInfo().getCadastreNumber());
                html = html.replace("{purposeRequested}", app.getObjectInfo().getPurposeRequested() == null ? "" : app.getObjectInfo().getPurposeRequested());
                html = html.replace("{changeReason}", app.getObjectInfo().getChangeReason() == null ? "" : app.getObjectInfo().getChangeReason());
                html =html.replace("{legalDocNumber}", app.getObjectInfo().getLegalDocNumber() == null ? "" : app.getObjectInfo().getLegalDocNumber());
                html =html.replace("{legalDocDate}", app.getObjectInfo().getLegalDocDate() == null ? "" : sdf.format(app.getObjectInfo().getLegalDocDate()));
                html =html.replace("{identDocNumber}", app.getObjectInfo().getIdentDocNumber() == null ? "" : app.getObjectInfo().getIdentDocNumber());
                html =html.replace("{identDocDate}", app.getObjectInfo().getIdentDocDate() == null ? "" : sdf.format(app.getObjectInfo().getIdentDocDate()));
                html =html.replace("{useRight}", app.getObjectInfo().getUseRight() == null ? "" : app.getObjectInfo().getUseRight());

            }
            if (app.getLandInfo() != null) {
                html = html.replace("{landInfoOrgName}", app.getLandInfo().getOrgName() == null ? "" : app.getLandInfo().getOrgName());
                html = html.replace("{landInfoCopyCount}", app.getLandInfo().getCopyCount() == null ? "" : app.getLandInfo().getCopyCount().toString());
                html = html.replace("{protocolDate}", app.getLandInfo().getProtocolDate() == null ? "" : sdf.format(app.getLandInfo().getProtocolDate()));
                html = html.replace("{protocolNumber}", app.getLandInfo().getProtocolNumber() == null ? "" : app.getLandInfo().getProtocolNumber());
            }
            html = html.replace("{signedDate}", sdf.format(app.getSignedDate()));
            html = html.replace("{currentDate}", sdf.format(new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*html += "</div></body></html>";*/
        return html;
    }

    public void saveApplicationPdfFile(Long id) {
        App app = appRepository.findById(id).get();

        byte[] file = generateApplicationPdf(id);

        if(file != null) {
            String fileName = "Заявление.pdf";
            FileDto dto = fileService.uploadFile(file, fileName, FileCategory.APPLICATION_PDF);
            AppFile appFile = new AppFile();
            appFile.setContentType("application/pdf");
            appFile.setName(fileName);
            appFile.setFileCategory(FileCategory.APPLICATION_PDF);
            appFile.setSize(Long.valueOf(file.length));
            appFile.setObjectId(dto.getUid());
            appFile.setUploadDate(new Date());
            appFile.setApp(app);
            appFileRepository.save(appFile);
        }
    }

}
