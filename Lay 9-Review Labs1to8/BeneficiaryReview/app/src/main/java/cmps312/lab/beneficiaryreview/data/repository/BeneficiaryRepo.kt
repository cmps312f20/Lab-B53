package cmps312.lab.beneficiaryreview.data.repository

import cmps312.lab.beneficiaryreview.data.api.BeneficiaryService
import cmps312.lab.beneficiaryreview.model.Beneficiary
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object BeneficiaryRepo {
    //you will create the retrofit
    val cid = 10001
    private const val BASE_URL  =  "https://cmps312banking.herokuapp.com/api/"

    //the server will send us json -> kotlin object
    //we will send the server kotlin -> json object
    private val contentType = "application/json".toMediaType()
    private val convertorFactory = Json {ignoreUnknownKeys = true}.asConverterFactory(contentType)

    val beneficiaryService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(convertorFactory)
            .build()
            .create(BeneficiaryService::class.java)
    }
    suspend fun getBeneficiaries() = beneficiaryService.getBeneficiaries(cid)
    suspend fun addBeneficiaries(beneficiary : Beneficiary) = beneficiaryService.addBeneficiaries(cid, beneficiary)
    suspend fun deleteBeneficiaries(beneficiary : Beneficiary) = beneficiaryService.deleteBeneficiaries(cid, beneficiary.accountNo)
}