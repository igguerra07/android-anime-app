package br.com.igguerra.animeapp.model

import com.google.gson.annotations.SerializedName

data class Related(
    val Adaptation: List<Adaptation>,
    @SerializedName("Alternative version")
    val alternativeVersion: List<AlternativeVersion>,
    @SerializedName("Side story")
    val sideStory: List<SideStory>,
    @SerializedName("Spin-off")
    val spinOff: List<SpinOff>
)