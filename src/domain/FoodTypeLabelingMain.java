package domain;

import labelingSystem.LabelingSystemFood;

public class FoodTypeLabelingMain {

	public static void main(String[] args) {
		new LabelingSystemFood().labeling(
				"foodType.txt",
				"foodTypeLabelingRule.txt",
				"foodTypeLabeled.txt"
		);
	}
}
