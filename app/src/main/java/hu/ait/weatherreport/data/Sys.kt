package hu.ait.weatherreport.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    @SerialName("country")
    val country: String? = null,
    @SerialName("sunrise")
    val sunrise: Int? = null,
    @SerialName("sunset")
    val sunset: Int? = null
)