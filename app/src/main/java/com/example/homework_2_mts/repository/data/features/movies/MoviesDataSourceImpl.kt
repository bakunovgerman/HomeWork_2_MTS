package com.example.homework_2_mts.repository.data.features.movies

import com.example.homework_2_mts.repository.database.entities.Movie

class MoviesDataSourceImpl : MoviesDataSource {
	override fun getMovies() = listOf(
		Movie(
			1,
			title = "Гнев человеческий",
			description = "Эйч — загадочный и холодный на вид джентльмен, но внутри него пылает жажда справедливости. Преследуя...",
			rateScore = 3,
			ageRestriction = 18,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5JP9X5tCZ6qz7DYMabLmrQirlWh.jpg"
		),
		Movie(
			2,
			title = "Мортал Комбат",
			description = "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии...",
			rateScore = 5,
			ageRestriction = 18,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pMIixvHwsD5RZxbvgsDSNkpKy0R.jpg"
		),
		Movie(
			3,
			title = "Упс... Приплыли!",
			description = "От Великого потопа зверей спас ковчег. Но спустя полгода скитаний они готовы сбежать с него куда угодно...",
			rateScore = 5,
			ageRestriction = 6,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/546RNYy9Wi5wgboQ7EtD6i0DY5D.jpg"
		),
		Movie(
			4,
			title = "The Box",
			description = "Уличный музыкант знакомится с музыкальным продюсером, и они вдвоём отправляются в путешествие...",
			rateScore = 4,
			ageRestriction = 12,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fq3DSw74fAodrbLiSv0BW1Ya4Ae.jpg"
		),
		Movie(
			5,
			title = "Сага о Дэнни Эрнандесе",
			description = "Tekashi69 или Сикснайн — знаменитый бруклинский рэпер с радужными волосами — прогремел...",
			rateScore = 2,
			ageRestriction = 18,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5xXGQLVtTAExHY92DHD9ewGmKxf.jpg"
		),
		Movie(
			6,
			title = "Пчелка Майя2",
			description = "Когда упрямая пчелка Майя и ее лучший друг Вилли спасают принцессу-муравьишку, начинается сказочное...",
			rateScore = 4,
			ageRestriction = 0,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xltjMeLlxywym14NEizl0metO10.jpg"
		),
		Movie(
			7,
			title = "Круэлла",
			description = "Невероятно одаренная мошенница по имени Эстелла решает сделать себе имя в мире моды.",
			rateScore = 4,
			ageRestriction = 12,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hUfyYGP9Xf6cHF9y44JXJV3NxZM.jpg"
		),
		Movie(
			8,
			title = "Чёрная вдова",
			description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
			rateScore = 3,
			ageRestriction = 16,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mbtN6V6y5kdawvAkzqN4ohi576a.jpg"
		),
		Movie(
			9,
			title = "Экипаж",
			description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
			rateScore = 3,
			ageRestriction = 16,
			imageUrl = "https://www.film.ru/sites/default/files/movies/posters/3563896-816272.jpg"
		),
		Movie(
			10,
			title = "Марсианин",
			description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
			rateScore = 3,
			ageRestriction = 16,
			imageUrl = "https://www.film.ru/sites/default/files/movies/posters/5444118-784641.jpg"
		)
	)
	override fun getMovies2() = listOf(
		Movie(
			4,
			title = "The Box",
			description = "Уличный музыкант знакомится с музыкальным продюсером, и они вдвоём отправляются в путешествие...",
			rateScore = 4,
			ageRestriction = 12,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fq3DSw74fAodrbLiSv0BW1Ya4Ae.jpg"
		),
		Movie(
			5,
			title = "Сага о Дэнни Эрнандесе",
			description = "Tekashi69 или Сикснайн — знаменитый бруклинский рэпер с радужными волосами — прогремел...",
			rateScore = 2,
			ageRestriction = 18,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5xXGQLVtTAExHY92DHD9ewGmKxf.jpg"
		),
		Movie(
			6,
			title = "Пчелка Майя",
			description = "Когда упрямая пчелка Майя и ее лучший друг Вилли спасают принцессу-муравьишку, начинается сказочное...",
			rateScore = 4,
			ageRestriction = 0,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xltjMeLlxywym14NEizl0metO10.jpg"
		),
		Movie(
			7,
			title = "Круэлла",
			description = "Невероятно одаренная мошенница по имени Эстелла решает сделать себе имя в мире моды.",
			rateScore = 4,
			ageRestriction = 12,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hUfyYGP9Xf6cHF9y44JXJV3NxZM.jpg"
		),
		Movie(
			8,
			title = "Чёрная вдова",
			description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
			rateScore = 3,
			ageRestriction = 16,
			imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mbtN6V6y5kdawvAkzqN4ohi576a.jpg"
		)
	)
}