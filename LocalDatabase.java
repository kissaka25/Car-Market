package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {

    public LocalDatabase(Context context) {
        super(context, "dbPost ", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table tblSaved (sID Integer Primary Key AUTOINCREMENT, postID nVarchar(100))");
        db.execSQL("Create table tblMake (mID Integer Primary Key AUTOINCREMENT, mName nVarchar(100))");
        db.execSQL("Create table tblModel (modID Integer Primary Key AUTOINCREMENT, mID Integer, modName nVarchar(100), Foreign Key (mID) References tblMake)");
        db.execSQL("Create table tblFuel (fID Integer Primary Key AUTOINCREMENT, fName nVarchar(100))");
        db.execSQL("Create table tblVariant (vID Integer Primary Key AUTOINCREMENT, modID Integer, fID Integer, vName nVarchar(100), Foreign Key (modID) References tblModel, Foreign Key (fID) References tblFuel)");
        db.execSQL("Create table tblBody (bodyID Integer Primary Key AUTOINCREMENT, bodyName nVarchar(100))");

        db.execSQL("Insert into tblMake(mName) values ('Acura'), ('Alfa Romeo'),('Asia Motors') ,('Audi'),('Bentley'),('BMW')," +
                "('Bugatti'),('Buick'),('Cadillac'),('Chevrolet'),('Chrysler'),('Citroën'),('Cobra'),('Corvette'),('Dacia')," +
                "('Daewoo'),('Daihatsu'),('Dodge'),('Ferrari'),('Fiat'),('Ford'),('GMC'),('Honda'),('Hummer'),('Hyundai')," +
                "('Infiniti'),('Isuzu'),('Iveco'),('Jaguar'),('Jeep'),('Kia'),('Koenigsegg'),('Lamborghini'),('Lancia')," +
                "('Land Rover'),('Lexus'),('Lincoln'),('Maserati'),('Maybach'),('Mazda'),('McLaren'),('Mercedes Benz'),('Mini')," +
                "('Mitsubishi'),('Nissan'),('Opel'),('Pagani'),('Peugeot'),('Porsche'),('Renault'),('Rolls-Royce'),('Saab')," +
                "('Seat'),('Skoda'),('Smart'),('Subaru'),('Suzuki'),('Tesla'),('Toyota'),('Volkswagen'),('Volvo'),('Tjetër')");

        db.execSQL("Insert into tblModel(mID, modName) values ('1','MDX'), ('1','NSX'),('1','RL'),('1','RSX'),('1','TL')," +
                "('1','TSX'),('1','Tjetër'); ");

        db.execSQL("Insert into tblModel(mID, modName) values ('2','4C'),('2','8C'),('2','Alfa 145'),('2','Alfa 146')," +
                "('2','Alfa 147'),('2','Alfa 155'),('2','Alfa 156'),('2','Alfa 159'),('2','Alfa 164'),('2','Alfa 166')," +
                "('2','Alfa 33'),('2','Alfa 75'),('2','Alfa 90'),('2','Alfasud'),('2','Brera'),('2','Crosswagon'),('2','Giulia')," +
                "('2','Giulietta'),('2','GT'),('2','GTV'),('2','Junior'),('2','Spider'),('2','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('2','4C'),('2','8C'),('2','Alfa 145'),('2','Alfa 146')," +
                "('2','Alfa 147'),('2','Alfa 155'),('2','Alfa 156'),('2','Alfa 159'),('2','Alfa 164'),('2','Alfa 166')," +
                "('2','Alfa 33'),('2','Alfa 75'),('2','Alfa 90'),('2','Alfasud'),('2','Brera'),('2','Crosswagon'),('2','Giulia')," +
                "('2','Giulietta'),('2','GT'),('2','GTV'),('2','Junior'),('2','Spider'),('2','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('3','Rocsta'),('3','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('4','100'),('4','200'),('4','80'),('4','90'),('4','A1'),('4','A2'),('4','A3'),('4','A4'),('4','A4 allroad'),('4','A5'),('4','A6'),('4','A6 Allroad'),('4','A7'),('4','A8'),('4','Q2'),('4','Q3'),('4','Q5'),('4','Q7'),('4','Q8'),('4','R8'),('4','RS2'),('4','RS3'),('4','RS4'),('4','RS5'),('4','RS6'),('4','RS7'),('4','RSQ3'),('4','S1'),('4','S2'),('4','S3'),('4','S4'),('4','S5'),('4','S6'),('4','S7'),('4','S8'),('4','SQ2'),('4','SQ5'),('4','SQ7'),('4','TT'),('4','TT RS'),('4','TTS'),('4','V8'),('4','Tjetër');");

        db.execSQL("Insert into tblModel(mID, modName) values ('5','Arnage'),('5','Azure'),('5','Bentayga'),('5','Brooklands'),('5','Continental'),('5','Eight'),('5','Flying Spur'),('5','Mulsanne'),('5','Turbo R'),('5','Turbo RT'),('5','Turbo S'),('5','Tjetër');");

        db.execSQL("Insert into tblModel(mID, modName) values ('6','2002'),('6','840'),('6','850'),('6','i3'),('6','i8'),('6','M1'),('6','M2'),('6','M3'),('6','M4'),('6','M5'),('6','M6'),('6','M7'),('6','114'),('6','116'),('6','118'),('6','120'),('6','123'),('6','125'),('6','130'),('6','135'),('6','214'),('6','216'),('6','218'),('6','220'),('6','225'),('6','230'),('6','316'),('6','318'),('6','318 GT'),('6','320'),('6','320 GT'),('6','330'),('6','330 GT'),('6','335'),('6','335 GT'),('6','340'),('6','340 GT'),('6','418'),('6','418'),('6','420'),('6','425'),('6','428'),('6','430'),('6','435'),('6','440'),('6','518'),('6','520 GT'),('6','525'),('6','530'),('6','530 GT'),('6','535'),('6','535 GT'),('6','540'),('6','545'),('6','550'),('6','550 GT'),('6','628'),('6','630'),('6','630 GT'),('6','635'),('6','640'),('6','640 GT'),('6','650'),('6','725'),('6','730'),('6','735'),('6','740'),('6','750'),('6','X1'),('6','X2'),('6','X3'),('6','X4'),('6','X5'),('6','X6'),('6','X7'),('6','Z1'),('6','Z3'),('6','Z4'),('6','Z8'),('6','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('7','Chiron'),('7','EB 110'),('7','Veyron'),('7','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('8','Century'),('8','Electra'),('8','Enclave'),('8','La Crosse'),('8','La Sabre'),('8','Park Avenue'),('8','Regal'),('8','Riviera'),('8','Road Master'),('8','Skylark'),('8','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('9','Allante'),('9','ATAS'),('9','BLS'),('9','CT6'),('9','CTS'),('9','Deville'),('9','Eldorado'),('9','Escalade'),('9','Fleetwood'),('9','Seville'),('9','SRX'),('9','STS'),('9','XLR'),('9','XT5'),('9','Tjetër')");

        db.execSQL("Insert into tblModel(mID, modName) values ('10','2500'),('10','Alero'),('10','Astro'),('10','Avalanche'),('10','Aceo'),('10','Beretta'),('10','Blazer'),('10','C1500'),('10','Camaro'),('10','Captiva'),('10','Cavalier'),('10','Chevelle'),('10','Colorado'),('10','El Camino'),('10','Express'),('10','G'),('10','Impala'),('10','Lacetti'),('10','Lumina'),('10','Malibu'),('10','Matiz'),('10','Niva'),('10','Nubira'),('10','Orlando'),('10','Silverado'),('10','Suburban'),('10','Tahoe'),('10','Trax'),('10','Venture'),('10','Volt'),('10','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('11','200'),('11','300C'),('11','300M'),('11','Aspen'),('11','Crossfire'),('11','Daytona'),('11','ES'),('11','Grand Voyager'),('11','GTS'),('11','Imperial'),('11','Neon'),('11','New Yorker'),('11','Pacifica'),('11','Vision'),('11','Voyager'),('11','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('12','2 CV'),('12','AX'),('12','Berlingo'),('12','BX'),('12','C1'),('12','C2'),('12','C3'),('12','C4'),('12','C5'),('12','C6'),('12','C8'),('12','C-Crosser'),('12','c-Elysse'),('12','CX'),('12','C-Zero'),('12','DS'),('12','DS3'),('12','DS4'),('12','DS5'),('12','E-MEHARI'),('12','Evasion'),('12','Jumper'),('12','Jumpy'),('12','Nemo'),('12','SAXO'),('12','SpaceTourer'),('12','Visa'),('12','Xantia'),('12','XM'),('12','Xsaea'),('12','ZX'),('12','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('13','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('14','C1'),('14','C2'),('14','C3'),('14','C4'),('14','C5'),('14','C6'),('14','C7'),('14','Z06'),('14','ZR1'),('14','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('15','Dokker'),('15','Duster'),('15','Lodgy'),('15','Logan'),('15','Pick-Up'),('15','Sandero'),('15','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('16','Espero'),('16','Evanda'),('16','Kalos'),('16','Korando'),('16','Lacetti'),('16','Lanos'),('16','Leganza'),('16','Matiz'),('16','Musso'),('16','Nexia'),('16','Nubira'),('16','Rezzo'),('16','Tacuma'),('16','Tjetër')");

        db.execSQL("Insert into tblModel(mID, modName) values ('17','Applause'),('17','Charade'),('17','Charmant'),('17','Copen'),('17','Cuore'),('17','FreeClimber'),('17','Hijet'),('17','MATERIA'),('17','Move'),('17','Sirion'),('17','Trevis'),('17','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('18','Avenger'),('18','Caliber'),('18','Challenger'),('18','Charger'),('18','Dakota'),('18','Dart'),('18','Demon'),('18','Durango'),('18','Grand Caravan'),('18','Hornet'),('18','Journey'),('18','Magnum'),('18','Neon'),('18','Nitro'),('18','RAM'),('18','Viper'),('18','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('19','204'),('19','246'),('19','250'),('19','275'),('19','288'),('19','308'),('19','328'),('19','330'),('19','348'),('19','360'),('19','365'),('19','400'),('19','412'),('19','456'),('19','488'),('19','California'),('19','Daytona'),('19','Enzo Ferrari'),('19','F12'),('19','F355'),('19','F40'),('19','F430'),('19','F50'),('19','FF'),('19','LaFerrari'),('19','Portofino'),('19','Superamerica'),('19','Testarossa'),('19','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('20','124'),('20','126'),('20','127'),('20','130'),('20','500'),('20','Albea'),('20','Barchetta'),('20','Brava'),('20','Bravo'),('20','Cinquecento'),('20','Coupe'),('20','Croma'),('20','Dino'),('20','Ducato'),('20','Fiorino'),('20','Freemont'),('20','Fullback'),('20','Grande Punto'),('20','Idea'),('20','Linea'),('20','Marea'),('20','Marenga'),('20','New Panda'),('20','Punto'),('20','Qubo'),('20','Regata'),('20','Ritmo'),('20','Seicento'),('20','Tempra'),('20','Ulysse'),('20','Uno'),('20','X 1/9'),('20','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('21','Aerostar'),('21','B-Max'),('21','Bronco'),('21','Capri'),('21','Cougar'),('21','Courier'),('21','Crown'),('21','Econoline'),('21','EcoSport'),('21','Edge'),('21','Escape'),('21','Escort'),('21','Excursion'),('21','Explorer'),('21','Express'),('21','F100'),('21','F150'),('21','F250'),('21','F350'),('21','Fairlane'),('21','Falcon'),('21','Fiesta'),('21','Flex'),('21','Focus'),('21','Fusion'),('21','Galaxy'),('21','Grand C-Max'),('21','Kuga'),('21','Mercury'),('21','Mondeo'),('21','Mustang'),('21','Orion'),('21','Probe'),('21','Puma'),('21','Ranger'),('21','Raptor'),('21','Sierra'),('21','S-Max'),('21','Taurus'),('21','Transit'),('21','Windstart'),('21','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('22','Acadia'),('22','Envoy'),('22','Safari'),('22','Savana'),('22','Sierra'),('22','Sonoma'),('22','Syclone'),('22','Terrain'),('22','Typhoon'),('22','Vandura'),('22','Yukon'),('22','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('23','Accord'),('23','Aerodeck'),('23','City'),('23','Civic'),('23','Clarity'),('23','Concerto'),('23','CR-V'),('23','CRX'),('23','CR-Z'),('23','Element'),('23','FR-V'),('23','HR-V'),('23','Insight'),('23','Integra'),('23','Jazz'),('23','Legend'),('23','Logo'),('23','NSX'),('23','Odyssey'),('23','Pilot'),('23','Tjetër')");

        db.execSQL("Insert into tblModel(mID, modName) values ('24','H1'), ('24','H2'), ('24','H3'),('24','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('25','Accent'),('25','Atos'),('25','Azera'),('25','Coupe'),('25','Elantra'),('25','Excel'),('25','Genesis'),('25','Getz'),('25','Grandeur'),('25','i10'),('25','i20'),('25','i30'),('25','i40'),('25','i50'),('25','ix20'),('25','ix35'),('25','ix55'),('25','Kona'),('25','Lantra'),('25','Matrix'),('25','Pony'),('25','Santa Fe'),('25','Terracan'),('25','Tucson'),('25','Veracruz'),('25','XG 30'),('25','XG 350'),('25','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('26','EX30'),('26','EX35'),('26','EX37'),('26','FX'),('26','G35'),('26','G37'),('26','M30'),('26','M35'),('26','M37'),('26','Q30'),('26','Q45'),('26','Q50'),('26','Q60'),('26','Q70'),('26','QZ30'),('26','QX50'),('26','QX56'),('26','QX70'),('26','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('27','Campo'),('27','D-Max'),('27','Gemini'),('27','Midi'),('27','PICK UP'),('27','Trooper'),('27','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('28','Massif'),('28','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('29','Daimler'),('29','E-Type'),('29','F-Pace'),('29','F-Type'),('29','MK II'),('29','S-Type'),('29','XE'),('29','XF'),('29','XJ'),('29','XJ12'),('29','XJ40'),('29','XJ6'),('29','XJ8'),('29','XJR'),('29','XJS'),('29','XJSC'),('29','XK'),('29','XK8'),('29','XKR'),('29','X-Type'),('29','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('30','Cherokee'),('30','CJ'),('30','Comanche'),('30','Commander'),('30','Compass'),('30','Grand Cherokee'),('30','Patriot'),('30','Renegade'),('30','Wagoneer'),('30','Willys'),('30','Wrangler'),('30','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('31','Besta'),('31','Borrego'),('31','Carens'),('31','Carnival'),('31','Cerato'),('31','Clarus'),('31','Elan'),('31','Joice'),('31','K2500'),('31','K2700'),('31','Leo'),('31','Magentis'),('31','Mentor'),('31','Mini'),('31','Niro'),('31','Opirus'),('31','Optima'),('31','Picento'),('31','Pregio'),('31','Roadster'),('31','Sorento'),('31','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('32','Agera'),('32','CCR'),('32','CCXR'),('32','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('33','Aventador'),('33','Countach'),('33','Diablo'),('33','Espada'),('33','Gallardo'),('33','Huracan'),('33','Jalpa'),('33','LM'),('33','Miura'),('33','Murcielago'),('33','Urraco'),('33','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('34','Beta'),('34','Dedra'),('34','Delta'),('34','Flaminia'),('34','Flavia'),('34','Fulvia'),('34','Gamma'),('34','Kappa'),('34','Lybra'),('34','MUSA'),('34','Phedra'),('34','Prisma'),('34','Stratos'),('34','Thema'),('34','Thesis'),('34','Voyager'),('34','Ypsilon'),('34','Zeta'),('34','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('35','Defender'),('35','Discovery'),('35','Freelander'),('35','Range Rover'),('35','Serie I'),('35','Serie II'),('35','Serie III'),('35','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('36','CT 200h'),('36','ES'),('36','GS'),('36','GX'),('36','IS'),('36','LC'),('36','LFA'),('36','LFA'),('36','LS'),('36','LX'),('36','NX'),('36','RC'),('36','RX'),('36','SC'),('36','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('37','Aviator'),('37','Continental'),('37','LS'),('37','Mark'),('37','Navigator'),('37','Town Car'),('37','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('38','222'),('38','224'),('38','228'),('38','3200'),('38','418'),('38','420'),('38','4200'),('38','422'),('38','424'),('38','430'),('38','Biturbo'),('38','Ghibli'),('38','GranCabrio'),('38','Gransport'),('38','Granturismo'),('38','Indy'),('38','Karif'),('38','Levante'),('38','Merak'),('38','Spyder'),('38','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('39','57'),('39','62'),('39','Pullman'),('39','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('40','121'),('40','2'),('40','3'),('40','323'),('40','5'),('40','6'),('40','626'),('40','929'),('40','Bongo'),('40','B series'),('40','BT-50'),('40','CX-3'),('40','CX-5'),('40','CX-7'),('40','CX-9'),('40','Demio'),('40','E series'),('40','Millenia'),('40','MVP'),('40','MX-3'),('40','MX-5'),('40','MX-6'),('40','RX-6'),('40','RX-7'),('40','RX-8'),('40','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('41','640C'),('41','570GT'),('41','570S'),('41','650S'),('41','675LT'),('41','720S'),('41','MP4-12C'),('41','P1'),('41','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('42','190'),('42','200'),('42','220'),('42','230'),('42','240'),('42','250'),('42','260'),('42','270'),('42','280'),('42','290'),('42','300'),('42','320'),('42','350'),('42','380'),('42','400'),('42','416'),('42','420'),('42','500'),('42','A Class'),('42','B Class'),('42','CE'),('42','C Class'),('42','CLA'),('42','CLC'),('42','CLK'),('42','CL'),('42','CLS'),('42','E Class'),('42','G Class'),('42','GLA'),('42','GLC'),('42','GLE'),('42','GLK'),('42','GL'),('42','GLS'),('42','GTS'),('42','ML'),('42','R'),('42','S Class'),('42','SLC'),('42','SLK'),('42','SL'),('42','SLR'),('42','SLS'),('42','Sprinter'),('42','Vaneo'),('42','Vario'),('42','Viano'),('42','Vito'),('42','V Class'),('42','X Class'),('42','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('43','Cooper'),('43','John Cooper'),('43','One'),('43','1000'),('43','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('44','3000 GT'),('44','ASX'),('44','Canter'),('44','Carisma'),('44','Colt'),('44','Cordi'),('44','Cosmos'),('44','Diamante'),('44','Eclipse'),('44','I-MiEV'),('44','Galant'),('44','Galloper'),('44','Lancer'),('44','Mirage'),('44','Montero'),('44','Santamo'),('44','Sigma'),('44','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('45','100 NX'),('45','20 SX'),('45','240 SX'),('45','280 ZX'),('45','300 ZX'),('45','350Z'),('45','370Z'),('45','Almera'),('45','Altima'),('45','Armada'),('45','Bluebird'),('45','Cabstar'),('45','Cargo'),('45','Cherry'),('45','Cube'),('45','e-NC200'),('45','Evalia'),('45','Frontier'),('45','GT-R'),('45','Interstar'),('45','Juke'),('45','King Cab'),('45','Kubistar'),('45','Laurel'),('45','Maxima'),('45','Micra'),('45','Murano'),('45','Navara'),('45','Note'),('45','Patrol'),('45','Qashqai'),('45','Skyline'),('45','Terrano'),('45','X-trail'),('45','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('46','Adam'),('46','Agila'),('46','Ampera'),('46','Antara'),('46','Arena'),('46','Ascona'),('46','Astra'),('46','Calibra'),('46','Campo'),('46','Cascada'),('46','Cavalier'),('46','Combo'),('46','Corsa'),('46','Diplomat'),('46','Frontera'),('46','GT'),('46','Insignia'),('46','Kadett'),('46','Karl'),('46','Meriva'),('46','Mokka'),('46','Monterey'),('46','Omega'),('46','Senator'),('46','Signum'),('46','Speedster'),('46','Vectra'),('46','Vivaro'),('46','Zafira'),('46','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('47','Huayra'),('47','Zonda'),('47','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('48','1007'),('48','104'),('48','106'),('48','107'),('48','108'),('48','2008'),('48','204'),('48','205'),('48','206'),('48','207'),('48','208'),('48',''),('48',''),('300848',''),('48','301'),('48','304'),('48','305'),('48','306'),('48','307'),('48','308'),('48','309'),('48','4007'),('48','4008'),('48','404'),('48','405'),('48','406'),('48','407'),('48','5008'),('48','504'),('48','505'),('48','508'),('48','604'),('48','605'),('48','607'),('48','806'),('48','807'),('48','Boxer'),('48','Expert'),('48','Traveller'),('48','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('49','356'),('49','911'),('49','912'),('49','914'),('49','918'),('49','924'),('49','928'),('49','944'),('49','959'),('49','962'),('49','968'),('49','Boxter'),('49','Carrera GT'),('49','Cayenne'),('49','Cayman'),('49','Macan'),('49','Panamera'),('49','930'),('49','964'),('49','991'),('49','993'),('49','996'),('49','997'),('49','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('50','Alaskan'),('50','Alpine'),('50','Avantime'),('50','Captur'),('50','Clio'),('50','Coupe'),('50','Espace'),('50','Express'),('50','Fluence'),('50','Fuego'),('50','Grand'),('50','Kadjar'),('50','Kangoo'),('50','Koleos'),('50','Laguna'),('50','Latitude'),('50','Mascott'),('50','Master'),('50','Megane'),('50','Modus'),('50','P 1400'),('50','R'),('50','Rapid'),('50','Safrane'),('50','Scenic'),('50','Spider'),('50','Talisman'),('50','Trafic'),('50','Twingo'),('50','Twizy'),('50','Vel Satis'),('50','Wind'),('50','ZOE'),('50','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('51','Corniche'),('51','Dawn'),('51','Flying Spur'),('51','Ghost'),('51','Park Ward'),('51','Phantom'),('51','Silver'),('51','Wraith'),('51','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('52','90'),('52','900'),('52','9000'),('52','9-3'),('52','9-4X'),('52','9-5'),('52','96'),('52','9-7X'),('52','99'),('52','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('53','Alhambra'),('53','Altea'),('53','Arona'),('53','Arosa'),('53','Ateca'),('53','Cordoba'),('53','Exeo'),('53','Ibiza'),('53','Inca'),('53','leon'),('53','Malaga'),('53','Marbella'),('53','Mii'),('53','Terra'),('53','Toledo'),('53','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('54','105'),('54','120'),('54','130'),('54','135'),('54','Citigo'),('54','Fabia'),('54','Favorit'),('54','Felicia'),('54','Forman'),('54','Karoq'),('54','Kodiaq'),('54','Octavia'),('54','Pick-up'),('54','Praktik'),('54','Rapid'),('54','Roomster'),('54','Superb'),('54','Yeti'),('54','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('55','Crossblade'),('55','ForFour'),('55','ForTwo'),('55','Roadster'),('55','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('56','B9 Tribeca'),('56','Baja'),('56','BRZ'),('56','Forester'),('56','Impreza'),('56','Justy'),('56','Legacy'),('56','Levorg'),('56','Libero'),('56','SVX'),('56','Trezia'),('56','Tribeca'),('56','Vivio'),('56','WRX STi'),('56','XT'),('56','XV'),('56','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('57','Alto'),('57','Baleno'),('57','Cappucino'),('57','Carry'),('57','Celerio'),('57','Grand Vitara'),('57','Ignis'),('57','iK-2'),('57','Jimny'),('57','Kizashi'),('57','Liana'),('57','Sj Samurai'),('57','Swift'),('57','Vitara'),('57','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('58','Model 3'),('58','Model S'),('58','Model X'),('58','Roadster'),('58','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('59','4-Runner'),('59','Alphard'),('59','Auris'),('59','Avalon'),('59','Avensis'),('59','Aygo'),('59','Camry'),('59','Carina'),('59','Corolla'),('59','Cressida'),('59','Crown'),('59','Dyna'),('59','FCV'),('59','Fortuner'),('59','GT86'),('59','Highlander'),('59','IQ'),('59','Land Cruiser'),('59','Matrix'),('59','Paseo'),('59','Picnic'),('59','Previa'),('59','Prius'),('59','Siena'),('59','Supra'),('59','Tacoma'),('59','Tundra'),('59','Urban Cruiser'),('59','Yaris'),('59','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('60','181'),('60','Amarok'),('60','Arteon'),('60','Beetle'),('60','Bora'),('60','Buggy'),('60','Caddy'),('60','CC'),('60','Corrado'),('60','Crafter'),('60','Eos'),('60','Fox'),('60','Golf I'),('60','Golf II'),('60','Golf III'),('60','Golf IV'),('60','Golf V'),('60','Golf VI'),('60','Golf VII'),('60','Golf Plus'),('60','Iltis'),('60','Jetta'),('60','Kafer'),('60','LT'),('60','Lupo'),('60','New Beetle'),('60','Passat'),('60','Phaeton'),('60','Polo'),('60','Poutan'),('60','Santana'),('60','Scirocco'),('60','Sharan'),('60','T1'),('60','T2'),('60','T3'),('60','T4'),('60','T5'),('60','T6'),('60','Taro'),('60','Tiguan'),('60','Touareg'),('60','Touran'),('60','T-Roc'),('60','up!'),('60','Vento'),('60','XL1'),('60','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('61','240'),('61','244'),('61','245'),('61','262'),('61','264'),('61','340'),('61','360'),('61','440'),('61','460'),('61','480'),('61','740'),('61','744'),('61','745'),('61','760'),('61','780'),('61','850'),('61','855'),('61','940'),('61','944'),('61','945'),('61','960'),('61','965'),('61','Amazon'),('61','C30'),('61','C70'),('61','Polar'),('61','S40'),('61','S60'),('61','S70'),('61','S80'),('61','S90'),('61','V40'),('61','V50'),('61','V60'),('61','V70'),('61','V90'),('61','XC40'),('61','XC60'),('61','XC70'),('61','XC90'),('61','Tjetër') ");

        db.execSQL("Insert into tblModel(mID, modName) values ('62','Tjetër') ");





        db.execSQL("Insert into tblFuel(fName) values ('Diesel'), ('Petrol'), ('Gas'), " +
                "('Electric'), ('Hybrid')");

        db.execSQL("Insert into tblBody(bodyName) values ('Sedan'),('Caravan'), ('Convertible'), ('Off-Road'), ('Small car'), ('Sportive')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
