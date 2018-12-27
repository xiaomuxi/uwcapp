package com.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    public static void main(String[] args) {
//        Schema schema = new Schema(1, "com.project.archives.common.dao");
//        schema.enableKeepSectionsByDefault();

        Schema msgSchema = new Schema(1, "com.weddingcar.user.common.dao");

        addTables(msgSchema);

        try {
            new DaoGenerator().generateAll(msgSchema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addMsg(schema);
//        addUsers(schema);
//        addDutyReportsEntities(schema);
//        addGiftEntities(schema);
//        addUserEntities(schema);

//        addCaseInvesEntities(schema);
//        addVerificationsEntities(schema);
//        addLettersEntities(schema);
//        addEndingsEntities(schema);
//        addZancunsEntities(schema);
//        addMultiDictionariesEntities(schema);

//        Entity entity = schema.addEntity("MultiDictionaries");
//        entity.addByteArrayProperty("ID").primaryKey();
//        entity.addStringProperty("OptionsName");
//        entity.addIntProperty("Type");
//        entity.addIntProperty("Sort");
//        entity.addIntProperty("IsDelete");
//        entity.addDateProperty("AddDate");
//        entity.addStringProperty("AddUser");
//        entity.addDateProperty("UpdateDate");
//        entity.addStringProperty("UpdateUser");
//
//        Entity caseInves = schema.addEntity("CaseInves");
//        caseInves.addByteArrayProperty("ID").primaryKey();
//        caseInves.addByteArrayProperty("UserID");
//        caseInves.addStringProperty("Name");
//        caseInves.addStringProperty("Init");
//        caseInves.addStringProperty("Position");
//        caseInves.addIntProperty("Rank");
//        caseInves.addIntProperty("Coding");
//        caseInves.addIntProperty("IsPcongress");
//        caseInves.addIntProperty("IsMember");
//        caseInves.addIntProperty("IsObject");
//        caseInves.addIntProperty("IsOfficer");
//        caseInves.addIntProperty("IsPartyMember");
//        caseInves.addStringProperty("Facts");
//        caseInves.addByteArrayProperty("Discipline");
//        caseInves.addIntProperty("Organ");
//        caseInves.addDateProperty("PutTime");
//        caseInves.addDateProperty("OutTime");
//        caseInves.addStringProperty("SurveyContent");
//        caseInves.addIntProperty("DisTypeD");
//        caseInves.addStringProperty("Note");
//        caseInves.addByteArrayProperty("AnnexIDStr");
//        caseInves.addIntProperty("isDelete");
//        caseInves.addDateProperty("AddDate");
//        caseInves.addStringProperty("AddUser");
//        caseInves.addDateProperty("UpdateDate");
//        caseInves.addStringProperty("UpdateUser");
//        caseInves.addStringProperty("PoliticsStatus");
//        caseInves.addIntProperty("Organization");
//        caseInves.addIntProperty("Xiansou");
//        caseInves.addDateProperty("ShouTime");
//        caseInves.addStringProperty("Trail");
//        caseInves.addStringProperty("Description");
//        caseInves.addDateProperty("ChuheTime");
//        caseInves.addDateProperty("LiaojieTime");
//        caseInves.addIntProperty("DisTypeX");

//        Entity verifications = schema.addEntity("Verifications");
//        verifications.addByteArrayProperty("ID").primaryKey();
//        verifications.addByteArrayProperty("UserID");
//        verifications.addStringProperty("Name");
//        verifications.addStringProperty("Init");
//        verifications.addStringProperty("Position");
//        verifications.addIntProperty("Rank");
//        verifications.addIntProperty("Coding");
//        verifications.addIntProperty("IsObject");
//        verifications.addIntProperty("IsOfficer");
////        verifications.addByteArrayProperty("Discipline");
//        verifications.addIntProperty("Organ");
//        verifications.addDateProperty("VerificTime");
//        verifications.addDateProperty("TakingTime");
//        verifications.addStringProperty("Clues");
//        verifications.addStringProperty("VerificResult");
//        verifications.addIntProperty("TakingResult");
//        verifications.addIntProperty("ResultSituation");
//        verifications.addStringProperty("Note");
//        verifications.addByteArrayProperty("AnnexIDStr");
//        verifications.addIntProperty("isDelete");
//        verifications.addDateProperty("AddDate");
//        verifications.addStringProperty("AddUser");
//        verifications.addDateProperty("UpdateDate");
//        verifications.addStringProperty("UpdateUser");
//        verifications.addIntProperty("objectSource");
//        verifications.addDateProperty("ProcessTime");
//        verifications.addStringProperty("Trail");
//        Property property1 =  caseInves.addByteArrayProperty("Discipline").getProperty();
//        Property property2 =  verifications.addByteArrayProperty("Discipline").getProperty();
//
//        caseInves.addToOne(entity, property1);
//        verifications.addToOne(entity, property2);
    }

    private static void addMsg(Schema schema) {
        Entity user = schema.addEntity("Notification");
        user.addIdProperty().primaryKey();
        user.addStringProperty("msgId");
        user.addStringProperty("account");
        user.addStringProperty("createTime");
        user.addStringProperty("title");
        user.addStringProperty("content");
        user.addStringProperty("url");
        user.addStringProperty("picUrl");
        user.addStringProperty("type");
    }

   private static void addUsers(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey();
        user.addStringProperty("userName").unique();
        user.addStringProperty("password");
   }

    private static void addDutyReportsEntities(Schema schema) {
        Entity dutyReports = schema.addEntity("DutyReports");
        dutyReports.setDbName("DutyReports");
        dutyReports.addByteArrayProperty("id").dbName("ID").primaryKey();
        dutyReports.addByteArrayProperty("userID").dbName("UserID");
        dutyReports.addStringProperty("name").dbName("Name");
        dutyReports.addStringProperty("init").dbName("Init");
        dutyReports.addStringProperty("position").dbName("Position");
        dutyReports.addStringProperty("dutyReportTime").dbName("DutyReportTime");
        dutyReports.addStringProperty("situation").dbName("Situation");
        dutyReports.addStringProperty("evaluation").dbName("Evaluation");
        dutyReports.addStringProperty("isDelete").dbName("IsDelete");
        dutyReports.addStringProperty("annexIDStr").dbName("AnnexIDStr");
        dutyReports.addStringProperty("addDate").dbName("AddDate");
        dutyReports.addStringProperty("addUser").dbName("AddUser");
        dutyReports.addStringProperty("updateUser").dbName("updateUser");
        dutyReports.addStringProperty("updateDate").dbName("updateDate");
        dutyReports.addStringProperty("dutyReportYear").dbName("DutyReportYear");
        dutyReports.addStringProperty("specialInspectNum").dbName("SpecialInspectNum");
        dutyReports.addStringProperty("discoverProblemsNum").dbName("DiscoverProblemsNum");
        dutyReports.addStringProperty("interviewNum").dbName("InterviewNum");
        dutyReports.addStringProperty("newSystemNum").dbName("NewSystemNum");
        dutyReports.addStringProperty("clueNum").dbName("ClueNum");
        dutyReports.addStringProperty("rectificationNum").dbName("RectificationNum");
        dutyReports.addStringProperty("isJoinMeeting").dbName("IsJoinMeeting");
        dutyReports.addStringProperty("qwEvaluation").dbName("QWEvaluation");
        dutyReports.addStringProperty("reviewLevel").dbName("ReviewLevel");
        dutyReports.addStringProperty("insufficient").dbName("Insufficient");
        dutyReports.addStringProperty("otherItem").dbName("OtherItem");

    }

    private static void addGiftEntities(Schema schema) {
        Entity gifts = schema.addEntity("Gifts");
        gifts.setDbName("Gifts");
        gifts.addByteArrayProperty("id").dbName("ID").primaryKey();
        gifts.addByteArrayProperty("giftHandID").dbName("GiftHandID");
        gifts.addStringProperty("giftHandTime").dbName("GiftHandTime");
        gifts.addStringProperty("giftHandAmount").dbName("GiftHandAmount");
        gifts.addStringProperty("giftName").dbName("GiftName");
        gifts.addIntProperty("giftNum").dbName("GiftNum");
        gifts.addStringProperty("giftNote").dbName("GiftNote");
        gifts.addIntProperty("isDelete").dbName("IsDelete");
        gifts.addStringProperty("addTime").dbName("AddTime");

        Entity giftHandDetails = schema.addEntity("GiftHandDetails");
        giftHandDetails.setDbName("GiftHandDetails");
        giftHandDetails.addLongProperty("id").dbName("ID").primaryKey();
        giftHandDetails.addLongProperty("giftHandID").dbName("GiftHandID");
        giftHandDetails.addStringProperty("handTime").dbName("HandTime");
        giftHandDetails.addIntProperty("handAmount").dbName("HandAmount");
        giftHandDetails.addStringProperty("handNote").dbName("HandNote");
        giftHandDetails.addIntProperty("isDelete").dbName("IsDelete");
        giftHandDetails.addStringProperty("addTime").dbName("AddTime");

        Entity giftCards = schema.addEntity("GiftCards");
        giftCards.setDbName("GiftCards");
        giftCards.addLongProperty("id").dbName("ID").primaryKey();
        giftCards.addLongProperty("giftHandID").dbName("GiftHandID");
        giftCards.addStringProperty("cardTime").dbName("CardTime");
        giftCards.addStringProperty("cardNumber").dbName("CardNumber");
        giftCards.addIntProperty("cardAmount").dbName("CardAmount");
        giftCards.addStringProperty("cardName").dbName("CardName");
        giftCards.addStringProperty("cardNote").dbName("CardNote");
        giftCards.addIntProperty("isDelete").dbName("IsDelete");
        giftCards.addStringProperty("addTime").dbName("AddTime");


        Entity giftHands = schema.addEntity("GiftHands");
        giftHands.setDbName("GiftHands");
        giftHands.addLongProperty("id").dbName("ID").primaryKey();
        giftHands.addLongProperty("userID").dbName("UserID");
        giftHands.addStringProperty("name").dbName("Name");
        giftHands.addStringProperty("init").dbName("Init");
        giftHands.addStringProperty("position").dbName("Position");
        giftHands.addIntProperty("rank").dbName("Rank");
        giftHands.addIntProperty("isDelete").dbName("IsDelete");
        giftHands.addStringProperty("addDate").dbName("AddDate");
        giftHands.addStringProperty("addUser").dbName("AddUser");
        giftHands.addStringProperty("updateDate").dbName("UpdateDate");
        giftHands.addStringProperty("updateUser").dbName("UpdateUser");

    }

    private static void addUserEntities(Schema schema) {
        Entity entity = schema.addEntity("Users");
        entity.addByteArrayProperty("ID").primaryKey();
        entity.addIntProperty("IsDelete");
        entity.addStringProperty("RealName");
        entity.addStringProperty("IDCard");
        entity.addIntProperty("Sex");
        entity.addIntProperty("Age");
        entity.addStringProperty("National");
        entity.addStringProperty("NativePlace");
        entity.addStringProperty("HomePlace");
        entity.addStringProperty("Health");
        entity.addStringProperty("ZPosition");
        entity.addStringProperty("Specialty");
        entity.addStringProperty("QEducation");
        entity.addStringProperty("QSchool");
        entity.addStringProperty("Education");
        entity.addStringProperty("School");
        entity.addStringProperty("Resume");
        entity.addStringProperty("BonusPenalty");
        entity.addStringProperty("CheckResult");
        entity.addStringProperty("Position");
        entity.addStringProperty("Birth");
        entity.addStringProperty("DataType");
        entity.addStringProperty("AddDate");
        entity.addStringProperty("AddUser");
        entity.addStringProperty("UpdateDate");
        entity.addStringProperty("UpdateUser");
        entity.addStringProperty("ContactPhone");
        entity.addStringProperty("HomeAddress");
        entity.addStringProperty("BirthYear");
        entity.addStringProperty("PictureUrl");
        entity.addStringProperty("PictureName");
        entity.addStringProperty("PartyTimeStr");
        entity.addStringProperty("WorkTimeStr");
        entity.addStringProperty("Init");
        entity.addIntProperty("Rank");
        entity.addStringProperty("IsCadre");
        entity.addIntProperty("CbInit");
        entity.addStringProperty("IsDone");
        entity.addStringProperty("IsDoneW");
        entity.addStringProperty("IsDoneJ");
        entity.addStringProperty("IsRen");
        entity.addStringProperty("IsZheng");
        entity.addStringProperty("GerenID");

    }

    private static Entity addMultiDictionariesEntities(Schema schema) {
        Entity entity = schema.addEntity("MultiDictionaries");
        entity.addByteArrayProperty("ID").primaryKey();
        entity.addStringProperty("OptionsName");
        entity.addIntProperty("Type");
        entity.addIntProperty("Sort");
        entity.addIntProperty("IsDelete");
        entity.addDateProperty("AddDate");
        entity.addStringProperty("AddUser");
        entity.addDateProperty("UpdateDate");
        entity.addStringProperty("UpdateUser");

        return entity;
    }

    private static Entity addZancunsEntities(Schema schema) {
        Entity entity = schema.addEntity("Zancuns");
        entity.addByteArrayProperty("UserID");
        entity.addStringProperty("Name");
        entity.addStringProperty("Init");
        entity.addStringProperty("Position");
        entity.addIntProperty("Rank");
        entity.addStringProperty("IdCard");
        entity.addStringProperty("Number");
        entity.addDateProperty("EndingTime");
        entity.addStringProperty("KeyWord");
        entity.addByteArrayProperty("Problem");
        entity.addStringProperty("EndingContent");
        entity.addStringProperty("SurveyContent");
        entity.addIntProperty("TrueDegree");
        entity.addStringProperty("Result");
        entity.addByteArrayProperty("ResultSituation");
        entity.addStringProperty("Note");
//        endings.addIntProperty("Organ");
        entity.addByteArrayProperty("AnnexIDStr");
        entity.addIntProperty("isDelete");
        entity.addDateProperty("AddDate");
        entity.addStringProperty("AddUser");
        entity.addDateProperty("UpdateDate");
        entity.addStringProperty("UpdateUser");
        entity.addIntProperty("objectSource");

        return entity;
    }

    private static Entity addEndingsEntities(final Schema schema) {
        Entity endings = schema.addEntity("Endings");
        endings.addByteArrayProperty("ID").primaryKey();
        endings.addByteArrayProperty("UserID");
        endings.addStringProperty("Name");
        endings.addStringProperty("Init");
        endings.addStringProperty("Position");
        endings.addIntProperty("Rank");
        endings.addStringProperty("IdCard");
        endings.addStringProperty("Number");
        endings.addDateProperty("EndingTime");
        endings.addStringProperty("KeyWord");
        endings.addByteArrayProperty("Problem");
        endings.addStringProperty("EndingContent");
        endings.addStringProperty("SurveyContent");
        endings.addIntProperty("TrueDegree");
        endings.addStringProperty("Result");
        endings.addByteArrayProperty("ResultSituation");
        endings.addStringProperty("Note");
//        endings.addIntProperty("Organ");
        endings.addByteArrayProperty("AnnexIDStr");
        endings.addIntProperty("isDelete");
        endings.addDateProperty("AddDate");
        endings.addStringProperty("AddUser");
        endings.addDateProperty("UpdateDate");
        endings.addStringProperty("UpdateUser");
        endings.addIntProperty("objectSource");

        return endings;
    }

    private static Entity addLettersEntities(final Schema schema) {
        Entity letters = schema.addEntity("Letters");
        letters.addByteArrayProperty("ID").primaryKey();
        letters.addByteArrayProperty("UserID");
        letters.addStringProperty("Name");
        letters.addStringProperty("Init");
        letters.addStringProperty("Position");
        letters.addIntProperty("Rank");
        letters.addStringProperty("IdCard");
        letters.addStringProperty("Number");
        letters.addDateProperty("LetterTime");
        letters.addStringProperty("KeyWord");
        letters.addByteArrayProperty("Problem");
        letters.addStringProperty("LetterContent");
        letters.addStringProperty("SurveyContent");
        letters.addIntProperty("TrueDegree");
        letters.addStringProperty("Result");
        letters.addByteArrayProperty("ResultSituation");
        letters.addStringProperty("Note");
        letters.addIntProperty("Organ");
        letters.addByteArrayProperty("AnnexIDStr");
        letters.addIntProperty("isDelete");
        letters.addDateProperty("AddDate");
        letters.addStringProperty("AddUser");
        letters.addDateProperty("UpdateDate");
        letters.addStringProperty("UpdateUser");
        letters.addIntProperty("objectSource");

        return letters;
    }

    private static Entity addVerificationsEntities(final Schema schema) {
        Entity verifications = schema.addEntity("Verifications");
        verifications.addByteArrayProperty("ID").primaryKey();
        verifications.addByteArrayProperty("UserID");
        verifications.addStringProperty("Name");
        verifications.addStringProperty("Init");
        verifications.addStringProperty("Position");
        verifications.addIntProperty("Rank");
        verifications.addIntProperty("Coding");
        verifications.addIntProperty("IsObject");
        verifications.addIntProperty("IsOfficer");
        verifications.addByteArrayProperty("Discipline");
        verifications.addIntProperty("Organ");
        verifications.addDateProperty("VerificTime");
        verifications.addDateProperty("TakingTime");
        verifications.addStringProperty("Clues");
        verifications.addStringProperty("VerificResult");
        verifications.addIntProperty("TakingResult");
        verifications.addIntProperty("ResultSituation");
        verifications.addStringProperty("Note");
        verifications.addByteArrayProperty("AnnexIDStr");
        verifications.addIntProperty("isDelete");
        verifications.addDateProperty("AddDate");
        verifications.addStringProperty("AddUser");
        verifications.addDateProperty("UpdateDate");
        verifications.addStringProperty("UpdateUser");
        verifications.addIntProperty("objectSource");
        verifications.addDateProperty("ProcessTime");
        verifications.addStringProperty("Trail");

        return verifications;
    }

    private static Entity addCaseInvesEntities(final Schema schema) {
        Entity caseInves = schema.addEntity("CaseInves");
        caseInves.addByteArrayProperty("ID").primaryKey();
        caseInves.addByteArrayProperty("UserID");
        caseInves.addStringProperty("Name");
        caseInves.addStringProperty("Init");
        caseInves.addStringProperty("Position");
        caseInves.addIntProperty("Rank");
        caseInves.addIntProperty("Coding");
        caseInves.addIntProperty("IsPcongress");
        caseInves.addIntProperty("IsMember");
        caseInves.addIntProperty("IsObject");
        caseInves.addIntProperty("IsOfficer");
        caseInves.addIntProperty("IsPartyMember");
        caseInves.addStringProperty("Facts");
        caseInves.addByteArrayProperty("Discipline");
        caseInves.addIntProperty("Organ");
        caseInves.addDateProperty("PutTime");
        caseInves.addDateProperty("OutTime");
        caseInves.addStringProperty("SurveyContent");
        caseInves.addIntProperty("DisTypeD");
        caseInves.addStringProperty("Note");
        caseInves.addByteArrayProperty("AnnexIDStr");
        caseInves.addIntProperty("isDelete");
        caseInves.addDateProperty("AddDate");
        caseInves.addStringProperty("AddUser");
        caseInves.addDateProperty("UpdateDate");
        caseInves.addStringProperty("UpdateUser");
        caseInves.addStringProperty("PoliticsStatus");
        caseInves.addIntProperty("Organization");
        caseInves.addIntProperty("Xiansou");
        caseInves.addDateProperty("ShouTime");
        caseInves.addStringProperty("Trail");
        caseInves.addStringProperty("Description");
        caseInves.addDateProperty("ChuheTime");
        caseInves.addDateProperty("LiaojieTime");

        return caseInves;
    }
}
