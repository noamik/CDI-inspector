package cz.muni.fi.cdii.plugin.visual.model;

import java.util.Set;

import cz.muni.fi.cdii.plugin.common.model.Class;
import cz.muni.fi.cdii.plugin.common.model.Field;
import cz.muni.fi.cdii.plugin.common.model.Function;

public class LocalClass extends Class {
	
	private String className;

	@Override
	public String getPackageName() {
		// TODO Auto-generated method stub
		return "wired package placeholder";
	}

	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public boolean openable() {
		return true;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Field> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Function> getFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

}
