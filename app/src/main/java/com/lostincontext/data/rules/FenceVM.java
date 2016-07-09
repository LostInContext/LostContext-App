package com.lostincontext.data.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.android.gms.awareness.fence.AwarenessFence;

/**
 * Representation of an {@link com.google.android.gms.awareness.fence.AwarenessFence}. <p>
 * Allows to manipulate the content of fences and can be used to generate the corresponding
 * {@link com.google.android.gms.awareness.fence.AwarenessFence} via {@link FenceVM#build(FenceBuilder)}.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompositeFenceVM.class, name = "composite"),
        @JsonSubTypes.Type(value = DetectedActivityFenceVM.class, name = "activity"),
        @JsonSubTypes.Type(value = HeadphoneFenceVM.class, name = "headphone"),
        @JsonSubTypes.Type(value = NotFenceVM.class, name = "notRule"),
        @JsonSubTypes.Type(value = LocationFenceVM.class, name = "location"),
        @JsonSubTypes.Type(value = TimeFenceVM.class, name = "time")
})
public interface FenceVM {

    AwarenessFence build(FenceBuilder builder);

    String describe(FenceDescriptor descriptor);
}
