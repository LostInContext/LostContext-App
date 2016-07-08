package com.lostincontext.data.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public interface RuleDescription {

    Rule visit(RuleBuilder builder);

}
