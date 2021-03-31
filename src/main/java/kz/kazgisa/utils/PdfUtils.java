package kz.kazgisa.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.Apz;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PdfUtils {
    public static final String IMG1 = "images/emblem.png";

    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
        InputStream is = new ClassPathResource(path).getInputStream();
        byte[] img = CommonUtils.inputStreamToByteArray(is);

        Image image = Image.getInstance(img);
        float width = image.getWidth();
        float height = image.getHeight();
        float ratio = width / height;

        image.scaleAbsoluteWidth(50f);
        image.scaleAbsoluteHeight(50f / ratio);
        PdfPCell cell = new PdfPCell(image);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public  static void getMainPage(Document doc, Apz apz, App app, Font fontTitle, Font fontHead, Font fontObject) {
        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidths(new int[] {3,1,3});

            PdfPCell leftTitle = new PdfPCell();
            leftTitle.addElement(new Phrase("Атырау қаласының сәулет және қала құрылысы бөлімі",fontTitle));
            leftTitle.setBorder(Rectangle.NO_BORDER);
            table.addCell(leftTitle);

            PdfPCell imgCell = createImageCell(IMG1);
            imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(imgCell);

            PdfPCell rightTitle = new PdfPCell(new Phrase("Отдел архитектуры и \n градостроительства города \n Атырау",fontTitle));
            rightTitle.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightTitle.setBorder(Rectangle.NO_BORDER);
            table.addCell(rightTitle);

            PdfPTable rightTable = new PdfPTable(1);
            rightTable.setTotalWidth(475);
            rightTable.setLockedWidth(true);

            PdfPCell rightTitle2 = new PdfPCell(new Phrase("Бекітемін:\n Утверждаю:\n Бөлім басшысының міндетін атқарушы\n Исполняющий обязанности руководителя отдела\n " +
                    "Байқадамов Ануар Бисембаевич \n" +
                    "(Т.А.Ә) (Ф.И.О)",fontTitle));
            rightTitle2.setBorder(Rectangle.NO_BORDER);
            rightTitle2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightTitle2.setPaddingTop(40);
            rightTable.addCell(rightTitle2);

            PdfPTable centerTable = new PdfPTable(1);
            centerTable.setTotalWidth(300);
            centerTable.setLockedWidth(true);



            PdfPCell centerText = new PdfPCell(new Phrase("Жобалауға арналған \n  сәулет-жоспарлау тапсырмасы (СЖТ)\n " +
                    "Архитектурно-планировочное задание (АПЗ)\n на проектирование",fontHead));
            centerText.setBorder(Rectangle.NO_BORDER);
            centerText.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            centerText.setPaddingTop(50);
            centerTable.addCell(centerText);

            PdfPTable leftNumberTable = new PdfPTable(1);
            leftNumberTable.setTotalWidth(350);
            leftNumberTable.setLockedWidth(true);

            SimpleDateFormat dt1 = new SimpleDateFormat("dd.MM.yyyy");


            PdfPCell number = new PdfPCell(new Phrase("Номер: "+app.getId()+" от Дата выдачи: " +dt1.format(new Date()),fontTitle ));
            number.setBorder(Rectangle.NO_BORDER);
            number.setPaddingTop(30);
            leftNumberTable.addCell(number);

            PdfPTable leftObjectTable = new PdfPTable(1);
            leftObjectTable.setTotalWidth(420);
            leftObjectTable.setLockedWidth(true);

            String fullname = app.getOrgName() == null ? app.getFirstName()+" "+ app.getLastName() : app.getOrgName();
            String objectName =  app.getObjectInfo().getName() == null ? "   ___" :  app.getObjectInfo().getName();
            PdfPCell objectText = new PdfPCell(new Phrase("Объектінің атауы: " + apz.getObjectNameKz()
                    +"\nНаименование объекта: "  + apz.getObjectNameRu() +   " \n" +
                    "Тапсырыс беруші (құрылыс салушы,инвестор): "+apz.getDeclarerNameKz() +" \n" +
                    "Заказчик (застройщик,инвестор):"+ apz.getDeclarerNameRu(),fontObject));
            objectText.setBorder(Rectangle.NO_BORDER);
            objectText.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            objectText.setPaddingTop(30);
            objectText.setPaddingBottom(150);
            leftObjectTable.addCell(objectText);


            PdfPTable cityName = new PdfPTable(1);

            PdfPCell city = new PdfPCell(new Phrase("Атырау қаласы / город Атырау, 2019",fontObject));
            city.setPaddingLeft(100);
            city.setPaddingBottom(150);
            city.setBorder(Rectangle.NO_BORDER);
            cityName.addCell(city);

            doc.add(table);
            doc.add(rightTable);
            doc.add(centerTable);
            doc.add(leftNumberTable);
            doc.add(leftObjectTable);
            doc.add(cityName);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PdfPTable getAreaCharInfoTable(Map<String, String> map, Font font, Font fontTitle,Font fontHead) {
        PdfPTable table = new PdfPTable(2);

        PdfPCell basisKZ = new PdfPCell();
        basisKZ.addElement(new Phrase("Сәулет - жоспарлау тапсырмасын (СЖТ) әзірлеу үшін негіздеме  ",fontTitle));
        basisKZ.addElement(new Phrase(map.get("basisDevelopmentApzKz"),font));
        basisKZ.setPaddingLeft(20);
        basisKZ.setPaddingRight(20);
        basisKZ.setPaddingBottom(5);
        table.addCell(basisKZ);

        PdfPCell basisRu = new PdfPCell();
        basisRu.addElement(new Phrase("Основание для разработки архитектурно - планировочного задания (АПЗ) ",fontTitle));
        basisRu.addElement(new Phrase(map.get("basisDevelopmentApzRu"),font));
        basisRu.setPaddingLeft(20);
        basisRu.setPaddingRight(20);
        basisRu.setPaddingBottom(5);
        table.addCell(basisRu);

        PdfPCell stadingKz = new PdfPCell();
        stadingKz.addElement(new Phrase("Сатылығы  ",fontTitle));
        stadingKz.addElement(new Phrase(map.get("stadingKz"),font));
        stadingKz.setPaddingLeft(20);
        stadingKz.setPaddingRight(20);
        stadingKz.setPaddingBottom(5);
        table.addCell(stadingKz);

        PdfPCell stadingRu = new PdfPCell();
        stadingRu.addElement(new Phrase("Стадийность  ",fontTitle));
        stadingRu.addElement(new Phrase(map.get("stadingRu"),font));
        stadingRu.setPaddingLeft(20);
        stadingRu.setPaddingRight(20);
        stadingRu.setPaddingBottom(5);
        table.addCell(stadingRu);

        PdfPCell areaChar = new PdfPCell(new Phrase("Учаскенің сипаттамасы \n Характеристика участка  ",fontHead));
        areaChar.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        areaChar.setVerticalAlignment(Element.ALIGN_CENTER);
        areaChar.setColspan(2);
        table.addCell(areaChar);

        PdfPCell locationKz = new PdfPCell();
        locationKz.addElement(new Phrase("Учаскенің орналасқан жері  ",fontTitle));
        locationKz.addElement(new Phrase(map.get("areaLocationKz"),font));
        locationKz.setPaddingLeft(20);
        locationKz.setPaddingRight(20);
        locationKz.setPaddingBottom(5);
        table.addCell(locationKz);

        PdfPCell locationRu = new PdfPCell();
        locationRu.addElement(new Phrase("Местонахождение участка  ",fontTitle));
        locationRu.addElement(new Phrase(map.get("areaLocationRu"),font));
        locationRu.setPaddingLeft(20);
        locationRu.setPaddingRight(20);
        locationRu.setPaddingBottom(5);
        table.addCell(locationRu);

        PdfPCell availabilityDevelopmentKz = new PdfPCell();
        availabilityDevelopmentKz.addElement(new Phrase("Салынған учаскенің болуы  ",fontTitle));
        availabilityDevelopmentKz.addElement(new Phrase(map.get("availabilityDevelopmentKz"),font));
        availabilityDevelopmentKz.setPaddingLeft(20);
        availabilityDevelopmentKz.setPaddingRight(20);
        availabilityDevelopmentKz.setPaddingBottom(5);
        table.addCell(availabilityDevelopmentKz);

        PdfPCell availabilityDevelopmentRu = new PdfPCell();
        availabilityDevelopmentRu.addElement(new Phrase("Наличие застройки  ",fontTitle));
        availabilityDevelopmentRu.addElement(new Phrase(map.get("availabilityDevelopmentRu"),font));
        availabilityDevelopmentRu.setPaddingLeft(20);
        availabilityDevelopmentRu.setPaddingRight(20);
        availabilityDevelopmentRu.setPaddingBottom(5);
        table.addCell(availabilityDevelopmentRu);

        PdfPCell geodesyKz = new PdfPCell();
        geodesyKz.addElement(new Phrase("Геодезиялық зерттелуі  ",fontTitle));
        geodesyKz.addElement(new Phrase(map.get("studyGeodesyKz"),font));
        geodesyKz.setPaddingLeft(20);
        geodesyKz.setPaddingRight(20);
        geodesyKz.setPaddingBottom(5);
        table.addCell(geodesyKz);

        PdfPCell geodesyRu = new PdfPCell();
        geodesyRu.addElement(new Phrase("Геодезическая изученность  ",fontTitle));
        geodesyRu.addElement(new Phrase(map.get("studyGeodesyRu"),font));
        geodesyRu.setPaddingLeft(20);
        geodesyRu.setPaddingRight(20);
        geodesyRu.setPaddingBottom(5);
        table.addCell(geodesyRu);

        PdfPCell engineeringKz = new PdfPCell();
        engineeringKz.addElement(new Phrase("Инженерлік-геологиялық зерттелуі  ",fontTitle));
        engineeringKz.addElement(new Phrase(map.get("engineeringGeologicalStudyKz"),font));
        engineeringKz.setPaddingLeft(20);
        engineeringKz.setPaddingRight(20);
        engineeringKz.setPaddingBottom(5);
        table.addCell(engineeringKz);

        PdfPCell engineeringRu = new PdfPCell();
        engineeringRu.addElement(new Phrase("Инженерно-геологическая изученность  ",fontTitle));
        engineeringRu.addElement(new Phrase(map.get("engineeringGeologicalStudyRu"),font));
        engineeringRu.setPaddingLeft(20);
        engineeringRu.setPaddingRight(20);
        engineeringRu.setPaddingBottom(5);
        table.addCell(engineeringRu);

        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getObjectCharInfoTable(Map<String, String> map, Font font,Font fontTitle ,Font fontHead){
        PdfPTable table = new PdfPTable(2);

        PdfPCell title = new PdfPCell(new Phrase("Жобаланатын объектінің сипаттамасы \n Характеристика проектируемого объекта", fontHead));
        title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);

        title.setColspan(2);
        table.addCell(title);

        PdfPCell funcObjKz = new PdfPCell();
        funcObjKz.addElement(new Phrase("Объектінің функционалдық мәні ",fontTitle));
        funcObjKz.addElement(new Phrase(map.get("functionalObjectKz"),font));
        funcObjKz.setPaddingLeft(20);
        funcObjKz.setPaddingRight(20);
        funcObjKz.setPaddingBottom(5);
        table.addCell(funcObjKz);

        PdfPCell funcObjRu = new PdfPCell();
        funcObjRu.addElement(new Phrase("Функциональное значение объекта ",fontTitle));
        funcObjRu.addElement(new Phrase(map.get("functionalObjectRu"),font));
        funcObjRu.setPaddingLeft(20);
        funcObjRu.setPaddingRight(20);
        funcObjRu.setPaddingBottom(5);
        table.addCell(funcObjRu);

        PdfPCell storeysKz = new PdfPCell();
        storeysKz.addElement(new Phrase("Қабаттылығы ",fontTitle));
        storeysKz.addElement(new Phrase(map.get("storeysKz"),font));
        storeysKz.setPaddingLeft(20);
        storeysKz.setPaddingRight(20);
        storeysKz.setPaddingBottom(5);
        table.addCell(storeysKz);

        PdfPCell storeysRu = new PdfPCell();
        storeysRu.addElement(new Phrase("Этажность ",fontTitle));
        storeysRu.addElement(new Phrase(map.get("storeysRu"),font));
        storeysRu.setPaddingLeft(20);
        storeysRu.setPaddingRight(20);
        storeysRu.setPaddingBottom(5);
        table.addCell(storeysRu);

        PdfPCell planSystemKz = new PdfPCell();
        planSystemKz.addElement(new Phrase("Жоспарлау жүйесі ",fontTitle));
        planSystemKz.addElement(new Phrase(map.get("planningSystemKz"),font));
        planSystemKz.setPaddingLeft(20);
        planSystemKz.setPaddingRight(20);
        planSystemKz.setPaddingBottom(5);
        table.addCell(planSystemKz);

        PdfPCell planSystemRu = new PdfPCell();
        planSystemRu.addElement(new Phrase("Планировочная система ",fontTitle));
        planSystemRu.addElement(new Phrase(map.get("planningSystemRu"),font));
        planSystemRu.setPaddingLeft(20);
        planSystemRu.setPaddingRight(20);
        planSystemRu.setPaddingBottom(5);
        table.addCell(planSystemRu);

        PdfPCell strucSchemeKz = new PdfPCell();
        strucSchemeKz.addElement(new Phrase("Конструктивті схема ",fontTitle));
        strucSchemeKz.addElement(new Phrase(map.get("structuralSchemeKz"),font));
        strucSchemeKz.setPaddingLeft(20);
        strucSchemeKz.setPaddingRight(20);
        strucSchemeKz.setPaddingBottom(5);
        table.addCell(strucSchemeKz);

        PdfPCell strucSchemeRu = new PdfPCell();
        strucSchemeRu.addElement(new Phrase("Конструктивная схема ",fontTitle));
        strucSchemeRu.addElement(new Phrase(map.get("strucSchemeRu"),font));
        strucSchemeRu.setPaddingLeft(20);
        strucSchemeRu.setPaddingRight(20);
        strucSchemeRu.setPaddingBottom(5);
        table.addCell(strucSchemeRu);

        PdfPCell engSupportKz = new PdfPCell();
        engSupportKz.addElement(new Phrase("Инженерлік қамтамасыз ету  ",fontTitle));
        engSupportKz.addElement(new Phrase(map.get("engineeringSupportKz"),font));
        engSupportKz.setPaddingLeft(20);
        engSupportKz.setPaddingRight(20);
        engSupportKz.setPaddingBottom(5);
        table.addCell(engSupportKz);

        PdfPCell engSupportRu = new PdfPCell();
        engSupportRu.addElement(new Phrase("Инженерное обеспечение ",fontTitle));
        engSupportRu.addElement(new Phrase(map.get("engineeringSupportRu"),font));
        engSupportRu.setPaddingLeft(20);
        engSupportRu.setPaddingRight(20);
        engSupportRu.setPaddingBottom(5);
        table.addCell(engSupportRu);

        PdfPCell energyClassKz = new PdfPCell();
        energyClassKz.addElement(new Phrase("Энергия тиімділік сыныбы  ",fontTitle));
        energyClassKz.addElement(new Phrase(map.get("energyEfficiencyClassKz"),font));
        energyClassKz.setPaddingLeft(20);
        energyClassKz.setPaddingRight(20);
        energyClassKz.setPaddingBottom(5);
        table.addCell(energyClassKz);

        PdfPCell energyClassRu = new PdfPCell();
        energyClassRu.addElement(new Phrase("Класс энергоэффективности ",fontTitle));
        energyClassRu.addElement(new Phrase(map.get("energyEfficiencyClassRu"),font));
        energyClassRu.setPaddingLeft(20);
        energyClassRu.setPaddingRight(20);
        energyClassRu.setPaddingBottom(5);
        table.addCell(energyClassRu);

        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getGradReqInfoTable(Map<String, String> map, Font font, Font fontTitle,Font fontHead){
        PdfPTable table = new PdfPTable(2);

        PdfPCell title = new PdfPCell(new Phrase("Қала құрылысы талаптары \n  Градостроительные требования", fontHead));
        title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);

        title.setColspan(2);
        table.addCell(title);

        PdfPCell solutionKz = new PdfPCell();
        solutionKz.addElement(new Phrase("Көлемдік-кеңістіктік шешім ",fontTitle));
        solutionKz.addElement(new Phrase(map.get("spatialSolutionKz"),font));
        solutionKz.setPaddingLeft(20);
        solutionKz.setPaddingRight(20);
        solutionKz.setPaddingBottom(5);
        table.addCell(solutionKz);

        PdfPCell solutionRu = new PdfPCell();
        solutionRu.addElement(new Phrase("Объемно-пространственное решение ",fontTitle));
        solutionRu.addElement(new Phrase(map.get("spatialSolutionRu"),font));
        solutionRu.setPaddingLeft(20);
        solutionRu.setPaddingRight(20);
        solutionRu.setPaddingBottom(5);
        table.addCell(solutionRu);

        PdfPCell MasterPlanKz = new PdfPCell();
        MasterPlanKz.addElement(new Phrase("Бас жоспар жобасы ",fontTitle));
        MasterPlanKz.addElement(new Phrase(map.get("draftMasterPlanKz"),font));
        MasterPlanKz.setPaddingLeft(20);
        MasterPlanKz.setPaddingRight(20);
        MasterPlanKz.setPaddingBottom(5);
        table.addCell(MasterPlanKz);

        PdfPCell MasterPlanRu = new PdfPCell();
        MasterPlanRu.addElement(new Phrase("Проект генерального плана ",fontTitle));
        MasterPlanRu.addElement(new Phrase(map.get("draftMasterPlanRu"),font));
        MasterPlanRu.setPaddingLeft(20);
        MasterPlanRu.setPaddingRight(20);
        MasterPlanRu.setPaddingBottom(5);
        table.addCell(MasterPlanRu);

        PdfPCell vertlPlanKz = new PdfPCell();
        vertlPlanKz.addElement(new Phrase("Тік жоспарлау ",fontTitle));
        vertlPlanKz.addElement(new Phrase(map.get("verticalPlanningKz"),font));
        vertlPlanKz.setPaddingLeft(20);
        vertlPlanKz.setPaddingRight(20);
        vertlPlanKz.setPaddingBottom(5);
        table.addCell(vertlPlanKz);

        PdfPCell vertlPlanRu = new PdfPCell();
        vertlPlanRu.addElement(new Phrase("Вертикальная планировка ",fontTitle));
        vertlPlanRu.addElement(new Phrase(map.get("verticalPlanningRu"),font));
        vertlPlanRu.setPaddingLeft(20);
        vertlPlanRu.setPaddingRight(20);
        vertlPlanRu.setPaddingBottom(5);
        table.addCell(vertlPlanRu);

        PdfPCell gardenKz = new PdfPCell();
        gardenKz.addElement(new Phrase("Абаттандыру және көгалдандыру ",fontTitle));
        gardenKz.addElement(new Phrase(map.get("landscapingGardeningKz"),font));
        gardenKz.setPaddingLeft(20);
        gardenKz.setPaddingRight(20);
        gardenKz.setPaddingBottom(5);
        table.addCell(gardenKz);

        PdfPCell gardenRu = new PdfPCell();
        gardenRu.addElement(new Phrase("Благоустройство и озеленение ",fontTitle));
        gardenRu.addElement(new Phrase(map.get("landscapingGardeningRu"),font));
        gardenRu.setPaddingLeft(20);
        gardenRu.setPaddingRight(20);
        gardenRu.setPaddingBottom(5);
        table.addCell(gardenRu);

        PdfPCell carParkingKz = new PdfPCell();
        carParkingKz.addElement(new Phrase("Автомобильдер тұрағы  ",fontTitle));
        carParkingKz.addElement(new Phrase(map.get("carParkingKz"),font));
        carParkingKz.setPaddingLeft(20);
        carParkingKz.setPaddingRight(20);
        carParkingKz.setPaddingBottom(5);
        table.addCell(carParkingKz);

        PdfPCell carParkingRu = new PdfPCell();
        carParkingRu.addElement(new Phrase("Парковка автомобилей ",fontTitle));
        carParkingRu.addElement(new Phrase(map.get("carParkingRu"),font));
        carParkingRu.setPaddingLeft(20);
        carParkingRu.setPaddingRight(20);
        carParkingRu.setPaddingBottom(5);
        table.addCell(carParkingRu);

        PdfPCell useOfFertileSoilKz = new PdfPCell();
        useOfFertileSoilKz.addElement(new Phrase("Топырақтың құнарлы қабатын пайдалану  ",fontTitle));
        useOfFertileSoilKz.addElement(new Phrase(map.get("useOfFertileSoilKz"),font));
        useOfFertileSoilKz.setPaddingLeft(20);
        useOfFertileSoilKz.setPaddingRight(20);
        useOfFertileSoilKz.setPaddingBottom(5);
        table.addCell(useOfFertileSoilKz);

        PdfPCell useOfFertileSoilRu = new PdfPCell();
        useOfFertileSoilRu.addElement(new Phrase("Использование плодородного слоя почвы ",fontTitle));
        useOfFertileSoilRu.addElement(new Phrase(map.get("useOfFertileSoilRu"),font));
        useOfFertileSoilRu.setPaddingLeft(20);
        useOfFertileSoilRu.setPaddingRight(20);
        useOfFertileSoilRu.setPaddingBottom(5);
        table.addCell(useOfFertileSoilRu);

        PdfPCell smArchFormsKz = new PdfPCell();
        smArchFormsKz.addElement(new Phrase("Шағын сәулет нысандары ",fontTitle));
        smArchFormsKz.addElement(new Phrase(map.get("smallArchitecturalFormsKz"),font));
        smArchFormsKz.setPaddingLeft(20);
        smArchFormsKz.setPaddingRight(20);
        smArchFormsKz.setPaddingBottom(5);
        table.addCell(smArchFormsKz);

        PdfPCell smArchFormsRu = new PdfPCell();
        smArchFormsRu.addElement(new Phrase("Малые архитектурные формы ",fontTitle));
        smArchFormsRu.addElement(new Phrase(map.get("smallArchitecturalFormsRu"),font));
        smArchFormsRu.setPaddingLeft(20);
        smArchFormsRu.setPaddingRight(20);
        smArchFormsRu.setPaddingBottom(5);
        table.addCell(smArchFormsRu);

        PdfPCell lightingKz = new PdfPCell();
        lightingKz.addElement(new Phrase("Жарықтандыру ",fontTitle));
        lightingKz.addElement(new Phrase(map.get("lightingKz"),font));
        lightingKz.setPaddingLeft(20);
        lightingKz.setPaddingRight(20);
        lightingKz.setPaddingBottom(5);
        table.addCell(lightingKz);

        PdfPCell lightingRu = new PdfPCell();
        lightingRu.addElement(new Phrase("Освещение ",fontTitle));
        lightingRu.addElement(new Phrase(map.get("lightingRu"),font));
        lightingRu.setPaddingLeft(20);
        lightingRu.setPaddingRight(20);
        lightingRu.setPaddingBottom(5);
        table.addCell(lightingRu);

        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getArchReqInfoTable(Map<String, String> map, Font font,Font fontTitle,Font fontHead){
        PdfPTable table = new PdfPTable(2);

        PdfPCell title = new PdfPCell(new Phrase("Сәулет талаптары \n Архитектурные требования", fontHead));
        title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);

        title.setColspan(2);
        table.addCell(title);

        PdfPCell stylArchKz = new PdfPCell();
        stylArchKz.addElement(new Phrase("Сәулеттік келбетінің стилистикасы ",fontTitle));
        stylArchKz.addElement(new Phrase(map.get("stylisticsArchitecturalKz"),font));
        stylArchKz.setPaddingLeft(20);
        stylArchKz.setPaddingRight(20);
        stylArchKz.setPaddingBottom(5);
        table.addCell(stylArchKz);

        PdfPCell stylArchRu = new PdfPCell();
        stylArchRu.addElement(new Phrase("Стилистика архитектурного образа ",fontTitle));
        stylArchRu.addElement(new Phrase(map.get("stylArchRu"),font));
        stylArchRu.setPaddingLeft(20);
        stylArchRu.setPaddingRight(20);
        stylArchRu.setPaddingBottom(5);
        table.addCell(stylArchRu);

        PdfPCell natureComKz = new PdfPCell();
        natureComKz.addElement(new Phrase("Қоршап тұрған құрылыс салумен өзара үйлесімдік сипаты ",fontTitle));
        natureComKz.addElement(new Phrase(map.get("natureCombinationKz"),font));
        natureComKz.setPaddingLeft(20);
        natureComKz.setPaddingRight(20);
        natureComKz.setPaddingBottom(5);
        table.addCell(natureComKz);

        PdfPCell natureComRu = new PdfPCell();
        natureComRu.addElement(new Phrase("Характер сочетания с окружающей застройкой ",fontTitle));
        natureComRu.addElement(new Phrase(map.get("natureCombinationRu"),font));
        natureComRu.setPaddingLeft(20);
        natureComRu.setPaddingRight(20);
        natureComRu.setPaddingBottom(5);
        table.addCell(natureComRu);

        PdfPCell colorSolutionKz = new PdfPCell();
        colorSolutionKz.addElement(new Phrase("Түсіне қатысты шешім ",fontTitle));
        colorSolutionKz.addElement(new Phrase(map.get("colorSolutionKz"),font));
        colorSolutionKz.setPaddingLeft(20);
        colorSolutionKz.setPaddingRight(20);
        colorSolutionKz.setPaddingBottom(5);
        table.addCell(colorSolutionKz);

        PdfPCell colorSolutionRu = new PdfPCell();
        colorSolutionRu.addElement(new Phrase("Цветовое решение ",fontTitle));
        colorSolutionRu.addElement(new Phrase(map.get("colorSolutionRu"),font));
        colorSolutionRu.setPaddingLeft(20);
        colorSolutionRu.setPaddingRight(20);
        colorSolutionRu.setPaddingBottom(5);
        table.addCell(colorSolutionRu);

        PdfPCell advInfoSolKz = new PdfPCell();
        advInfoSolKz.addElement(new Phrase("Жарнамалық-ақпараттық шешім, оның ішінде: ",fontTitle));
        advInfoSolKz.addElement(new Phrase(map.get("advertisingInformationSolutionKz"),font));
        advInfoSolKz.setPaddingLeft(20);
        advInfoSolKz.setPaddingRight(20);
        advInfoSolKz.setPaddingBottom(5);
        table.addCell(advInfoSolKz);

        PdfPCell advInfoSolRu = new PdfPCell();
        advInfoSolRu.addElement(new Phrase("Рекламно-информационное решение, в том числе: ",fontTitle));
        advInfoSolRu.addElement(new Phrase(map.get("advertisingInformationSolutionRu"),font));
        advInfoSolRu.setPaddingLeft(20);
        advInfoSolRu.setPaddingRight(20);
        advInfoSolRu.setPaddingBottom(5);
        table.addCell(advInfoSolRu);

        PdfPCell nightLightingKz = new PdfPCell();
        nightLightingKz.addElement(new Phrase("түнгі жарықпен безендіру  ",fontTitle));
        nightLightingKz.addElement(new Phrase(map.get("nightLightingKz"),font));
        nightLightingKz.setPaddingLeft(20);
        nightLightingKz.setPaddingRight(20);
        nightLightingKz.setPaddingBottom(5);
        table.addCell(nightLightingKz);

        PdfPCell nightLightingRu = new PdfPCell();
        nightLightingRu.addElement(new Phrase("ночное световое оформление ",fontTitle));
        nightLightingRu.addElement(new Phrase(map.get("nightLightingRu"),font));
        nightLightingRu.setPaddingLeft(20);
        nightLightingRu.setPaddingRight(20);
        nightLightingRu.setPaddingBottom(5);
        table.addCell(nightLightingRu);

        PdfPCell inputNodesKz = new PdfPCell();
        inputNodesKz.addElement(new Phrase("Кіреберіс тораптар ",fontTitle));
        inputNodesKz.addElement(new Phrase(map.get("inputNodesKz"),font));
        inputNodesKz.setPaddingLeft(20);
        inputNodesKz.setPaddingRight(20);
        inputNodesKz.setPaddingBottom(5);
        table.addCell(inputNodesKz);

        PdfPCell inputNodesRu = new PdfPCell();
        inputNodesRu.addElement(new Phrase("Входные узлы ",fontTitle));
        inputNodesRu.addElement(new Phrase(map.get("inputNodesRu"),font));
        inputNodesRu.setPaddingLeft(20);
        inputNodesRu.setPaddingRight(20);
        inputNodesRu.setPaddingBottom(5);
        table.addCell(inputNodesRu);

        PdfPCell creatConditionKz = new PdfPCell();
        creatConditionKz.addElement(new Phrase("Халықтың мүмкіндігі шектеулі топтарының өмір сүруі үшін жағдай жасау ",fontTitle));
        creatConditionKz.addElement(new Phrase(map.get("creatingConditionsKz"),font));
        creatConditionKz.setPaddingLeft(20);
        creatConditionKz.setPaddingRight(20);
        creatConditionKz.setPaddingBottom(5);
        table.addCell(creatConditionKz);

        PdfPCell creatConditionRu = new PdfPCell();
        creatConditionRu.addElement(new Phrase("Создание условий для жизнедеятельности маломобильных групп населения ",fontTitle));
        creatConditionRu.addElement(new Phrase(map.get("creatingConditionsRu"),font));
        creatConditionRu.setPaddingLeft(20);
        creatConditionRu.setPaddingRight(20);
        creatConditionRu.setPaddingBottom(5);
        table.addCell(creatConditionRu);

        PdfPCell complianceCondKz = new PdfPCell();
        complianceCondKz.addElement(new Phrase("Дыбыс-шу көрсеткіштері бойынша шарттарды сақтау ",fontTitle));
        complianceCondKz.addElement(new Phrase(map.get("complianceConditionsKz"),font));
        complianceCondKz.setPaddingLeft(20);
        complianceCondKz.setPaddingRight(20);
        complianceCondKz.setPaddingBottom(5);
        table.addCell(complianceCondKz);

        PdfPCell complianceCondRu = new PdfPCell();
        complianceCondRu.addElement(new Phrase("Соблюдение условий по звукошумовым показателям ",fontTitle));
        complianceCondRu.addElement(new Phrase(map.get("complianceConditionsRu"),font));
        complianceCondRu.setPaddingLeft(20);
        complianceCondRu.setPaddingRight(20);
        complianceCondRu.setPaddingBottom(5);
        table.addCell(complianceCondRu);

        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getExteriorReqInfoTable(Map<String, String> map, Font font, Font fontTitle,Font fontHead) {
        PdfPTable table = new PdfPTable(2);

        PdfPCell title = new PdfPCell(new Phrase("Сыртқы әрлеуге қойылатын талаптар \n Требования к наружной отделке", fontHead));
        title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);

        title.setColspan(2);
        table.addCell(title);

        PdfPCell socleKz = new PdfPCell();
        socleKz.addElement(new Phrase("Цоколь  ",fontTitle));
        socleKz.addElement(new Phrase(map.get("socleKz"),font));
        socleKz.setPaddingLeft(20);
        socleKz.setPaddingRight(20);
        socleKz.setPaddingBottom(5);
        table.addCell(socleKz);

        PdfPCell socleRu = new PdfPCell();
        socleRu.addElement(new Phrase("Цоколь ",fontTitle));
        socleRu.addElement(new Phrase(map.get("socleRu"),font));
        socleRu.setPaddingLeft(20);
        socleRu.setPaddingRight(20);
        socleRu.setPaddingBottom(5);
        table.addCell(socleRu);

        PdfPCell facadeKz = new PdfPCell();
        facadeKz.addElement(new Phrase("Қасбет. Қоршау конструкциялары ",fontTitle));
        facadeKz.addElement(new Phrase(map.get("facadeKz"),font));
        facadeKz.setPaddingLeft(20);
        facadeKz.setPaddingRight(20);
        facadeKz.setPaddingBottom(5);
        table.addCell(facadeKz);

        PdfPCell facadeRu = new PdfPCell();
        facadeRu.addElement(new Phrase("Фасад. Ограждающие конструкций  ",fontTitle));
        facadeRu.addElement(new Phrase(map.get("facadeRu"),font));
        facadeRu.setPaddingLeft(20);
        facadeRu.setPaddingRight(20);
        facadeRu.setPaddingBottom(5);
        table.addCell(facadeRu);


        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getEngNetworkReqInfoTable(Map<String, String> map, Font font, Font fontTitle,Font fontHead){
        PdfPTable table = new PdfPTable(2);

        PdfPCell title = new PdfPCell(new Phrase("Инженерлік желілерге қойылатын талаптар \n Требования к инженерным сетям", fontHead));
        title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);

        title.setColspan(2);
        table.addCell(title);

        PdfPCell heatSupplyKz = new PdfPCell();
        heatSupplyKz.addElement(new Phrase("Жылумен жабдықтау ",fontTitle));
        heatSupplyKz.addElement(new Phrase(map.get("heatSupplyKz"),font));
        heatSupplyKz.setPaddingLeft(20);
        heatSupplyKz.setPaddingRight(20);
        heatSupplyKz.setPaddingBottom(5);
        table.addCell(heatSupplyKz);

        PdfPCell heatSupplyRu = new PdfPCell();
        heatSupplyRu.addElement(new Phrase("Теплоснабжение",fontTitle));
        heatSupplyRu.addElement(new Phrase(map.get("heatSupplyRu"),font));
        heatSupplyRu.setPaddingLeft(20);
        heatSupplyRu.setPaddingRight(20);
        heatSupplyRu.setPaddingBottom(5);
        table.addCell(heatSupplyRu);

        PdfPCell waterSupplyKz = new PdfPCell();
        waterSupplyKz.addElement(new Phrase("Сумен жабдықтау ",fontTitle));
        waterSupplyKz.addElement(new Phrase(map.get("waterSupplyKz"),font));
        waterSupplyKz.setPaddingLeft(20);
        waterSupplyKz.setPaddingRight(20);
        waterSupplyKz.setPaddingBottom(5);
        table.addCell(waterSupplyKz);

        PdfPCell waterSupplyRu = new PdfPCell();
        waterSupplyRu.addElement(new Phrase("Водоснабжение ",fontTitle));
        waterSupplyRu.addElement(new Phrase(map.get("waterSupplyRu"),font));
        waterSupplyRu.setPaddingLeft(20);
        waterSupplyRu.setPaddingRight(20);
        waterSupplyRu.setPaddingBottom(5);
        table.addCell(waterSupplyRu);

        PdfPCell sewerageKz = new PdfPCell();
        sewerageKz.addElement(new Phrase("Кәріз ",fontTitle));
        sewerageKz.addElement(new Phrase(map.get("sewerageKz"),font));
        sewerageKz.setPaddingLeft(20);
        sewerageKz.setPaddingRight(20);
        sewerageKz.setPaddingBottom(5);
        table.addCell(sewerageKz);

        PdfPCell sewerageRu = new PdfPCell();
        sewerageRu.addElement(new Phrase("Канализация ",fontTitle));
        sewerageRu.addElement(new Phrase(map.get("sewerageRu"),font));
        sewerageRu.setPaddingLeft(20);
        sewerageRu.setPaddingRight(20);
        sewerageRu.setPaddingBottom(5);
        table.addCell(sewerageRu);

        PdfPCell powerSupplyKz = new PdfPCell();
        powerSupplyKz.addElement(new Phrase("Электрмен жабдықтау ",fontTitle));
        powerSupplyKz.addElement(new Phrase(map.get("powerSupplyKz"),font));
        powerSupplyKz.setPaddingLeft(20);
        powerSupplyKz.setPaddingRight(20);
        powerSupplyKz.setPaddingBottom(5);
        table.addCell(powerSupplyKz);

        PdfPCell powerSupplyRu = new PdfPCell();
        powerSupplyRu.addElement(new Phrase("Электроснабжение ",fontTitle));
        powerSupplyRu.addElement(new Phrase(map.get("powerSupplyRu"),font));
        powerSupplyRu.setPaddingLeft(20);
        powerSupplyRu.setPaddingRight(20);
        powerSupplyRu.setPaddingBottom(5);
        table.addCell(powerSupplyRu);

        PdfPCell gasSupplyKz = new PdfPCell();
        gasSupplyKz.addElement(new Phrase("Газбен жабдықтау  ",fontTitle));
        gasSupplyKz.addElement(new Phrase(map.get("gasSupplyKz"),font));
        gasSupplyKz.setPaddingLeft(20);
        gasSupplyKz.setPaddingRight(20);
        gasSupplyKz.setPaddingBottom(5);
        table.addCell(gasSupplyKz);

        PdfPCell gasSupplyRu = new PdfPCell();
        gasSupplyRu.addElement(new Phrase("Газоснабжение ",fontTitle));
        gasSupplyRu.addElement(new Phrase(map.get("gasSupplyRu"),font));
        gasSupplyRu.setPaddingLeft(20);
        gasSupplyRu.setPaddingRight(20);
        gasSupplyRu.setPaddingBottom(5);
        table.addCell(gasSupplyRu);

        PdfPCell telAndBroadcastingKz = new PdfPCell();
        telAndBroadcastingKz.addElement(new Phrase("Телекоммуникациялар және телерадиохабар  ",fontTitle));
        telAndBroadcastingKz.addElement(new Phrase(map.get("telAndBroadcastingKz"),font));
        telAndBroadcastingKz.setPaddingLeft(20);
        telAndBroadcastingKz.setPaddingRight(20);
        telAndBroadcastingKz.setPaddingBottom(5);
        table.addCell(telAndBroadcastingKz);

        PdfPCell telAndBroadcastingRu = new PdfPCell();
        telAndBroadcastingRu.addElement(new Phrase("Телекоммуникации и телерадиовещания ",fontTitle));
        telAndBroadcastingRu.addElement(new Phrase(map.get("telAndBroadcastingRu"),font));
        telAndBroadcastingRu.setPaddingLeft(20);
        telAndBroadcastingRu.setPaddingRight(20);
        telAndBroadcastingRu.setPaddingBottom(5);
        table.addCell(telAndBroadcastingRu);

        PdfPCell drainageKz = new PdfPCell();
        drainageKz.addElement(new Phrase("Дренаж (қажет болған жағдайда) және нөсерлік кәріз ",fontTitle));
        drainageKz.addElement(new Phrase(map.get("drainageKz"),font));
        drainageKz.setPaddingLeft(20);
        drainageKz.setPaddingRight(20);
        drainageKz.setPaddingBottom(5);
        table.addCell(drainageKz);

        PdfPCell drainageRu = new PdfPCell();
        drainageRu.addElement(new Phrase("Дренаж (при необходимости) и ливневая канализация ",fontTitle));
        drainageRu.addElement(new Phrase(map.get("drainageRu"),font));
        drainageRu.setPaddingLeft(20);
        drainageRu.setPaddingRight(20);
        drainageRu.setPaddingBottom(5);
        table.addCell(drainageRu);

        PdfPCell statIrrigationSysKz = new PdfPCell();
        statIrrigationSysKz.addElement(new Phrase("Стационарлы суғару жүйелері ",fontTitle));
        statIrrigationSysKz.addElement(new Phrase(map.get("statIrrigationSysKz"),font));
        statIrrigationSysKz.setPaddingLeft(20);
        statIrrigationSysKz.setPaddingRight(20);
        statIrrigationSysKz.setPaddingBottom(5);
        table.addCell(statIrrigationSysKz);

        PdfPCell statIrrigationSysRu = new PdfPCell();
        statIrrigationSysRu.addElement(new Phrase("Стационарные поливочные системы ",fontTitle));
        statIrrigationSysRu.addElement(new Phrase(map.get("statIrrigationSysRu"),font));
        statIrrigationSysRu.setPaddingLeft(20);
        statIrrigationSysRu.setPaddingRight(20);
        statIrrigationSysRu.setPaddingBottom(5);
        table.addCell(statIrrigationSysRu);

        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getDevCommitmentInfoTable(Map<String, String> map, Font font, Font fontTitle,Font fontHead){
        PdfPTable table = new PdfPTable(2);

        PdfPCell title = new PdfPCell(new Phrase("Құрылыс салушыға жүктелетін міндеттер \n Обязательства, возлагаемые на застройщика", fontHead));
        title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);

        title.setColspan(2);
        table.addCell(title);

        PdfPCell engSurveysKz = new PdfPCell();
        engSurveysKz.addElement(new Phrase("Инженерлік іздестірулер бойынша ",fontTitle));
        engSurveysKz.addElement(new Phrase(map.get("engSurveysKz"),font));
        engSurveysKz.setPaddingLeft(20);
        engSurveysKz.setPaddingRight(20);
        engSurveysKz.setPaddingBottom(5);
        table.addCell(engSurveysKz);

        PdfPCell engSurveysRu = new PdfPCell();
        engSurveysRu.addElement(new Phrase("По инженерным изысканиям ",fontTitle));
        engSurveysRu.addElement(new Phrase(map.get("engSurveysRu"),font));
        engSurveysRu.setPaddingLeft(20);
        engSurveysRu.setPaddingRight(20);
        engSurveysRu.setPaddingBottom(5);
        table.addCell(engSurveysRu);

        PdfPCell accordingDemolitionKz = new PdfPCell();
        accordingDemolitionKz.addElement(new Phrase("Қолданыстағы құрылыстар мен имараттарды бұзу (көшіру) бойынша ",fontTitle));
        accordingDemolitionKz.addElement(new Phrase(map.get("accordingDemolitionKz"),font));
        accordingDemolitionKz.setPaddingLeft(20);
        accordingDemolitionKz.setPaddingRight(20);
        accordingDemolitionKz.setPaddingBottom(5);
        table.addCell(accordingDemolitionKz);

        PdfPCell accDemolitionRu = new PdfPCell();
        accDemolitionRu.addElement(new Phrase("По сносу (переносу) существующих строений и сооружений ",fontTitle));
        accDemolitionRu.addElement(new Phrase(map.get("accordingDemolitionRu"),font));
        accDemolitionRu.setPaddingLeft(20);
        accDemolitionRu.setPaddingRight(20);
        accDemolitionRu.setPaddingBottom(5);
        table.addCell(accDemolitionRu);

        PdfPCell transServicesKz = new PdfPCell();
        transServicesKz.addElement(new Phrase("Жер асты және жер үсті коммуникацияларын ауыстыру бойынша ",fontTitle));
        transServicesKz.addElement(new Phrase(map.get("transExisUndOverEngServicesKz"),font));
        transServicesKz.setPaddingLeft(20);
        transServicesKz.setPaddingRight(20);
        transServicesKz.setPaddingBottom(5);
        table.addCell(transServicesKz);

        PdfPCell transServicesRu = new PdfPCell();
        transServicesRu.addElement(new Phrase("По переносу существующих подземных и надземных инженерных коммуникаций ",fontTitle));
        transServicesRu.addElement(new Phrase(map.get("transExisUndOverEngServicesRu"),font));
        transServicesRu.setPaddingLeft(20);
        transServicesRu.setPaddingRight(20);
        transServicesRu.setPaddingBottom(5);
        table.addCell(transServicesRu);

        PdfPCell consTransGreenPlanKz = new PdfPCell();
        consTransGreenPlanKz.addElement(new Phrase("Жасыл көшеттерді сақтау және/немесе отырғызу бойынша ",fontTitle));
        consTransGreenPlanKz.addElement(new Phrase(map.get("consTransGreenPlanKz"),font));
        consTransGreenPlanKz.setPaddingLeft(20);
        consTransGreenPlanKz.setPaddingRight(20);
        consTransGreenPlanKz.setPaddingBottom(5);
        table.addCell(consTransGreenPlanKz);

        PdfPCell consTransGreenPlanRu = new PdfPCell();
        consTransGreenPlanRu.addElement(new Phrase("По сохранению и/или пересадке зеленых насаждений ",fontTitle));
        consTransGreenPlanRu.addElement(new Phrase(map.get("consTransGreenPlanRu"),font));
        consTransGreenPlanRu.setPaddingLeft(20);
        consTransGreenPlanRu.setPaddingRight(20);
        consTransGreenPlanRu.setPaddingBottom(5);
        table.addCell(consTransGreenPlanRu);

        PdfPCell construcOfTempoKz = new PdfPCell();
        construcOfTempoKz.addElement(new Phrase("Учаскенің уақытша қоршау құрылысы бойынша ",fontTitle));
        construcOfTempoKz.addElement(new Phrase(map.get("constructionOfTemporaryKz"),font));
        construcOfTempoKz.setPaddingLeft(20);
        construcOfTempoKz.setPaddingRight(20);
        construcOfTempoKz.setPaddingBottom(5);
        table.addCell(construcOfTempoKz);

        PdfPCell construcOfTempoRu = new PdfPCell();
        construcOfTempoRu.addElement(new Phrase("По строительству временного ограждения участка ",fontTitle));
        construcOfTempoRu.addElement(new Phrase(map.get("constructionOfTemporaryRu"),font));
        construcOfTempoRu.setPaddingLeft(20);
        construcOfTempoRu.setPaddingRight(20);
        construcOfTempoRu.setPaddingBottom(5);
        table.addCell(construcOfTempoRu);

        PdfPCell additionalReqKz = new PdfPCell();
        additionalReqKz.addElement(new Phrase("Қосымша талаптар ",fontTitle));
        additionalReqKz.addElement(new Phrase(map.get("additionalReqKz"),font));
        additionalReqKz.setPaddingLeft(20);
        additionalReqKz.setPaddingRight(20);
        additionalReqKz.setPaddingBottom(5);
        table.addCell(additionalReqKz);

        PdfPCell additionalReqRu = new PdfPCell();
        additionalReqRu.addElement(new Phrase("Дополнительные требования ",fontTitle));
        additionalReqRu.addElement(new Phrase(map.get("additionalReqRu"),font));
        additionalReqRu.setPaddingLeft(20);
        additionalReqRu.setPaddingRight(20);
        additionalReqRu.setPaddingBottom(5);

        table.addCell(additionalReqRu);

        PdfPCell generalReqKz = new PdfPCell();
        generalReqKz.addElement(new Phrase("Жалпы талаптар ",fontTitle));
        generalReqKz.addElement(new Phrase(map.get("generalReqKz"),font));
        generalReqKz.setPaddingLeft(20);
        generalReqKz.setPaddingRight(20);
        generalReqKz.setPaddingBottom(5);
        table.addCell(generalReqKz);

        PdfPCell generalReqRu = new PdfPCell();
        generalReqRu.addElement(new Phrase("Общие требования",fontTitle));
        generalReqRu.addElement(new Phrase(map.get("generalReqRu"),font));
        generalReqRu.setPaddingLeft(20);
        generalReqRu.setPaddingRight(20);
        generalReqRu.setPaddingBottom(5);
        table.addCell(generalReqRu);

        table.setSplitLate(false);
        return table;
    }

    public static PdfPTable getNotesInfoTable(Font fontTitle,Font fontObject){
        PdfPTable table = new PdfPTable(1);

        PdfPCell text = new PdfPCell(new Phrase("Ескертпелер:\n" +
                "1.\tСәулет-жоспарлау тапсырмасы (бұдан әрі - СЖТ) және техникалық талаптар жобалау (жобалау-сметалық) құжаттаманың құрамында бекітілген құрылыстың бүкіл нормативтік ұзақтыгының мерзімі шегінде қолданылады.\n" +
                "2.\tСТЖ шарттарын қайта қарауды талап ететін мэн-жайлар туындаган кезде, оған өзгерістер тапсырыс берушінің келісімі бойынша снгізілуі мүмкін.\n" +
                "3.\tСЖТ-да көрсетілген талаптар мен шарттар меншік нысанына және қаржыландыру көздеріне қарамастан инвестициялық процестің барлық қатысушылары үшін міндетті. СЖТ тапсырыс берушінің немесе жергілікті сәуле г жэне қала қүрылысы органының өтініші бойынша қала қүрылыстық кеңестің, сәулеттік жүртшылықтыңталқылау нысанасы болып, тәуелсіз сараптамада қарала алады.\n" +
                "4.\tТапсырыс беруші СЖТ-да қамтылған талаптармен келіспеуі соттэртібімен шагымдана алады.\n" +
                "5.\tБерілген СЖТ сәулет, қала қүрылысы және қүрылыс қызметі саласындагы уәкілетті мемлекеттік орган белгілеген тэртіпте қүрылысқа жобалау алдындағы жэне жобалау (жобалау-сметалық) құжаттама эзірлеуге және сараптамадан өткізуге арналған негіздемені білдіреді.\n" +
                "6.\tМемлекеттік инвестициялардың қатысуынсыз салынып жатқан (салынган), бірақ мемлекеттік жоне қоғамдық мүдделерді қозғайтын объектілерді қабылдау комиссиялары пайдалануға қабылдауға тиіс.\n" +
                "Аталған талапты тапсырыс берушіге (құрылыс салушыға) СЖТ берген кезде аудандардың (қалалардың) жергілікті атқарушы органдары белгілейді жоне ол сол тапсырмада, сондай-ақ құрылыс- монтаж жүмыстарын жүргізуге берілген рүқсатта тіркеуге тиіс.\n" +
                "Примечания:\n" +
                "1.\tАрхитектурно-планировочное задание (далее - АПЗ) и технические условия действуют в течение всего срока нормативной продолжительности строительства, утвержденного в составе проектной (проектно-сметной) документации.\n" +
                "2.\tВ случае возникновения обстоятельств, требующих пересмотра условий АПЗ, изменения в него могут быть внесены по согласованию с заказчиком.\n" +
                "3.\tТребования и условия, изложенные в АПЗ, обязательны для всех участников инвестиционного процесса независимо от форм собственности и источников финансирования. АПЗ по просьбе заказчика или местного органа архитектуры и градостроительства может быть предметом обсуждения градостроительного совета, архитектурной общественности, рассмо трено в независимой экспертизе.\n" +
                "4.\tНесогласие заказчика с требованиями, содержащимися в АПЗ, может быть обжаловано в судебном порядке.\n" +
                "5.\tВыданное АПЗ является основанием на разработку и проведение экспертизы предпроектной и проектной (проектно-сметной) документации на строительство в установленном уполномоченным государственным органом в сфере архитектурной, градостроительной и строительной деятельности порядке.\n" +
                "6.\tОбъекты, строящиеся (построенные) без участия государственных инвестиций, но затрагивающие государственные и общественные интересы, подлежат приемке в эксплуатацию приемочными комиссиями.\n" +
                "Указанное условие устанавливается местными исполнительными органами (городов) при выдаче заказчику (застройщику) АПЗ и должно быть зафиксировано в этом задании, а также в разрешении на производство строительно-мошажных работ.\n", fontObject));
        text.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        text.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
        text.setBorder(Rectangle.NO_BORDER);
        table.addCell(text);

        PdfPCell boss = new PdfPCell(new Phrase("И.о. руководителя отдела                           Байқадамов Ануар Бисембаевич",fontTitle));
        boss.setPaddingTop(100);
        boss.setBorder(Rectangle.NO_BORDER);
        table.addCell(boss);
        table.setSplitLate(false);
        return table;
    }

    /*public static PdfPTable getGasSupplyTable(Font font,App app){
        PdfPTable gasSupplyTable = new PdfPTable(3);

        gasSupplyTable.setPaddingTop(200);

        PdfPCell gasSupply = new PdfPCell();
        gasSupply.addElement(new Phrase("Газоснабжение", font));
        gasSupply.setRowspan(7);
        gasSupplyTable.addCell(gasSupply);

        PdfPCell gasSupply2 = new PdfPCell();
        gasSupply2.addElement(new Phrase("Общая потребность", font));
        gasSupply2.setPaddingLeft(5);
        gasSupply2.setPaddingBottom(15);
        gasSupplyTable.addCell(gasSupply2);

        PdfPCell gasSupply3 = new PdfPCell();
        gasSupply3.addElement(new Phrase(app.getGasInfo().getTotal() + " м3/час", font));
        gasSupply3.setPaddingLeft(5);
        gasSupply3.setPaddingBottom(15);
        gasSupplyTable.addCell(gasSupply3);

        PdfPCell gasEmpty2 = new PdfPCell();
        gasEmpty2.addElement(new Phrase(" ", font));
        gasEmpty2.setPaddingLeft(5);
        gasEmpty2.setPaddingBottom(15);
        gasSupplyTable.addCell(gasEmpty2);

        PdfPCell gasEmpty3 = new PdfPCell();
        gasEmpty3.addElement(new Phrase("в т.ч.:", font));
        gasEmpty3.setPaddingLeft(5);
        gasEmpty3.setPaddingBottom(15);
        gasSupplyTable.addCell(gasEmpty3);

        PdfPCell onCooking = new PdfPCell();
        onCooking.addElement(new Phrase("На приготовление пищи", font));
        onCooking.setPaddingLeft(5);
        onCooking.setPaddingBottom(15);
        gasSupplyTable.addCell(onCooking);

        PdfPCell onCooking2 = new PdfPCell();
        onCooking2.addElement(new Phrase(app.getGasInfo().getCooking() + " м3/час", font));
        onCooking2.setPaddingLeft(5);
        onCooking2.setPaddingBottom(15);
        gasSupplyTable.addCell(onCooking2);

        PdfPCell gasHeating = new PdfPCell();
        gasHeating.addElement(new Phrase("Отопление", font));
        gasHeating.setPaddingLeft(5);
        gasHeating.setPaddingBottom(15);
        gasSupplyTable.addCell(gasHeating);

        PdfPCell gasHeating2 = new PdfPCell();
        gasHeating2.addElement(new Phrase(app.getGasInfo().getHeating() + " м3/час", font));
        gasHeating2.setPaddingLeft(5);
        gasHeating2.setPaddingBottom(15);
        gasSupplyTable.addCell(gasHeating2);

        PdfPCell gasVentilation = new PdfPCell();
        gasVentilation.addElement(new Phrase("Вентиляция", font));
        gasVentilation.setPaddingLeft(5);
        gasVentilation.setPaddingBottom(15);
        gasSupplyTable.addCell(gasVentilation);

        PdfPCell gasVentilation2 = new PdfPCell();
        gasVentilation2.addElement(new Phrase(app.getGasInfo().getVentilation() + " м3/час", font));
        gasVentilation2.setPaddingLeft(5);
        gasVentilation2.setPaddingBottom(15);
        gasSupplyTable.addCell(gasVentilation2);

        PdfPCell gasConditioning = new PdfPCell();
        gasConditioning.addElement(new Phrase("Кондиционирование", font));
        gasConditioning.setPaddingLeft(5);
        gasConditioning.setPaddingBottom(15);
        gasSupplyTable.addCell(gasConditioning);

        PdfPCell gasConditioning2 = new PdfPCell();
        gasConditioning2.addElement(new Phrase(app.getGasInfo().getConditioning() + " м3/час", font));
        gasConditioning2.setPaddingLeft(5);
        gasConditioning2.setPaddingBottom(15);
        gasSupplyTable.addCell(gasConditioning2);

        PdfPCell gasWaterSupply = new PdfPCell();
        gasWaterSupply.addElement(new Phrase("Горячее водоснабжение при газификации многоэтажных домов", font));
        gasWaterSupply.setPaddingLeft(5);
        gasWaterSupply.setPaddingBottom(15);
        gasSupplyTable.addCell(gasWaterSupply);

        PdfPCell gasWaterSupply2 = new PdfPCell();
        gasWaterSupply2.addElement(new Phrase(app.getGasInfo().getHotWater() + " м3/час", font));
        gasWaterSupply2.setPaddingLeft(5);
        gasWaterSupply2.setPaddingBottom(15);
        gasSupplyTable.addCell(gasWaterSupply2);
        return gasSupplyTable;
    }*/
    public static PdfPTable getTelephonizationTable(Font font,App app){
        PdfPTable telephonizationTable = new PdfPTable(3);
        telephonizationTable.setPaddingTop(200);

        PdfPCell telephonization = new PdfPCell();
        telephonization.addElement(new Phrase("Телефонизация", font));
        telephonization.setRowspan(4);
        telephonizationTable.addCell(telephonization);

        PdfPCell telephonization2 = new PdfPCell();
        telephonization2.addElement(new Phrase("Количество ОТА и услуг в разбивке физ.лиц и юр.лиц", font));
        telephonization2.setPaddingLeft(5);
        telephonization2.setPaddingBottom(15);
        telephonizationTable.addCell(telephonization2);

        PdfPCell telephonization3 = new PdfPCell();
        telephonization3.addElement(new Phrase("-", font));
        telephonization3.setPaddingLeft(5);
        telephonization2.setPaddingBottom(15);
        telephonizationTable.addCell(telephonization3);

        PdfPCell phoneCapacity2 = new PdfPCell();
        phoneCapacity2.addElement(new Phrase("Телефонная емкость", font));
        phoneCapacity2.setPaddingLeft(5);
        phoneCapacity2.setPaddingBottom(15);
        telephonizationTable.addCell(phoneCapacity2);

        PdfPCell phoneCapacity3 = new PdfPCell();
        phoneCapacity3.addElement(new Phrase("-", font));
        phoneCapacity3.setPaddingLeft(5);
        phoneCapacity3.setPaddingBottom(15);
        telephonizationTable.addCell(phoneCapacity3);

        PdfPCell plannedPhone2 = new PdfPCell();
        plannedPhone2.addElement(new Phrase("Планируемая телефонная канализация", font));
        plannedPhone2.setPaddingLeft(5);
        plannedPhone2.setPaddingBottom(15);
        telephonizationTable.addCell(plannedPhone2);

        PdfPCell plannedPhone3 = new PdfPCell();
        plannedPhone3.addElement(new Phrase("-", font));
        plannedPhone3.setPaddingLeft(5);
        plannedPhone3.setPaddingBottom(15);
        telephonizationTable.addCell(plannedPhone3);

        PdfPCell wishesCustomer2 = new PdfPCell();
        wishesCustomer2.addElement(new Phrase("Пожелания заказчика (тип оборудования, тип кабеля и др.)", font));
        wishesCustomer2.setPaddingLeft(5);
        wishesCustomer2.setPaddingBottom(15);
        telephonizationTable.addCell(wishesCustomer2);

        PdfPCell wishesCustomer3 = new PdfPCell();
        wishesCustomer3.addElement(new Phrase("-", font));
        wishesCustomer3.setPaddingLeft(5);
        wishesCustomer3.setPaddingBottom(15);
        telephonizationTable.addCell(wishesCustomer3);

        return telephonizationTable;
    }
    public static PdfPTable getStormSewerageTable(Font font,App app){
        PdfPTable stormSewerageTable = new PdfPTable(3);

        stormSewerageTable.setPaddingTop(200);

        PdfPCell stormSewerage = new PdfPCell();
        stormSewerage.addElement(new Phrase("Ливневая канализация", font));
        stormSewerage.setPaddingLeft(5);
        stormSewerage.setPaddingBottom(15);
        stormSewerageTable.addCell(stormSewerage);

        PdfPCell stormSewerage2 = new PdfPCell();
        stormSewerage2.addElement(new Phrase("Пожелание заказчика", font));
        stormSewerage2.setPaddingLeft(5);
        stormSewerage2.setPaddingBottom(15);
        stormSewerageTable.addCell(stormSewerage2);

        PdfPCell stormSewerage3 = new PdfPCell();
        stormSewerage3.addElement(new Phrase("-", font));
        stormSewerage3.setPaddingLeft(5);
        stormSewerage3.setPaddingBottom(15);
        stormSewerageTable.addCell(stormSewerage3);

        return stormSewerageTable;
    }
    /*public static PdfPTable getHeatSupplyTable(Font font,App app){
        PdfPTable heatSupplyTable = new PdfPTable(3);

        heatSupplyTable.setPaddingTop(200);

        PdfPCell heatSupply = new PdfPCell();
        heatSupply.addElement(new Phrase("Теплоснабжение", font));
        heatSupply.setRowspan(8);
        heatSupplyTable.addCell(heatSupply);

        PdfPCell totalHeat2 = new PdfPCell();
        totalHeat2.addElement(new Phrase("Общая тепловая нагрузка", font));
        totalHeat2.setPaddingLeft(5);
        totalHeat2.setPaddingBottom(15);
        heatSupplyTable.addCell(totalHeat2);

        PdfPCell totalHeat3 = new PdfPCell();
        totalHeat3.addElement(new Phrase(app.getHeatInfo().getTotal() + " Гкал/ч", font));
        totalHeat3.setPaddingLeft(5);
        totalHeat3.setPaddingBottom(15);
        heatSupplyTable.addCell(totalHeat3);

        PdfPCell empty2 = new PdfPCell();
        empty2.addElement(new Phrase(" ", font));
        empty2.setPaddingLeft(5);
        empty2.setPaddingBottom(15);
        heatSupplyTable.addCell(empty2);

        PdfPCell empty3 = new PdfPCell();
        empty3.addElement(new Phrase("____ в т.ч.:", font));
        empty3.setPaddingLeft(5);
        empty3.setPaddingBottom(15);
        heatSupplyTable.addCell(empty3);

        PdfPCell heating2 = new PdfPCell();
        heating2.addElement(new Phrase("Отопление", font));
        heating2.setPaddingLeft(5);
        heating2.setPaddingBottom(15);
        heatSupplyTable.addCell(heating2);

        PdfPCell heating3 = new PdfPCell();
        heating3.addElement(new Phrase(app.getHeatInfo().getHeating() + " Гкал/ч", font));
        heating3.setPaddingLeft(5);
        heating3.setPaddingBottom(15);
        heatSupplyTable.addCell(heating3);

        PdfPCell ventilation2 = new PdfPCell();
        ventilation2.addElement(new Phrase("Вентиляция", font));
        ventilation2.setPaddingLeft(5);
        ventilation2.setPaddingBottom(15);
        heatSupplyTable.addCell(ventilation2);

        PdfPCell ventilation3 = new PdfPCell();
        ventilation3.addElement(new Phrase(app.getHeatInfo().getVentilation() + " Гкал/ч", font));
        ventilation3.setPaddingLeft(5);
        ventilation3.setPaddingBottom(15);
        heatSupplyTable.addCell(ventilation3);

        PdfPCell hotWaterSupply2 = new PdfPCell();
        hotWaterSupply2.addElement(new Phrase("Горячее водоснабжение", font));
        hotWaterSupply2.setPaddingLeft(5);
        hotWaterSupply2.setPaddingBottom(15);
        heatSupplyTable.addCell(hotWaterSupply2);

        PdfPCell hotWaterSupply3 = new PdfPCell();
        hotWaterSupply3.addElement(new Phrase(app.getHeatInfo().getHotWater() + " Гкал/ч", font));
        hotWaterSupply3.setPaddingLeft(5);
        hotWaterSupply3.setPaddingBottom(15);
        heatSupplyTable.addCell(hotWaterSupply3);

        PdfPCell pairs2 = new PdfPCell();
        pairs2.addElement(new Phrase("Технологические нужды(пар)", font));
        pairs2.setPaddingLeft(5);
        pairs2.setPaddingBottom(15);
        heatSupplyTable.addCell(pairs2);

        PdfPCell pairs3 = new PdfPCell();
        pairs3.addElement(new Phrase(app.getHeatInfo().getTechnical() + " т/ч", font));
        pairs3.setPaddingLeft(5);
        pairs3.setPaddingBottom(15);
        heatSupplyTable.addCell(pairs3);

        PdfPCell load2 = new PdfPCell();
        load2.addElement(new Phrase("Разделить нагрузку по жилью и по встроенным помещениям", font));
        load2.setPaddingLeft(5);
        load2.setPaddingBottom(15);
        heatSupplyTable.addCell(load2);

        PdfPCell load3 = new PdfPCell();
        load3.addElement(new Phrase("-", font));
        load3.setPaddingLeft(5);
        load3.setPaddingBottom(15);
        heatSupplyTable.addCell(load3);

        PdfPCell energySaving2 = new PdfPCell();
        energySaving2.addElement(new Phrase("Энергосберегающее мероприятие", font));
        energySaving2.setPaddingLeft(5);
        energySaving2.setPaddingBottom(15);
        heatSupplyTable.addCell(energySaving2);

        PdfPCell energySaving3 = new PdfPCell();
        energySaving3.addElement(new Phrase("-", font));
        energySaving3.setPaddingLeft(5);
        energySaving3.setPaddingBottom(15);
        heatSupplyTable.addCell(energySaving3);

        return heatSupplyTable;
    }
    public static PdfPTable getCanalizationTable(Font font,App app){
        PdfPTable canalizationTable = new PdfPTable(7);

        canalizationTable.setPaddingTop(200);

        PdfPCell canalizaciya = new PdfPCell();
        canalizaciya.addElement(new Phrase("Канализация", font));
        canalizaciya.setPaddingLeft(5);
        canalizaciya.setPaddingBottom(15);
        canalizaciya.setRowspan(6);
        canalizationTable.addCell(canalizaciya);


        PdfPCell obshiy = new PdfPCell();
        obshiy.addElement(new Phrase("Общее количество сточных вод", font));
        obshiy.setPaddingLeft(5);
        obshiy.setPaddingBottom(15);
        canalizationTable.addCell(obshiy);

        PdfPCell obshiy2 = new PdfPCell();
        obshiy2.addElement(new Phrase(" " + app.getSewerageInfo().getTotal(), font));
        obshiy2.setPaddingLeft(5);
        obshiy2.setPaddingBottom(15);
        canalizationTable.addCell(obshiy2);

        PdfPCell obshiy3 = new PdfPCell();
        obshiy3.addElement(new Phrase("м3/сутки", font));
        obshiy3.setPaddingLeft(5);
        obshiy3.setPaddingBottom(15);
        canalizationTable.addCell(obshiy3);

        PdfPCell obshiy4 = new PdfPCell();
        obshiy4.addElement(new Phrase(" " + app.getSewerageInfo().getMaxTotal(), font));
        obshiy4.setPaddingLeft(5);
        obshiy4.setPaddingBottom(15);
        canalizationTable.addCell(obshiy4);

        PdfPCell obshiy5 = new PdfPCell();
        obshiy5.addElement(new Phrase("м3/час", font));
        obshiy5.setPaddingLeft(5);
        obshiy5.setPaddingBottom(15);
        canalizationTable.addCell(obshiy5);

        PdfPCell obshiy6 = new PdfPCell();
        obshiy6.addElement(new Phrase(" ", font));
        obshiy6.setRowspan(6);
        obshiy6.setPaddingLeft(5);
        obshiy6.setPaddingBottom(15);
        canalizationTable.addCell(obshiy6);

        PdfPCell vtchCan = new PdfPCell();
        vtchCan.addElement(new Phrase("в т/ч.", font));
        vtchCan.setPaddingLeft(5);
        vtchCan.setPaddingBottom(15);
        canalizationTable.addCell(vtchCan);

        PdfPCell vtchCan2 = new PdfPCell();
        vtchCan2.addElement(new Phrase(" ", font));
        vtchCan2.setPaddingLeft(5);
        vtchCan2.setPaddingBottom(15);
        canalizationTable.addCell(vtchCan2);

        PdfPCell vtchCan3 = new PdfPCell();
        vtchCan3.addElement(new Phrase(" ", font));
        vtchCan3.setPaddingLeft(5);
        vtchCan3.setPaddingBottom(15);
        canalizationTable.addCell(vtchCan3);

        PdfPCell vtchCan4 = new PdfPCell();
        vtchCan4.addElement(new Phrase(" ", font));
        vtchCan4.setPaddingLeft(5);
        vtchCan4.setPaddingBottom(15);
        canalizationTable.addCell(vtchCan4);

        PdfPCell vtchCan5 = new PdfPCell();
        vtchCan5.addElement(new Phrase(" ", font));
        vtchCan5.setPaddingLeft(5);
        vtchCan5.setPaddingBottom(15);
        canalizationTable.addCell(vtchCan5);

        PdfPCell fecal = new PdfPCell();
        fecal.addElement(new Phrase("фекальных", font));
        fecal.setPaddingLeft(5);
        fecal.setPaddingBottom(15);
        canalizationTable.addCell(fecal);

        PdfPCell fecal2 = new PdfPCell();
        fecal2.addElement(new Phrase(" " + app.getSewerageInfo().getFecal(), font));
        fecal2.setPaddingLeft(5);
        fecal2.setPaddingBottom(15);
        canalizationTable.addCell(fecal2);

        PdfPCell fecal3 = new PdfPCell();
        fecal3.addElement(new Phrase("м3/сутки", font));
        fecal3.setPaddingLeft(5);
        fecal3.setPaddingBottom(15);
        canalizationTable.addCell(fecal3);

        PdfPCell fecal4 = new PdfPCell();
        fecal4.addElement(new Phrase(" " + app.getSewerageInfo().getMaxFecal(), font));
        fecal4.setPaddingLeft(5);
        fecal4.setPaddingBottom(15);
        canalizationTable.addCell(fecal4);

        PdfPCell fecal5 = new PdfPCell();
        fecal5.addElement(new Phrase("м3/час", font));
        fecal5.setPaddingLeft(5);
        fecal5.setPaddingBottom(15);
        canalizationTable.addCell(fecal5);


        PdfPCell proiz = new PdfPCell();
        proiz.addElement(new Phrase("Производственно-загрязненных", font));
        proiz.setPaddingLeft(5);
        proiz.setPaddingBottom(15);
        canalizationTable.addCell(proiz);

        PdfPCell proiz2 = new PdfPCell();
        proiz2.addElement(new Phrase(" " + app.getSewerageInfo().getIndustrial(), font));
        proiz2.setPaddingLeft(5);
        proiz2.setPaddingBottom(15);
        canalizationTable.addCell(proiz2);

        PdfPCell proiz3 = new PdfPCell();
        proiz3.addElement(new Phrase("м3/сутки", font));
        proiz3.setPaddingLeft(5);
        proiz3.setPaddingBottom(15);
        canalizationTable.addCell(proiz3);

        PdfPCell proiz4 = new PdfPCell();
        proiz4.addElement(new Phrase(" " + app.getSewerageInfo().getMaxIndustrial(), font));
        proiz4.setPaddingLeft(5);
        proiz4.setPaddingBottom(15);
        canalizationTable.addCell(proiz4);

        PdfPCell proiz5 = new PdfPCell();
        proiz5.addElement(new Phrase("м3/час", font));
        proiz5.setPaddingLeft(5);
        proiz5.setPaddingBottom(15);
        canalizationTable.addCell(proiz5);


        PdfPCell uslovno = new PdfPCell();
        uslovno.addElement(new Phrase("Условно-чистых сбрасываемых на городскую канализацию", font));
        uslovno.setPaddingLeft(5);
        uslovno.setPaddingBottom(15);
        canalizationTable.addCell(uslovno);

        PdfPCell uslovno2 = new PdfPCell();
        uslovno2.addElement(new Phrase(" " + app.getSewerageInfo().getClean(), font));
        uslovno2.setPaddingLeft(5);
        uslovno2.setPaddingBottom(15);
        canalizationTable.addCell(uslovno2);

        PdfPCell uslovno3 = new PdfPCell();
        uslovno3.addElement(new Phrase("м3/сутки", font));
        uslovno3.setPaddingLeft(5);
        uslovno3.setPaddingBottom(15);
        canalizationTable.addCell(uslovno3);

        PdfPCell uslovno4 = new PdfPCell();
        uslovno4.addElement(new Phrase(" " + app.getSewerageInfo().getMaxClean(), font));
        uslovno4.setPaddingLeft(5);
        uslovno4.setPaddingBottom(15);
        canalizationTable.addCell(uslovno4);

        PdfPCell uslovno5 = new PdfPCell();
        uslovno5.addElement(new Phrase("м3/час", font));
        uslovno5.setPaddingLeft(5);
        uslovno5.setPaddingBottom(15);
        canalizationTable.addCell(uslovno5);

        PdfPCell uslovno7 = new PdfPCell();
        uslovno7.addElement(new Phrase("Качественный состав и характеристики промышленных стоков (рH, взвешенных веществ, БГ концентрация кислот, щелочей, взрывчатых, воспламеняющих радиоактивных веществ и др.)", font));
        uslovno7.setColspan(5);
        uslovno7.setPaddingLeft(5);
        uslovno7.setPaddingBottom(15);
        canalizationTable.addCell(uslovno7);

        return canalizationTable;
    }
    public static PdfPTable getPowerSupplyTable(Font font,App app,Font fontBold){
        PdfPTable powerSupplyTable = new PdfPTable(3);

        powerSupplyTable.setPaddingTop(100);

        PdfPCell powerSupply = new PdfPCell();
        powerSupply.addElement(new Phrase("Электроснабжение допольнительно при строительстве по очередям при реконструкции", fontBold));
        powerSupply.setRowspan(10);
        powerSupply.setPaddingLeft(5);
        powerSupply.setPaddingBottom(15);
        powerSupplyTable.addCell(powerSupply);

        PdfPCell power = new PdfPCell();
        power.addElement(new Phrase("Требуемая мошность, кВт", font));
        power.setPaddingLeft(5);
        power.setPaddingBottom(15);
        powerSupplyTable.addCell(power);

        PdfPCell powerVal = new PdfPCell();
        powerVal.addElement(new Phrase(" " + app.getElectricInfo().getRequiredPower(), font));
        powerVal.setPaddingLeft(5);
        powerVal.setPaddingBottom(15);
        powerSupplyTable.addCell(powerVal);

        PdfPCell phase = new PdfPCell();
        phase.addElement(new Phrase("Характер нагрузки (фаза)", font));
        phase.setPaddingLeft(5);
        phase.setPaddingBottom(15);
        powerSupplyTable.addCell(phase);

        PdfPCell phaseVal = new PdfPCell();
        phaseVal.addElement(new Phrase("Однофазная, трехфазная, постоянная, временная, сезонная", font));
        phaseVal.setPaddingLeft(5);
        phaseVal.setPaddingBottom(15);
        powerSupplyTable.addCell(phaseVal);

        PdfPCell category = new PdfPCell();
        category.addElement(new Phrase("Категория по надежности", font));
        category.setPaddingLeft(5);
        category.setPaddingBottom(15);
        powerSupplyTable.addCell(category);

        PdfPCell categoryVal = new PdfPCell();
        categoryVal.addElement(new Phrase("I категория -" + app.getElectricInfo().getRelCat1() + "кВт (кВА), II категория -" + app.getElectricInfo().getRelCat2() + " кВт (кВА), III категория - " + app.getElectricInfo().getRelCat3() + " кВт (кВА)", font));
        categoryVal.setPaddingLeft(5);
        categoryVal.setPaddingBottom(15);
        powerSupplyTable.addCell(categoryVal);

        PdfPCell maxLoad = new PdfPCell();
        maxLoad.addElement(new Phrase("Максимальная нагрузка после ввода в эксплуатацию по годам (нарастающим итогом с учетом существующей нагрузки)", font));
        maxLoad.setColspan(2);
        maxLoad.setPaddingLeft(5);
        maxLoad.setPaddingBottom(15);
        powerSupplyTable.addCell(maxLoad);

        PdfPCell blank = new PdfPCell();
        blank.addElement(new Phrase(" ", font));
        blank.setPaddingLeft(5);
        blank.setPaddingBottom(15);
        powerSupplyTable.addCell(blank);

        PdfPCell blankVal = new PdfPCell();
        blankVal.addElement(new Phrase("20__г.____кВт, 20__г.____кВт, 20__г.____кВт", font));
        blankVal.setPaddingLeft(5);
        blankVal.setPaddingBottom(15);
        powerSupplyTable.addCell(blankVal);

        PdfPCell maxLoad2 = new PdfPCell();
        maxLoad2.addElement(new Phrase("из указанной макс. нагрузки относятся к электроприемникам:", font));
        maxLoad2.setColspan(2);
        maxLoad2.setPaddingLeft(5);
        maxLoad2.setPaddingBottom(15);
        powerSupplyTable.addCell(maxLoad2);

        PdfPCell hollow = new PdfPCell();
        hollow.addElement(new Phrase(" ", font));
        hollow.setPaddingLeft(5);
        hollow.setPaddingBottom(15);
        powerSupplyTable.addCell(hollow);

        PdfPCell hollowVal = new PdfPCell();
        hollowVal.addElement(new Phrase("I категория - " + app.getElectricInfo().getLoadCat1() + "кВт (кВА), II кат." + app.getElectricInfo().getLoadCat2() + " кВт (кВА), III кат." + app.getElectricInfo().getLoadCat3() + "кВт (кВА)", font));
        hollowVal.setPaddingLeft(5);
        hollowVal.setPaddingBottom(15);
        powerSupplyTable.addCell(hollowVal);

        PdfPCell boilers = new PdfPCell();
        boilers.addElement(new Phrase("Предполагается установить электрокотлы,электрокалориферы,электроплитки,электропечи,электроводонагреватели(нужное подчеркнуть)", font));
        boilers.setPaddingLeft(5);
        boilers.setPaddingBottom(15);
        powerSupplyTable.addCell(boilers);

        PdfPCell boilersVal = new PdfPCell();
        boilersVal.addElement(new Phrase("в кол-ве " + app.getElectricInfo().getBoilerCount() + " шт., единичной мощности " + app.getElectricInfo().getBoilerPower() + " кВт (кВА)", font));
        boilersVal.setPaddingLeft(5);
        boilersVal.setPaddingBottom(15);
        powerSupplyTable.addCell(boilersVal);

        PdfPCell existingMaxLoad = new PdfPCell();
        existingMaxLoad.addElement(new Phrase("Существующая максимальная нагрузка", font));
        existingMaxLoad.setPaddingLeft(5);
        existingMaxLoad.setPaddingBottom(15);
        powerSupplyTable.addCell(existingMaxLoad);

        PdfPCell existingMaxLoadVal = new PdfPCell();
        existingMaxLoadVal.addElement(new Phrase(" " + app.getElectricInfo().getMaxLoad(), font));
        existingMaxLoadVal.setPaddingLeft(5);
        existingMaxLoadVal.setPaddingBottom(15);
        powerSupplyTable.addCell(existingMaxLoadVal);

        PdfPCell powerTransformer = new PdfPCell();
        powerTransformer.addElement(new Phrase("Разрешенная по договору мощность трансформаторов", font));
        powerTransformer.setPaddingLeft(5);
        powerTransformer.setPaddingBottom(15);
        powerSupplyTable.addCell(powerTransformer);

        PdfPCell powerTransformerVal = new PdfPCell();
        powerTransformerVal.addElement(new Phrase("В ТП №1: " + app.getElectricInfo().getTransformerNumber1() + "-" + app.getElectricInfo().getTransformerPower1() + " кВА в ТП №2:" + app.getElectricInfo().getTransformerNumber2() + "-" + app.getElectricInfo().getTransformerPower2() + " кВА", font));
        powerTransformerVal.setPaddingLeft(5);
        powerTransformerVal.setPaddingBottom(15);
        powerSupplyTable.addCell(powerTransformerVal);

        return powerSupplyTable;
    }
    public static PdfPTable getWaterSystemTable(Font font,App app,Font fontBold){
        PdfPTable waterSystemTable = new PdfPTable(8);

        waterSystemTable.setPaddingTop(30);

        PdfPCell water = new PdfPCell();
        water.addElement(new Phrase("Водоснабжение", fontBold));
        water.setPaddingLeft(5);
        water.setPaddingBottom(15);
        water.setRowspan(5);
        waterSystemTable.addCell(water);

        PdfPCell generalNeed = new PdfPCell();
        generalNeed.addElement(new Phrase("Общая потребность в воде", fontBold));
        generalNeed.setPaddingLeft(5);
        generalNeed.setPaddingBottom(15);
        waterSystemTable.addCell(generalNeed);

        PdfPCell waterTotal = new PdfPCell();
        waterTotal.addElement(new Phrase(" " + app.getWaterInfo().getTotal(), fontBold));
        waterTotal.setPaddingLeft(5);
        waterTotal.setPaddingBottom(15);
        waterSystemTable.addCell(waterTotal);

        PdfPCell generalNeed2 = new PdfPCell();
        generalNeed2.addElement(new Phrase("м3/сутки", fontBold));
        generalNeed2.setPaddingLeft(5);
        generalNeed2.setPaddingBottom(15);
        waterSystemTable.addCell(generalNeed2);

        PdfPCell waterTotalDrink = new PdfPCell();
        waterTotalDrink.addElement(new Phrase(" " + app.getWaterInfo().getTotalDrink(), fontBold));
        waterTotalDrink.setPaddingLeft(5);
        waterTotalDrink.setPaddingBottom(15);
        waterSystemTable.addCell(waterTotalDrink);

        PdfPCell generalNeed3 = new PdfPCell();
        generalNeed3.addElement(new Phrase("м3/час питьевой воды", fontBold));
        generalNeed3.setPaddingLeft(5);
        generalNeed3.setPaddingBottom(15);
        waterSystemTable.addCell(generalNeed3);

        PdfPCell waterMaxTotal = new PdfPCell();
        waterMaxTotal.addElement(new Phrase(" " + app.getWaterInfo().getMaxTotal(), fontBold));
        waterMaxTotal.setPaddingLeft(5);
        waterMaxTotal.setPaddingBottom(15);
        waterSystemTable.addCell(waterMaxTotal);

        PdfPCell generalNeed4 = new PdfPCell();
        generalNeed4.addElement(new Phrase("л/сек макс.", fontBold));
        generalNeed4.setPaddingLeft(5);
        generalNeed4.setPaddingBottom(15);
        waterSystemTable.addCell(generalNeed4);

        PdfPCell vtch = new PdfPCell();
        vtch.addElement(new Phrase("в т/ч.", font));
        vtch.setPaddingLeft(5);
        vtch.setPaddingBottom(15);
        waterSystemTable.addCell(vtch);

        PdfPCell vtch2 = new PdfPCell();
        vtch2.addElement(new Phrase(" ", font));
        vtch2.setPaddingLeft(5);
        vtch2.setPaddingBottom(15);
        waterSystemTable.addCell(vtch2);

        PdfPCell vtch3 = new PdfPCell();
        vtch3.addElement(new Phrase(" ", font));
        vtch3.setPaddingLeft(5);
        vtch3.setPaddingBottom(15);
        waterSystemTable.addCell(vtch3);

        PdfPCell vtch4 = new PdfPCell();
        vtch4.addElement(new Phrase(" ", font));
        vtch4.setPaddingLeft(5);
        vtch4.setPaddingBottom(15);
        waterSystemTable.addCell(vtch4);

        PdfPCell vtch5 = new PdfPCell();
        vtch5.addElement(new Phrase(" ", font));
        vtch5.setPaddingLeft(5);
        vtch5.setPaddingBottom(15);
        waterSystemTable.addCell(vtch5);

        PdfPCell vtch6 = new PdfPCell();
        vtch6.addElement(new Phrase(" ", font));
        vtch6.setPaddingLeft(5);
        vtch6.setPaddingBottom(15);
        waterSystemTable.addCell(vtch6);

        PdfPCell vtch7 = new PdfPCell();
        vtch7.addElement(new Phrase(" ", font));
        vtch7.setPaddingLeft(5);
        vtch7.setPaddingBottom(15);
        waterSystemTable.addCell(vtch7);

        PdfPCell domesticNeed = new PdfPCell();
        domesticNeed.addElement(new Phrase("На хозпитьевые нужды", font));
        domesticNeed.setPaddingLeft(5);
        domesticNeed.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed);

        PdfPCell domesticNeed2 = new PdfPCell();
        domesticNeed2.addElement(new Phrase(" " + app.getWaterInfo().getDayDrink(), font));
        domesticNeed2.setPaddingLeft(5);
        domesticNeed2.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed2);

        PdfPCell domesticNeed3 = new PdfPCell();
        domesticNeed3.addElement(new Phrase("м3/сутки", font));
        domesticNeed3.setPaddingLeft(5);
        domesticNeed3.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed3);

        PdfPCell domesticNeed4 = new PdfPCell();
        domesticNeed4.addElement(new Phrase(" " + app.getWaterInfo().getHourDrink(), font));
        domesticNeed4.setPaddingLeft(5);
        domesticNeed4.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed4);

        PdfPCell domesticNeed5 = new PdfPCell();
        domesticNeed5.addElement(new Phrase("м3/час", font));
        domesticNeed5.setPaddingLeft(5);
        domesticNeed5.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed5);

        PdfPCell domesticNeed6 = new PdfPCell();
        domesticNeed6.addElement(new Phrase(" " + app.getWaterInfo().getMaxDrink(), font));
        domesticNeed6.setPaddingLeft(5);
        domesticNeed6.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed6);

        PdfPCell domesticNeed7 = new PdfPCell();
        domesticNeed7.addElement(new Phrase("л/сек макс.", font));
        domesticNeed7.setPaddingLeft(5);
        domesticNeed7.setPaddingBottom(15);
        waterSystemTable.addCell(domesticNeed7);

        PdfPCell productionNeeds = new PdfPCell();
        productionNeeds.addElement(new Phrase("На производственные нужды", font));
        productionNeeds.setPaddingLeft(5);
        productionNeeds.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds);

        PdfPCell productionNeeds2 = new PdfPCell();
        productionNeeds2.addElement(new Phrase(" " + app.getWaterInfo().getDayIndustrial(), font));
        productionNeeds2.setPaddingLeft(5);
        productionNeeds2.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds2);

        PdfPCell productionNeeds3 = new PdfPCell();
        productionNeeds3.addElement(new Phrase("м3/сутки", font));
        productionNeeds3.setPaddingLeft(5);
        productionNeeds3.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds3);

        PdfPCell productionNeeds4 = new PdfPCell();
        productionNeeds4.addElement(new Phrase(" " + app.getWaterInfo().getHourIndustrial(), font));
        productionNeeds4.setPaddingLeft(5);
        productionNeeds4.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds4);

        PdfPCell productionNeeds5 = new PdfPCell();
        productionNeeds5.addElement(new Phrase("м3/час", font));
        productionNeeds5.setPaddingLeft(5);
        productionNeeds5.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds5);

        PdfPCell productionNeeds6 = new PdfPCell();
        productionNeeds6.addElement(new Phrase(" " + app.getWaterInfo().getMaxIndustrial(), font));
        productionNeeds6.setPaddingLeft(5);
        productionNeeds6.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds6);

        PdfPCell productionNeeds7 = new PdfPCell();
        productionNeeds7.addElement(new Phrase("л/сек макс.", font));
        productionNeeds7.setPaddingLeft(5);
        productionNeeds7.setPaddingBottom(15);
        waterSystemTable.addCell(productionNeeds7);

        PdfPCell potreb = new PdfPCell();
        potreb.addElement(new Phrase("Потребные расходы пожаротушения", font));
        potreb.setPaddingLeft(5);
        potreb.setPaddingBottom(15);
        waterSystemTable.addCell(potreb);

        PdfPCell potreb2 = new PdfPCell();
        potreb2.addElement(new Phrase(" " + app.getWaterInfo().getFirefighting(), font));
        potreb2.setPaddingLeft(5);
        potreb2.setPaddingBottom(15);
        waterSystemTable.addCell(potreb2);

        PdfPCell potreb3 = new PdfPCell();
        potreb3.addElement(new Phrase("л/сек макс.", font));
        potreb3.setColspan(5);
        potreb3.setPaddingLeft(5);
        potreb3.setPaddingBottom(15);
        waterSystemTable.addCell(potreb3);

        return waterSystemTable;
    }*/

    public static PdfPTable getQuestionnaireTable(Font font,App app,Font fontBold,String fullname){
        PdfPTable table = new PdfPTable(2);

        table.setPaddingTop(30);

        PdfPCell customer2 = new PdfPCell();
        customer2.addElement(new Phrase("Заказчик", font));
        customer2.setPaddingLeft(5);
        customer2.setPaddingBottom(15);
        table.addCell(customer2);

        PdfPCell customerValue = new PdfPCell();
        customerValue.addElement(new Phrase(fullname, font));
        customerValue.setPaddingLeft(5);
        customerValue.setPaddingBottom(15);
        table.addCell(customerValue);

        PdfPCell objectName2 = new PdfPCell();
        objectName2.addElement(new Phrase("Наименование объекта", font));
        objectName2.setPaddingLeft(5);
        objectName2.setPaddingBottom(15);
        table.addCell(objectName2);

        PdfPCell objectName2Val = new PdfPCell();
        objectName2Val.addElement(new Phrase(app.getObjectInfo().getName(), font));
        objectName2Val.setPaddingLeft(5);
        objectName2Val.setPaddingBottom(15);
        table.addCell(objectName2Val);

        PdfPCell deadline = new PdfPCell();
        deadline.addElement(new Phrase("Срок строительства по нормам", font));
        deadline.setPaddingLeft(5);
        deadline.setPaddingBottom(15);
        table.addCell(deadline);

        PdfPCell deadlineVal = new PdfPCell();
        deadlineVal.setPaddingLeft(5);
        deadlineVal.setPaddingBottom(15);
        deadlineVal.addElement(new Phrase(" " + app.getObjectInfo().getPeriod(), font));
        table.addCell(deadlineVal);


        PdfPCell right = new PdfPCell();
        right.addElement(new Phrase("Правоустанавливающие документы на объект (реконструкция)", font));
        right.setPaddingLeft(5);
        right.setPaddingBottom(15);
        table.addCell(right);

        PdfPCell rightVal = new PdfPCell();
        rightVal.setPaddingLeft(5);
        rightVal.setPaddingBottom(15);
        rightVal.addElement(new Phrase("_"));
        table.addCell(rightVal);


        PdfPCell floor = new PdfPCell();
        floor.addElement(new Phrase("Этажность", font));
        floor.setPaddingLeft(5);
        floor.setPaddingBottom(15);
        table.addCell(floor);

        PdfPCell floorVal = new PdfPCell();
        floorVal.addElement(new Phrase("" + app.getObjectInfo().getFloorsCount(), font));
        floorVal.setPaddingLeft(5);
        floorVal.setPaddingBottom(15);
        table.addCell(floorVal);


        PdfPCell area = new PdfPCell();
        area.addElement(new Phrase("Площадь здания", font));
        area.setPaddingLeft(5);
        area.setPaddingBottom(15);
        table.addCell(area);

        PdfPCell areaVal = new PdfPCell();
        areaVal.addElement(new Phrase("" + app.getObjectInfo().getArea(), font));
        areaVal.setPaddingLeft(5);
        areaVal.setPaddingBottom(15);
        table.addCell(areaVal);


        PdfPCell numberOfFlats = new PdfPCell();
        numberOfFlats.addElement(new Phrase("Количества Квартир (номеров,кабинетов)", font));
        numberOfFlats.setPaddingLeft(5);
        numberOfFlats.setPaddingBottom(15);
        table.addCell(numberOfFlats);

        PdfPCell numberOfFlatsVal = new PdfPCell();
        numberOfFlatsVal.addElement(new Phrase("" + app.getObjectInfo().getFlatsCount(), font));
        numberOfFlatsVal.setPaddingLeft(5);
        numberOfFlatsVal.setPaddingBottom(15);
        table.addCell(numberOfFlatsVal);

        return table;
    }



}
