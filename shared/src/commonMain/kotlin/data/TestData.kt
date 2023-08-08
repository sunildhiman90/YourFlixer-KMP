package data

import kotlin.random.Random

//import java.util.UUID

object TestData {

    val testProfileUrl =
        "https://stat4.bollywoodhungama.in/wp-content/uploads/2023/01/The-Crew-2.jpg"
    val testProfileUrl2 =
        "https://m.media-amazon.com/images/M/MV5BYjBiOTYxZWItMzdiZi00NjlkLWIzZTYtYmFhZjhiMTljOTdkXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"
    val testImageUrl =
        "https://m.media-amazon.com/images/M/MV5BMTg1MTY2MjYzNV5BMl5BanBnXkFtZTgwMTc4NTMwNDI@._V1_SX300.jpg"

    val imagesList = listOf(
        // "https://m.media-amazon.com/images/M/MV5BOTJlZWMxYzEtMjlkMS00ODE0LThlM2ItMDI3NGQ2YjhmMzkxXkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_SX300.jpg",
        // "https://m.media-amazon.com/images/M/MV5BOTQ5NDI3MTI4MF5BMl5BanBnXkFtZTgwNDQ4ODE5MDE@._V1_SX300.jpg",
        "https://stat5.bollywoodhungama.in/wp-content/uploads/2021/10/Gadar-2.jpg",
        "https://stat4.bollywoodhungama.in/wp-content/uploads/2023/01/Pathaan-25.jpg",
        //"https://stat4.bollywoodhungama.in/wp-content/uploads/2023/01/Sakshi-Malik-2-1.jpg",
        //"https://stat4.bollywoodhungama.in/wp-content/uploads/2023/02/Selfiee-17.jpg",
        "https://stat4.bollywoodhungama.in/wp-content/uploads/2023/01/The-Crew-2.jpg",
        "https://media5.bollywoodhungama.in/wp-content/uploads/2023/02/Disha-Patani-1.jpg",
        //"https://static.india.com/wp-content/uploads/2022/11/Disha-Patani-Maximises-Hotness-in-New-Lingerie-Photoshoot-Fans-Cant-Believe-Her-Incredible-Waistline-See-Pic-369x246.jpg?impolicy=Medium_Widthonly&w=340",
        "https://m.media-amazon.com/images/M/MV5BNGUxYWM3M2MtMGM3Mi00ZmRiLWE0NGQtZjE5ODI2OTJhNTU0XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
        "https://m.media-amazon.com/images/M/MV5BMGEyNzhkYzktMGMyZS00YzRiLWJlYjktZjJkOTU5ZDY0ZGI4XkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg"
    )

    val size = imagesList.size - 1
    val feedList1 = List(15) {
        val random = (0..size).random()
        FeedItem(
            id = Random.nextInt(100, 100000).toString(),
            url = imagesList[random],
            title = "Title $it"
        )
    }

    val searchItemsList = List(100) {
        val random = (0..size).random()
        FeedItem(
            id = Random.nextInt(100, 100000).toString(),
            url = imagesList[random],
            title = "Title $it"
        )
    }

    val downloadItemsList = List(5) {

        DownloadedItem(
            id = Random.nextInt(100, 100000).toString(),
            title = "Test",
            thumbnailUrl = "",
            size = 121.0,
            addedOn = ""
        )
    }

}
