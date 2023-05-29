package UserManagementSystem;

record CompanyRecord(int id, String name){}
record UserRecord(int id, int company_id, String name, int score){}
record AlmostRecord(int id, String 企業名, String ユーザー名, int スコア){}