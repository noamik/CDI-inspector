package cz.muni.fi.cdii.common.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@JsonAutoDetect(getterVisibility=Visibility.NONE, setterVisibility=Visibility.NONE, 
	isGetterVisibility=Visibility.NONE, fieldVisibility=Visibility.NONE)
public class Method implements Member {

    @JsonProperty
    private String name;
    
    /**
     * null iff method is a constructor or returns void
     */
    @JsonProperty
    private Type type;

    @JsonProperty
    private Bean producedBean;

    @JsonProperty
    private List<MethodParameter> parameters = new ArrayList<>();

    @JsonProperty
    private boolean isConstructor = false;
    
    /**
     * the only purpose of this field is equality computation
     */

    @JsonProperty
    private Type surroundingType;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public Bean getProducedBean() {
        return producedBean;
    }
    
    public void setProducedBean(Bean producedBean) {
        this.producedBean = producedBean;
    }

    public List<MethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<MethodParameter> parameters) {
        this.parameters = parameters;
    }

    public Type getSurroundingType() {
        return surroundingType;
    }

    public void setSurroundingType(Type surroundingType) {
        this.surroundingType = surroundingType;
    }

    public boolean isConstructor() {
        return isConstructor;
    }

    public void setConstructor(boolean isConstructor) {
        this.isConstructor = isConstructor;
    }

    @Override
    public String getNodeText() {
        StringBuilder result = new StringBuilder();
        if (!this.isConstructor) {
            if (this.type == null) {
                result.append("void");
            } else {
                result.append(this.type.toString(false, true));   
            }
            result.append(" ");
        }
        result.append(this.getName());
        result.append(getParametersAsString(false));
        return result.toString();
    }
    
    @Override
    public String getNodeTooltipText() {
        StringBuilder result = new StringBuilder();
        if (this.getProducedBean() != null) {
            result.append("@Produces").append("\n");
        } else if (this.hasInjectionPoints()) {
            result.append("@Inject").append("\n");
        }
        result.append(this.getType() != null ? this.getType().toString(true, true) : "void");
        result.append(" ").append(this.getName());
        result.append(getParametersAsString(true));
        return result.toString();
    }
    
    private boolean hasInjectionPoints() {
        for (MethodParameter parameter : this.getParameters()) {
            if (parameter.getInjectionPoint() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param verbose if true, parameters has qualified names and each one is on separate line
     *                <br> otherwise parameters are unqualified and comma-space separated
     * @return "(...params...)"              
     */
    private String getParametersAsString(boolean verbose) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (MethodParameter param : this.parameters) {
            builder.append(param.getType().toString(verbose, true));
            builder.append(",");
            builder.append(verbose ? "\n" : " ");
        }
        if (!this.parameters.isEmpty()) {
            builder.delete(builder.length()-2, builder.length());
        }
        builder.append(")");
        return builder.toString();
    }
    
    @Override
    public String toString() {
        return this.getNodeText();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surroundingType == null) ? 0 : surroundingType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Method other = (Method) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surroundingType == null) {
            if (other.surroundingType != null)
                return false;
        } else if (!surroundingType.equals(other.surroundingType))
            return false;
        return true;
    }
    
    public String getReturnTypeString() {
        if (this.isConstructor()) {
            return "";
        }
        if (this.getType() == null) {
            return "void";
        }
        return this.getType().toString(true, true);
    }

    @Override
    public String getDetailsLinkLabel() {
        return getReturnTypeString() + " " + this.getSurroundingType().getName() 
                + " " + this.getParametersAsString(false);
    }
    
}
