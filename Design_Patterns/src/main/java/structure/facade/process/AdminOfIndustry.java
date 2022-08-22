package structure.facade.process;

import structure.facade.Company;

// 工商注册:
public class AdminOfIndustry {
    String name;
    public Company register(String name) {
        Company company = new Company(name);
        company.setAdminOfIndustry("测试工商");
        return company;
    }
}
