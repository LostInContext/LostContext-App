package com.lostincontext.model.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompositeRuleDescription.class, name = "composite"),
        @JsonSubTypes.Type(value = DetectedActivityRuleDescription.class, name = "activity"),
        @JsonSubTypes.Type(value = HeadPhoneRuleDescription.class, name = "headphone"),
        @JsonSubTypes.Type(value = NotRuleDescription.class, name = "notRule"),
        @JsonSubTypes.Type(value = LocationRuleDescription.class, name = "location"),
        @JsonSubTypes.Type(value = TimeRuleDescription.class, name = "time")
})


public abstract class RuleDescription {
    private String name;

    public RuleDescription() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Rule visit(RuleBuilder builder);

}
