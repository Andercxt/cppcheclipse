package com.googlecode.cppcheclipse.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

public class SuppressionProfile implements TableModel<Suppression> {
	private static final String DELIMITER = "!";
	
	// contains the suppressions per file
	private final ListMultimap<File, Suppression> suppressionList;
	private final IPreferenceStore projectPreferences;
	private final IProject project;

	public SuppressionProfile(IPreferenceStore projectPreferences,
			IProject project) {
		this.projectPreferences = projectPreferences;
		this.suppressionList = LinkedListMultimap.create();
		this.project = project;
		load();
	}

	private void load() {
		String suppressions = projectPreferences
				.getString(IPreferenceConstants.P_SUPPRESSIONS);
		StringTokenizer tokenizer = new StringTokenizer(suppressions, DELIMITER);
		while (tokenizer.hasMoreTokens()) {
			try {
				Suppression suppression = Suppression.deserialize(tokenizer
						.nextToken());
				addSuppression(suppression);
			} catch (Exception e) {
				CppcheclipsePlugin.logWarning("Could not load preferences", e);
			}
		}
	}

	public void save() throws IOException {
		StringBuffer suppressions = new StringBuffer();
		Iterator<?> iterator = suppressionList.values().iterator();
		while (iterator.hasNext()) {
			Suppression suppression = (Suppression) iterator.next();
			suppressions.append(suppression.serialize()).append(DELIMITER);
		}

		projectPreferences.setValue(IPreferenceConstants.P_SUPPRESSIONS,
				suppressions.toString());

		if (projectPreferences instanceof IPersistentPreferenceStore) {
			((IPersistentPreferenceStore) projectPreferences).save();
		}
	}

	private void addSuppression(Suppression suppression) {
		suppressionList.put(suppression.getFile(project), suppression);
	}

	public Suppression addFileSuppression(File file) {
		Suppression suppression = new Suppression(file, project);
		addSuppression(suppression);
		return suppression;
	}

	public void addProblemSuppression(File file, String problemId) {
		addSuppression(new Suppression(file, problemId));
	}

	public void addProblemInLineSuppression(File file, String problemId,
			int line) {
		addSuppression(new Suppression(file, problemId, line));
	}

	public void remove(Suppression suppression) {
		suppressionList.remove(suppression.getFile(project), suppression);
	}

	public void removeAll() {
		suppressionList.clear();
	}

	private File makeAbsoluteFile(File file) {
		if (file.isAbsolute()) {
			return file;
		}
		return new File(project.getLocation().toFile(), file.toString());
	}

	public boolean isFileSuppressed(File file) {
		List<Suppression> suppressions = suppressionList
				.get(makeAbsoluteFile(file));
		for (Suppression suppression : suppressions) {
			if (suppression.isFileSuppression())
				return true;
		}
		return false;
	}

	public boolean isProblemInLineSuppressed(File file, String problemId,
			int line) {
		// not possible to suppress problems with no file attached
		if (file == null) {
			return false;
		}
		List<Suppression> suppressions = suppressionList
				.get(makeAbsoluteFile(file));
		for (Suppression suppression : suppressions) {
			if (suppression.isFileSuppression()) {
				return true;
			}
			if (suppression.isSuppression(file, problemId, line, project)) {
				return true;
			}
		}
		return false;
	}

	public Collection<Suppression> getSuppressions() {
		return suppressionList.values();
	}
	
	public Iterator<Suppression> iterator() {
		return suppressionList.values().iterator();
	}
	
	public Suppression[] toArray() {
		return (Suppression[]) suppressionList.values().toArray(new Suppression[0]);
	}
}