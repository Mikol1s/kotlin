package _3_zaawansowane

import models.character_model.Character
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RickAndMortyApi{

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: String): Call<Character>

    @GET("character")
    fun getAllCharacters()
}

object RickiAndMortyService{

    private val service = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApi() = service.create(RickAndMortyApi::class.java)

}


fun main() {

    val api = RickiAndMortyService.getApi()
    val response = api.getCharacter("3").execute()
    println(response.body()?.name)


}
