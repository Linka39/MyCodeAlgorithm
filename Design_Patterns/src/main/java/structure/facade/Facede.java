package structure.facade;

import structure.facade.process.AdminOfIndustry;
import structure.facade.process.Bank;
import structure.facade.process.Taxation;

public class Facede {
    AdminOfIndustry admin = new AdminOfIndustry();
    Bank bank = new Bank();
    Taxation taxation = new Taxation();

    // 开公司直接到Facade 就可以
    public Company openCompany(String name) {
        Company company = this.admin.register(name);
        company.setBankAccount(this.bank.openAccount(company.companyId));
        company.setTaxCode(this.taxation.applyTaxCode(company.companyId));
        return company;
    }
}
