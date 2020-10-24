package cmps312.lab.beneficiaryreview.data.api

import cmps312.lab.beneficiaryreview.model.Beneficiary
import retrofit2.http.*

interface BeneficiaryService {

    @GET("beneficiaries/{cid}")
    suspend fun getBeneficiaries(@Path("cid") cid : Int) : List<Beneficiary>

    @POST("beneficiaries/{cid}")
    suspend fun addBeneficiaries(@Path("cid") cid : Int ,
                                 @Body beneficiary: Beneficiary) : Beneficiary

    @PUT("beneficiaries/{cid}")
    suspend fun updateBeneficiaries(@Path("cid") cid : Int ,
                                    @Body beneficiary: Beneficiary) : String

    @DELETE("beneficiaries/{cid}/{accountNo}")
    suspend fun deleteBeneficiaries(@Path("cid") cid : Int,
                                    @Path("accountNo") accountNo : String) : String
}