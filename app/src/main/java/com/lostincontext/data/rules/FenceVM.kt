package com.lostincontext.data.rules

import android.support.annotation.DrawableRes

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.google.android.gms.awareness.fence.AwarenessFence

/**
 * Representation of an [com.google.android.gms.awareness.fence.AwarenessFence].
 *
 *
 * Allows to manipulate the content of fences and can be used to generate the corresponding
 * [com.google.android.gms.awareness.fence.AwarenessFence] via [FenceVM.build].
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(JsonSubTypes.Type(value = CompositeFenceVM::class, name = "composite"),
              JsonSubTypes.Type(value = DetectedActivityFenceVM::class, name = "activity"),
              JsonSubTypes.Type(value = HeadphoneFenceVM::class, name = "headphone"),
              JsonSubTypes.Type(value = NotFenceVM::class, name = "notRule"),
              JsonSubTypes.Type(value = LocationFenceVM::class, name = "location"),
              JsonSubTypes.Type(value = TimeFenceVM::class, name = "time"))
interface FenceVM {

    fun build(builder: FenceBuilder): AwarenessFence

    fun describe(descriptor: FenceDescriptor): String

    fun giveIcon(iconGiver: FenceIconGiver,
                 icons: MutableList<Int>)
}
