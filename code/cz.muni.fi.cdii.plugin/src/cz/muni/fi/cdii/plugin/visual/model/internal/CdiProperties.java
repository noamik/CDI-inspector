package cz.muni.fi.cdii.plugin.visual.model.internal;

import java.util.Set;

public abstract class CdiProperties {
	public abstract String getName();
	public abstract String getTypeName();
	public abstract Set<String> getQualifiers();
	public abstract Set<String> getStereotypes();
}
