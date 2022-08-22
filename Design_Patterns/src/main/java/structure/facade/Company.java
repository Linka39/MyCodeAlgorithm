package structure.facade;

public class Company {
    String name;
    String companyId;

    String adminOfIndustry;
    String bankAccount;
    String taxCode;

    public Company(String name) {
        this.name = name;
        this.companyId = "001";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAdminOfIndustry() {
        return adminOfIndustry;
    }

    public void setAdminOfIndustry(String adminOfIndustry) {
        this.adminOfIndustry = adminOfIndustry;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", companyId='" + companyId + '\'' +
                ", adminOfIndustry='" + adminOfIndustry + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", taxCode='" + taxCode + '\'' +
                '}';
    }
}
