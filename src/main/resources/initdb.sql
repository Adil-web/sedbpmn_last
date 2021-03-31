insert into service (id,name_ru,name_kk,service_type_id,short_name_ru,code) values (1,'Предоставление исходных материалов при разработке проектов строительства и реконструкции (перепланировки и переоборудования)','Предоставление исходных материалов при разработке проектов строительства и реконструкции (перепланировки и переоборудования)',1,'АПЗ','apz');
insert into service (id,name_ru,name_kk,code) values(14,'Согласование эскизного проекта','Согласование эскизного проекта','prelimDesign');
insert into service (id,name_ru,name_kk,code) values(6,'Выдача справки по определению адреса объектов недвижимости на территории Республики Казахстан','Выдача справки по определению адреса объектов недвижимости на территории Республики Казахстан','address');
insert into service (id,name_ru,short_name_ru,code) values(2,'Системный БП','','system');
insert into service (id,name_ru,name_kk,service_type_id,short_name_ru) values (77,'Корреспонденция','Корреспонденция',1,'Корреспонденция');
insert into service (id,name_ru,name_kk,code) values(10,'Выдача решения на изменение целевого назначения земельного участка','Выдача решения на изменение целевого назначения земельного участка','cn');
insert into service (id,name_ru,name_kk,code) values(30,'Выдача тех условий','Выдача тех условий','tu');
insert into service (id,name_ru,name_kk,code) values(5,'ИЖС','ИЖС','ijs');
insert into service (id,name_ru,name_kk,code) values(16,'Оформление в частную собственность','Оформление в частную собственность','chasSobst');
insert into service (id,name_ru,name_kk,code) values(9,'Утверждение землеустроительных проектов','Утверждение землеустроительных проектов','uzp');

insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(1,'На проектирование технически и (или) технологически несложных объектов: Архитектурно-планировочное задание и технические условия','На проектирование технически и (или) технологически несложных объектов: Архитектурно-планировочное задание и технические условия',1,'АПЗ и ТУ несложный',6,'апз ту несложный',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(2,'На проектирование технически и (или) технологически несложных объектов: Полный комплект исходно-разрешительных материалов (АПЗ, технические условия, выкопировка из проекта детальной планировки, вертикальные планировочные отметки, поперечные профили дорог','На проектирование технически и (или) технологически несложных объектов: Полный комплект исходно-разрешительных материалов (АПЗ, технические условия, выкопировка из проекта детальной планировки, вертикальные планировочные отметки, поперечные профили дорог',1,'АПЗ, ТУ и ВП несложный',15,'апз ту вп несложный',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(3,'На проектирование технически и (или) технологически сложных объектов: Архитектурно-планировочное задание и технические условия','На проектирование технически и (или) технологически сложных объектов: Архитектурно-планировочное задание и технические условия',1,'АПЗ и ТУ сложный',15,'апз ту сложный',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(4,'На проектирование технически и (или) технологически сложных объектов: Полный комплект исходно-разрешительных материалов (АПЗ, технические условия, выкопировка из проекта детальной планировки, вертикальные планировочные отметки, поперечные профили дорог и ','На проектирование технически и (или) технологически сложных объектов: Полный комплект исходно-разрешительных материалов (АПЗ, технические условия, выкопировка из проекта детальной планировки, вертикальные планировочные отметки, поперечные профили дорог и ',1,'АПЗ, ТУ и ВП сложный',17,'апз ту вп сложный',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(9,'Для получения исходных материалов и разрешительных документов для реконструкции (перепланировки, переоборудования) помещений (отдельных частей) существующих зданий','Для получения исходных материалов и разрешительных документов для реконструкции (перепланировки, переоборудования) помещений (отдельных частей) существующих зданий',1,'АПЗ, реконструкция',15,'апз реконструкция',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(21,'Согласование эскизного проекта','Согласование эскизного проекта',14,'Согласование эскизного проекта',7,'эскиз',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values(17,'Выдача справки по уточнению адреса объектов недвижимости с историей (при отсутствии архивных сведений об изменении адреса объекта недвижимости в информационной системе "Адресный регистр")','Выдача справки по уточнению адреса объектов недвижимости с историей (при отсутствии архивных сведений об изменении адреса объекта недвижимости в информационной системе "Адресный регистр")',6,'Уточнение адреса',3,'адрес',true,'addressUtochnenie');
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values(18,'Выдача справки о присвоении адреса объекта недвижимости, с выездом на место нахождения объекта недвижимости и с обязательной регистрацией его в информационной системе "Адресный регистр" с указанием регистрационного кода адреса','Выдача справки о присвоении адреса объекта недвижимости, с выездом на место нахождения объекта недвижимости и с обязательной регистрацией его в информационной системе "Адресный регистр" с указанием регистрационного кода адреса',6,'Присвоение адреса',6,'адрес',true,'addressPrisvoenie');
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values(19,'Выдача справки об упразднении адреса объекта недвижимости, с выездом на место нахождения объекта недвижимости и с обязательной регистрацией его в информационной системе "Адресный регистр" с указанием регистрационного кода адреса','Выдача справки об упразднении адреса объекта недвижимости, с выездом на место нахождения объекта недвижимости и с обязательной регистрацией его в информационной системе "Адресный регистр" с указанием регистрационного кода адреса',6,'Упразднение адреса',6,'адрес',true,'addressUprazdnenie');
insert into subservice (id,name_ru,short_name_ru) values(5,'Системный бизнес процесс','Системный бизнес процесс');
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(77,'Корреспонденция','Корреспонденция',77,'Корреспонденция',7,'Корреспонденция',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only) values(25,'Выдача решения на изменение целевого назначения земельного участка','Выдача решения на изменение целевого назначения земельного участка',10,'Изм. целевого назн.',30,'Изм. целевого назн.я',true);
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values(75,'Выдача решения на изменение целевого назначения земельного участка - Аульный округ','Выдача решения на изменение целевого назначения земельного участка - Аульный округ',10,'Изм. целевого назн.',30,'Изм. целевого назн.я - Аульный округ',true,'cnAulOkrug');
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values(33,'Продажа в частную собственность земельного участка, ранее предоставленного в землепользование','Продажа в частную собственность земельного участка, ранее предоставленного в землепользование',16,'Оформ. в част. собст.',15,'Оформ. в част. собст.',true,'chasSobst');
insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values(34,'Продажа в частную собственность земельного участка, ранее предоставленного в землепользование - Аульный округ','Продажа в частную собственность земельного участка, ранее предоставленного в землепользование - Аульный округ',16,'Оформ. в част. собст.',15,'Оформ. в част. собст.',true,'chasSobstAulOkrug');

insert into subservice (id,name_ru,name_kk,service_id,code) values (31,'Выдача тех условий Атырау Су Арнасы','Выдача тех условий Атырау Су Арнасы',30,'tuSuArnasy');
insert into subservice (id,name_ru,name_kk,service_id,code) values (32,'Выдача тех условий Атырау Жарык','Выдача тех условий Атырау Жарык',30,'tuJaryk');
insert into subservice (id,name_ru,name_kk,service_id,code) values (35,'Выдача тех условий Атырау тепловые сети','Выдача тех условий Атырау тепловые сети',30,'tuTeploSeti');

insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values (14,'Предоставление земельного участка для строительства объекта в черте населенного пункта (ИЖС)','Предоставление земельного участка для строительства объекта в черте населенного пункта (ИЖС)',5,'ИЖС',30,null,true,'ijs');

insert into subservice (id,name_ru,name_kk,service_id,short_name_ru,days,tags,workdays_only,code) values (23,'Утверждение землеустроительных проектов по формированию земельных участков','Жерді қалыптастыру үшін жерге орналастыру жобаларын бекіту',9,'Утверждение ЗУ',7,null,true,'uzp');

alter table act_hi_varinst alter column text_ type text;
alter table act_hi_varinst alter column text2_ type text;
alter table act_hi_detail alter column text_ type text;
alter table act_hi_detail alter column text2_ type text;
alter table act_ru_variable alter column text_ type text;
alter table act_ru_variable alter column text2_ type text;

insert into correspondence_category (id,name_ru,name_kk) values(1,'Письмо','Хат');
insert into correspondence_category (id,name_ru,name_kk) values(2,'Факс','Факс');
insert into correspondence_category (id,name_ru,name_kk) values(3,'Электронная почта','Электронды хат');
insert into correspondence_category (id,name_ru,name_kk) values(4,'Докладная или служебная записка','Докладная или служебная записка');
insert into correspondence_category (id,name_ru,name_kk) values(5,'Объяснительная','Объяснительная');
insert into correspondence_category (id,name_ru,name_kk) values(6,'Заявление','Арыз');
insert into correspondence_category (id,name_ru,name_kk) values(7,'Жалоба','Жалоба');

insert into phase(id,name_ru) values(1,'Однофазная');
insert into phase(id,name_ru) values(2,'Трехфазная');

insert into phase_period(id,name_ru) values(1,'Постоянная');
insert into phase_period(id,name_ru) values(2,'Временная');
insert into phase_period(id,name_ru) values(3,'Сезонная');

insert into adm_document_category (id,name_ru,name_kk) values(1,'Прием на работу','Прием на работу');
insert into adm_document_category (id,name_ru,name_kk) values(2,'Прием на работу переводом','Прием на работу переводом');
insert into adm_document_category (id,name_ru,name_kk) values(3,'Увольнение','Увольнение');
insert into adm_document_category (id,name_ru,name_kk) values(4,'Назначение надбавки','Назначение надбавки');

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),21,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),21,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),21,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='EXECUTOR'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='LINGVIST'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='JURIST'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='ZAM_AKIM'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='RUK_APPARAT'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),25,(select id from role where name='EXECUTOR_COM'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),77,(select id from role where name='EXECUTOR'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),5,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),1,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),1,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),1,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),2,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),2,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),2,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),3,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),3,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),3,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),4,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),4,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),4,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),9,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),9,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),9,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),17,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),17,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),17,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),18,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),18,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),18,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),19,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),19,(select id from role where name='CONTROL'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),19,(select id from role where name='EXECUTOR'));

insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),31,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),31,(select id from role where name='EXECUTOR'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),32,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),32,(select id from role where name='EXECUTOR'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),33,(select id from role where name='HEAD'));
insert into subservice_role (id,subservice_id,role_id) values(nextval('subservice_role_id_seq'),33,(select id from role where name='EXECUTOR'));

insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ЖУРНАЛ ВХ.','incomingCorrespondences');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ЖУРНАЛ ИСХ.','outcomingCorrespondences');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'АДМ. ДОКУМЕНТЫ','documents');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ГОСУСЛУГИ (ПОСТУПИВШИЕ)','incoming');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ГОСУСЛУГИ НА ИСПОЛНЕНИИ','allTasks');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ГОСУСЛУГИ ЗАВЕРШЕННЫЕ','outcoming');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'У МЕНЯ НА КОНТРОЛЕ','control');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'МОИ ЗАДАЧИ - НАЗНАЧЕННЫЕ','currentTasks');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'МОИ ЗАДАЧИ - ИСПОЛНЕННЫЕ','finishedTasks');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'СТАТИСТИКА','statistic');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ШАБЛОНЫ ПИСЕМ','templateEditor');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'СПРАВОЧНИК ПОЛЬЗОВАТЕЛЕЙ','users');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'СПРАВОЧНИК УСЛУГ','services');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'СПРАВОЧНИК РОЛЕЙ','roles');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'РЕЕСТР ДОГОВОРОВ','contracts');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ГРАФИК ДОГОВОРОВ','contractExecutions');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ШАБЛОНЫ ДОГОВОРОВ','subjectTemplateEditor');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'СПРАВОЧНИК ЗАМЕЧАНИЙ','comments');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'ОТЧЕТ ПО ИСПОЛНИТЕЛЯМ','executorReport');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'Мониторинг договоров','monitoringContract');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'Мониторинг ЗУ','monitoringZu');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'Заявления. Операции','appOperations');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'Статистика об исполненных услугах','serviceStatistics');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'Отчеты','reports');
insert into menu (id,name_ru, code) values (nextval('menu_id_seq'),'Админка','admin');

insert into communal(id,name_ru) values (1,'Электроснабжение');
insert into communal(id,name_ru) values (2,'Водоснабжение');
insert into communal(id,name_ru) values (3,'Канализация');
insert into communal(id,name_ru) values (4,'Теплоснабжение');
insert into communal(id,name_ru) values (5,'Газоснабжение');

update communal set code='tuJaryk' where name_ru = 'Электроснабжение';
update communal set code='tuSuArnasy' where name_ru='Водоснабжение';
update communal set code='tuTeploSeti' where name_ru='Теплоснабжение';

insert into contract_subject (id,name_ru, name_kk, subject_type) values (nextval('contract_subject_id_seq'), 'ИЖС','ИЖС','IJS');
insert into contract_subject (id,name_ru, name_kk, subject_type) values (nextval('contract_subject_id_seq'), 'Аукцион','Аукцион','AUCTION');
