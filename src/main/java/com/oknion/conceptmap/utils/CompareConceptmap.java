package com.oknion.conceptmap.utils;

import java.util.HashSet;
import java.util.Set;

import com.oknion.conceptmap.Model.Ccrelationship;
import com.oknion.conceptmap.Model.Concept;
import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Error;

public class CompareConceptmap {

	public static boolean checkNameCc(Concept sourceCc, Concept compareCc) {

		String[] conceptNames;

		try {
			conceptNames = sourceCc.getCcName().split(",");
		} catch (NullPointerException e) {
			conceptNames = null;
		}

		if (sourceCc.getCcText().trim().equals(compareCc.getCcText().trim())) {
			return true;

		}
		if (conceptNames != null) {
			for (String string : conceptNames) {
				if (string.trim().toLowerCase()
						.equals(compareCc.getCcText().trim().toLowerCase())) {

					return true;

				}
			}
		}

		return false;
	}

	public static Set<Error> compare(Conceptmap sourceCM, Conceptmap compareCm) {
		Set<Error> errors = new HashSet<Error>();
		for (Ccrelationship ccrelationship : sourceCM.getCcrelationships()) {
			for (Concept concept : sourceCM.getConcepts()) {
				if (ccrelationship.getFrom() == concept.getKeyId()) {
					ccrelationship.fromConcept = concept;
				}
				if (ccrelationship.getTo() == concept.getKeyId()) {
					ccrelationship.toConcept = concept;
				}
			}

		}

		for (Ccrelationship ccrelationship : compareCm.getCcrelationships()) {
			for (Concept concept : compareCm.getConcepts()) {
				if (ccrelationship.getFrom() == concept.getKeyId()) {
					ccrelationship.fromConcept = concept;
				}
				if (ccrelationship.getTo() == concept.getKeyId()) {
					ccrelationship.toConcept = concept;
				}
			}

		}

		for (Concept concept : sourceCM.getConcepts()) {

			boolean isCorrect = false;

			for (Concept cpConcept : compareCm.getConcepts()) {
				if (!cpConcept.checked) {
					if (checkNameCc(concept, cpConcept)) {
						cpConcept.checked = true;
						isCorrect = true;
						break;
					}

				}

			}
			if (!isCorrect) {
				System.out.println("Concept " + concept.getCcText()
						+ " is missing!");
				Error error = new Error();
				error.setConceptmap(compareCm);
				error.setDocuments(concept.getDocuments());
				if (error.getDocuments().size() != 0) {

					error.setDescrip("Missing Concept!");
				} else {
					error.setDescrip("Missing Concept!");
				}
				error.setName("Missing");
				errors.add(error);
			} else {
				System.out.println("Concept " + concept.getCcText()
						+ " is correct!");
			}
		}

		// check relationship
		for (Ccrelationship ccrelationship : sourceCM.getCcrelationships()) {
			boolean isCorrect = false;
			for (Ccrelationship cpCcrelationship : compareCm
					.getCcrelationships()) {
				System.out.println(ccrelationship.getText().trim()
						+ cpCcrelationship.getText().trim());
				if (checkNameCc(ccrelationship.fromConcept,
						cpCcrelationship.fromConcept)
						&& checkNameCc(ccrelationship.toConcept,
								cpCcrelationship.toConcept)
						&& ccrelationship.getText().trim()
								.equals(cpCcrelationship.getText().trim())
						&& !cpCcrelationship.checked) {
					cpCcrelationship.checked = true;
					isCorrect = true;
					break;

				}
			}
			if (!isCorrect) {
				System.out.println("Relationship " + ccrelationship.getText()
						+ " is missing!");
				Error error = new Error();
				error.setConceptmap(compareCm);
				error.setDescrip("Missing Relationship!");
				error.setName("Missing");
				errors.add(error);
			} else {
				System.out.println("Relationship " + ccrelationship.getText()
						+ " is correct!");
			}

		}
		int errorBefore = errors.size();
		int numWrongConcept = 0;
		for (Concept concept : compareCm.getConcepts()) {
			if (!concept.checked) {
				numWrongConcept++;
			}

		}
		if (numWrongConcept > 0) {
			Error errorNumWrongConcept = new Error();
			errorNumWrongConcept.setName("Wrong concept");
			errorNumWrongConcept.setDescrip(numWrongConcept
					+ " concepts is wrong!");
			errorNumWrongConcept.setConceptmap(compareCm);
			errors.add(errorNumWrongConcept);
		}

		int numWrongRela = 0;
		for (Ccrelationship rela : compareCm.getCcrelationships()) {
			if (!rela.checked) {
				numWrongRela++;
			}

		}
		if (numWrongRela > 0) {
			Error errorNumWrongRela = new Error();
			errorNumWrongRela.setName("Wrong Relationship");
			errorNumWrongRela.setDescrip(numWrongRela
					+ " relationships is wrong!");
			errorNumWrongRela.setConceptmap(compareCm);
			errors.add(errorNumWrongRela);
		}

		// tinh diem
		int numberConceptandRela = sourceCM.getConcepts().size()
				+ sourceCM.getCcrelationships().size();

		Short score = (short) (100 - (errorBefore + numWrongRela + numWrongConcept)
				* 100 / numberConceptandRela);
		System.out.print("____________________" + errors.size() * 100
				/ numberConceptandRela + "__________________");
		System.out.print("____________________" + score + "__________________");
		if (score > 0) {
			compareCm.setScore(score);
		} else {
			compareCm.setScore((short) 0);
		}

		return errors;
	}
}
