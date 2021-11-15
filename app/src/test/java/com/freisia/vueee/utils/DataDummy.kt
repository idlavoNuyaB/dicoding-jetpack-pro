package com.freisia.vueee.utils

import com.freisia.vueee.model.all.Genres
import com.freisia.vueee.model.movie.Countries
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.movie.Releases
import com.freisia.vueee.model.tv.TV

object DataDummy {

    fun getMovies() : Movie {
        val genresMovies = ArrayList<Genres>()
        val genreMovie= Genres(16,"Animation")
        genresMovies.add(genreMovie)
        val countriesMovies = ArrayList<Countries>()
        val countryMovie = Countries("","CA",false,
            "2020-08-14")
        countriesMovies.add(countryMovie)
        val releases = Releases(countriesMovies)
        return Movie(genresMovies,400160,"When his best friend Gary is " +
                "suddenly snatched away, SpongeBob takes Patrick on a madcap mission far beyond" +
                " Bikini Bottom to save their pink-shelled pal.",
            "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg","2020-08-14",95,
            "The SpongeBob Movie: Sponge on the Run",8.0,1548,releases)
    }

    fun getTV(): TV{
        val episodeTV = ArrayList<Int>()
        val genresTV = ArrayList<Genres>()
        val genreTV = Genres(10765,"Sci-Fi & Fantasy")
        episodeTV.add(35)
        genresTV.add(genreTV)
        return TV(episodeTV,"2019-11-12",genresTV,82856,"The Mandalorian",
            "A lone gunfighter makes his way through the outer reaches of the galaxy, far " +
                    "from the authority of the New Republic.",
            "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",8.5,2943)
    }
}