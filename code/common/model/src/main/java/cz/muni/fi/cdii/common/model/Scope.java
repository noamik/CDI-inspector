package cz.muni.fi.cdii.common.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@JsonAutoDetect(getterVisibility=Visibility.NONE, setterVisibility=Visibility.NONE, 
	isGetterVisibility=Visibility.NONE, fieldVisibility=Visibility.DEFAULT)
public class Scope {

	@JsonProperty
	private boolean buildin;

	@JsonProperty
	private boolean pseudo;
}