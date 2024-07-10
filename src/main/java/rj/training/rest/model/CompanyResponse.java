package rj.training.rest.model;

public class CompanyResponse {
Company myCompany;

@Override
public String toString() {
	return "CompanyResponse [myCompany=" + myCompany + "]";
}

public Company getMyCompany() {
	return myCompany;
}

public void setMyCompany(Company myCompany) {
	this.myCompany = myCompany;
}
}
