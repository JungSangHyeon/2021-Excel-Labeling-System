package domain;

import labelingSystem.LabelingSystem;

public class CompanyLabelingMain {

	public static void main(String[] args) {
		new LabelingSystem().labeling(
				"companyType.txt",
				"companyLabelingRule.txt",
				"companyLabeled.txt"
		);
	}
}
